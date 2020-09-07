import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/information',
        component: pages('root'),
        children: [
            {
                path: 'agency',
                component: pages('information/agency.vue'),
                meta: {
                    layout: 'Default',
                },
            },
            {
                path: 'calendar',
                component: pages('information/calendar.vue'),
                meta: {
                    layout: 'Default',
                },
            },
        ],
    },
];

export default routes;
