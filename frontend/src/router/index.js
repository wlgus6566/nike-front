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

// Import methods
import store from '@/store';
import { pages } from '@/utils/global-methods';
import { getAuthFromCookie } from '@/utils/cookies';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default', aside: 'Default' },
        },
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
    ],
});

router.beforeEach((to, from, next) => {
    if (to.meta.unauthorized) {
        next();
    } else {
        if (store.getters['isLoggedIn'] || getAuthFromCookie()) {
            next();
        } else {
            next('/login');
        }
    }
});
export default router;
