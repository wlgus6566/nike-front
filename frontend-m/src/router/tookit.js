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
                    pageTitle: 'TOOLKIT',
                    tab: [
                        {
                            title: 'VMS',
                            value: 'VMS',
                        },
                        {
                            title: 'EKIN',
                            value: 'EKIN',
                        },
                        {
                            title: 'SOCIAL',
                            value: 'SOCIAL',
                        },
                        {
                            title: 'RB',
                            value: 'RB',
                        },
                    ],
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'TOOLKIT',
                    tab: [
                        {
                            title: 'VMS',
                            value: 'VMS',
                        },
                        {
                            title: 'EKIN',
                            value: 'EKIN',
                        },
                    ],
                },
            },
            {
                path: 'social',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'TOOLKIT',
                    tab: [
                        {
                            title: 'VMS',
                            value: 'VMS',
                        },
                        {
                            title: 'EKIN',
                            value: 'EKIN',
                        },
                    ],
                },
            },
        ],
    },
];

export default routes;
