<template>
    <div>
        <h2 class="page-title" v-html="title"></h2>
        <div class="sorting-area">
            <ListSorting :listTypes="listTypes" />
            <FilterSelect :listSortSelect="listSortSelect" />
            <CascaderSelect :listCascader="authority" />
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="reportListData">
            <ReportList
                v-if="reportListData.length"
                :listTypes="listTypes"
                :reportListData="reportListData"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
    </div>
</template>
<script>
import ReportList from '@/components/report-list';
import FilterSelect from '@/components/filter-select';
import CascaderSelect from '@/components/cascader-select';
import ListSorting from '@/components/list-sorting';
import SearchInput from '@/components/search-input';
import NoData from '@/components/no-data';
import Loading from '@/components/loading';
import { getReportList, getGroupAuthority } from '@/api/report';
import { getCategoryList } from '@/utils/code';

export default {
    name: 'management',
    data() {
        return {
            title: this.$route.meta.title,
            reportListData: null,
            page: 0,
            itemLength: 20,
            totalPage: 0,
            listTypes: [
                {
                    title: '컬럼타입',
                    active: true,
                },
                {
                    title: '로우타입',
                    active: false,
                },
            ],
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'ALL',
                        label: 'ALL',
                    },
                ],
                value: 'ALL',
            },
            authority: {
                value: [null],
                options: [
                    {
                        value: null,
                        label: '전체 그룹',
                    },
                ],
            },
            loadingData: false,
            searchKeyword: '',
            userDataList: '',
        };
    },
    components: {
        ReportList,
        FilterSelect,
        CascaderSelect,
        ListSorting,
        SearchInput,
        NoData,
        Loading,
    },
    created() {
        this.authCacheList();
        this.initReportProduct();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        if (this.$store.state.reload) {
            this.initReportProduct();
            this.authCacheList();
            this.$store.commit('SET_RELOAD', false);
        }
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },

    mounted() {
        getCategoryList(
            'REPORT_SECTION_CODE',
            this.listSortSelect.listSortOptions
        );
    },
    watch: {
        'listSortSelect.value'(val) {
            if (val === '') {
                this.listSortSelect.value = 'ALL';
            }
            this.initReportProduct();
        },
        'authority.value'(val) {
            if (val.length === 0) {
                this.authority.value = [null];
            }
            this.initReportProduct();
        },
    },

    methods: {
        initReportProduct() {
            this.totalPage = null;
            this.page = 0;
            this.reportListData = null;
            this.getReport();
        },

        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.reportListData.length >= this.itemLength &&
                this.reportListData.length !== 0
            ) {
                this.getReport(true);
            }
        },

        //권한 조회
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getGroupAuthority();
                this.userDataList = response;
                this.recursionFn(response, this.authority.options, 1);
            } catch (error) {
                console.error(error);
            }
        },

        recursionFn(data, item, minIndx) {
            let _minIndx = minIndx;
            if (minIndx === undefined) {
                _minIndx = 0;
            }
            data.forEach((el, index) => {
                const _boolean = el.checkBoxYn === 'Y';
                item.push({
                    value: el.authSeq,
                    label: el.authName,
                    disabled: _boolean,
                });
                if (el.subAuths) {
                    item[index + _minIndx].children = [];
                    this.recursionFn(
                        el.subAuths,
                        item[index + _minIndx].children
                    );
                }
            });
        },
        searchSubmit(val) {
            this.searchKeyword = val;
            this.initReportProduct();
        },
        async getReport(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getReportList({
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.searchKeyword,
                    sectionCode: this.listSortSelect.value,
                    groupSeq: this.authority.value.slice(-1)[0],
                });
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.reportListData = this.reportListData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.reportListData = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
        endPage() {
            alert('마지막 페이지 입니다.');
        },
    },
};
</script>
<style scoped></style>
