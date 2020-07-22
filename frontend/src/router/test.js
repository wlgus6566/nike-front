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
    /* sg */
    /* news 목록 */
    {
        path: '/newsList',
        component: pages('testPage/customerenter/newslist'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
    },
    /* news & 공지사항 상세 */
    {
        path: '/myview',
        component: pages('testPage/customercenter/view'),
        meta: { layout: 'Default', aside: 'Order', title: 'ALL' },
    },
];
export default routes;
