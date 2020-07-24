import { pages } from '@/utils/global-methods';

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
        component: pages('login/agree'),
        meta: {
            layout: 'Clean',
        },
    },
    {
        path: '/password-set',
        component: pages('login/passwordSet'),
        meta: {
            layout: 'Clean',
        },
    },
    {
        path: '/password-change',
        component: pages('login/passwordChange'),
        meta: {
            layout: 'Clean',
        },
    },
];
export default routes;
