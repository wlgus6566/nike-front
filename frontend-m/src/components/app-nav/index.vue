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
        <transition name="slide-fade">
            <div
                class="aside-wrap"
                :class="{ active: menuUse === true }"
                v-if="menuUse"
            >
                <div class="dimmed"></div>
                <div class="inner">
                    <UserInfo></UserInfo>
                    <MenuList :menuData="menuData"></MenuList>
                    <button class="btn-close" @click="menuClose">닫기</button>
                </div>
            </div>
        </transition>
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
            show: false,
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
<style scoped>
.aside-wrap {
    z-index: 5;
    position: fixed;
    top: 0;
    left: 0;
    display: none;
    justify-content: flex-end;
    width: 100%;
    height: 100vh;
}
.aside-wrap .dimmed {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100vh;
    background: rgba(0, 0, 0, 0.6);
}
.aside-wrap.active {
    display: flex;
}
.aside-wrap .inner {
    z-index: 5;
    position: relative;
    box-sizing: border-box;
    height: 100vh;
    width: 270px;
    padding: 24px 20px 40px;
    background: #fff;
}
.aside-wrap .inner .btn-close {
    position: absolute;
    top: 36px;
    right: 10px;
    display: block;
    width: 36px;
    height: 36px;
    background: url(../../assets/images/svg/icon-close-popup.svg) no-repeat
        center;
    overflow: hidden;
    text-indent: -99999px;
}
.slide-fade-enter-active {
    transition: all 0.3s ease;
}
.slide-fade-leave-active {
    transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter-active .inner {
    transition: all 0.3s ease;
}
.slide-fade-enter .inner {
    transition: all 0.3s ease;
    transform: translateX(100%);
}
.slide-fade-leave-active .inner {
    transition: all 0.3s;
    transform: translateX(100%);
}
.slide-fade-enter-active .dimmed {
    transition: all 0.3s ease;
}
.slide-fade-leave-active .dimmed {
    transition: all 0s ease;
}
.slide-fade-enter .dimmed,
.slide-fade-leave-to .dimmed {
    /*transform: translateX(10px);*/
    opacity: 0;
}
</style>
