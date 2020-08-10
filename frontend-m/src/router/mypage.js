import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/mypage',
        name: 'mypage',
        component: pages('mypage/index.vue'),
        meta: {
            layout: 'Index',
            pageTitle: '',
            title: '마이페이지 메인',
        },
    },
    {
        path: '/mypage',
        component: pages('root'),
        children: [
            {
                path: 'history/upload',
                component: pages('mypage/upload.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'HISTORY',
                    tab: [
                        {
                            title: '최근 업로드한 폴더',
                            value: 'upload',
                        },
                        {
                            title: '최근 본 폴더',
                            value: 'latest',
                        },
                    ],
                },
            },
            {
                path: 'history/folder',
                component: pages('mypage/latest.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'HISTORY',
                    tab: [
                        {
                            title: '최근 업로드한 폴더',
                            value: 'upload',
                        },
                        {
                            title: '최근 본 폴더',
                            value: 'latest',
                        },
                    ],
                },
            },
            {
                path: 'myinfo/info',
                component: pages('mypage/info.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'MY INFO',
                    tab: [
                        {
                            title: '회원정보 조회',
                            value: 'info',
                        },
                        {
                            title: '비밀번호 변경',
                            value: 'password',
                        },
                    ],
                },
            },
            {
                path: 'myinfo/password',
                component: pages('mypage/password.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'MY INFO',
                    tab: [
                        {
                            title: '회원정보 조회',
                            value: 'info',
                        },
                        {
                            title: '비밀번호 변경',
                            value: 'password',
                        },
                    ],
                },
            },
            {
                path: 'customer/notice',
                component: pages('mypage/notice-list.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'CUSTOMER CENTER',
                    tab: [
                        {
                            title: '공지사항',
                            value: 'notice',
                        },
                        {
                            title: 'NEWS',
                            value: 'news',
                        },
                        {
                            title: '자주묻는질문',
                            value: 'faq',
                        },
                    ],
                },
            },
            {
                path: 'customer/news',
                component: pages('mypage/news-list.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'CUSTOMER CENTER',
                    historyBack: true,
                    tab: [
                        {
                            title: '공지사항',
                            value: 'notice',
                        },
                        {
                            title: 'NEWS',
                            value: 'news',
                        },
                        {
                            title: '자주묻는질문',
                            value: 'faq',
                        },
                    ],
                },
            },
            {
                path: 'customer/faq',
                component: pages('mypage/faq-list.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'CUSTOMER CENTER',
                    tab: [
                        {
                            title: '공지사항',
                            value: 'notice',
                        },
                        {
                            title: 'NEWS',
                            value: 'news',
                        },
                        {
                            title: '자주묻는질문',
                            value: 'faq',
                        },
                    ],
                },
            },
        ],
    },
];

export default routes;
