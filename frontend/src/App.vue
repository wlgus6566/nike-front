<template>
    <div id="app">
        <transition name="layout-change" mode="out-in">
            <component :is="Applayout" />
        </transition>
    </div>
</template>
<script>
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.use(ElementUI);

export default {
    name: 'App',
    render: (h) => h(App),
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
@import url('assets/css/ui.nikeFront.css');
</style>
