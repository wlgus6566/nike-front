<template>
    <nav class="nav-area">
        <ul class="tab-bar">
            <li :class="{ active: page === 0 }">
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
            <li @click="alertMsg" :class="{ active: page === 1 }">
                <router-link :to="setUrl()" @click.native="menuLottie(1)">
                    <lottie
                        class="loading"
                        :options="aniReport"
                        @animCreated="handleAnimation"
                    />
                    <!--                    <i class="icon-report"></i>-->
                    <span>REPORT</span>
                </router-link>
            </li>
            <li :class="{ active: page === 2 }">
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
            <li :class="{ active: page === 3 }">
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
                <div class="dimmed" @click="menuClose"></div>
                <div class="inner">
                    <UserInfo></UserInfo>
                    <MenuList
                        :menuData="menuData"
                        @menuClose="menuClose"
                    ></MenuList>
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
import bus from '@/utils/bus';
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
            topScollVal: 0,
            reportUrl: null,
            page: 0,
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
    mounted() {},
    computed: {
        pathUrl() {
            return this.$route.path;
        },
    },
    watch: {
        pathUrl(val) {
            if (val.split('/')[1] === 'report') {
                this.menuLottie(1);
            }
            this.pathLottie();
        },
        '$store.state.menuData'() {
            this.menuFn();
            this.setUrl();
            this.pathLottie();
        },
    },
    methods: {
        pathLottie() {
            let _lottieIndex = 0;
            const path = this.pathUrl.split('/')[1];
            if (this.pathUrl === '/') {
                _lottieIndex = 0;
            } else {
                if (path === 'mypage') {
                    _lottieIndex = 2;
                } else if (path === 'report') {
                    _lottieIndex = 1;
                } else {
                    _lottieIndex = 3;
                }
            }
            this.menuLottie(_lottieIndex);
        },
        alertMsg() {
            if (this.reportUrl.split('/')[1] !== 'report') {
                alert('접근 권한이 없습니다.');
                this.pathLottie();
            }
        },
        setUrl() {
            let _reportUrl = '';
            if (!this.$store.state.menuData) {
                _reportUrl = this.pathUrl;
            } else {
                const report = this.$store.state.menuData.filter(
                    el => el.menuCode === 'REPORT'
                );
                if (report[0].listYn === 'Y') {
                    const reportMenus = report[0].menus.filter(menu => {
                        return menu.listYn === 'Y';
                    });
                    _reportUrl = reportMenus[0].menuPathUrl;
                } else {
                    _reportUrl = this.$route.path;
                }
            }
            this.reportUrl = _reportUrl;
            return _reportUrl;
        },
        menuLottie(index) {
            this.page = index;
            if (!this.$store.state.menuData) return;
            bus.$emit('closeFn');
            const state = this.$store.state.menuData.some(
                el => el.menuCode === 'REPORT'
            );
            if (!state) {
                if (index === 1) {
                    return;
                }
            }
            this.anim.forEach(el => {
                el.goToAndStop(0, true);
            });
            this.anim[index].play();
        },
        menuFn() {
            if (!this.$store.state.menuData) return;
            const menu = this.$store.state.menuData.filter(
                el =>
                    el.menuCode !== 'MYPAGE' &&
                    el.menuCode !== 'HOME' &&
                    el.mobileYn === 'Y' &&
                    el.listYn === 'Y'
            );
            this.menuData = menu;
        },
        menuOpen() {
            bus.$emit('closeFn');
            this.topScollVal = document.scrollingElement.scrollTop;
            this.menuUse = true;
            document.querySelector('body').classList.add('menu-open');
            document.querySelector('.menu-open').style.overflow = 'hidden';
            document.querySelector('#wrap').style.marginTop =
                '-' + this.topScollVal + 'px';
        },
        menuClose() {
            this.menuUse = false;
            document.querySelector('.menu-open').style.overflow = '';
            document.querySelector('#wrap').style.margin = '';
            window.scrollTo(0, this.topScollVal);
            document.querySelector('body').classList.remove('menu-open');
            this.pathLottie();
        },
        handleAnimation: function(anim) {
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
    padding: 0 20px 40px;
    background: #fff;
}
.aside-wrap .inner .btn-close {
    position: absolute;
    top: 10px;
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
