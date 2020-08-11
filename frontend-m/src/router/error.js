import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '404',
        component: pages('error/404'),
        meta: { layout: 'Clean' },
    },
    {
        path: '500',
        component: pages('error/500'),
        meta: { layout: 'Clean' },
    },
];

if (typeof window !== 'undefined') {
    routes.push({
        path: '*',
        name: '404',
        component: pages('error/404'),
        meta: { layout: 'Clean' },
    });
}
export default routes;
