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
                path: 'notice',
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
                path: 'news',
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
                path: 'faq',
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
