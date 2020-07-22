import Vue from 'vue';
import VueRouter from 'vue-router';

// Routes
import AssetRoutes from './asset';
import ToolkitRoutes from './toolkit';
import FoundationRoutes from './foundation';
import OrderRoutes from './order';
import ReportRoutes from './report';
import InformationRoutes from './information';
import ManagementRoutes from './management';
import MyPageRoutes from './mypage';
import ErrorRoutes from './error';
import testRoutes from './test';
import PubRoutes from './pub';

// Import methods
import store from '@/store';
import { pages } from '@/utils/global-methods';
import { getUserFromCookie } from '@/utils/cookies.js';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/login',
            component: pages('login'),
            meta: {
                layout: 'Clean',
            },
            /*beforeEnter(to, from, next) {
                store.getters['isLoggedIn'] ? next('/') : next();
            },*/
        },
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default', aside: 'Default' },
            /*beforeEnter(to, from, next) {
                if (store.getters['isLoggedIn'] || getUserFromCookie()) {
                    next();
                } else {
                    next();
                    //next('/login');
                }
            },*/
        },
        ...AssetRoutes,
        ...ToolkitRoutes,
        ...FoundationRoutes,
        ...OrderRoutes,
        ...ReportRoutes,
        ...ManagementRoutes,
        ...InformationRoutes,
        ...MyPageRoutes,
        ...ErrorRoutes,

        ...PubRoutes,

        ...testRoutes,
    ],
});

export default router;

/*function beforeEnter(to, from, next) {
    if (store.getters['isLoggedIn'] || getUserFromCookie()) {
        next();
    } else {
        alert('sign in please');
        next('/login');
    }
}*/
