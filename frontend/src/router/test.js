import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/sampleBoardList',
        component: pages('sampleBoard/sampleBoardList'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
    },
    {
        path: '/file',
        component: pages('file'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
    },
    {
        path: '/sampleBoardDetail',
        component: pages('sampleBoard/sampleBoardDetail'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
    },
];
export default routes;
