<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <div class="sorting-area">
            <ListSorting v-bind:listTypes="listTypes" />
            <filterSelect :listSortSelect="listSortSelect" />
            <div class="search-input">
                <input type="text" placeholder="검색어를 입력해주세요." />
                <button type="button"><span>검색</span></button>
            </div>
        </div>
        <folderList v-bind:listTypes="listTypes" v-bind:items="folderItems" />
    </div>
</template>
<script>
import filterSelect from '@/components/filter-select';
import ListSorting from '@/components/folder-list/list-sorting.vue';
import folderList from '@/components/folder-list/index.vue';
import { fetchContents, deleteContentsById } from '@/api/contents.js';
import bus from '@/utils/bus.js';

export default {
    name: 'folder-list',
    mounted() {},
    data() {
        return {
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'new',
                        label: '최신순',
                    },
                    {
                        value: 'startDate',
                        label: '시작일순',
                    },
                ],
                value: 'new',
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
            folderItems: [
                {
                    title:
                        '타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 타이틀 ',
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
        filterSelect,
        folderList,
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
