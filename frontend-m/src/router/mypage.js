import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/mypage/upload',
        name: 'upload',
        component: pages('mypage/upload.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '내가 업로드한 폴더',
        },
    },
    {
        path: '/mypage/latest',
        name: 'latest',
        component: pages('mypage/latest.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '최근 본 폴더',
        },
    },
    {
        path: '/mypage/order',
        name: 'order',
        component: pages('mypage/order.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '주문내역확인',
        },
    },
    {
        path: '/mypage/wish-list',
        name: 'wish-list',
        component: pages('mypage/wish-list.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '위시리스트',
        },
    },
    {
        path: '/mypage/info',
        name: 'info',
        component: pages('mypage/info.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '회원정보 조회',
        },
    },
    {
        path: '/mypage/password',
        name: 'password',
        component: pages('mypage/password.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '비밀번호 변경',
        },
    },
    {
        path: '/mypage/logout',
        name: 'logout',
        component: pages('mypage/logout.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '로그아웃',
        },
    },
    // customer 상세 공통
    {
        path: '/mypage/view',
        name: 'notice',
        component: pages('mypage/detail-view.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            //title: '공지사항',
        },
    },
    {
        path: '/mypage/notice',
        name: 'notice',
        component: pages('mypage/notice-list.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NOTICE',
            title: '공지사항',
        },
    },
    {
        path: '/mypage/news',
        name: 'news',
        component: pages('mypage/news.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NEWS',
            title: 'NEWS',
        },
    },
    {
        path: '/mypage/form',
        name: 'notice',
        component: pages('mypage/notice-form.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '공지사항',
        },
    },

    {
        path: '/mypage/faq',
        name: 'faq',
        component: pages('mypage/faq.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'QNA',
            title: '자주 묻는 질문',
        },
    },
];

export default routes;
