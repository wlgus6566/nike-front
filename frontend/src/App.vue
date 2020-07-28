<template>
    <div id="app">
        <transition name="layout-change" mode="out-in">
            <component :is="AppLayout" :key="$route.meta.layout" />
        </transition>
    </div>
</template>
<script>
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en';
import App from './App.vue';
import { layouts } from '@/utils/global-methods';

Vue.use(ElementUI, { locale });

export default {
    name: 'App',
    render: (h) => h(App),
    watch: {
        $route() {
            console.log(this.$route);
        },
    },
    data() {
        return {};
    },

    computed: {
        AppLayout() {
            return `Layout${this.$route.meta.layout || 'Default'}`;
        },
    },
    components: {
        LayoutDefault: layouts('default'),
        LayoutClean: layouts('clean'),
        LayoutPub: layouts('pub'),
    },
};
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');
@import url('assets/css/ui.nikeFront.css');
</style>
