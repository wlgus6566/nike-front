<template>
    <div id="wrap">
        <header class="sticky-container" sticky-container>
            <div sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky>
                <appHeader />
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <transition mode="out-in" name="component-fade">
                    <router-view></router-view>
                </transition>
            </div>
            <aside class="sticky-container" sticky-container>
                <div sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky>
                    <transition
                        mode="out-in"
                        name="aside-change"
                        v-on:before-enter="AsideBeforeEnter"
                        v-on:enter="AsideEnter"
                        v-on:after-enter="AsideAfterEnter"
                        v-on:leave="AsideLeave"
                        appear
                        v-on:before-appear="AsideBeforeAppear"
                        v-on:appear="AsideAppear"
                    >
                        <component :is="AppAside" />
                    </transition>
                </div>
                <div class="bg"></div>
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
    methods: {
        //aside
        AsideBeforeAppear: function (el) {
            this.AsideInit(el);
        },
        AsideAppear: function (el, done) {
            this.AsideAnimation(el, '0%', done);
        },
        AsideBeforeEnter: function (el) {
            this.AsideInit(el);
        },
        AsideEnter: function (el, done) {
            this.AsideAnimation(el, '0%', done);
        },
        AsideAfterEnter: function () {
            this.shouldStick = true;
        },
        AsideLeave: function (el, done) {
            const scrollElement = window.document.scrollingElement || window.document.body || window.document.documentElement;
            this.$anime({
                targets: scrollElement,
                scrollTop: 0,
                duration: 300,
                easing: 'easeInOutQuad',
            });
            this.AsideAnimation(el, '100%', done);
        },
        AsideAnimation: function (el, status, done) {
            const elements = [document.querySelector('aside .bg'), el];
            this.$anime({
                targets: elements,
                translateX: status,
                duration: 300,
                easing: 'easeInOutQuart',
                complete: function () {
                    done();
                },
                update: function (anime) {
                    console.log(anime.progress);
                },
            });
        },
        AsideInit: function (el) {
            const elements = [document.querySelector('aside .bg'), el];
            for (let i = 0; i < elements.length; i++) {
                elements[i].style.transform = 'translateX(100%)';
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
