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
            <div class="search-input" :class="{ active: searchIsActive }">
                <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input
                        type="text"
                        placeholder="검색어를 입력해주세요."
                        @keyup.enter="search()"
                        v-model="keyword"
                    />
                    <button type="button" class="search" @click="searchView()">
                        <span>검색</span>
                    </button>
                </div>
                <button type="button" class="btn-txt" @click="searchView()">
                    <span>취소</span>
                </button>
            </div>
        </div>
        <ul :class="viewTypeClass">
            <li
                class="folder-list-item"
                v-for="item in reportList"
                :key="item.reportSeq"
            >
                <router-link :to="`/report/${item.reportSeq}`">
                    <div class="thumbnail">
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
                            {{
                                $moment(item.registrationDt).format(
                                    'YYYY.MM.DD'
                                )
                            }}
                        </p>
                    </div>
                    <div class="view-area">
                        <span class="view" v-text="item.readCount">10,000</span>
                    </div>
                </router-link>
            </li>
        </ul>
        <Loading v-if="loadingData" />
    </div>
</template>
<script>
import FilterSelect from '@/components/filter-select';
import CascaderSelect from '@/components/cascader-select';
import {getReportList} from '@/api/report';
import {getCategoryList} from '@/utils/code';
import {getAuthCacheList} from '@/api/auth';

export default {
    name: 'management',
    data() {
        return {
            loadingData: false,
            page: 0,
            size: 10,
            pageLast : false,
            totalPage : 0,
            keyword: '',
            sectionCode: '',
            groupSeq: '',
            reportList: [],
            searchIsActive: false,
            viewType: true,
            viewTypeClass : 'folder-list-row',
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
                value: ['all'],
                name: 'authority',
                options: [
                    {
                        value: 'all',
                        label: '전체 그룹',
                    },
                ],
            },
        };
    },
    components: {
        FilterSelect,
        CascaderSelect,
        Loading: () => import('@/components/loading/')
    },
    created() {
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
        this.fetchData();
        getCategoryList('REPORT_SECTION_CODE', this.selectList.listSortOptions);
    },
    watch: {
        'selectList.value'(val) {
            if (val === '') {
                this.selectList.value = 'ALL';
            }
            this.fetchData();
        },
        'authority.value'() {
            this.fetchData();
        },
    },
    methods: {
        //권한 조회 (리포트 권한 목록 수정되면 교체 되어야함)
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthCacheList();

                this.recursionFn(response, this.authority.options, 1);
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
                item.push({
                    value: el.authSeq,
                    label: el.authName,
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
                    //groupSeq: this.authority.value,
                });
                if(paging){
                    this.reportList = this.reportList.concat(response.content);
                }else{
                    this.reportList = response.content;
                }
                console.log(response);
                this.pageLast = response.last;
                this.totalPage = response.totalPages;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
                this.loadingData = false;
            }
        },
        searchView() {
            if (this.searchIsActive) {
                this.searchIsActive = false;
            } else {
                this.searchIsActive = true;
            }
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
            this.fetchData();
        },
        handleScroll() {

            if(this.loadingData) return;
            const windowE = document.documentElement;
            if(
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        }, infiniteScroll() {
            if(
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.reportList.length >= this.size &&
                this.reportList.length !== 0 &&
                !this.pageLast
            ) {
                this.page++;
                this.fetchData(true);
            }
        }
    },
};
</script>
<style scoped></style>
