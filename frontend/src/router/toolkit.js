import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/toolkit',
        name: 'toolkit',
        component: pages('toolkit/list'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
        children: [
            {
                path: 'upload',
                name: 'UPLOAD',
                component: pages('toolkit/upload'),
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'VMS',
                name: 'VMS',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'VMS' },
            },
            {
                path: 'EKIN',
                name: 'EKIN',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'EKIN' },
            },
            {
                path: 'SOCIAL',
                name: 'SOCIAL',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'SOCIAL' },
            },
            {
                path: 'RB',
                name: 'RB',
                component: pages('toolkit/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'RB' },
            },
        ],
    },
];
export default routes;
