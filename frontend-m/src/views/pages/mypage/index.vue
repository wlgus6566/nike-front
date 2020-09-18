<template>
    <div class="my-main">
        <div class="user-info">
            <strong class="name">{{ userNickname }}</strong>
            <div class="store">{{ userAuthName }}</div>
            <div class="email">{{ userIdVal }}</div>
            <div class="btn-box">
                <button type="button" class="logout" @click="logout">
                    로그아웃
                </button>
                <button type="button" class="btn-alarm" @click="alarmModal">
                    알람
                </button>
            </div>
            <AlarmModal
                ref="Alarm"
                :visible.sync="visible.activeModal"
                :alarmList="alarmData"
                @prAlarmData="getAlarmData"
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
        <a href="mailto:NIKESPACE@NIKE.COM?subject=[NIKE SPACE]" class="btn-help">
            고객문의
        </a>
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
            this.alarmData = null;
            this.getAlarmData();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.alarmData.length >= this.itemLength &&
                this.alarmData.length !== 0
            ) {
                this.getAlarmData(true);
            }
        },
        //클릭시 업로드 한 폴더 리스트 다시 불러오기
        handleScroll() {
            if (this.loadingData) return;
            const alarmList = document.querySelector('.modal-contents');
            if (
                alarmList.offsetHeight + alarmList.scrollTop >=
                alarmList.scrollHeight
            ) {
                console.log(1);
                this.infiniteScroll();
            }
        },
        //알람
        alarmModal() {
            this.visible.activeModal = true;
            this.initFetchData();
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
                    if (this.alarmData.length > 0) {
                        await this.handleScroll();
                        document
                            .querySelector('.modal-contents')
                            .addEventListener('scroll', this.handleScroll);
                    }
                }
                this.alarmData.forEach(el => {
                    el.typeCd === 'REPORT_MANAGE'
                        ? (el.typeCd = 'REPORT')
                        : el.typeCd;
                });
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
