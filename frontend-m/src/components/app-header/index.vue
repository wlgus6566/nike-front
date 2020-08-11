<template>
    <header :class="{ 'page-header': tabMenuData.menuName }">
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
            <div class="inner" v-if="tabMenuData.menuName">
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
            tabMenuData: [],
        };
    },
    created() {
        this.tabMenuFn();
        console.log(this.tabMenuData);
    },
    computed: {},
    watch: {},
    components: {
        NavItem,
    },
    methods: {
        tabMenuFn() {
            const titleValue = this.$route.path.split('/')[1];
            const menu = this.$store.state.menuData.filter(el => {
                if (el.menuCode !== 'MYPAGE') {
                    return (
                        el.menuCode !== 'HOME' &&
                        el.menuPathUrl === '/' + titleValue
                    );
                } else {
                    return el.menuCode;
                }
            });
            if (titleValue === 'mypage') {
                const pathSplit = this.$route.path.split('/');
                const myPath = '/' + pathSplit[1] + '/' + pathSplit[2];
                const myMenu = menu[0].menus.filter(
                    el => el.menuPathUrl === myPath
                );

                this.tabMenuData = myMenu[0];
            } else {
                this.tabMenuData = menu[0];
            }
        },
    },
};
</script>
<style scoped></style>
