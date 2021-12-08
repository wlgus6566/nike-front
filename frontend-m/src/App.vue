<template>
    <div id="app">
        <transition
            mode="out-in"
            @enter="pageEnter"
            @leave="pageLeave"
            name="layout-change"
        >
            <component :is="AppLayout" :key="AppLayout" />
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
require('es6-promise/auto');
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en';
import App from './App.vue';
import { layouts } from '@/utils/global-methods';
import bus from '@/utils/bus';
import Loading from '@/components/loading/index';
Vue.use(ElementUI, { locale });

Vue.use({ locale });

export default {
    name: 'App',
    render: (h) => h(App),
    watch: {
        $route() {},
    },
    data() {
        return {
            pageLoading: false,
        };
    },
    computed: {
        AppLayout() {
            return `Layout${this.$route.meta.layout || 'Default'}`;
        },
    },
    mounted() {},
    created() {
        this.urlCheck();
        bus.$on('pageLoading', (state) => {
            this.pageLoading = state;
        });
    },
    components: {
        Loading,
        LayoutDefault: layouts('default'),
        LayoutClean: layouts('clean'),
        LayoutPub: layouts('pub'),
        LayoutIndex: layouts('index'),
    },
    methods: {
        pageEnter(el, done) {
            done();
        },
        pageLeave(el, done) {
            done();
        },
        urlCheck() {
            const filter = 'win16|win32|win64|macintel|mac|'; // PC일 경우 가능한 값
            if (navigator.platform) {
                if (filter.indexOf(navigator.platform.toLowerCase()) < 0) {
                    console.log('모바일에서 접속하셨습니다');
                } else {
                    console.log('PC에서 접속하셨습니다');
                    console.log(window.location.hostname);
                     if (window.location.hostname === 'devm.nikespace.co.kr') {
                        document.location = 'https://devwww.nikespace.co.kr/';
                    }
                    if (window.location.hostname === 'm.nikespace.co.kr') {
                        document.location = 'https://www.nikespace.co.kr/';
                    }
                }
            }
        },
    },
};
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');
@import url('assets/css/ui.nikeFront.css');
.page-loading {
    flex-direction: column;
    z-index: 2000;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
.layout-change-enter-active,
.layout-change-leave-active {
    transition: opacity 0.4s;
}
.layout-change-enter,
.layout-change-leave-to {
    opacity: 0;
}
</style>
