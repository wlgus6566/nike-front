import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/foundation',
        component: pages('foundation'),
        redirect: '/foundation/vms',
        children: [
            {
                path: 'upload',
                component: pages('foundation/upload'),
                meta: { layout: 'Default', aside: 'Order', title: 'UPLOAD' },
            },
            {
                path: 'vms',
                component: pages('foundation/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'VMS' },
            },
            {
                path: 'ekin',
                component: pages('foundation/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'EKIN' },
            },
            {
                path: 'Digital',
                component: pages('foundation/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'DIGITAL' },
            },
            {
                path: 'rb',
                component: pages('foundation/list'),
                meta: { layout: 'Default', aside: 'Order', title: 'RB' },
            },
        ],
    },
];
export default routes;
