<template>
    <ul class="nav-tab" v-if="tabMenuData">
        <li
            v-for="menu in tabMenuData"
            :key="menu.menuSeq"
            :class="tabItemClass(menu.menuPathUrl)"
        >
            <router-link :to="menu.menuPathUrl">
                <span>{{ menu.menuName }}</span>
            </router-link>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'nav-item',
    props: ['tabMenuData'],
    created() {},
    computed: {},
    methods: {
        tabItemClass(val) {
            const titleValue = this.$route.path;
            console.log(titleValue);
            return {
                active: titleValue === val,
            };
        },
    },
};
</script>
<style scoped></style>
