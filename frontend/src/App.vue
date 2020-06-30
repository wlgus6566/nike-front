<template>
    <div id="app">
        <transition name="layout-change" mode="out-in">
            <component :is="AppLayout" />
        </transition>
    </div>
</template>
<script>
export default {
    name: 'App',
    data() {
        return {};
    },
    computed: {
        AppLayout() {
            return `Layout${this.$route.meta.layout || 'Default'}`;
        },
    },
    components: {
        LayoutDefault: () => import('@/views/layouts/LayoutDefault.vue'),
        LayoutClean: () => import('@/views/layouts/LayoutClean.vue'),
    },
};
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');
@import url('assets/css/ui.nikeFront.css');
</style>
