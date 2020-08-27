<template>
    <div>
        <div class="sorting-area">
<!--            <ListSorting :listTypes="listTypes" />-->
            <button
                    type="button"
                    :class="[viewType ? 'type-list' : 'type-list-thum']"
                    @click="viewTypeToggle()"
            >
                <span>컬럼타입</span>
            </button>
            <FilterSelect :selectList="listSortSelect"></FilterSelect>
            <div class="search-input" :class="{ active: isActive }">
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." v-model="searchKeyword"/>
                    <button type="button" class="search" @click="onClickSearch"><span>검색</span></button>
                </div>
                <button type="button" class="btn-txt" @click="cancelSearch"><span>취소</span></button>
            </div>
        </div>
        <template v-if="folderListData">
            <ul :class="viewTypeClass" v-if="folderListData.length">
                <FolderList
                    v-if="folderListData.length"
                    :listTypes="listTypes"
                    :folderListData="folderListData"
                />
            </ul>
            <template v-else>
                <NoData>
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading v-if="loadingData" />
    </div>
</template>
<script>

import { getContents } from '@/api/contents';

import ListSorting from '@/components/list-sorting/index';
import FilterSelect from '@/components/filter-select';
import FolderList from '@/components/folder-list';
import NoData from '@/components/no-data';

export default {
    name: 'folder-list',
    components: {
        FilterSelect,
        FolderList,
        NoData,
        Loading: () => import('@/components/loading/'),
        ListSorting,
    },
    data() {
        return {
            isActive: false,
            folderListData: [],
            searchKeyword: null,
            page: 0,
            pageSize: 5,
            totalPage: null,
            typeCd: 'ALL',
            viewType: true,
            viewTypeClass: 'folder-list-row',
            isLastPage: true,
            loadingData: false,
            // orderType: 'LATEST',
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
            this.initFetchData();
        },
    },
    created() {
        this.initFetchData();
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
                this.isLastPage = response.last;
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        // 검색
        onClickSearch() {
            console.log('onClickSearch')
            this.isActive = true;
            if (!!this.searchKeyword) {
                this.initFetchData();
            }
        },
        // 검색 취소
        cancelSearch() {
            this.isActive = false;
            this.searchKeyword = null;
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
        /**
         * 스크롤 관련 method
         */
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop+1 >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            console.log('aaaaa', !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.folderListData.length >= this.itemLength &&
                this.folderListData.length !== 0 &&
                !this.isLastPage)
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.folderListData.length >= this.itemLength &&
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
