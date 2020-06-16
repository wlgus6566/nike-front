<template>
    <div id="wrap">
        <appHeader />
        <section id="container">
            <div class="contents">
                <transition mode="out-in" name="component-fade">
                    <router-view></router-view>
                </transition>
            </div>
            <transition mode="out-in" name="aside-change" v-on:before-enter="AsideBeforeEnter" v-on:enter="AsideEnter" v-on:after-enter="AsideAfterEnter" v-on:leave="AsideLeave" appear v-on:before-appear="AsideBeforeAppear" v-on:appear="AsideAppear">
                <component :is="AppAside" v-bind:shouldStick="shouldStick" />
            </transition>
        </section>
        <footer>
            footer
        </footer>
    </div>
</template>
<script>
import appHeader from '@/components/app-header';

export default {
    name: 'LayoutDefault',
    data() {
        return {
            shouldStick: true,
        };
    },
    computed: {
        AppAside() {
            return `Aside${this.$route.meta.aside || 'Default'}`;
        },
    },
    components: {
        appHeader,
        AsideDefault: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideFile: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideOrder: () => import('@/components/app-aside/AsideOrder.vue'),
    },
    methods: {
        //header
        // HeaderBeforeAppear: function (el) {
        //     console.log(el);
        //     this.HeaderInit(el);
        // },
        // HeaderAppear: function (el, done) {
        //     this.HeaderAnimation(el, '0%', done);
        // },
        // HeaderBeforeEnter: function (el) {
        //     this.HeaderInit(el);
        // },
        // HeaderEnter: function (el, done) {
        //     this.HeaderAnimation(el, '0%', done);
        // },
        // HeaderLeave: function (el, done) {
        //     this.HeaderAnimation(el, '100%', done);
        // },
        // HeaderAnimation: function (el, status, done) {
        //     const logo = document.querySelector('header h1');
        //     this.$anime({
        //         targets: logo,
        //         translateX: status,
        //         duration: 300,
        //         easing: 'easeInOutQuart',
        //         complete: function (anim) {
        //             done();
        //         },
        //     });
        // },
        // HeaderInit: function (el) {
        //     el.style.transform = 'translateX(100%)';
        // },

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
        AsideAfterEnter: function (el) {
            this.shouldStick = true;
        },
        AsideLeave: function (el, done) {
            this.AsideAnimation(el, '100%', done);
        },
        AsideAnimation: function (el, status, done) {
            const target = el.querySelector('.test');
            this.$anime({
                targets: target,
                translateX: status,
                duration: 300,
                easing: 'easeInOutQuart',
                complete: function (anim) {
                    done();
                },
            });
        },
        AsideInit: function (el) {
            const target = el.querySelector('.test');
            target.style.transform = 'translateX(100%)';
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
