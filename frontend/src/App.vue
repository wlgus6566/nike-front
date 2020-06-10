<template>
    <div id="app">
        <component :is="Applayout" />
    </div>
</template>
<script>
export default {
    name: 'App',
    data() {
        return {
            navActive: true,
        };
    },
    watch: {
        $route() {},
    },
    computed: {
        Applayout() {
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
</style>
