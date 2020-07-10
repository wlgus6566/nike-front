<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <ListSorting v-bind:listTypes="listTypes"></ListSorting>
        <Item v-bind:listTypes="listTypes" v-bind:items="items"></Item>
    </div>
</template>
<script>
import ListSorting from '@/components/asset-list/list-sorting.vue';
import Item from '@/components/asset-list/asset-item.vue';
export default {
    name: 'index',
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
            items: [
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
};
</script>
<style scoped></style>
