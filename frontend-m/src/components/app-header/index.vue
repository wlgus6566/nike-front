<template>
    <header :class="{ 'page-header': $route.meta.pageTitle }">
        <h1 class="logo" v-if="this.$route.path === '/'">
            <a href="#"><span>나이키</span></a>
        </h1>
        <template v-else>
            <button
                type="button"
                class="btn-back"
                v-if="$route.meta.historyBack !== null"
            >
                뒤로가기
            </button>
            <div class="inner" v-if="$route.meta.pageTitle">
                <h1 class="page-title">{{ $route.meta.pageTitle }}</h1>
                <NabItem :menuItem="menuItem" />
            </div>
        </template>
    </header>
</template>
<script>
import NabItem from '@/components/app-header/nav-item';
export default {
    name: 'headerIndex',
    data() {
        return {
            navItems: [
                {
                    pageTitle: 'HOME',
                    titleKo: '메인',
                    to: '/',
                    exact: true,
                    menuUse: 'Y',
                    navUse: 'N',
                },
                {
                    pageTitle: 'TOOLKIT',
                    to: '/toolkit/vms',
                    exact: false,
                    menuUse: 'N',
                    navUse: 'Y',
                    children: [
                        {
                            title: `VMS`,
                            to: '/toolkit/vms',
                            exact: false,
                        },
                        {
                            title: `EKIN`,
                            to: '/toolkit/ekin',
                            exact: false,
                        },
                        {
                            title: `SOCIAL`,
                            to: '/toolkit/social',
                            exact: false,
                        },
                        {
                            title: `RB`,
                            to: '/toolkit/rb',
                            exact: false,
                        },
                    ],
                },
                {
                    pageTitle: 'FOUNDATION',
                    to: '/foundation/vms',
                    exact: false,
                    menuUse: 'N',
                    navUse: 'Y',
                    children: [
                        {
                            title: `VMS`,
                            to: '/foundation/vms',
                            exact: false,
                        },
                        {
                            title: `EKIN`,
                            to: '/foundation/ekin',
                            exact: false,
                        },
                        {
                            title: `DIGITAL`,
                            to: '/foundation/digital',
                            exact: false,
                        },
                        {
                            title: `RB`,
                            to: '/foundation/rb',
                            exact: false,
                        },
                    ],
                },
                {
                    pageTitle: 'REPORT',
                    to: '/report/report',
                    exact: false,
                    menuUse: 'Y',
                    navUse: 'Y',
                    children: [
                        {
                            title: `업로드`,
                            to: '/report/report',
                            exact: false,
                        },
                        {
                            title: `관리`,
                            to: '/report/management',
                            exact: false,
                        },
                    ],
                },
                {
                    pageTitle: 'INFORMATION',
                    to: '/information/agency',
                    exact: false,
                    navUse: 'Y',
                    myPage: 'N',
                    children: [
                        {
                            title: `AGENCY`,
                            to: '/information/agency',
                            exact: false,
                        },
                        {
                            title: `CALENDAR`,
                            to: '/information/calendar',
                            exact: false,
                        },
                    ],
                },
                {
                    pageTitle: 'MYPAGE',
                    to: '/mypage',
                    exact: false,
                    menuUse: 'Y',
                    navUse: 'N',
                },
                {
                    pageTitle: `CUSTOMER CENTER`,
                    to: '/mypage/notice',
                    exact: false,
                    menuUse: 'N',
                    navUse: 'N',
                    children: [
                        {
                            title: `공지사항`,
                            to: '/mypage/notice',
                            exact: false,
                        },
                        {
                            title: `news`,
                            to: '/mypage/news',
                            exact: false,
                        },
                        {
                            title: `자주묻는질문`,
                            to: '/mypage/faq',
                            exact: false,
                        },
                    ],
                },
            ],
        };
    },
    created() {
        //console.log(this.menuItem);
    },
    computed: {
        menuItem() {
            const path = this.$route.path.split('/').slice(0)[1];
            //const path = this.$route.path;
            //console.log(path);
            const index = this.navItems.findIndex(
                el => el.to.split('/').slice(0)[1] === path
            );
            return this.navItems[index];
        },
    },
    watch: {},
    components: {
        NabItem,
    },
    methods: {},
};
</script>
<style scoped></style>
