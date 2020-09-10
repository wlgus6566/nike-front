import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/foundation',
        component: pages('root'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'FOUNDATION'
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
                    topMenuCode: 'FOUNDATION',
                    menuCode: 'FOUNDATION_VMS',
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
                    menuCode: 'FOUNDATION_EKIN',
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
                    menuCode: 'FOUNDATION_DIGITAL',
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
                    menuCode: 'FOUNDATION_RB',
                    title: 'RB',
                },
            },
            {
                path: 'upload',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                    title: 'UPLOAD',
                },
            },
            {
                path: '*/modify/:id',
                component: pages('common/folder-upload'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                    title: 'MODIFY',
                },
            },
            {
                path: '*/:id',
                component: pages('common/folder-view'),
                meta: {
                    layout: 'Default',
                    aside: 'File',
                    topMenuCode: 'FOUNDATION',
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = [
                        'FOUNDATION_VMS',
                        'FOUNDATION_EKIN',
                        'FOUNDATION_DIGITAL',
                        'FOUNDATION_RB',
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
