<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <div class="sorting-area">
            <SearchInput @:searchSubmit="searchSubmit" />
        </div>
        <newsList />

        <bottomArea>
            <template slot="btn-bottom-area">
                <hr class="hr-gray" />
                <div class="btn-tbl-box">
                    <div class="right">
                        <router-link to="/mypage/edit/news">
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
import newsList from '@/components/news/news-list';
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
        newsList,
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
