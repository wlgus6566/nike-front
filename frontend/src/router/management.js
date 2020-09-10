import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/management',
        component: pages('management'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'MANAGEMENT'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex((el) => el.listYn === 'Y');

            return store.state.gnbMenuListData[depth1Idx].menus[depth2Idx]
                .menuPathUrl;
        },
        children: [
            {
                path: 'visual',
                component: pages('management/visual'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '메인 비주얼 관리',
                    topMenuCode: 'MANAGEMENT',
                    menuCode: 'MANAGEMENT_VISUAL',
                },
            },
            {
                path: 'account',
                component: pages('management/account'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '계정관리',
                    topMenuCode: 'MANAGEMENT',
                    menuCode: 'MANAGEMENT_ACCOUNT',
                },
            },
            {
                path: 'group',
                component: pages('management/group'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '권한 그룹관리',
                    topMenuCode: 'MANAGEMENT',
                    menuCode: 'MANAGEMENT_GROUP',
                },
            },
        ],
    },
];
export default routes;
