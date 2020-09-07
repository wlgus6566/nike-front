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
            <FilterSelect :selectList="listSortSelect"></FilterSelect>
            <form action="#" @submit.prevent="onClickSearch">
                <div class="search-input" :class="{ active: isActive }">
                    <div class="input-box">
                        <input
                            type="search"
                            placeholder="검색어를 입력해주세요."
                            @keyup.enter="onClickSearch"
                            v-model="searchKeyword"
                        />
                        <button type="button" class="btn-del" v-if="searchKeyword" @click="keywordDel">삭제</button>
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
        <template v-if="folderListData">
            <ul :class="viewTypeClass" v-if="folderListData.length">
                <li
                    class="folder-list-item"
                    v-for="(item, index) in folderListData"
                    :key="index"
                >
                    <router-link :to="setUrl(item)" @click.native="alertMsg(item)">
                        <div class="thumbnail">
                            <span class="exposure" v-if="item.exposureYn === 'N'">
                                <i></i>작성중
                            </span>
                            <span
                                class="auth"
                                v-if="item.exposureYn === 'Y' && item.detailAuthYn === 'N'">
                                <i></i>권한 없음
                            </span>
                            <img :src="item.imageFilePhysicalName" alt="" />
                        </div>
                        <div class="info-box">
                            <strong class="title">{{ item.folderName }}</strong>
                            <p class="txt">{{ item.folderContents }}</p>
                            <p
                                v-if="
                                    item.campaignPeriodSectionCode === 'EVERY'
                                "
                                class="date"
                            >
                                365
                            </p>
                            <p v-else class="date">
                                {{
                                    $moment(item.campaignBeginDt).format(
                                        'YYYY.MM.DD'
                                    )
                                }}
                                ~
                                {{
                                    $moment(item.campaignEndDt).format(
                                        'YYYY.MM.DD'
                                    )
                                }}
                            </p>
                        </div>
                        <div class="view-area">
                            <span class="view">{{ item.readCount }}</span>
                        </div>
                    </router-link>
                </li>
            </ul>
            <template v-else>
                <NoData>
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
import { getContents } from '@/api/contents';

import ListSorting from '@/components/list-sorting/index';
import FilterSelect from '@/components/filter-select';
import NoData from '@/components/no-data';

export default {
    name: 'folder-list',
    components: {
        FilterSelect,
        NoData,
        Loading: () => import('@/components/loading/'),
        ListSorting,
    },
    data() {
        return {
            reset : false,
            isActive: false,
            folderListData: null,
            searchKeyword: null,
            page: 0,
            pageSize: 5,
            totalPage: null,
            typeCd: 'ALL',
            viewType: true,
            viewTypeClass: 'folder-list-row',
            isLastPage: true,
            loadingData: false,
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'LATEST',
                        label: '최신순',
                    },
                    {
                        value: 'START_DATE',
                        label: '시작일순',
                    },
                ],
                value: 'LATEST',
            },
            listTypes: [
                {
                    title: '컬럼타입',
                    active: false,
                },
                {
                    title: '로우타입',
                    active: true,
                },
            ],
        };
    },
    watch: {
        'listSortSelect.value'(val) {
            if (val === '') {
                this.listSortSelect.value = 'LATEST';
            }
            this.initPageData();
        },
        // 라우터 변경 감지
        $route(to, from) {
            this.initPageData();
        },
    },
    created() {
        this.initPageData();
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
    methods: {
        keywordDel(){
          this.searchKeyword = null;
          if(this.reset){
            this.initPageData();
            this.reset = false
          }
        },
        alertMsg(item) {
            if (item.detailAuthYn === 'N') {
                alert('접근 권한이 없습니다.');
            }
        },
        initPageData() {
            this.totalPage = null;
            this.page = 0;
            this.folderListData = null;
            this.initFetchData();
        },
        // 초기 데이타 조회
        async initFetchData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getContents(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    {
                        page: this.page,
                        size: this.pageSize,
                        keyword: this.searchKeyword,
                        orderType: this.listSortSelect.value,
                    }
                );
                this.isLastPage = response.last;
                this.totalPage = response.totalPages;

                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.folderListData = this.folderListData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.folderListData = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        // 검색
        onClickSearch() {
            this.isActive = true;
            if(this.searchKeyword){
              this.reset = true;
            }
            if (!!this.searchKeyword) {
                this.page = 0;
                this.folderListData = null;
                this.initPageData();
            }
        },
        // 검색 취소
        cancelSearch() {
            this.isActive = false;
            this.searchKeyword = null;
            this.initPageData();
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
        setUrl(item) {
            if (item.detailAuthYn === 'N') {
                return `${this.$route.fullPath}`;
            } else {
                return `/${item.topMenuCode}/${item.menuCode}/${item.contentsSeq}`.toLocaleLowerCase();
            }
        },
        /**
         * 스크롤 관련 method
         */
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
                this.folderListData.length >= this.pageSize &&
                this.folderListData.length !== 0 &&
                !this.isLastPage
            ) {
                this.initFetchData(true);
            }
        },
    },
};
</script>
<style scoped></style>
