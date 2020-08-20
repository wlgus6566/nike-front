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
            show: false,
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
        handleAnimation: function (anim) {
            this.anim.push(anim);
            anim.stop();
            anim.loop = false;
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
