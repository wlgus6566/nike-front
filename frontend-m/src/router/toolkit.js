import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/toolkit',
        component: pages('root'),
        redirect: () => {
            const depth1Idx = store.state.menuData.findIndex(
                el => el.menuCode === 'TOOLKIT'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex(el => el.listYn === 'Y');
            return store.state.menuData[depth1Idx].menus[depth2Idx].menuPathUrl;
        },
        children: [
            {
                path: 'vms',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'VMS',
                    title: 'VMS',
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'EKIN',
                    title: 'EKIN',
                },
            },
            {
                path: 'social',
                component: pages('common/folder-list.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'SOCIAL',
                    title: 'SOCIAL',
                },
            },
            {
                path: 'rb',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'RB',
                    title: 'RB',
                },
            },
            {
                path: '*/:id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    detail: true,
                    btn: false,
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = ['vms', 'ekin', 'social', 'rb'];
                    const findMenuCode = menuCodeArr.some(
                        el => el === to.params.pathMatch
                    );
                    if (findMenuCode) {
                        to.meta.menuCode = menuCodeArr[findMenuCode];
                        next();
                    } else {
                        next('/404'); //todo 404 만들기
                    }
                },
            },
        ],
    },
];

export default routes;
