import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/foundation',
        component: pages('report/index.vue'),
        redirect: '/foundation/vms',
        children: [
            {
                path: 'vms',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                },
            },
            {
                path: 'digital',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                },
            },
            {
                path: 'rb',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                },
            },
        ],
    },
];

export default routes;
