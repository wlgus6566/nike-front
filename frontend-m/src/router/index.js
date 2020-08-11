import Vue from 'vue';
import VueRouter from 'vue-router';

// Routes
import FoundationRoutes from './foundation';
import TookitRoutes from './tookit';
import RepotRoutes from './report';
import InformationRoutes from './information';
import MyPageRoutes from './mypage';

// Import methods
import { pages } from '@/utils/global-methods';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: pages('main-page'),
            meta: { layout: 'Default' },
        },
        ...TookitRoutes,
        ...FoundationRoutes,
        ...RepotRoutes,
        ...InformationRoutes,
        ...MyPageRoutes,
    ],
});

router.beforeEach((to, from, next) => {
    /*if (to.meta.unauthorized) {
        if (store.getters['isLoggedIn'] || getAuthFromCookie()) {
            next('/');
        } else {
            next();
        }
    } else {
        if (store.getters['isLoggedIn'] || getAuthFromCookie()) {
            next();
        } else {
            next('/login');
        }
    }*/
    next();
});
export default router;
