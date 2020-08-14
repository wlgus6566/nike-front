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
            footer
        </footer>
    </div>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import { gsap, Cubic, TimelineLite } from 'gsap/all';

import appHeader from '@/components/app-header';

export default {
    name: 'LayoutDefault',
    created() {},
    data() {
        return {
            tw: new TimelineLite({ paused: true }),
            newRoutePath: '',
            oldRoutePath: '',
            activeLink: null,
            activeParent: null,
            activeUl: null,
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
    mounted() {
        this.activeSet();
        this.headerAni();
        this.toggleHeader(this.headerActiveNav, this.tw._dur);
        const target = [document.querySelector('header .inner')];
        this.asideAnimation(target, '-100%', '0%');
    },

    methods: {
        activeSet() {
            this.activeLink = document.querySelector(
                `.depth1 > a[href='${this.$route.matched[0].path}']`
            );
            if (this.activeLink) {
                this.activeParent = this.activeLink.parentNode;
                this.activeUl = this.activeLink.nextSibling;
            }
        },
        headerAni() {
            const header = document.querySelector('header');
            const logo = header.querySelector('h1');
            const bg = header.querySelector('.header-bg');
            const nav = header.querySelector('nav');
            const anchor = nav.querySelector('.depth1 > .router-link-active');
            let parentN = null;
            let ul = null;
            if (anchor) {
                parentN = anchor.parentNode;
                ul = anchor.nextSibling;
            }
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
                    this.headerAni();
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
            if (status) {
                this.tw.play(duration);
            } else {
                this.tw.reverse();
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
