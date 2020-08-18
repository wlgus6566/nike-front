<template>
    <nav class="nav-area">
        <ul class="tab-bar">
            <li>
                <router-link to="/">
                    <i class="icon-home"></i>
                    <span>HOME</span>
                </router-link>
            </li>
            <li>
                <router-link to="/report/management">
                    <i class="icon-report"></i>
                    <span>REPORT</span>
                </router-link>
            </li>
            <li>
                <router-link to="/mypage">
                    <i class="icon-my"></i>
                    <span>MY</span>
                </router-link>
            </li>
            <li>
                <button type="button" @click="menuOpen">
                    <i class="icon-menu"></i>
                    <span>MENU</span>
                </button>
            </li>
        </ul>
        <div class="aside-wrap" :class="{ active: menuUse === true }">
            <div class="inner">
                <UserInfo></UserInfo>
                <MenuList :menuData="menuData"></MenuList>
                <button class="btn-close" @click="menuClose">닫기</button>
            </div>
        </div>
    </nav>
</template>
<script>
import UserInfo from '@/components/app-aside/user-info';
import MenuList from '@/components/app-aside/menu-list';
export default {
    name: 'navigation',
    data() {
        return {
            menuUse: false,
            menuData: [],
        };
    },
    components: {
        UserInfo,
        MenuList,
    },
    created() {
        this.menuFn();
    },
    computed: {},
    methods: {
        menuFn() {
            const menu = this.$store.state.menuData.filter(
                el =>
                    el.menuCode !== 'MYPAGE' &&
                    el.menuCode !== 'HOME' &&
                    el.mobileYn === 'Y'
            );
            this.menuData = menu;
            // console.log(this.menuData);
        },
        menuOpen() {
            this.menuUse = true;
        },
        menuClose() {
            this.menuUse = false;
        },
    },
};
</script>
<style scoped></style>
