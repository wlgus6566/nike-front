<template>
    <div id="wrap">
        <header
            class="sticky-container"
            sticky-container
            v-on:mouseleave="mouseEvent(true)"
        >
            <div
                class="sticky-content"
                sticky-offset="{top:0, bottom:0}"
                sticky-side="both"
                sticky-z-index="20"
                v-sticky
            >
                <appHeader />
            </div>
            <i class="icon-ellipsis"></i>
            <!--            <i class="icon-lnb"></i>-->
            <div class="header-bg" v-on:mouseenter="mouseEvent(false)"></div>
        </header>
        <section id="container">
            <div class="contents">
                <transition
                    name="page-change"
                    :css="false"
                    mode="out-in"
                    appear
                    @enter="pageEnter"
                    @leave="pageLeave"
                >
                    <keep-alive max="2">
                        <router-view :key="$route.fullPath" />
                    </keep-alive>
                </transition>
            </div>
            <aside class="sticky-container" sticky-container>
                <div
                    class="sticky-content"
                    sticky-offset="{top:0, bottom:0}"
                    sticky-side="both"
                    sticky-z-index="20"
                    v-sticky
                >
                    <transition
                        name="aside-change"
                        mode="out-in"
                        appear
                        v-on:appear="asideAppear"
                        v-on:enter="asideEnter"
                        v-on:leave="asideLeave"
                    >
                        <component :is="AppAside" />
                    </transition>
                </div>
                <div class="aside-bg"></div>
            </aside>
        </section>

        <footer>
            <div class="inner">
                <span class="copy">
                    ⓒ 2020 Nike Korea LLC. All Rights Reserved
                </span>
                <ul class="info-list">
                    <li>
                        <router-link to="/terms/privacy"
                            >개인정보처리방침</router-link
                        >
                    </li>
                    <li>
                        <router-link to="/terms/service">이용약관</router-link>
                    </li>
                </ul>
            </div>
        </footer>
    </div>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import { gsap, Cubic, TimelineLite } from 'gsap/all';
