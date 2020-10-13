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
        path: '/password-set',
        component: pages('login/password-set'),
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
    /*{
        path: '/password-change',
        component: pages('login/password-change'),
        meta: {
            layout: 'Clean',
        },
    },*/
    {
        path: '/password-change',
        name: 'password-change',
        component: pages('login/password-change'),
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
