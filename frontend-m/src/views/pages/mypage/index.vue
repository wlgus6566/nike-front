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
            <div class="modal-wrap">
                <AlarmModal
                    ref="Alarm"
                    :visible.sync="visible.activeModal"
                    :alarmList="alarmData"
                    @assetClick="clickAsset"
                    @prAlarmData="getAlarmData"
                />
            </div>
        </div>
        <hr />
        <ul class="my-menu">
            <li v-for="(menu, index) in myMenuData.menus" :key="index">
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
            alarmData: [],
            alarmActive: false,
            totalPage: null,
            page: 0,
            itemLength: 10,
            loadingData: false,
        };
    },
    components: {
        AlarmModal: () => import('@/views/pages/mypage/alarm-modal/'),
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
        console.log('mounted');
        this.myMenuFn();
        this.getAlarmData();
    },
    watch: {
        '$store.state.menuData'() {
            this.menuFn();
        },
    },
    methods: {
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
        myMenuFn() {
            if (!this.$store.state.menuData) return;
            const menu = this.$store.state.menuData.filter(
                el => el.menuCode === 'MYPAGE' && el.mobileYn === 'Y'
            );
            this.myMenuData = menu[0];
        },
        //알람
        alarmModal() {
            this.visible.activeModal = true;
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
                    console.log('getAlarmData infinite : ' + infinite);
                    if (this.totalPage > this.page - 1) {
                        console.log('getAlarmData : ' + true);
                        this.alarmData = this.alarmData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        console.log('getAlarmData : ' + false);
                        this.endPage();
                    }
                } else {
                    console.log('getAlarmData infinite : ' + infinite);
                    this.alarmData = response.content;
                }

                this.alarmData.forEach(el => {
                    el.typeCd === 'REPORT_MANAGE'
                        ? (el.typeCd = 'REPORT')
                        : el.typeCd;
                });
                this.page++;
                console.log(this.totalPage);
                console.log(this.alarmData);
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
