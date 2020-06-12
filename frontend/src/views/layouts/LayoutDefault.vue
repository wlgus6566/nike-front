<template>
    <div id="wrap">
        <appHeader />
        <section id="container">
            <div class="contents">
                <transition name="component-fade" mode="out-in">
                    <router-view></router-view>
                </transition>
            </div>
            <component :is="AppAside" />
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
        return {};
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
