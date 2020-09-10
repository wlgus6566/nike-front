import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/asset',
        component: pages('root'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'ASSET'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex((el) => el.listYn === 'Y');

            return store.state.gnbMenuListData[depth1Idx].menus[depth2Idx]
                .menuPathUrl;
        },
        children: [
            {
                path: 'all',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    menuCode: 'ASSET_ALL',
                    title: 'ALL',
                },
            },
            {
                path: 'sp',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    menuCode: 'ASSET_SP',
                    title: 'SP',
                },
            },
            {
                path: 'su',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    menuCode: 'ASSET_SU',
                    title: 'SU',
                },
            },
            {
                path: 'fa',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    menuCode: 'ASSET_FA',
                    title: 'FA',
                },
            },
            {
                path: 'ho',
                component: pages('common/folder-list'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    menuCode: 'ASSET_HO',
                    title: 'HO',
                },
            },
            {
                path: 'upload',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    title: 'UPLOAD',
                },
            },
            {
                path: '*/modify/:id',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                    title: 'MODIFY',
                },
            },
            {
                path: '*/:id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'ASSET',
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = [
                        'ASSET_SP',
                        'ASSET_SU',
                        'ASSET_FA',
                        'ASSET_HO',
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