import appHeader from '@/components/app-header';
import { getGnbMenu } from '@/api/mypage';
export default {
    name: 'LayoutDefault',
    data() {
        return {
            lnb: [],
            tw: new TimelineLite({ paused: true }),
            newRoutePath: '',
            oldRoutePath: '',
            activeLink: null,
            activeParent: null,
            activeUl: null,
            menuHoverState: false,
        };
    },
    computed: {
        AppAside() {
            return `Aside${this.$route.meta.aside || 'Default'}`;
        },
        headerActiveNav() {
            return this.$route.path !== '/' && this.activeLink;
        },
    },
    directives: {
        Sticky,
    },
    watch: {
        $route(newRoute, oldRoute) {
            this.newRoutePath = newRoute.path;
            this.oldRoutePath = oldRoute.path;
            this.activeSet();
        },
    },
    components: {
        appHeader,
        AsideDefault: () => import('@/components/app-aside/default.vue'),
        AsideFile: () => import('@/components/app-aside/file.vue'),
        AsideOrder: () => import('@/components/app-aside/order.vue'),
        AsideReport: () => import('@/components/app-aside/report.vue'),
    },
    created() {
        this.gnbMenuList();
    },
    methods: {
        async gnbMenuList() {
            try {
                await this.$store.dispatch('gnbMenuList');
                this.activeSet();
                this.headerAni();
                this.toggleHeader(this.headerActiveNav, this.tw._dur);
                const target = [document.querySelector('header .inner')];
                this.asideAnimation(target, '-100%', '0%');
            } catch (error) {
                console.error(error);
            }
        },
        activeSet() {
            this.activeLink = document.querySelector(
                `.depth1 > a[href='${this.$route.matched[0].path}']`
            );
            if (this.activeLink) {
                this.activeParent = this.activeLink.parentNode;
                this.activeUl = this.activeLink.nextSibling;
            }
        },
        headerAni(headerActiveNav) {
            const header = document.querySelector('header');
            const logo = header.querySelector('h1');
            const bg = header.querySelector('.header-bg');
            const nav = header.querySelector('nav');
            const anchor = headerActiveNav
                ? headerActiveNav
                : nav.querySelector('.depth1 > .router-link-active');
            let parentN = null;
            let ul = null;
            if (anchor) {
                parentN = anchor.parentNode;
                ul = anchor.nextSibling;
            }

            gsap.set(header, { clearProps: 'all' });
            gsap.set(logo, { clearProps: 'all' });
            gsap.set(bg, { clearProps: 'all' });
            gsap.set(nav, { clearProps: 'all' });
            gsap.set(nav.querySelectorAll('.depth1'), { clearProps: 'all' });
            gsap.set(nav.querySelectorAll('.depth1 > ul'), { display: 'none' });
            gsap.set(nav.querySelectorAll('.depth1 > a'), {
                clearProps: 'all',
            });
            gsap.set(nav.querySelectorAll('.depth1 span '), {
                clearProps: 'all',
            });
            gsap.set(anchor.querySelectorAll('span '), {
                clearProps: 'all',
            });
            this.tw.clear();
            this.tw
                .set(header, {
                    backgroundImage:
                        'linear-gradient(to right, rgba(255,255,255,1) 100%, rgba(0,0,0,0) 100%)',
                })
                .to(
                    logo,
                    0.5,
                    {
                        translateX: '-23px',
                        translateY: '-30px',
                        scale: 0.25,
                        ease: Cubic.easeInOut,
                    },
                    0
                )
                .to(
                    header,
                    0.5,
                    {
                        backgroundImage:
                            'linear-gradient(to right, rgba(247,247,247,1) 20%, rgba(0,0,0,0) 20%)',
                        ease: Cubic.easeInOut,
                    },
                    0
                )
                .set(
                    bg,
                    {
                        display: 'block',
                    },
                    0.3
                )
                .to(
                    nav,
                    0.3,
                    {
                        opacity: '0',
                        translateX: '-30px',
                        ease: Cubic.easeInOut,
                    },
                    0
                )
                .set(
                    nav,
                    {
                        width: '160px',
                        padding: '146px 20px 130px 20px',
                        opacity: '1',
                        translateX: '40px',
                    },
                    0.3
                )
                .set(
                    nav.querySelectorAll('.depth1'),
                    {
                        display: 'none',
                    },
                    0.3
                )
                .set(parentN, {
                    display: 'block',
                })
                .set(
                    anchor,
                    {
                        opacity: '0',
                        position: 'absolute',
                        top: '-70px',
                        left: '0',
                        fontSize: '22px',
                        translateX: '30px',
                    },
                    0.3
                )
                .set(anchor.querySelector('span'), {
                    translateY: '0',
                })
                .set(anchor.querySelector('.ko'), {
                    opacity: '0',
                })
                .set(
                    ul,
                    {
                        opacity: '0',
                        translateX: '30px',
                        display: 'block',
                    },
                    0.3
                )
                .to(
                    anchor,
                    0.3,
                    {
                        opacity: '1',
                        translateX: '0',
                        ease: Cubic.easeInOut,
                    },
                    0.3
                )
                .to(
                    ul,
                    0.3,
                    {
                        opacity: '1',
                        translateX: '0',
                        ease: Cubic.easeInOut,
                    },
                    0.4
                );
        },
        pageEnter(el, done) {
            this.pageAnimation(
                el,
                { translateY: '30px', opacity: 0 },
                { translateY: '0', opacity: 1 },
                done
            );
        },
        pageLeave(el, done) {
            this.pageAnimation(
                el,
                { translateY: '0', opacity: 1 },
                { translateY: '30px', opacity: 0 },
                done
            );
            if (
                this.newRoutePath.split('/')[1] !==
                this.oldRoutePath.split('/')[1]
            ) {
                if (this.headerActiveNav) {
                    this.headerAni(this.headerActiveNav);
                }
                this.toggleHeader(this.headerActiveNav);
            }
        },
        pageAnimation(el, fromVal, toVal, done) {
            gsap.fromTo(
                el,
                0.3,
                {
                    ...fromVal,
                    ease: Cubic.easeInOut,
                },
                {
                    ...toVal,
                    ease: Cubic.easeInOut,
                    onComplete: () => {
                        el.style.transform = 'none';
                        if (done) {
                            done();
                        }
                    },
                }
            );
        },

        toggleHeader(status, duration) {
            const anchor = document.querySelector(
                '.depth1 > .router-link-active'
            );
            if (status) {
                this.menuHoverState = false;
                anchor.removeEventListener('mouseover', this.menuHover);
                this.tw.play(duration);
            } else {
                this.menuHoverState = true;
                anchor.addEventListener('mouseover', this.menuHover);
                this.tw.reverse();
            }
        },
        menuHover() {
            const anchor = document.querySelector(
                '.depth1 > .router-link-active'
            );
            const en = anchor.querySelector('span');
            const ko = anchor.querySelector('.ko');
            if (this.menuHoverState) {
                en.removeAttribute('style');
                ko.removeAttribute('style');
            } else {
                en.style.transform = 'translateY(0)';
                ko.style.opacity = '0';
            }
        },
        mouseEvent(status) {
            if (this.headerActiveNav) {
                this.toggleHeader(status);
            }
        },

        // Aside
        asideAppear(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.asideAnimation(elements, '100%', '0%', done);
        },
        asideEnter(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.asideAnimation(elements, '100%', '0%', done);
        },
        asideLeave(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.asideAnimation(elements, '0%', '100%', done);
        },
        asideAnimation(el, fromVal, toVal, done) {
            gsap.fromTo(
                el,
                0.3,
                {
                    translateX: fromVal,
                    ease: Cubic.easeInOut,
                },
                {
                    translateX: toVal,
                    ease: Cubic.easeInOut,
                    onComplete: () => {
                        if (done) {
                            done();
                        }
                    },
                }
            );
        },
    },
};
</script>
<style scoped></style>
