import Vue from 'vue';
import VueRouter from 'vue-router';
// Routes
import LoginRoutes from './login';
import AssetRoutes from './asset';
import ToolkitRoutes from './toolkit';
import FoundationRoutes from './foundation';
import OrderRoutes from './order';
import ReportRoutes from './report';
import InformationRoutes from './information';
import ManagementRoutes from './management';
import MyPageRoutes from './mypage';
import ErrorRoutes from './error';
import PubRoutes from './pub';
import TermsRoutes from './terms';
import testRoutes from './test';

// Import methods
import store from '@/store';
import { pages } from '@/utils/global-methods';
import { getAuthFromCookie } from '@/utils/cookies';
import bus from '@/utils/bus';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        /**
         * 시스템 점검 팝업
         */
        /*{
            path: '*',
            name: 'system',
            component: pages('error/system'),
            meta: { layout: 'Clean', unauthorized: true },
        },*/
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default', aside: 'Default' },
        },
        /*{
            path: '/mainTest',
            component: pages('main-page_test'),
            meta: { layout: 'Default', aside: 'Default' },
        },*/
        ...LoginRoutes,
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
        ...TermsRoutes,
        ...testRoutes,
    ],
    scrollBehavior(to, from, savedPosition) {
        let position = { x: 0, y: 0 };
        /*if (savedPosition) {
            position = savedPosition;
        }*/
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(position);
            }, 310);
        });
    },
});
router.beforeEach((to, from, next) => {
    store.state.saveFolder = false;
    if (to.meta.unauthorized) {
        if (store.state.token || getAuthFromCookie()) {
            next('/');
        } else {
            next();
        }
    } else {
        if (store.state.token || getAuthFromCookie()) {
            bus.$emit('pageLoading', false);
            next();
        } else {
            next('/login');
        }
    }
});
export default router;
