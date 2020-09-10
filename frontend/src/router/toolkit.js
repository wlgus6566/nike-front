import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/toolkit',
        component: pages('root'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'TOOLKIT'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex((el) => el.listYn === 'Y');

            return store.state.gnbMenuListData[depth1Idx].menus[depth2Idx]
                .menuPathUrl;
        },
        children: [
            {
                path: 'vms',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'TOOLKIT_VMS',
                    title: 'VMS',
                },
            },
            {
                path: 'ekin',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'TOOLKIT_EKIN',
                    title: 'EKIN',
                },
            },
            {
                path: 'social',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    menuCode: 'TOOLKIT_SOCIAL',
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
                    menuCode: 'TOOLKIT_RB',
                    title: 'RB',
                },
            },
            {
                path: 'upload',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    title: 'UPLOAD',
                },
            },
            {
                path: '*/modify/:id',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                    title: 'MODIFY',
                },
            },
            {
                path: '*/:id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'TOOLKIT',
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = [
                        'TOOLKIT_VMS',
                        'TOOLKIT_EKIN',
                        'TOOLKIT_SOCIAL',
                        'TOOLKIT_RB',
                    ];
                    const findMenuCode = menuCodeArr.findIndex(
                        (el) =>
                            el ===
                            `${
                                to.meta.topMenuCode
                            }_${to.params.pathMatch.toUpperCase()}`
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
