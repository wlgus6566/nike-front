import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/information',
        component: pages('information/index.vue'),
        redirect: '/information/agency',
        children: [
            {
                path: 'agency',
                component: pages('information/agency.vue'),
                meta: { layout: 'Default', aside: 'Order', title: 'AGENCY CONTACT' },
            },
            {
                path: 'calendar',
                component: pages('information/calendar.vue'),
                meta: { layout: 'Default', aside: 'Order', title: 'CALENDAR' },
            },
        ],
    },
];
export default routes;
