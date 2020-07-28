<template>
    <div>
        <h2 class="page-title" v-if="$route.meta.title == '공지사항'">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <h2 class="page-title" v-else>
            {{ this.$route.meta.title }}
        </h2>
        <Edit> </Edit>
    </div>
</template>

<script>
import Edit from '@/components/news/edit.vue';

//import {  } from '@/api/.js';

export default {
    name: 'edit',
    data() {
        return {
            num: 10,
        };
    },
    components: {
        Edit,
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
