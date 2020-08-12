import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/toolkit',
        component: pages('report/index.vue'),
        redirect: '/toolkit/vms',
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
                path: 'social',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                },
            },
        ],
    },
];

export default routes;
