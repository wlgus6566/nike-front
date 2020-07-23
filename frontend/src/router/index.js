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
import ErrorRoutes from './error';
import testRoutes from './test';
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
            path: '/login',
            component: pages('login'),
            meta: {
                layout: 'Clean',
                unauthorized: true,
            },
        },
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default', aside: 'Default' },
        },
        ...AssetRoutes,
        ...ToolkitRoutes,
        ...FoundationRoutes,
        ...OrderRoutes,
        ...ReportRoutes,
        ...ManagementRoutes,
        ...InformationRoutes,
        ...ErrorRoutes,

        ...PubRoutes,

        ...testRoutes,
    ],
});

router.beforeEach(async (to, from, next) => {
    if (to.meta.unauthorized) {
        store.getters['isLoggedIn'] ? next('/') : next();
    } else {
        if (store.getters['isLoggedIn'] || getAuthFromCookie()) {
            next();
        } else {
            next('/login');
        }
    }
});
export default router;
