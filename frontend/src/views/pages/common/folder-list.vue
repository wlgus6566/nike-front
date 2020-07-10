<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <ListSorting v-bind:listTypes="listTypes"></ListSorting>
        <assetItem v-bind:listTypes="listTypes" v-bind:items="folderItems"></assetItem>
    </div>
</template>
<script>
import ListSorting from '@/components/asset-list/list-sorting.vue';
import assetItem from '@/components/asset-list/asset-item.vue';
import { fetchContents, deleteContentsById } from '@/api/contents.js';
import bus from '@/utils/bus.js';

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
            folderItems: [
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
        assetItem,
    },
    methods: {
        async fetchData() {
            try {
                const {
                    data: { posts: folderItems },
                } = await fetchContents();
                this.folderItems = folderItems;
                return;
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
