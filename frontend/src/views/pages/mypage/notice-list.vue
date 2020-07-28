<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <div class="sorting-area">
            <SearchInput @:searchSubmit="searchSubmit" />
        </div>
        <NoticeList />
        <bottomArea>
            <template slot="btn-bottom-area">
                <div class="btn-tbl-box">
                    <button type="button" class="btn-form">
                        <span data-v-1756ba49="">삭제</span>
                    </button>
                    <div class="right">
                        <router-link to="/mypage/edit/notice">
                            <button type="button" class="btn-form-gray">
                                <span data-v-1756ba49="">등록</span>
                            </button>
                        </router-link>
                    </div>
                </div>
            </template>
        </bottomArea>
    </div>
</template>

<script>
import NoticeList from '@/components/news/notice-list';
import SearchInput from '@/components/search-input/index';
import bottomArea from '@/components/bottom-area/pagination';

//import {  } from '@/api/.js';

export default {
    name: 'news-list',
    data() {
        return {};
    },
    components: {
        bottomArea,
        NoticeList,
        SearchInput,
    },
    methods: {
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
    },
    async fetchData() {
        try {
        } catch (error) {
            console.log(error);
        }
    },
};
</script>
<style scoped></style>
