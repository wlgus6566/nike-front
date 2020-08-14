import { pages } from '@/utils/global-methods';

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
                    aside: 'Report',
                    title: 'REPORT <span class="ko">업로드</span>',
                },
            },
            {
                path: '*/modify/:id',
                component: pages('report/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    topMenuCode: 'REPORT <span class="ko">업로드 수정</span>',
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
            {
                path: 'management',
                component: pages('report/management.vue'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    title: 'REPORT <span class="ko">관리</span>',
                },
            },
            {
                path: '*/:id',
                component: pages('report/detail-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
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
