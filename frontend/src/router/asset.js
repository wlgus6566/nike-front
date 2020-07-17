import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/asset',
        component: pages('asset'),
        meta: {
            layout: 'Default',
            aside: 'Order',
            topMenuCode: 'ASSET',
            menuCode: 'ALL',
            title: 'ALL',
        },
        redirect: '/asset/all',
        children: [
            {
                path: 'upload',
                component: pages('asset/upload.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    title: 'UPLOAD',
                },
            },
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
                path: ':id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    menuCode: '',
                    title: '',
                },
            },
        ],
    },
];
export default routes;
