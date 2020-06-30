import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/asset',
        name: 'asset',
        component: pages('asset/list'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
        children: [
            {
                path: 'upload',
                name: 'UPLOAD',
                component: pages('asset/upload'),
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'sp',
                name: 'sp',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SP' },
            },
            {
                path: 'su',
                name: 'su',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SU' },
            },
            {
                path: 'fa',
                name: 'fa',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'FA' },
            },
            {
                path: 'ho',
                name: 'ho',
                component: pages('asset/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'HO' },
            },
        ],
    },
];
export default routes;
