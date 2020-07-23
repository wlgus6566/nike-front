<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <div class="sorting-area">
            <SearchInput @:searchSubmit="searchSubmit" />
        </div>
        <newsList />
        <Pagination>
            <template slot="btn-right-sign">
                <div class="btn-tbl-box">
                    <div class="right">
                        <button type="button" class="btn-form-gray">
                            <span data-v-1756ba49="">등록</span>
                        </button>
                    </div>
                </div>
            </template>
        </Pagination>
    </div>
</template>

<script>
import newsList from '@/components/news/list';
import SearchInput from '@/components/search-input/index';
import Pagination from '@/components/pagination/index';

//import {  } from '@/api/.js';

export default {
    name: 'news-list',
    data() {
        return {
            btn: true,
        };
    },
    components: {
        Pagination,
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
