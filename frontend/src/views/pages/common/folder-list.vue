<template>
    <div>
        <h2 class="page-title">{{ title }}</h2>
        <div class="upload-summury">
            <strong class="title">파일올리기</strong>
            <p class="desc">
                자료는 NIKE.INC.와 NIKE KOREA LLC.의 자산입니다.<br />
                보안 규정을 준수하시기 바랍니다.
            </p>
            <router-link :to="uploadLink" class="btn-s-black">
                UPLOAD
            </router-link>
        </div>
        <div class="sorting-area">
            <ListSorting :listTypes="listTypes" />
            <FilterSelect :listSortSelect="listSortSelect" />
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="folderListData">
            <FolderList
                v-if="folderListData.length"
                :listTypes="listTypes"
                :folderListData="folderListData"
            />
            <template v-else>
                <NoData
                    :style="{ height: '500px' }"
                    v-if="searchKeyword === ''"
                >
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
                <NoData v-else :style="{ height: '500px' }">
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
import ListSorting from '@/components/list-sorting/index';
import SearchInput from '@/components/search-input';
import FolderList from '@/components/folder-list';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';

import { getContents } from '@/api/contents.js';

export default {
    name: 'folder-list',
    watch: {
        'listSortSelect.value'(val) {
            if (val === '') {
                this.listSortSelect.value = 'LATEST';
            }
            this.initFetchData();
        },
    },
    computed: {
        uploadLink() {
            return this.$route.meta.topMenuCode
                ? `/${this.$route.meta.topMenuCode.toLowerCase()}/upload`
                : '/';
        },
    },
    data() {
        return {
            title: this.$route.meta.title,
            itemLength: 20,
            totalPage: null,
            page: 0,
            loadingData: false,
            searchKeyword: '',
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
                    active: true,
                },
                {
                    title: '로우타입',
                    active: false,
                },
            ],
            folderListData: null,
        };
    },
    components: {
        ListSorting,
        FilterSelect,
        FolderList,
        NoData,
        SearchInput,
        Loading,
    },
    methods: {
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
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.folderListData = null;
            this.fetchData();
        },
        searchSubmit(val) {
            this.searchKeyword = val;
            this.initFetchData();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.folderListData.length >= this.itemLength &&
                this.folderListData.length !== 0
            ) {
                this.fetchData(true);
            }
        },
        endPage() {
            alert('마지막 페이지');
        },
        async fetchData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getContents(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    {
                        page: this.page,
                        size: this.itemLength,
                        keyword: this.searchKeyword,
                        orderType: this.listSortSelect.value,
                    }
                );
                this.totalPage = response.totalPages - 1;
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
    },
    created() {
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        if (this.$store.state.reload) {
            this.initFetchData();
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
};
</script>
<style scoped>
.list-loading {
    position: relative;
    padding-top: 70%;
}
::v-deep .list-loading .lottie {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
