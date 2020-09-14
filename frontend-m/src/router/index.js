import Vue from 'vue';
import VueRouter from 'vue-router';

// Routes
import LoginRoutes from './login';
import FoundationRoutes from './foundation';
import ToolkitRoutes from './toolkit';
import ReportRoutes from './report';
import InformationRoutes from './information';
import MyPageRoutes from './mypage';
import TermsRoutes from './terms';
import testRoutes from './test';
import ErrorRoutes from './error';

// Import methods
import store from '@/store';
import { pages } from '@/utils/global-methods';
import { getAuthFromCookie } from '@/utils/cookies';
import bus from '@/utils/bus';

Vue.use(VueRouter);

const router = new VueRouter({
    scrollBehavior(to, from, savedPosition) {
        let position = { x: 0, y: 0 };
        if (savedPosition) {
            position = savedPosition;
        }
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(position);
            }, 0);
        });
        /* return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(position);
            }, 310);
        });*/
    },
    mode: 'history',
    routes: [
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default' },
        },
        ...LoginRoutes,
        ...ToolkitRoutes,
        ...FoundationRoutes,
        ...ReportRoutes,
        ...InformationRoutes,
        ...MyPageRoutes,
        ...TermsRoutes,
        ...testRoutes,
        ...ErrorRoutes,
    ],
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
