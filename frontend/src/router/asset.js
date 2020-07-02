import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/asset',
        component: pages('asset'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
        redirect: '/asset/all',
        children: [
            {
                path: 'upload',
                component: pages('asset/upload.vue'),
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'all',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
            },
            {
                path: 'sp',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SP' },
            },
            {
                path: 'su',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SU' },
            },
            {
                path: 'fa',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'FA' },
            },
            {
                path: 'ho',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'HO' },
            },
        ],
    },
];
export default routes;
