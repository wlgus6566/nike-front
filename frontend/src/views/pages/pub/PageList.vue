<template>
    <div>
        <h1 style="font-size: 40px;">퍼블리싱 리스트</h1>
        <ul class="list list-top">
            <li>
                <span></span>
                <span class="name">작업자</span>
                <span class="filename">파일명</span>
                <span class="name">완료일</span>
                <span class="name">검수</span>
                <span class="com">비고</span>
            </li>
        </ul>
        <ul class="list">
            <li v-for="(item, index) in pageLists" :key="index" :depth="1">
                <span>{{ item.title }}</span>
                <span class="name">
                    {{ item.name }}
                </span>
                <span class="filename">
                    <router-link :to="item.link" target="_blank">{{ item.filename }}</router-link>
                </span>
                <span class="name">
                    {{ item.com }}
                </span>
                <span>
                    {{ item.abc }}
                </span>
                <span class="com">
                    {{ item.comment }}
                </span>
                <ul v-if="item.depth">
                    <li v-for="(item, index) in item.depth" :key="index">
                        <span>{{ item.title }}</span>
                        <span class="name">
                            {{ item.name }}
                        </span>
                        <span class="filename">
                            <router-link :to="item.link" target="_blank">{{ item.filename }}</router-link>
                        </span>
                        <span class="name">
                            {{ item.com }}
                        </span>
                        <span>
                            {{ item.abc }}
                        </span>
                        <span class="com">
                            {{ item.comment }}
                        </span>
                        <ul v-if="item.depth">
                            <li v-for="(item, index) in item.depth" :key="index">
                                <span>{{ item.title }}</span>
                                <span class="name">
                                    {{ item.name }}
                                </span>
                                <span class="filename">
                                    <router-link :to="item.link" target="_blank">{{ item.filename }}</router-link>
                                </span>
                                <span class="name">
                                    {{ item.com }}
                                </span>
                                <span>
                                    {{ item.abc }}
                                </span>
                                <span class="com">
                                    {{ item.comment }}
                                </span>
                                <ul v-if="item.depth">
                                    <li v-for="(item, index) in item.depth" :key="index">
                                        <span>{{ item.title }}</span>
                                        <span class="name">
                                            {{ item.name }}
                                        </span>
                                        <span class="filename">
                                            <router-link :to="item.link" target="_blank">{{ item.filename }}</router-link>
                                        </span>
                                        <span class="name">
                                            {{ item.com }}
                                        </span>
                                        <span>
                                            {{ item.abc }}
                                        </span>
                                        <span class="com">
                                            {{ item.comment }}
                                        </span>
                                        <ul v-if="item.depth">
                                            <li v-for="(item, index) in item.depth" :key="index">
                                                <span>{{ item.title }}</span>
                                                <span class="name">
                                                    {{ item.name }}
                                                </span>
                                                <span class="filename">
                                                    <router-link :to="item.link" target="_blank">{{ item.filename }}</router-link>
                                                </span>
                                                <span class="name">
                                                    {{ item.com }}
                                                </span>
                                                <span>
                                                    {{ item.abc }}
                                                </span>
                                                <span class="com">
                                                    {{ item.comment }}
                                                </span>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'PageList',
    data() {
        return {
            pageLists: [
                {
                    title: 'HOME',
                    name: '지은유',
                    filename: 'mainPage.vue',
                    link: 'main',
                    com: '',
                    abc: '',
                    comment: ' ',
                    depth: false,
                },
                {
                    title: 'GUDE',
                    name: '지은유',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: 'BUTTON',
                            name: '지은유',
                            filename: 'button.vue',
                            link: 'button',
                            com: ' ',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                        {
                            title: 'INPUT',
                            name: '지은유',
                            filename: 'input.vue',
                            link: 'input',
                            com: ' ',
                            abc: '',
                            comment: '디자인 수정 반영 예정',
                            depth: false,
                        },
                    ],
                },
                {
                    title: 'LOGIN',
                    name: '지은유',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '로그인',
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_01.vue',
                            link: 'NIKE_P_LOGIN_01',
                            com: '2020.07.02',
                            abc: '',
                            comment: '배경 추가 반영 필요',
                            depth: false,
                        },
                        {
                            title: '이메일 인증',
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_02.vue',
                            link: 'NIKE_P_LOGIN_02',
                            com: '2020.07.02',
                            abc: '',
                            comment: '배경 추가 반영 필요 , input 디자인 반영',
                            depth: false,
                        },
                        {
                            title: '비밀번호 찾기',
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_03.vue',
                            link: 'NIKE_P_LOGIN_03',
                            com: '2020.07.02',
                            abc: '',
                            comment: '배경 추가 반영 필요',
                            depth: false,
                        },
                        {
                            title: '비밀번호 설정',
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_05.vue',
                            link: '	NIKE_P_LOGIN_05',
                            com: ' ',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                        {
                            title: '약관동의',
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_04.vue',
                            link: '	NIKE_P_LOGIN_04',
                            com: ' ',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                        {
                            title: '비밀번호 변경',
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_06.vue',
                            link: '	NIKE_P_LOGIN_06',
                            com: ' ',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                    ],
                },
                {
                    title: 'GNB',
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: 'ASSET',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    name: '지은유',
                                    filename: 'NIKE_P_ASSET_01.vue',
                                    link: 'NIKE_P_ASSET_01',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: '기간제외 검수 가능',
                                    depth: false,
                                },
                                {
                                    title: '목록',
                                    name: '지은유',
                                    filename: 'ASSET_SP_LIST.vue',
                                    link: 'ASSET_SP_LIST',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '상세',
                                    name: '지은유',
                                    filename: 'ASSET_SP_DETAIL.vue',
                                    link: 'ASSET_SP_DETAIL',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                            ],
                        },
                        {
                            title: 'TOOLKIT',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    name: '지은유',
                                    filename: 'NIKE_P_TOOLKIT_01.vue',
                                    link: 'NIKE_P_TOOLKIT_01',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: '기간제외 검수 가능',
                                    depth: false,
                                },
                                {
                                    title: '목록',
                                    name: '지은유',
                                    filename: 'NIKE_P_TOOLKIT_02.vue',
                                    link: 'NIKE_P_TOOLKIT_02',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '상세',
                                    name: '지은유',
                                    filename: 'NIKE_P_TOOLKIT_03.vue',
                                    link: 'NIKE_P_TOOLKIT_03',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                            ],
                        },
                        {
                            title: 'FOUNDATION',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    name: '지은유',
                                    filename: 'NIKE_P_FD_01.vue',
                                    link: 'NIKE_P_FD_01',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: '기간제외 검수 가능',
                                    depth: false,
                                },
                                {
                                    title: '목록',
                                    name: '지은유',
                                    filename: 'NIKE_P_FD_02.vue',
                                    link: 'NIKE_P_FD_02',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '상세',
                                    name: '지은유',
                                    filename: 'NIKE_P_FD_03.vue',
                                    link: 'NIKE_P_FD_03',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                            ],
                        },
                        {
                            title: 'ORDER: 부자재 주문',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: '상품관리',
                                    name: '지은유',
                                    filename: '	NIKE_P_ORDER_06.vue',
                                    link: '	NIKE_P_ORDER_06',
                                    com: ' ',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '상품등록',
                                    name: '지은유',
                                    filename: '	NIKE_P_ORDER_01.vue',
                                    link: '	NIKE_P_ORDER_01',
                                    com: '2020.07.03',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '상품목록',
                                    name: '지은유',
                                    filename: 'NIKE_P_ORDER_02.vue',
                                    link: 'NIKE_P_ORDER_02',
                                    com: '2020.07.03',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                // {
                                //     title: '부자재',
                                //     name: '',
                                //     filename: '',
                                //     link: '',
                                //     com: '',
                                //     abc: '',
                                //     comment: '',
                                //     depth: [
                                //         {
                                //             title: '운영 비품',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '스태프 비품',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '운영 사이니지',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '세일 사이니지',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '오픈 패키지',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //     ],
                                // },
                                // {
                                //     title: 'NIKE BY YOU',
                                //     name: '',
                                //     filename: '',
                                //     link: '',
                                //     com: '',
                                //     abc: '',
                                //     comment: '',
                                //     depth: [
                                //         {
                                //             title: '상품리스트',
                                //             name: '지은유',
                                //             filename: 'NIKE_P_ORDER_02.vue',
                                //             link: 'NIKE_P_ORDER_02',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //     ],
                                // },
                                // {
                                //     title: '신발 커스텀 (단품)',
                                //     name: ' ',
                                //     filename: ' ',
                                //     link: ' ',
                                //     com: ' ',
                                //     abc: '',
                                //     comment: ' ',
                                //     depth: [
                                //         {
                                //             title: '신발 커스텀 (패키지)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '의류 커스텀 (단품)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '의류 커스텀 (패키지)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: 'OTHERS',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //     ],
                                // },
                                // {
                                //     title: 'CUSTOM23',
                                //     name: '',
                                //     filename: '',
                                //     link: '',
                                //     com: '',
                                //     abc: '',
                                //     comment: '',
                                //     depth: [
                                //         {
                                //             title: '신발 커스텀 (단품)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '신발 커스텀 (패키지)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '의류 커스텀 (단품)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '의류 커스텀 (패키지)',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: 'OTHERS',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //     ],
                                // },
                                // {
                                //     title: 'MNQ',
                                //     name: '',
                                //     filename: '',
                                //     link: '',
                                //     com: '',
                                //     abc: '',
                                //     comment: '',
                                //     depth: [
                                //         {
                                //             title: '남성',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '여성',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //         {
                                //             title: '유아동',
                                //             name: ' ',
                                //             filename: ' ',
                                //             link: ' ',
                                //             com: ' ',
                                //             abc: '',
                                //             comment: ' ',
                                //             depth: false,
                                //         },
                                //     ],
                                // },
                                {
                                    title: '주문 완료',
                                    name: '지은유',
                                    filename: 'NIKE_P_ORDER_07.vue',
                                    link: 'NIKE_P_ORDER_07',
                                    com: '2020.06.30',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                            ],
                        },
                        {
                            title: 'REPORT 시공보고서',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'REPORT UPLOAD',
                                    name: '지은유',
                                    filename: '	NIKE_P_REPORT_01.vue',
                                    link: '	NIKE_P_REPORT_01',
                                    com: ' ',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: 'REPORT 관리',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '목록',
                                            name: '지은유',
                                            filename: 'NIKE_P_REPORT_02.vue',
                                            link: 'NIKE_P_REPORT_02',
                                            com: ' ',
                                            abc: '',
                                            comment: ' ',
                                            depth: false,
                                        },
                                        {
                                            title: '상세',
                                            name: '지은유',
                                            filename: 'NIKE_P_REPORT_03.vue',
                                            link: 'NIKE_P_REPORT_03',
                                            com: ' ',
                                            abc: '',
                                            comment: ' ',
                                            depth: false,
                                        },
                                    ],
                                },
                            ],
                        },
                        {
                            title: 'INFORMATION',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'AGENCY CONTACT',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '업로드',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '\t목록',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'LAUNCHING CALENDAR',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '업로드',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '\t목록',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                            ],
                        },
                        {
                            title: 'MANAGEMENT',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: '메인 비쥬얼 관리',
                                    name: '지은유',
                                    filename: 'NIKE_P_MANAGE_01.vue',
                                    link: 'NIKE_P_MANAGE_01',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: false,
                                },
                                {
                                    title: '계정관리',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '업로드',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '목록',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: '권한 그룹관리',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: false,
                                },
                            ],
                        },
                    ],
                },
                {
                    title: 'UTIL',
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '알림',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'HISTORY',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'MY PAGE',
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'HISTORY',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '내가 업로드한 폴더',
                                            name: '지은유',
                                            filename: 'NIKE_P_MY_01_001.vue',
                                            link: 'NIKE_P_MY_01_001',
                                            com: '2020.07.03',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '최근 본 폴더',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'ORDER',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '\t주문내역확인',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '위시리스트',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'MY INFO',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '회원정보 관리',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '비밀번호 변경',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '로그아웃',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'CUSTOMER CENTER',
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '공지사항',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_001.vue',
                                                    link: 'NIKE_P_MY_04_001',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '상세',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_002.vue',
                                                    link: 'NIKE_P_MY_04_002',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '등록/수정',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_003.vue',
                                                    link: 'NIKE_P_MY_04_003',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                            ],
                                        },
                                        {
                                            title: 'NEWS',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_004.vue',
                                                    link: 'NIKE_P_MY_04_004',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '상세',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_005.vue',
                                                    link: 'NIKE_P_MY_04_005',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '등록/수정',
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_006.vue',
                                                    link: 'NIKE_P_MY_04_006',
                                                    com: '2020.07.03',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                            ],
                                        },
                                        {
                                            title: '자주 묻는 질문',
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    name: '',
                                                    filename: '',
                                                    link: '',
                                                    com: '',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '상세',
                                                    name: '',
                                                    filename: '',
                                                    link: '',
                                                    com: '',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '등록/수정',
                                                    name: '',
                                                    filename: '',
                                                    link: '',
                                                    com: '',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                            ],
                                        },
                                    ],
                                },
                            ],
                        },
                    ],
                },
                {
                    title: '이메일 발송',
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '이메일 인증 안내',
                            name: '한슬기',
                            filename: 'NIKE_P_LOGIN_01e.vue',
                            link: 'NIKE_P_LOGIN_01e',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '비밀번호 설정 안내',
                            name: '한슬기',
                            filename: 'NIKE_P_LOGIN_03e.vue',
                            link: 'NIKE_P_LOGIN_03e',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '컨텐츠 업데이트 알림',
                            name: '한슬기',
                            filename: 'NIKE_P_ASSET_02_001e.vue',
                            link: 'NIKE_P_ASSET_02_001e',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '계정 생성 안내',
                            name: '한슬기',
                            filename: 'NIKE_P_MANAGE_02_001e.vue',
                            link: 'NIKE_P_MANAGE_02_001e',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '주문서',
                            name: '한슬기',
                            filename: 'NIKE_P_ORDER_01e.vue',
                            link: 'NIKE_P_ORDER_01e',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                    ],
                },
            ],
        };
    },
};
</script>
<style scoped>
div {
    padding: 40px;
}
.list {
    min-width: 1300px;
    border: solid #ddd;
    border-width: 1px 0 1px 1px;
}
.list-top li {
    text-align: center;
}
.list-top li span {
    background: #eee;
}
.list li {
    display: flex;
    line-height: 30px;
    flex-wrap: wrap;
}
.list > li + li {
    border-top: 1px solid #ddd;
}
.list li span {
    display: block;
    padding: 5px 10px;
    border: solid #ddd;
    border-width: 0 1px;
    box-sizing: border-box;
    flex-grow: 0;
    flex-shrink: 0;
}
.list li span:first-child {
    border-left: none;
}
.list ul {
    border-top: 1px solid #ddd;
    width: 100%;
}
.list ul li span {
    border-top: 1px solid #ddd;
}
.list ul li:first-child span {
    border-top: none;
}
.list > li > span:first-child {
    flex-basis: 660px;
}
.list li span:nth-child(2) {
    flex-basis: 100px;
}
.list li span:nth-child(3) {
    flex-basis: 200px;
}
.list li span:nth-child(4) {
    flex-basis: 100px;
}
.list li span:nth-child(5) {
    flex-basis: 100px;
}
.list li span:nth-child(6) {
    flex-grow: 1;
    flex-shrink: 1;
    flex-basis: auto;
}
.list ul > li > span:nth-child(1) {
    margin-left: 100px;
    width: 560px;
}
.list ul > li > ul > li span:nth-child(1) {
    margin-left: 260px;
    width: 400px;
}
.list ul > li > ul > li > ul > li span:nth-child(1) {
    margin-left: 389px;
    width: 271px;
}
.list ul > li > ul > li > ul > li > ul > li span:nth-child(1) {
    margin-left: 510px;
    width: 150px;
}
.list ul li span:nth-child(1) {
    position: relative;
}
.list ul li span:nth-child(1):before {
    position: absolute;
    top: 15px;
    left: -9px;
    content: ' ';
    width: 5px;
    height: 5px;
    border: solid #888;
    border-width: 0 0 1px 1px;
}
.list a {
    color: #00a1e0;
}
</style>
