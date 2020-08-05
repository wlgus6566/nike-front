<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <div class="sorting-area">
            <SearchInput @:searchSubmit="searchSubmit" />
        </div>
        <newsList />
    </div>
</template>

<script>
import newsList from '@/components/news/news-list';
import SearchInput from '@/components/search-input/index';

//import {  } from '@/api/.js';

export default {
    name: 'news-list',
    data() {
        return {};
    },
    components: {
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
