<template>
    <div id="wrap">
        <header class="sticky-container" sticky-container>
            <div sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky>
                <appHeader />
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <transition mode="out-in" name="page-change">
                    <router-view></router-view>
                </transition>
            </div>
            <aside class="sticky-container" sticky-container>
                <div class="sticky-content" sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky>
                    <transition name="aside-change" mode="out-in" appear v-on:appear="AsideAppear" v-on:enter="AsideEnter" v-on:leave="AsideLeave">
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
import appHeader from '@/components/app-header';

export default {
    name: 'LayoutDefault',
    data() {
        return {};
    },
    computed: {
        AppAside() {
            return `Aside${this.$route.meta.aside || 'Default'}`;
        },
    },
    directives: {
        Sticky,
    },
    components: {
        appHeader,
        AsideDefault: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideFile: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideOrder: () => import('@/components/app-aside/AsideOrder.vue'),
    },
    mounted() {
        const el = [document.querySelector('header .inner')];
        this.LayoutInit(el, '-100%');
        this.LayoutAnimation(el, '0%');
    },
    methods: {
        // Aside
        AsideAppear: function (el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.LayoutInit(elements, '100%');
            this.LayoutAnimation(elements, '0%', done);
        },

        AsideEnter: function (el, done) {
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.LayoutInit(elements, '100%');
            this.LayoutAnimation(elements, '0%', done);
        },

        AsideLeave: function (el, done) {
            const scrollElement = window.document.scrollingElement || window.document.body || window.document.documentElement;
            this.$anime({
                targets: scrollElement,
                scrollTop: 0,
                duration: 300,
                easing: 'easeInOutQuad',
            });
            const elements = [document.querySelector('aside .aside-bg'), el];
            this.LayoutAnimation(elements, '100%', done);
        },
        LayoutAnimation: function (el, status, done) {
            console.log(el);
            this.$anime({
                targets: el,
                translateX: status,
                duration: 300,
                easing: 'easeInOutQuart',
                complete: function () {
                    if (done) {
                        done();
                    }
                },
                update: function () {},
            });
        },
        LayoutInit: function (el, status) {
            console.log(el);
            for (let i = 0; i < el.length; i++) {
                el[i].style.transform = `translateX(${status})`;
            }
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
