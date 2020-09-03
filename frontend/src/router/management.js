import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/management',
        component: pages('management'),
        redirect: () => {
            const idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'MANAGEMENT'
            );
            return store.state.gnbMenuListData[idx].menus[0].menuPathUrl;
        },
        children: [
            {
                path: 'visual',
                component: pages('management/visual'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '메인 비주얼 관리',
                },
            },
            {
                path: 'account',
                component: pages('management/account'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '계정관리',
                },
            },
            {
                path: 'group',
                component: pages('management/group'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '권한 그룹관리',
                },
            },
        ],
    },
];
export default routes;
