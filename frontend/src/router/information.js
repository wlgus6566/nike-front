import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/information',
        component: pages('information/index.vue'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'INFORMATION'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex((el) => el.listYn === 'Y');

            return store.state.gnbMenuListData[depth1Idx].menus[depth2Idx]
                .menuPathUrl;
        },
        children: [
            {
                path: 'agency',
                component: pages('information/agency.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: 'AGENCY CONTACT',
                    topMenuCode: 'INFORMATION',
                    menuCode: 'INFORMATION_CONTACT',
                },
            },
            {
                path: 'calendar',
                component: pages('information/calendar.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: 'CALENDAR',
                    topMenuCode: 'INFORMATION',
                    menuCode: 'INFORMATION_CALENDAR',
                },
            },
        ],
    },
];
export default routes;
