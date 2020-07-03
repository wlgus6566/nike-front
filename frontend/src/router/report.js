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
                meta: { layout: 'Default', aside: 'Order', title: 'REPORT UPLOAD' },
            },
            {
                path: 'management',
                component: pages('report/management.vue'),
                meta: { layout: 'Default', aside: 'Order', title: 'REPORT 관리' },
            },
        ],
    },
];
export default routes;
