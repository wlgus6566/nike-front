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
            <li
                v-for="(item, index) in pageLists"
                :key="index"
                :depth="1"
                :class="{ depth: item.depthTitle }"
            >
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
                    <li
                        v-for="(item, index) in item.depth"
                        :key="index"
                        :class="{ depth: item.depthTitle }"
                    >
                        <span>{{ item.title }}</span>
                        <span class="name">
                            {{ item.name }}
                        </span>
                        <span class="filename">
                            <router-link :to="item.link" target="_blank">{{
                                item.filename
                            }}</router-link>
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
                            <li
                                v-for="(item, index) in item.depth"
                                :key="index"
                                :class="{ depth: item.depthTitle }"
                            >
                                <span>{{ item.title }}</span>
                                <span class="name">
                                    {{ item.name }}
                                </span>
                                <span class="filename">
                                    <router-link :to="item.link" target="_blank">{{
                                        item.filename
                                    }}</router-link>
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
                                    <li
                                        v-for="(item, index) in item.depth"
                                        :key="index"
                                        :class="{ depth: item.depthTitle }"
                                    >
                                        <span>{{ item.title }}</span>
                                        <span class="name">
                                            {{ item.name }}
                                        </span>
                                        <span class="filename">
                                            <router-link :to="item.link" target="_blank">{{
                                                item.filename
                                            }}</router-link>
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
                                            <li
                                                v-for="(item, index) in item.depth"
                                                :key="index"
                                                :class="{ depth: item.depthTitle }"
                                            >
                                                <span>{{ item.title }}</span>
                                                <span class="name">
                                                    {{ item.name }}
                                                </span>
                                                <span class="filename">
                                                    <router-link :to="item.link" target="_blank">{{
                                                        item.filename
                                                    }}</router-link>
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
                    depthTitle: false,
                    name: '지은유',
                    filename: 'mainPage.vue',
                    link: 'main',
                    com: '2020.07.06',
                    abc: '',
                    comment: '캘린더 VUE 작업 필요',
                    depth: false,
                },
                {
                    title: 'GUDE',
                    depthTitle: true,
                    name: '지은유',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: 'BUTTON',
                            depthTitle: false,
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
                            depthTitle: false,
                            name: '지은유',
                            filename: 'input.vue',
                            link: 'input',
                            com: ' ',
                            abc: '',
                            comment: '디자인 수정 반영 예정',
                            depth: false,
                        },
                        {
                            title: 'RADIO',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'radio.vue',
                            link: 'radio',
                            com: ' ',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'CHECKBOX',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'checkbox.vue',
                            link: 'checkbox',
                            com: ' ',
                            abc: '',
                            comment: '작업해야함',
                            depth: false,
                        },
                        {
                            title: 'nodata-업로드한 폴더',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'nodata01.vue',
                            link: 'nodata01',
                            com: '2020.07.08',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'nodata-최근 본 폴더',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'nodata02.vue',
                            link: 'nodata02',
                            com: '2020.07.08',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'nodata-주문 내역',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'nodata03.vue',
                            link: 'nodata03',
                            com: '2020.07.08',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: 'nodata-위시리스트',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'nodata04.vue',
                            link: 'nodata04',
                            com: '2020.07.08',
                            abc: '',
                            comment: '',
                        },
                    ],
                },
                {
                    title: 'LOGIN',
                    depthTitle: true,
                    name: '지은유',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '로그인',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_01.vue',
                            link: 'NIKE_P_LOGIN_01',
                            com: '2020.07.02',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '이메일 인증',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_02.vue',
                            link: 'NIKE_P_LOGIN_02',
                            com: '2020.07.02',
                            abc: '',
                            comment: 'input 디자인 반영',
                            depth: false,
                        },
                        {
                            title: '비밀번호 찾기',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_03.vue',
                            link: 'NIKE_P_LOGIN_03',
                            com: '2020.07.02',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '휴면 해제 안내',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_LOGIN_07.vue',
                            link: 'NIKE_P_LOGIN_07',
                            com: '2020.07.09',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '비밀번호 설정',
                            depthTitle: false,
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_05.vue',
                            link: '	NIKE_P_LOGIN_05',
                            com: '2020.06.24',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                        {
                            title: '약관동의',
                            depthTitle: false,
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_04.vue',
                            link: '	NIKE_P_LOGIN_04',
                            com: '2020.06.24',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                        {
                            title: '비밀번호 변경',
                            depthTitle: false,
                            name: '한슬기',
                            filename: '	NIKE_P_LOGIN_06.vue',
                            link: '	NIKE_P_LOGIN_06',
                            com: '2020.06.24',
                            abc: '',
                            comment: ' ',
                            depth: false,
                        },
                    ],
                },
                {
                    title: 'GNB',
                    depthTitle: true,
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: 'ASSET',
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'UPLOAD',
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                                    depthTitle: false,
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
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: '상품관리',
                                    depthTitle: false,
                                    name: '지은유',
                                    filename: '	NIKE_P_ORDER_06.vue',
                                    link: '	NIKE_P_ORDER_06',
                                    com: '2020.07.03',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },

                                {
                                    title: '상품등록',
                                    depthTitle: false,
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
                                    depthTitle: false,
                                    name: '지은유',
                                    filename: 'NIKE_P_ORDER_02.vue',
                                    link: 'NIKE_P_ORDER_02',
                                    com: '2020.07.03',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: '주문 완료',
                                    depthTitle: false,
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
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'REPORT UPLOAD',
                                    depthTitle: false,
                                    name: '지은유',
                                    filename: '	NIKE_P_REPORT_01.vue',
                                    link: '	NIKE_P_REPORT_01',
                                    com: '2020.07.02',
                                    abc: '',
                                    comment: ' ',
                                    depth: false,
                                },
                                {
                                    title: 'REPORT 관리',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '목록',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_REPORT_02.vue',
                                            link: 'NIKE_P_REPORT_02',
                                            com: '2020.07.02',
                                            abc: '',
                                            comment: ' ',
                                            depth: false,
                                        },
                                        {
                                            title: '상세',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_REPORT_03.vue',
                                            link: 'NIKE_P_REPORT_03',
                                            com: '2020.07.02',
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
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'AGENCY CONTACT',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '업로드 팝업',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_INFO_01_001p.vue',
                                            link: 'NIKE_P_INFO_01_001p',
                                            com: '2020.07.08',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '\t목록',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_INFO_01_001.vue',
                                            link: 'NIKE_P_INFO_01_001',
                                            com: '2020.07.06',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'LAUNCHING CALENDAR',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '업로드 팝업',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_INFO_02_001P.vue',
                                            link: 'NIKE_P_INFO_02_001P',
                                            com: '2020.07.08',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '\t목록',
                                            depthTitle: false,
                                            name: '---------------------',
                                            filename: '---------------------',
                                            link: '',
                                            com: '---------------------',
                                            abc: '---------------------',
                                            comment: '프론트엔드에서 작업 예정',
                                            depth: false,
                                        },
                                    ],
                                },
                            ],
                        },
                        {
                            title: 'MANAGEMENT',
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: '메인 비쥬얼 관리',
                                    depthTitle: false,
                                    name: '염주성',
                                    filename: 'NIKE_P_MANAGE_01.vue',
                                    link: 'NIKE_P_MANAGE_01',
                                    com: '2020.07.09',
                                    abc: '',
                                    comment: '',
                                    depth: false,
                                },
                                {
                                    title: '계정관리',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '목록',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_MANAGE_02_001.vue',
                                            link: 'NIKE_P_MANAGE_02_001',
                                            com: '',
                                            abc: '',
                                            comment: '프론트엔드에서 작업 예정',
                                            depth: false,
                                        },
                                        {
                                            title: '계정 관리',
                                            depthTitle: false,
                                            name: '염주성',
                                            filename: 'NIKE_P_MANAGE_02_002P.vue',
                                            link: 'NIKE_P_MANAGE_02_002P',
                                            com: '2020.07.09',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: '권한 그룹관리',
                                    depthTitle: false,
                                    name: '염주성',
                                    filename: 'NIKE_P_MANAGE_03.vue',
                                    link: 'NIKE_P_MANAGE_03',
                                    com: '',
                                    abc: '',
                                    comment: '프론트엔드에서 작업 예정',
                                    depth: false,
                                },
                            ],
                        },
                    ],
                },
                {
                    title: 'UTIL',
                    depthTitle: true,
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: 'MY PAGE',
                            depthTitle: true,
                            name: '',
                            filename: '',
                            link: '',
                            com: '',
                            abc: '',
                            comment: '',
                            depth: [
                                {
                                    title: 'HISTORY',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '내가 업로드한 폴더',
                                            depthTitle: false,
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
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_MY_01_002.vue',
                                            link: 'NIKE_P_MY_01_002',
                                            com: '2020.07.07',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'ORDER',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '\t주문내역확인',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_MY_02_001.vue',
                                            link: 'NIKE_P_MY_02_001',
                                            com: '2020.07.07',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '상품 크게보기 팝업',
                                                    depthTitle: false,
                                                    name: '지은유',
                                                    filename: 'NIKE_P_ORDER_01P.vue',
                                                    link: 'NIKE_P_ORDER_01P',
                                                    com: '2020.07.08',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '주문서 발송 팝업',
                                                    depthTitle: false,
                                                    name: '지은유',
                                                    filename: 'NIKE_P_ORDER_02P.vue',
                                                    link: 'NIKE_P_ORDER_02P',
                                                    com: '2020.07.09',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                            ],
                                        },
                                        {
                                            title: '위시리스트',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_MY_02_002.vue',
                                            link: 'NIKE_P_MY_02_002',
                                            com: '2020.07.07',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'MY INFO',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '회원정보 조회',
                                            depthTitle: false,
                                            name: '지은유',
                                            filename: 'NIKE_P_MY_03_001.vue',
                                            link: 'NIKE_P_MY_03_001',
                                            com: '2020.07.07',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                        {
                                            title: '비밀번호 변경',
                                            depthTitle: false,
                                            name: '한슬기',
                                            filename: 'NIKE_P_MY_03_002.vue',
                                            link: 'NIKE_P_MY_03_002',
                                            com: '2020.07.07',
                                            abc: '',
                                            comment: '',
                                            depth: false,
                                        },
                                    ],
                                },
                                {
                                    title: 'CUSTOMER CENTER',
                                    depthTitle: true,
                                    name: '',
                                    filename: '',
                                    link: '',
                                    com: '',
                                    abc: '',
                                    comment: '',
                                    depth: [
                                        {
                                            title: '공지사항',
                                            depthTitle: true,
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    depthTitle: false,
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
                                                    depthTitle: false,
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
                                                    depthTitle: false,
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
                                            depthTitle: true,
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    depthTitle: false,
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
                                                    depthTitle: false,
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
                                                    depthTitle: false,
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
                                            depthTitle: true,
                                            name: '',
                                            filename: '',
                                            link: '',
                                            com: '',
                                            abc: '',
                                            comment: '',
                                            depth: [
                                                {
                                                    title: '목록',
                                                    depthTitle: false,
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_007.vue',
                                                    link: 'NIKE_P_MY_04_007',
                                                    com: '2020.07.06',
                                                    abc: '',
                                                    comment: '',
                                                    depth: false,
                                                },
                                                {
                                                    title: '등록/수정',
                                                    depthTitle: false,
                                                    name: '지은유',
                                                    filename: 'NIKE_P_MY_04_009.vue',
                                                    link: 'NIKE_P_MY_04_009',
                                                    com: '2020.07.06',
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
                    title: 'ECT',
                    depthTitle: true,
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '페이지 오류',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_ERROR_01.vue',
                            link: 'NIKE_P_ERROR_01',
                            com: '2020.07.07',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '시스템 점검중',
                            depthTitle: false,
                            name: '지은유',
                            filename: 'NIKE_P_ERROR_02.vue',
                            link: 'NIKE_P_ERROR_02',
                            com: '2020.07.07',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                    ],
                },
                {
                    title: '이메일 발송',
                    depthTitle: true,
                    name: '',
                    filename: '',
                    link: '',
                    com: '',
                    abc: '',
                    comment: '',
                    depth: [
                        {
                            title: '이메일 인증 안내',
                            depthTitle: false,
                            name: '한슬기',
                            filename: 'NIKE_P_LOGIN_01e.vue',
                            link: 'NIKE_P_LOGIN_01e',
                            com: '2020.06.24',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '비밀번호 설정 안내',
                            depthTitle: false,
                            name: '한슬기',
                            filename: 'NIKE_P_LOGIN_03e.vue',
                            link: 'NIKE_P_LOGIN_03e',
                            com: '2020.06.24',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '컨텐츠 업데이트 알림',
                            depthTitle: false,
                            name: '한슬기',
                            filename: 'NIKE_P_ASSET_02_001e.vue',
                            link: 'NIKE_P_ASSET_02_001e',
                            com: '2020.06.24',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '계정 생성 안내',
                            depthTitle: false,
                            name: '한슬기',
                            filename: 'NIKE_P_MANAGE_02_001e.vue',
                            link: 'NIKE_P_MANAGE_02_001e',
                            com: '2020.06.24',
                            abc: '',
                            comment: '',
                            depth: false,
                        },
                        {
                            title: '주문서',
                            depthTitle: false,
                            name: '한슬기',
                            filename: 'NIKE_P_ORDER_01e.vue',
                            link: 'NIKE_P_ORDER_01e',
                            com: '2020.06.24',
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
    margin-left: 449px;
    width: 211px;
}
.list ul > li > ul > li > ul > li > ul > li span:nth-child(1) {
    margin-left: 559px;
    width: 101px;
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
.depth > ul {
    margin-top: -41px;
}
</style>
