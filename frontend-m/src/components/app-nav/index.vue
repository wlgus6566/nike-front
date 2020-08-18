<template>
    <nav class="nav-area">
        <ul class="tab-bar">
            <li>
                <router-link to="/" @click.native="menuLottie(0)">
                    <lottie
                        class="loading"
                        :options="aniHome"
                        :exact="true"
                        @animCreated="handleAnimation"
                    />
                    <!--                    <i class="icon-home"></i>-->
                    <span>HOME</span>
                </router-link>
            </li>
            <li>
                <router-link
                    to="/report/management"
                    @click.native="menuLottie(1)"
                >
                    <lottie
                        class="loading"
                        :options="aniReport"
                        @animCreated="handleAnimation"
                    />
                    <!--                    <i class="icon-report"></i>-->
                    <span>REPORT</span>
                </router-link>
            </li>
            <li>
                <router-link to="/mypage" @click.native="menuLottie(2)">
                    <lottie
                        class="loading"
                        :options="aniMy"
                        @animCreated="handleAnimation"
                    />
                    <!--                    <i class="icon-my"></i>-->
                    <span>MY</span>
                </router-link>
            </li>
            <li>
                <button
                    type="button"
                    @click="
                        menuOpen();
                        menuLottie(3);
                    "
                >
                    <Lottie
                        class="loading"
                        :options="aniMenu"
                        @animCreated="handleAnimation"
                    />
                    <!--                    <i class="icon-menu"></i>-->
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
import Lottie from '@/components/lottie';
import * as aniHome from '@/assets/images/lottie/menu_home.json';
import * as aniReport from '@/assets/images/lottie/menu_report.json';
import * as aniMy from '@/assets/images/lottie/menu_my.json';
import * as aniMenu from '@/assets/images/lottie/menu_menu.json';
export default {
    name: 'navigation',
    data() {
        return {
            anim: [],
            menuUse: false,
            menuData: [],
            aniHome: { animationData: aniHome.default },
            aniReport: { animationData: aniReport.default },
            aniMy: { animationData: aniMy.default },
            aniMenu: { animationData: aniMenu.default },
            animationSpeed: 1,
        };
    },
    components: {
        UserInfo,
        MenuList,
        Lottie,
    },
    created() {
        this.menuFn();
    },
    mounted() {
        this.menuLottie(0);
    },
    computed: {},
    methods: {
        menuLottie(index) {
            this.anim.forEach((el) => {
                el.goToAndStop(0, true);
            });
            this.anim[index].play();
        },
        menuFn() {
            const menu = this.$store.state.menuData.filter(
                (el) => el.menuCode !== 'MYPAGE' && el.menuCode !== 'HOME'
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
        handleAnimation: function (anim) {
            this.anim.push(anim);
            anim.stop();
            anim.loop = false;
        },
    },
};
</script>
<style scoped></style>
