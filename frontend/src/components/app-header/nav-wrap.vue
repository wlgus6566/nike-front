<template>
    <div>
        <ul>
            <navItem
                v-for="(item, index) in lnb"
                :key="index"
                :navIdx="index"
                :item="item"
                :depth="1"
                :activeIndex="activeIndex"
            />
        </ul>
    </div>
</template>
<script>
import navItem from './nav-item';

export default {
    name: 'nav-wrap',
    data() {
        return {
            /*  lnb: [
                {
                    title: 'HOME',
                    titleKo: '메인',
                    to: '/',
                    exact: true,
                },
                {
                    title: 'ASSET',
                    titleKo: '캠페인 그래픽',
                    to: '/asset',
                    exact: false,
                    children: [
                        {
                            title: 'ALL',
                            to: '/asset/all',
                            exact: false,
                        },
                        {
                            title: 'SP',
                            to: '/asset/sp',
                            exact: false,
                        },
                        {
                            title: 'SU',
                            to: '/asset/su',
                            exact: false,
                        },
                        {
                            title: 'FA',
                            to: '/asset/fa',
                            exact: false,
                        },
                        {
                            title: 'HO',
                            to: '/asset/ho',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'TOOLKIT',
                    titleKo: '메인',
                    to: '/toolkit',
                    exact: false,
                    children: [
                        {
                            title: 'VMS',
                            to: '/toolkit/vms',
                            exact: false,
                        },
                        {
                            title: 'EKIN',
                            to: '/toolkit/ekin',
                            exact: false,
                        },
                        {
                            title: 'SOCIAL',
                            to: '/toolkit/social',
                            exact: false,
                        },
                        {
                            title: 'RB',
                            to: '/toolkit/rb',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'FOUNDATION',
                    titleKo: '메인',
                    to: '/foundation',
                    exact: false,
                    children: [
                        {
                            title: 'VMS',
                            to: '/foundation/vms',
                            exact: false,
                        },
                        {
                            title: 'EKIN',
                            to: '/foundation/ekin',
                            exact: false,
                        },
                        {
                            title: 'DIGITAL',
                            to: '/foundation/digital',
                            exact: false,
                        },
                        {
                            title: 'RB',
                            to: '/foundation/rb',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'ORDER',
                    titleKo: `부자재 주문`,
                    to: '/order',
                    exact: false,
                    children: [
                        {
                            title: `<span class="noto-sans">상품 관리</span>`,
                            to: '/order/management',
                            exact: false,
                        },
                        {
                            title: `<span class="noto-sans">부자재</span>`,
                            to: '/order/subsidiary',
                            exact: false,
                            children: [
                                {
                                    title: `운영 비품`,
                                    to: '/order/subsidiary/operating',
                                    exact: false,
                                },
                                {
                                    title: `스태프 비품`,
                                    to: '/order/subsidiary/staff',
                                    exact: false,
                                },
                                {
                                    title: `운영 사이니지`,
                                    to: '/order/subsidiary/operating-signage',
                                    exact: false,
                                },
                                {
                                    title: `세일 사이니지`,
                                    to: '/order/subsidiary/sale-signage',
                                    exact: false,
                                },
                                {
                                    title: `오픈 패키지`,
                                    to: '/order/subsidiary/open-package',
                                    exact: false,
                                },
                                {
                                    title: `나이키 골프`,
                                    to: '/order/subsidiary/nike-golf',
                                    exact: false,
                                },
                            ],
                        },
                        {
                            title: 'Niki By You',
                            to: '/order/niki-by-you',
                            exact: false,
                            children: [
                                {
                                    title: `신발 커스텀 (단품)`,
                                    to: '/order/niki-by-you/shoes',
                                    exact: false,
                                },
                                {
                                    title: `신발 커스텀 (패키지)`,
                                    to: '/order/niki-by-you/shoes-package',
                                    exact: false,
                                },
                                {
                                    title: `의류 커스텀 (단품)`,
                                    to: '/order/niki-by-you/apparel',
                                    exact: false,
                                },
                                {
                                    title: `의류 커스텀 (패키지)`,
                                    to: '/order/niki-by-you/apparel-package',
                                    exact: false,
                                },
                                {
                                    title: 'OTHERS',
                                    to: '/order/niki-by-you/others',
                                    exact: false,
                                },
                            ],
                        },
                        {
                            title: 'CUSTOM23',
                            to: '/order/custom23',
                            exact: false,
                            children: [
                                {
                                    title: '신발 커스텀 (단품)',
                                    to: '/order/custom23/shoes',
                                    exact: false,
                                },
                                {
                                    title: '신발 커스텀 (패키지)',
                                    to: '/order/custom23/shoes-package',
                                    exact: false,
                                },
                                {
                                    title: '의류 커스텀 (단품)',
                                    to: '/order/custom23/apparel',
                                    exact: false,
                                },
                                {
                                    title: '의류 커스텀 (패키지)',
                                    to: '/order/custom23/apparel-package',
                                    exact: false,
                                },
                                {
                                    title: 'OTHERS',
                                    to: '/order/custom23/others',
                                    exact: false,
                                },
                            ],
                        },
                        {
                            title: 'MNQ',
                            to: '/order/mnq',
                            exact: false,
                            children: [
                                {
                                    title: '남성',
                                    to: '/order/mnq/male',
                                    exact: false,
                                },
                                {
                                    title: '여성',
                                    to: '/order/mnq/female',
                                    exact: false,
                                },
                                {
                                    title: '유아동',
                                    to: '/order/mnq/child',
                                    exact: false,
                                },
                                {
                                    title: '수리/보수',
                                    to: '/order/mnq/repair',
                                    exact: false,
                                },
                            ],
                        },
                    ],
                },
                {
                    title: 'REPORT',
                    titleKo: '시공보고서',
                    to: '/report',
                    exact: false,
                    children: [
                        {
                            title: 'REPORT UPLOAD',
                            to: '/report/upload',
                            exact: false,
                        },
                        {
                            title: `REPORT <span class="noto-sans">관리</span>`,
                            to: '/report/management',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'INFORMATION',
                    titleKo: '에이전시 정보',
                    to: '/information',
                    exact: false,
                    children: [
                        {
                            title: `AGENCY CONTACT`,
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
                    title: 'MANAGEMENT',
                    titleKo: '플랫폼 관리',
                    to: '/management',
                    exact: false,
                    children: [
                        {
                            title: `<span class="noto-sans">메인 비쥬얼 관리</span>`,
                            to: '/management/visual',
                            exact: false,
                        },
                        {
                            title: `<span class="noto-sans">계정관리</span>`,
                            to: '/management/account',
                            exact: false,
                        },
                        {
                            title: `<span class="noto-sans">권한 그룹관리</span>`,
                            to: '/management/group',
                            exact: false,
                        },
                    ],
                },
            ],*/
            activeIndex: null,
        };
    },
    created() {},
    computed: {
        lnb() {
            const menu = this.$store.state.gnbMenuListData;
            return menu.filter((item) => {
                console.log(item.menuCode);
                if (item.menuCode !== 'MYPAGE' && item.pcYn === 'Y')
                    return item;
            });
        },
    },
    components: { navItem },
};
</script>
<style scoped></style>
