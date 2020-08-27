import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/terms/privacy',
        component: pages('terms/privacy.vue'),
        meta: {
            layout: 'Default',
            aside: 'Default',
            title: '개인정보 처리방침',
        },
    },
    {
        path: '/terms/service',
        component: pages('terms/service.vue'),
        meta: { layout: 'Default', aside: 'Default', title: '이용약관' },
    },
];
export default routes;
