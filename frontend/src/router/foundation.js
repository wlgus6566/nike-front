import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/foundation',
        component: pages('foundation'),
        redirect: '/foundation/vms',
        children: [
            {
                path: 'upload',
                name: 'upload',
                component: pages('foundation/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'ASSET',
                    title: 'UPLOAD',
                },
            },
            {
                path: 'vms',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'VMS',
                    title: 'VMS',
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'EKIN',
                    title: 'EKIN',
                },
            },
            {
                path: 'Digital',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'DIGITAL',
                    title: 'DIGITAL',
                },
            },
            {
                path: 'rb',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'RB',
                    title: 'RB',
                },
            },
            {
                path: ':id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'FOUNDATION',
                    title: '',
                },
            },
        ],
    },
];
export default routes;
