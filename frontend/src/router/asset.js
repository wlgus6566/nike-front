import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/asset',
        name: 'ASSET',
        component: pages('asset'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
        redirect: '/asset/all',
        children: [
            {
                path: 'upload',
                name: 'UPLOAD',
                component: pages('asset/upload.vue'),
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'all',
                name: 'ALL',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
            },
            {
                path: 'sp',
                name: 'SP',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SP' },
            },
            {
                path: 'su',
                name: 'SU',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SU' },
            },
            {
                path: 'fa',
                name: 'FA',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'FA' },
            },
            {
                path: 'ho',
                name: 'HO',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'HO' },
            },
        ],
    },
];
export default routes;
