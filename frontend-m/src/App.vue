<template>
    <div id="app">
        <transition name="layout-change" mode="out-in">
            <component :is="AppLayout" :key="$route.meta.layout" />
        </transition>
    </div>
</template>
<script>
require('es6-promise/auto');
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en';
import App from './App.vue';
import { layouts } from '@/utils/global-methods';

Vue.use(ElementUI, { locale });

Vue.use({ locale });

export default {
    name: 'App',
    render: h => h(App),
    watch: {
        $route() {},
    },
    data() {
        return {};
    },

    computed: {
        AppLayout() {
            return `Layout${this.$route.meta.layout || 'Default'}`;
        },
    },
    mounted() {},
    components: {
        LayoutDefault: layouts('default'),
        LayoutClean: layouts('clean'),
        LayoutPub: layouts('pub'),
        LayoutIndex: layouts('index'),
    },
};
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');
@import url('assets/css/ui.nikeFront.css');
</style>
