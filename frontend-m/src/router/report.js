import {pages} from '@/utils/global-methods';

const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
        redirect: '/report/upload',
        children: [
            {
                path: 'upload',
                component: pages('report/upload.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    title: 'REPORT UPLOAD',
                },
            },
            {
                path: 'management',
                component: pages('report/management.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    title: 'REPORT 관리',
                },
            },
            {
                path: '*/:id',
                component: pages('report/detail-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    topMenuCode: 'REPORT 상세',
                },
                beforeEnter: (to, from, next) => {
                    const menuCodeArr = ['sp', 'su', 'fa', 'ho'];
                    const findMenuCode = menuCodeArr.findIndex(
                        (el) => el === to.params.pathMatch
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
