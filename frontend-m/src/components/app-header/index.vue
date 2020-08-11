<template>
    <header :class="{ 'page-header': tabMenuData !== null }">
        <h1 class="logo" v-if="this.$route.path === '/'">
            <a href="#"><span>나이키</span></a>
        </h1>
        <template v-else>
            <button
                type="button"
                class="btn-back"
                v-if="$route.meta.historyBack !== null"
            >
                뒤로가기
            </button>
            <div class="inner" v-if="tabMenuData !== null">
                <h1 class="page-title">{{ tabMenuData.menuName }}</h1>
                <NavItem :tabMenuData="tabMenuData.menus" />
            </div>
        </template>
    </header>
</template>
<script>
import NavItem from '@/components/app-header/nav-item';
export default {
    name: 'headerIndex',
    data() {
        return {
            tabMenuData: null,
        };
    },
    created() {
        this.tabMenuFn();
    },
    computed: {},
    watch: {},
    components: {
        NavItem,
    },
    methods: {
        async tabMenuFn() {
            const titleValue = this.$route.path.split('/')[1];
            this.tabMenuData = this.$store.state.menuData.filter(el => {
                if (titleValue.toUpperCase() === el.menuName) {
                    return el.menuPathUrl === '/' + titleValue;
                } else {
                    return null;
                }
            });

            this.tabMenuData =
                this.tabMenuData.length === 0 ? null : this.tabMenuData[0];
            if (this.$route.meta.depth) {
                const menu = this.tabMenuData.menus.filter(el => {
                    return el.menuPathUrl === this.$route.meta.depth;
                });
                this.tabMenuData = menu.length === 0 ? null : menu[0];
            }
        },
    },
};
</script>
<style scoped></style>
