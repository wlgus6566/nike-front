import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/asset',
        component: pages('root'),
        redirect: '/asset/all',
        children: [
            {
                path: 'all',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: 'ALL',
                    title: 'ALL',
                },
            },
            {
                path: 'sp',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: 'SP',
                    title: 'SP',
                },
            },
            {
                path: 'su',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: 'SU',
                    title: 'SU',
                },
            },
            {
                path: 'fa',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: 'FA',
                    title: 'FA',
                },
            },
            {
                path: 'ho',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: 'HO',
                    title: 'HO',
                },
            },
            {
                path: 'upload',
                component: pages('asset/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    title: 'UPLOAD',
                },
            },
            {
                path: ':id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                },
            },
        ],
    },
];
export default routes;
