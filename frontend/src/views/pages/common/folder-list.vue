<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <ListSorting v-bind:listTypes="listTypes"></ListSorting>
        <Item v-bind:listTypes="listTypes" v-bind:items="postItems"></Item>
    </div>
</template>
<script>
import ListSorting from '@/components/asset-list/list-sorting.vue';
import Item from '@/components/asset-list/Item.vue';
import { fetchPosts, deletePostById } from '@/api/contents.js';

export default {
    name: 'folder-list',
    mounted() {},
    data() {
        return {
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
            postItems: [
                {
                    title: '타이틀',
                    img: '@/assets/images/img-asset-none@2x.png"',
                    desc: '설명',
                    date: '2020.00.00 ~ 2020.00.00',
                },
                {
                    title: '타이틀2',
                    img: '@/assets/images/img-asset-none@2x.png"',
                    desc: '설명2',
                    date: '2020.00.00 ~ 2020.00.00',
                },
            ],
        };
    },
    components: {
        ListSorting,
        Item,
    },
    methods: {
        async fetchData() {
            try {
                const {
                    data: { posts: postItems },
                } = await fetchPosts();
                console.log(postItems);
                this.postItems = postItems;
                return;
            } catch (error) {
                console.log(error);
            }
        },
        editPost(id) {
            this.$router.push(`/post/${id}`);
        },
        async removePost(id) {
            try {
                if (confirm('Delete it?')) {
                    const response = await deletePostById(id);
                    await this.fetchData();
                    bus.$emit('show:toast', `${response.data.title} was deleted`);
                }
            } catch (error) {
                console.log(error);
            }
        },
    },
    created() {
        this.fetchData();
    },
};
</script>
<style scoped></style>
