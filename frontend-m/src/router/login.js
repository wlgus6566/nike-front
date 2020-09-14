import { pages } from '@/utils/global-methods';
import { certCode } from '@/api/login';

const routes = [
    {
        path: '/login',
        component: pages('login/login'),
        meta: {
            layout: 'Clean',
            unauthorized: true,
        },
    },
    {
        path: '/agree',
        name: 'agree',
        component: pages('login/agree'),
        meta: {
            layout: 'Clean',
            unauthorized: true,
        },
    },
    {
        path: '/pawd-set',
        component: pages('login/pawd-set'),
        meta: {
            layout: 'Clean',
            unauthorized: true,
        },
        beforeEnter: async (to, from, next) => {
            try {
                const { data: response } = await certCode({
                    certCode: encodeURIComponent(to.query.certCode),
                });
                if (response.success) {
                    next();
                } else {
                    if (response.existMsg) {
                        alert(response.msg);
                    }
                    next('/login');
                }
            } catch (error) {
                console.log(error);
                next();
            }
        },
    },
    {
        path: '/pawd-change',
        name: 'pawd-change',
        component: pages('login/pawd-change'),
        meta: {
            layout: 'Clean',
            unauthorized: true,
        },
        beforeEnter: async (to, from, next) => {
            try {
                const { data: response } = await certCode({
                    certCode: to.params.certCode,
                });
                if (response.success) {
                    next();
                } else {
                    if (response.existMsg) {
                        alert(response.msg);
                    }
                    next('/login');
                }
            } catch (error) {
                console.log(error);
                next();
            }
        },
    },
];
export default routes;
