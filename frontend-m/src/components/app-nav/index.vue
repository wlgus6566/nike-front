<template>
    <nav class="nav-area">
        <ul class="tab-bar">
            <li>
                <a href="#">
                    <i class="icon-home"></i>
                    <span>HOME</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <i class="icon-report"></i>
                    <span>REPORT</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <i class="icon-my"></i>
                    <span>MY</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <i class="icon-menu"></i>
                    <span>MENU</span>
                </a>
            </li>
        </ul>
        <div class="aside-wrap">
            <div class="inner">
                <div class="user-info">
                    <span class="store-name">홍대 SKNRS</span>
                    <p class="store">WINWIN OFFICE</p>
                    <a href="mailto:nike@win-win.co.kr" class="mail"
                        >nike@win-win.co.kr</a
                    >
                </div>
                <ul class="menu-list">
                    <li v-for="(item, index) in navItem" :key="index">
                        <a href="#">
                            <span>{{ item.title }}</span>
                        </a>
                        <ul class="depth" v-if="item.children">
                            <li
                                v-for="(depth, index) in item.children"
                                :key="index"
                            >
                                <a href="#">{{ depth.title }}</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <button class="btn-close">닫기</button>
            </div>
        </div>
    </nav>
</template>
<script>
export default {
    name: 'navigation',
    data() {
        return {
            navItems: [
                {
                    title: 'HOME',
                    titleKo: '메인',
                    to: '/',
                    exact: true,
                    menuUse: 'Y',
                    navUse: 'N',
                },
                {
                    title: 'TOOLKIT',
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
                    title: 'FOUNDATION',
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
                    title: 'REPORT',
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
                    title: 'INFORMATION',
                    to: '/information/agency',
                    exact: false,
                    menuUse: 'N',
                    navUse: 'Y',
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
                    title: 'MYPAGE',
                    to: '/mypage',
                    exact: false,
                    menuUse: 'Y',
                    navUse: 'N',
                    children: [
                        {
                            title: `CUSTOMER CENTER`,
                            to: '/mypage/notice',
                            exact: false,
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
                        {
                            title: `history`,
                            to: '/information/calendar',
                            exact: false,
                        },
                    ],
                },
            ],
        };
    },
    created() {
        //console.log(this.navItems);
        //console.log(this.navItem);
    },
    computed: {
        navItem() {
            return this.navItems.filter(el => el.navUse === 'Y');
        },
    },
};
</script>
<style scoped></style>
