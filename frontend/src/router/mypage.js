import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/mypage/upload',
        component: pages('mypage/upload.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '내가 업로드한 폴더',
        },
    },
    {
        path: '/mypage/latest',
        component: pages('mypage/latest.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '최근 본 폴더',
        },
    },
    {
        path: '/mypage/order',
        component: pages('mypage/order.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '주문내역확인',
        },
    },
    {
        path: '/mypage/wish-list',
        component: pages('mypage/wish-list.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '위시리스트',
        },
    },
    {
        path: '/mypage/info',
        component: pages('mypage/info.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '회원정보 조회',
        },
    },
    {
        path: '/mypage/pawd',
        component: pages('mypage/pawd.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '비밀번호 변경',
        },
    },
    //공지사항
    {
        path: '/mypage/notice/',
        component: pages('mypage/notice'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NOTICE',
            title: '공지사항',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NOTICE',
        },
    },
    {
        //TODO detail 삭제
        path: '/mypage/notice/detail/:id',
        component: pages('mypage/detail'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NOTICE',
            title: '공지사항',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NOTICE',
        },
    },
    {
        path: '/mypage/notice/form',
        component: pages('mypage/notice-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NOTICE',
            title: '공지사항',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NOTICE',
        },
    },
    {
        path: '/mypage/notice/modify/:id',
        component: pages('mypage/notice-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NOTICE',
            title: '공지사항',
            modify: true,
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NOTICE',
        },
    },
    //NEWS
    {
        path: '/mypage/news/',
        component: pages('mypage/news.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NEWS',
            title: 'NEWS',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NEWS',
        },
    },
    {
        path: '/mypage/news/detail/:id',
        component: pages('mypage/detail'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NEWS',
            title: 'NEWS',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NEWS',
        },
    },
    {
        path: '/mypage/news/form',
        component: pages('mypage/news-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NEWS',
            title: 'NEWS',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NEWS',
        },
    },
    {
        path: '/mypage/news/modify/:id',
        component: pages('mypage/news-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'NEWS',
            title: 'NEWS',
            modify: true,
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_NEWS',
        },
    },
    //FAQ
    {
        path: '/mypage/faq',
        component: pages('mypage/faq.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'QNA',
            title: '자주 묻는 질문',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_FAQ',
        },
    },
    {
        path: '/mypage/faq/form',
        component: pages('mypage/faq-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'QNA',
            title: '자주 묻는 질문',
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_FAQ',
        },
    },
    {
        path: '/mypage/faq/modify/:id',
        component: pages('mypage/faq-form'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            sectionCode: 'QNA',
            title: '자주 묻는 질문',
            modify: true,
            topMenuCode: 'MYPAGE',
            menuCode: 'CUSTOMER_FAQ',
        },
    },
];

export default routes;
