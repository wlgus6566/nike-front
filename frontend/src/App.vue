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
        <ModalLogoutConfirm />
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
import ModalLogoutConfirm from '@/views/pages/common/modal-logout-confirm';

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
        this.urlCheck();
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
        ModalLogoutConfirm,
        LayoutDefault: layouts('default'),
        LayoutClean: layouts('clean'),
        LayoutPub: layouts('pub'),
    },
    methods:{
      urlCheck(){
        const filter = "win16|win32|win64|macintel|mac|"; // PC일 경우 가능한 값
        if(navigator.platform){
          if( filter.indexOf(navigator.platform.toLowerCase())<0 ) {
            console.log("모바일에서 접속하셨습니다");
            if(window.location.hostname === 'devwww.nikespace.co.kr'){
              document.location = "http://devm.nikespace.co.kr/";
            }
            if(window.location.hostname === 'www.nikespace.co.kr'){
              document.location = "http://m.nikespace.co.kr/";
            }
          } else{
            console.log("PC에서 접속하셨습니다");
            console.log(window.location.hostname)
          }
        }
      }
    }
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
    min-width: 1440px;
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
