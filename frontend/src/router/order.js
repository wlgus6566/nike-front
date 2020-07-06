import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/order',
        component: pages('order'),
        meta: { layout: 'Default', aside: 'Order', title: '부자재 주문' },
        redirect: '/order/subsidiary/operating',
        children: [
            {
                path: 'registration',
                component: pages('order/registration'),
                meta: { layout: 'Default', aside: 'Order', title: '상품 등록' },
            },
            {
                path: 'management',
                component: pages('order/management'),
                meta: { layout: 'Default', aside: 'Order', title: '상품 관리' },
            },
            {
                path: 'complete',
                component: pages('order/complete'),
                meta: { layout: 'Default', aside: 'Order', title: '주문완료 안내' },
            },
            {
                path: 'subsidiary',
                component: pages('root'),
                meta: { layout: 'Default', aside: 'Order', title: '부자재' },
                redirect: '/order/subsidiary/operating',
                children: [
                    {
                        path: 'operating',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '운영 비품' },
                    },
                    {
                        path: 'staff',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '스태프 비품' },
                    },
                    {
                        path: 'operating-signage',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '운영 사이니지' },
                    },
                    {
                        path: 'sale-signage',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '세일 사이니지' },
                    },
                    {
                        path: 'sale-signage/:id',
                        component: pages('order/view'),
                        meta: { layout: 'Default', aside: 'Order', title: '오픈 패키지' },
                    },
                    {
                        path: 'open-package',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '오픈 패키지' },
                    },
                    {
                        path: 'open-package/:id',
                        component: pages('order/view'),
                        meta: { layout: 'Default', aside: 'Order', title: '오픈 패키지' },
                    },
                ],
            },
            {
                path: 'niki-by-you',
                component: pages('root'),
                meta: { layout: 'Default', aside: 'Order', title: 'Niki By You' },
                redirect: '/order/niki-by-you/shoes',
                children: [
                    {
                        path: 'shoes',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '신발 커스텀 (단품)' },
                    },
                    {
                        path: 'shoes-package',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '신발 커스텀 (패키지)' },
                    },
                    {
                        path: 'apparel',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '의류 커스텀 (단품)' },
                    },
                    {
                        path: 'apparel-package',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '의류 커스텀 (패키지)' },
                    },
                    {
                        path: 'others',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: 'OTHERS' },
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
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '신발 커스텀 (단품)' },
                    },
                    {
                        path: 'shoes-package',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '신발 커스텀 (패키지)' },
                    },
                    {
                        path: 'apparel',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '의류 커스텀 (단품)' },
                    },
                    {
                        path: 'apparel-package',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '의류 커스텀 (패키지)' },
                    },
                    {
                        path: 'others',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: 'OTHERS' },
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
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '남성' },
                    },
                    {
                        path: 'female',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '여성' },
                    },
                    {
                        path: 'child',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '유아동' },
                    },
                    {
                        path: 'repair',
                        component: pages('order/list'),
                        meta: { layout: 'Default', aside: 'Order', title: '수리/보수' },
                    },
                ],
            },
        ],
    },
];
export default routes;
