import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/toolkit',
        component: pages('toolkit'),
        meta: { layout: 'Default', aside: 'Order', title: '' },
        redirect: '/toolkit/vms',
        children: [
            {
                path: 'upload',
                component: pages('toolkit/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'TOOLKIT',
                    title: 'UPLOAD',
                },
            },
            {
                path: 'vms',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'TOOLKIT',
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
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'EKIN',
                    title: 'EKIN',
                },
            },
            {
                path: 'social',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'SOCIAL',
                    title: 'SOCIAL',
                },
            },
            {
                path: 'rb',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'TOOLKIT',
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
                    topMenuCode: 'TOOLKIT',
                    title: '',
                },
            },
        ],
    },
];
export default routes;
