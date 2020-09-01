import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/information',
        component: pages('information/index.vue'),
        redirect: () => {
            const idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'INFORMATION'
            );
            return store.state.gnbMenuListData[idx].menus[0].menuPathUrl;
        },
        children: [
            {
                path: 'agency',
                component: pages('information/agency.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: 'AGENCY CONTACT',
                },
            },
            {
                path: 'calendar',
                component: pages('information/calendar.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: 'CALENDAR',
                },
            },
        ],
    },
];
export default routes;
