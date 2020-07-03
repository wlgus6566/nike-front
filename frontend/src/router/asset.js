import { pages } from '@/utils/global-methods';

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
                component: pages('common/folder-list'),
                meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
            },
            {
                path: 'sp',
                component: pages('common/folder-list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SP' },
            },
            {
                path: 'su',
                component: pages('common/folder-list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SU' },
            },
            {
                path: 'fa',
                component: pages('common/folder-list'),
                meta: { layout: 'Default', aside: 'Order', title: 'FA' },
            },
            {
                path: 'ho',
                component: pages('common/folder-list'),
                meta: { layout: 'Default', aside: 'Order', title: 'HO' },
            },
            {
                path: ':id',
                component: pages('common/folder-view'),
                meta: { layout: 'Default', aside: 'Order', title: '' },
            },
        ],
    },
];
export default routes;
