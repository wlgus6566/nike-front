<template>
    <div>
        <h2 class="page-title" v-html="this.$route.meta.title" />
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
        <Loading v-if="loadingData" />
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
import { getAuthCacheList } from '@/api/auth';

export default {
    name: 'management',
    data() {
        return {
            reportListData: null,
            page: 0,
            itemLength: 20,
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
        this.getReport();
        this.authCacheList();
    },
    activated() {
        this.getReport();
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
            this.getReport();
        },
        'authority.value'() {
            this.getReport();
        },
    },

    methods: {
        //권한 조회
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getGroupAuthority();
                this.userDataList = response;
                this.recursionFn(response, this.authority.options, 1);
                this.recursionFn(response, this.addAuthority.options, 1);
            } catch (error) {
                console.log(error);
            }
        },

        recursionFn(data, item, minIndx) {
            let _minIndx = minIndx;
            if (minIndx === undefined) {
                _minIndx = 0;
            }
            data.forEach((el, index) => {
                if (el.checkBoxYn) {
                    item.push({
                        value: el.authSeq,
                        label: el.authName,
                    });
                }
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
            this.getReport();
        },
        async getReport() {
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
                console.log(response);
                this.reportListData = response.content;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
