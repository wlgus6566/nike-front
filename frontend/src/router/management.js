import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/management',
        component: pages('management'),
        redirect: '/management/visual',
        children: [
            {
                path: 'visual',
                component: pages('management/visual'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '메인 비쥬얼 관리',
                },
            },
            {
                path: 'account',
                component: pages('management/account'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '계정관리',
                },
            },
            {
                path: 'group',
                component: pages('management/group'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: '권한 그룹관리',
                },
            },
        ],
    },
];
export default routes;
