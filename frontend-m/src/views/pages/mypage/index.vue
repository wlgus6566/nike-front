<template>
    <div class="my-main">
        <div class="user-info">
            <strong class="name">{{ userNickname }}</strong>
            <div class="store">{{ userAuthName }}</div>
            <div class="email">{{ userIdVal }}</div>
            <div class="btn-box">
                <button type="button" class="btn-out" @click="logout">
                    나가기
                </button>
                <button type="button" class="btn-alarm" @click="alarmModal">
                    알람
                </button>
            </div>
            <AlarmModal
                ref="Alarm"
                :visible.sync="visible.activeModal"
                :alarmList="alarmData"
                @assetClick="clickAsset"
                @prAlarmData="getAlarmData"
                v-if="alarmData"
            />
        </div>
        <hr />
        <ul class="my-menu">
            <li v-for="(menu, index) in myMenuData" :key="index">
                <strong class="title">{{ menu.menuName }}</strong>
                <ul v-if="menu.menus">
                    <li v-for="(depth, index) in menu.menus" :key="index">
                        <router-link
                            :to="depth.menuPathUrl"
                            v-html="depth.menuName"
                        ></router-link>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>
<script>
import { getAlarm } from '@/api/alarm';
import {
    getUserNickFromCookie,
    getUserIdFromCookie,
    getAuthNameFromCookie,
} from '@/utils/cookies';

export default {
    name: 'mypageIndex',
    data() {
        return {
            myMenuData: [],
            visible: {
                activeModal: false,
            },
            alarmList: null,
            alarmData: null,
            alarmActive: false,
            totalPage: null,
            page: 0,
            itemLength: 6,
            loadingData: false,
        };
    },
    components: {
        AlarmModal: () => import('@/views/pages/mypage/alarm-modal'),
        // Loading: () => import('@/components/loading/'),
    },
    computed: {
        userNickname() {
            return this.$store.state.nick || getUserNickFromCookie();
        },
        userIdVal() {
            return this.$store.state.user || getUserIdFromCookie();
        },
        userAuthName() {
            return this.$store.state.authName || getAuthNameFromCookie();
        },
    },
    mounted() {
        this.myMenuFn();
    },
    created() {},
    watch: {
        '$store.state.menuData'() {
            this.myMenuFn();
        },
    },
    methods: {
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.alarmList = null;
            this.getAlarmData();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.alarmList.length >= this.itemLength &&
                this.alarmList.length !== 0
            ) {
                this.getAlarmData(true);
            }
        },
        //클릭시 업로드 한 폴더 리스트 다시 불러오기
        handleScroll() {
            console.log(221231);
            if (this.loadingData) return;

            /*if (
                modalContents.clientHeight + modalContents.scrollTop >=
                modalContents.scrollHeight
            ) {
                this.infiniteScroll();
            }*/
        },
        //알람
        alarmModal() {
            this.initFetchData();
            this.visible.activeModal = !this.visible.activeModal;
            console.log(this.alarmData.length);
            this.handleScroll();

            if (this.alarmData.length > 0) {
                console.log(2);
                console.log(document.querySelector('.modal'));
            }

            /*console.log(modalContents.clientHeight + modalContents.scrollTop);
            console.log(modalContents.scrollHeight);*/
        },
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
        myMenuFn() {
            if (!this.$store.state.menuData) return;
            const menu = this.$store.state.menuData.filter(
                el => el.menuCode === 'MYPAGE' && el.mobileYn === 'Y'
            );
            this.myMenuData = menu[0].menus.filter(el => {
                return el.mobileYn === 'Y';
            });
        },
        async getAlarmData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: this.page,
                    size: this.itemLength,
                });
                this.totalPage = response.totalPages;

                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.alarmData = this.alarmData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.alarmData = response.content;
                }

                this.alarmData.forEach(el => {
                    el.typeCd = el.typeCd.toLowerCase();
                    el.menuCode = el.menuCode.toLowerCase();
                    if (el.typeCd === 'report_manage') el.typeCd = 'report';
                });
                this.page++;
            } catch (error) {
                console.log(error);
            }
        },
        clickAsset() {
            console.log('clickAsset');
            alert(
                '해당 메뉴는 모바일 버전에서 제공되지 않습니다. 자세한 내용은 PC로 접속 시 확인할 수 있습니다.'
            );
            this.getAlarmData();
        },
    },
};
</script>
<style scoped></style>
