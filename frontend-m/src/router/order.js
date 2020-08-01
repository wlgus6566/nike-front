import {pages} from '@/utils/global-methods';

const routes = [
    {
        path: '/order',
        component: pages('product'),
        meta: { layout: 'Default', aside: 'Order', title: '부자재 주문' },
        redirect: '/order/subsidiary/operating',
        children: [
            {
                path: 'registration',
                component: pages('product/registration'),
                meta: { layout: 'Default', aside: 'Order', title: '상품 등록' },
            },
            {
                path: 'registration/:id',
                component: pages('product/registration'),
                meta: { layout: 'Default', aside: 'Order', title: '상품 수정' },
            },
            {
                path: 'management',
                component: pages('product/management'),
                meta: { layout: 'Default', aside: 'Order', title: '상품 관리' },
            },
            {
                path: 'complete',
                component: pages('product/complete'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    title: '주문완료 안내',
                },
            },
            {
                path: 'subsidiary',
                component: pages('root'),
                meta: { layout: 'Default', aside: 'Order', title: '부자재' },
                redirect: '/order/subsidiary/operating',
                children: [
                    {
                        path: 'operating',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY21',
                            title: '운영 비품',
                        },
                    },
                    {
                        path: 'staff',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY22',
                            title: '스태프 비품',
                        },
                    },
                    {
                        path: 'operating-signage',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY23',
                            title: '운영 사이니지',
                        },
                    },
                    {
                        path: 'sale-signage',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY24',
                            title: '세일 사이니지',
                        },
                    },
                    {
                        path: 'sale-signage/:id',
                        component: pages('order/order-view'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY24',
                            title: '세일 사이니지',
                        },
                    },
                    {
                        path: 'open-package',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY25',
                            title: '오픈 패키지',
                        },
                    },
                    {
                        path: 'nike-golf',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY26',
                            title: '나이키 골프',
                        },
                    },
                    {
                        path: 'open-package/:id',
                        component: pages('order/order-view'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'SUBSIDIARY',
                            category3Code: 'SUBSIDIARY25',
                            title: '오픈 패키지',
                        },
                    },
                ],
            },
            {
                path: 'niki-by-you',
                component: pages('root'),
                meta: {
                    layout: 'Default',
                    aside: 'Order',
                    title: 'Niki By You',
                },
                redirect: '/order/niki-by-you/shoes',
                children: [
                    {
                        path: 'shoes',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'NIKE_BY_YOU',
                            category3Code: 'NIKE_BY_YOU27',
                            title: '신발 커스텀 (단품)',
                        },
                    },
                    {
                        path: 'shoes-package',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'NIKE_BY_YOU',
                            category3Code: 'NIKE_BY_YOU28',
                            title: '신발 커스텀 (패키지)',
                        },
                    },
                    {
                        path: 'apparel',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'NIKE_BY_YOU',
                            category3Code: 'NIKE_BY_YOU29',
                            title: '의류 커스텀 (단품)',
                        },
                    },
                    {
                        path: 'apparel-package',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'NIKE_BY_YOU',
                            category3Code: 'NIKE_BY_YOU30',
                            title: '의류 커스텀 (패키지)',
                        },
                    },
                    {
                        path: 'others',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'NIKE_BY_YOU',
                            category3Code: 'NIKE_BY_YOU31',
                            title: 'OTHERS',
                        },
                    },
                ],
            },
            {
                path: 'custom23',
                component: pages('root'),
                meta: { layout: 'Default', aside: 'Order', title: 'CUSTOM23' },
                redirect: '/order/custom23/shoes',
                children: [
                    {
                        path: 'shoes',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'CUSTOM23',
                            category3Code: 'CUSTOM2332',
                            title: '신발 커스텀 (단품)',
                        },
                    },
                    {
                        path: 'shoes-package',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'CUSTOM23',
                            category3Code: 'CUSTOM2333',
                            title: '신발 커스텀 (패키지)',
                        },
                    },
                    {
                        path: 'apparel',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'CUSTOM23',
                            category3Code: 'CUSTOM2334',
                            title: '의류 커스텀 (단품)',
                        },
                    },
                    {
                        path: 'apparel-package',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'CUSTOM23',
                            category3Code: 'CUSTOM2335',
                            title: '의류 커스텀 (패키지)',
                        },
                    },
                    {
                        path: 'others',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'CUSTOM23',
                            category3Code: 'CUSTOM2336',
                            title: 'OTHERS',
                        },
                    },
                ],
            },
            {
                path: 'mnq',
                component: pages('root'),
                meta: { layout: 'Default', aside: 'Order', title: 'MNQ' },
                redirect: '/order/mnq/male',
                children: [
                    {
                        path: 'male',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'MNQ',
                            category3Code: 'MNQ37',
                            title: '남성',
                        },
                    },
                    {
                        path: 'female',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'MNQ',
                            category3Code: 'MNQ38',
                            title: '여성',
                        },
                    },
                    {
                        path: 'child',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'MNQ',
                            category3Code: 'MNQ39',
                            title: '유아동',
                        },
                    },
                    {
                        path: 'repair',
                        component: pages('product/product-list'),
                        meta: {
                            layout: 'Default',
                            aside: 'Order',
                            category2Code: 'MNQ',
                            category3Code: 'MNQ40',
                            title: '수리/보수',
                        },
                    },
                ],
            },
        ],
    },
];
export default routes;
