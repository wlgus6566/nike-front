import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/toolkit',
        component: pages('report/index.vue'),
        redirect: '/toolkit/vms',
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
                        (el) => el === to.params.pathMatch
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
