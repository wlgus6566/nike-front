<template>
    <div id="wrap">
        <header class="sticky-container" sticky-container v-on:mouseleave="mouseEvent(true)">
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
                    mode="out-in"
                    appear
                    v-on:appear="pageAppear"
                    v-on:enter="pageEnter"
                    v-on:leave="pageLeave"
                >
                    <router-view />
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
    data() {
        return {
            tw: new TimelineLite({ paused: true }),
            newRoutePath: '',
            oldRoutePath: '',
        };
    },
    computed: {
        AppAside() {
            return `Aside${this.$route.meta.aside || 'Default'}`;
        },
    },
    directives: {
        Sticky,
    },
    watch: {
        $route(newRoute, oldRoute) {
            this.newRoutePath = newRoute.path;
            this.oldRoutePath = oldRoute.path;
        },
    },
    components: {
        appHeader,
        AsideDefault: () => import('@/components/app-aside/default.vue'),
        AsideFile: () => import('@/components/app-aside/default.vue'),
        AsideOrder: () => import('@/components/app-aside/order.vue'),
    },
    mounted() {
        this.headerAni();
        this.toggleHeader(this.$route.path !== '/', this.tw._dur);
        const target = [document.querySelector('header .inner')];
        this.layoutAnimation(target, '-100%', '0%');
    },

    methods: {
        headerAni() {
            const header = document.querySelector('header');
            const logo = header.querySelector('h1');
            const bg = header.querySelector('.header-bg');
            const nav = header.querySelector('nav');
            const anchor = nav.querySelector('.depth1 > .router-link-active');
            const ul = anchor.nextSibling;

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
                .set(anchor.parentNode, {
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
        pageAppear() {},
        pageEnter() {},
        pageLeave() {
            if (this.newRoutePath.split('/')[1] !== this.oldRoutePath.split('/')[1]) {
                if (this.newRoutePath !== '/') {
                    this.headerAni();
                }
                this.toggleHeader(this.$route.path !== '/');
            }
        },
        toggleHeader(status, duration) {
            if (status) {
                this.tw.play(duration);
            } else {
                this.tw.reverse();
            }
        },

        mouseEvent(status) {
            if (this.$route.path !== '/') {
                this.toggleHeader(status);
            }
        },

        // Aside
        asideAppear(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.layoutAnimation(elements, '100%', '0%', done);
        },
        asideEnter(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.layoutAnimation(elements, '100%', '0%', done);
        },
        asideLeave(el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.layoutAnimation(elements, '0%', '100%', done);
        },
        layoutAnimation(el, fromVal, toVal, done) {
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
<style scoped>
/*.layout-change-enter-active,*/
/*.layout-change-leave-active {*/
/*    transition: opacity 0.3s ease-in-out;*/
/*}*/
/*.layout-change-enter,*/
/*.layout-change-leave-to {*/
/*    opacity: 0;*/
/*}*/
</style>
