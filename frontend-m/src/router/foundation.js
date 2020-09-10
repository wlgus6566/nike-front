import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/foundation',
        component: pages('root'),
        redirect: () => {
            const depth1Idx = store.state.menuData.findIndex(
                el => el.menuCode === 'FOUNDATION'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex(el => el.listYn === 'Y');
            return store.state.menuData[depth1Idx].menus[depth2Idx].menuPathUrl;
        },
        children: [
            {
                path: 'vms',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'VMS',
                    title: 'VMS',
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'EKIN',
                    title: 'EKIN',
                },
            },
            {
                path: 'digital',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'DIGITAL',
                    title: 'DIGITAL',
                },
            },
            {
                path: 'rb',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
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
                    topMenuCode: 'FOUNDATION',
                    detail: true,
                    btn: false,
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = ['vms', 'ekin', 'digital', 'rb'];
                    const findMenuCode = menuCodeArr.findIndex(
                        el => el === to.params.pathMatch
                    );
                    if (findMenuCode !== -1) {
                        to.meta.menuCode = menuCodeArr[findMenuCode];
                        next();
                    } else {
                        next('404'); //todo 404 만들기
                    }
                },
            },
        ],
    },
];

export default routes;
