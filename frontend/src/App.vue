<template>
    <div id="app">
        <transition name="layout-change" mode="out-in">
            <component :is="AppLayout" :key="$route.meta.layout" />
        </transition>
        <Loading
            class="page-loading"
            :width="172"
            :height="172"
            v-if="pageLoading"
        />
    </div>
</template>
<script>
import Loading from '@/components/loading/index';
require('es6-promise/auto');
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from '@/utils/element-lang';
import App from './App.vue';
import { layouts } from '@/utils/global-methods';
import bus from '@/utils/bus';

Vue.use(ElementUI, { locale });

export default {
    name: 'App',
    render: (h) => h(App),
    data() {
        return {
            pageLoading: false,
        };
    },
    created() {
        bus.$on('pageLoading', (state) => {
            this.pageLoading = state;
        });
    },
    computed: {
        AppLayout() {
            return `Layout${this.$route.meta.layout || 'Clean'}`;
        },
    },
    components: {
        Loading,
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
.page-loading {
    z-index: 2000;
    position: fixed;
    top: 0;
    left: 0;
    min-width: 1440px;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
