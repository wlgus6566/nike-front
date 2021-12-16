<template>
    <div>
        <div class="sorting-area">
            <button
                type="button"
                :class="[viewType ? 'type-list' : 'type-list-thum']"
                @click="viewTypeToggle()"
            >
                <span>컬럼타입</span>
            </button>
            <FilterSelect :selectList="selectList"></FilterSelect>
            <CascaderSelect :cascaderList="authority"></CascaderSelect>
            <form action="#" @submit.prevent="search">
                <div class="search-input" :class="{ active: searchIsActive }">
                    <!-- active 추가하면 검색 화면 보임 -->
                    <div class="input-box">
                        <input
                            type="search"
                            placeholder="검색어를 입력해주세요."
                            @keyup.enter="search()"
                            v-model="keyword"
                        />
                        <button
                            type="button"
                            class="btn-del"
                            v-if="keyword"
                            @click="keywordDel"
                        >
                            삭제
                        </button>
                        <button type="submit" class="search">
                            <span>검색</span>
                        </button>
                    </div>
                    <button type="button" class="btn-txt" @click="cancelSearch">
                        <span>취소</span>
                    </button>
                </div>
            </form>
        </div>
        <template v-if="reportList">
            <ul :class="viewTypeClass" v-if="reportList.length">
                <li
                    class="folder-list-item"
                    v-for="item in reportList"
                    :key="item.reportSeq"
                >
                    <router-link :to="setUrl(item)">
                        <div class="thumbnail">
                            <!-- <span class="auth" v-if="item.detailAuthYn === 'N'">
                                <i></i>권한 없음
                            </span>-->
                            <img :src="item.imageFilePhysicalName" alt="" />
                        </div>
                        <div class="info-box">
                            <strong class="title" v-text="item.nickname"
                                >NIKE GANGNAM</strong
                            >
                            <p class="txt" v-text="item.reportName">
                                FA2ss0 RN NIKE DIRECT 시공 보고서 자료
                            </p>
                            <p class="date">
<!--                                {{
                                    $moment(item.updateDt).format(
                                        'YYYY.MM.DD'
                                    )
                                }}-->
                              {{ item.updateDt }}
                            </p>
                        </div>
                        <div class="view-area">
                            <span class="view" v-text="item.readCount">
                                10,000
                            </span>
                        </div>
                    </router-link>
                </li>
            </ul>
            <template v-else>
                <NoData v-if="keyword === ''">
                    <i class="icon-upload"></i>
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
import FilterSelect from '@/components/filter-select';
import CascaderSelect from '@/components/cascader-select';
import NoData from '@/components/no-data';
import { getGroupAuthority, getReportList } from '@/api/report';
import { getCategoryList } from '@/utils/code';

export default {
    name: 'management',
    data() {
        return {
            reset: false,
            loadingData: false,
            page: 0,
            size: 10,
            pageLast: false,
            totalPage: 0,
            keyword: '',
            sectionCode: '',
            groupSeq: '',
            reportList: null,
            searchIsActive: false,
            viewType: true,
            viewTypeClass: 'folder-list-row',
            userDataList: '',
            selectList: {
                value: 'ALL',
                listSortOptions: [
                    {
                        label: 'ALL',
                        value: 'ALL',
                    },
                ],
            },
            authority: {
                value: null,
                name: 'authority',
                options: [
                    {
                        value: null,
                        label: '전체그룹',
                    },
                ],
            },
        };
    },
    components: {
        FilterSelect,
        CascaderSelect,
        NoData,
        Loading: () => import('@/components/loading/'),
    },
    created() {
        this.initRepoerProduct();
        this.authCacheList();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    mounted() {
        getCategoryList('REPORT_SECTION_CODE', this.selectList.listSortOptions);
    },
    watch: {
        'selectList.value'(val) {
            if (val === '') {
                this.selectList.value = 'ALL';
            }
            this.initRepoerProduct();
        },
        'authority.value'() {
            this.initRepoerProduct();
        },
    },
    methods: {
        setUrl(item) {
            /* if (item.detailAuthYn === 'N') {
                return `${this.$route.fullPath}`;
            } else {
                return `/report/detail/${item.reportSeq}`;
            }*/
            return `/report/detail/${item.reportSeq}`;
        },
        /*alertMsg(item) {
            if (item.detailAuthYn === 'N') {
                alert('접근 권한이 없습니다.');
            }
        },*/
        keywordDel() {
            this.keyword = null;
            if (this.reset) {
                this.initRepoerProduct();
                this.reset = false;
            }
        },
        initRepoerProduct() {
            this.totalPage = null;
            this.page = 0;
            this.reportList = null;
            this.fetchData();
        },
        //권한 조회
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getGroupAuthority();
                this.userDataList = response;
                console.log(response);
                this.recursionFn(response, this.authority.options, 1);
                //this.recursionFn(response, this.addAuthority.options, 1);
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
                const _boolean = el.checkBoxYn === 'N';
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
        // 기본 데이터 조회
        async fetchData(paging) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getReportList({
                    page: this.page,
                    size: this.size,
                    keyword: this.keyword,
                    sectionCode: this.selectList.value,
                    groupSeq: this.authority.value,
                });
                if (paging) {
                    this.reportList = this.reportList.concat(response.content);
                } else {
                    this.reportList = response.content;
                }
                this.pageLast = response.last;
                this.totalPage = response.totalPages;
                this.loadingData = false;


            } catch (error) {
                console.log(error);
            }
        },
        // 검색 취소
        cancelSearch() {
            this.searchIsActive = false;
            this.keyword = null;
            this.initRepoerProduct();
        },
        viewTypeToggle() {
            if (this.viewType) {
                this.viewType = false;
                this.viewTypeClass = 'folder-list';
            } else {
                this.viewType = true;
                this.viewTypeClass = 'folder-list-row';
            }
        },
        search() {
            this.searchIsActive = true;
            if (this.keyword) {
                this.reset = true;
            }
            if (!!this.keyword) {
                this.page = 0;
                this.reportList = null;
                this.initRepoerProduct();
            }
        },
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.offsetHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.reportList.length >= this.size &&
                this.reportList.length !== 0 &&
                !this.pageLast
            ) {
                this.page++;
                this.fetchData(true);
            }
        },
    },
};
</script>
<style scoped></style>
