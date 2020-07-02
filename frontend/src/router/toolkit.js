import { pages } from '@/util/global-methods';

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
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'vms',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'VMS' },
            },
            {
                path: 'ekin',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'EKIN' },
            },
            {
                path: 'social',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SOCIAL' },
            },
            {
                path: 'rb',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'RB' },
            },
        ],
    },
];
export default routes;
