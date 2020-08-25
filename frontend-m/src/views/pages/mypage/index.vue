<template>
    <div class="my-main">
        <div class="user-info">
            <strong class="name">ADMIN</strong>
            <div class="store">store</div>
            <div class="email">email@email.com</div>
            <div class="btn-box">
                <button type="button" class="btn-out">나가기</button>
                <button type="button" class="btn-alarm" @click="alarmModal">알람</button>
            </div>
            <div class="modal-wrap" >
                <AlarmModal :visible.sync="visible.activeModal" :alarmList="alarmData"/>
            </div>
        </div>
        <hr />
        <ul class="my-menu">
            <li v-for="(menu, index) in myMenuData.menus" :key="index">
                <strong class="title">{{ menu.menuName }}</strong>
                <ul v-if="menu.menus">
                    <li v-for="(depth, index) in menu.menus" :key="index">
                        <router-link :to="depth.menuPathUrl">{{
                            depth.menuName
                        }}</router-link>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>
<script>
import { getAlarm } from '@/api/alarm';
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
        };
    },
    components: {
        AlarmModal: () => import('@/views/pages/mypage/alarm-modal'),
        Loading: () => import('@/components/loading/')
    },

    mounted() {
        console.log("mounted");
        this.myMenuFn();
        this.getAlarmData();
    },
    watch: {},
    methods: {
        myMenuFn() {
            const menu = this.$store.state.menuData.filter(
                el => el.menuCode === 'MYPAGE' && el.mobileYn === 'Y'
            );
            this.myMenuData = menu[0];
        },
        //알람
        alarmModal() {
            console.log("alarmModal")
            this.visible.activeModal = true;
        },
        async getAlarmData() {
            console.log("getAlarmData")
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: this.page,
                    size: this.itemLength,
                });
                this.totalPage = response.totalPages;
                this.alarmData = response.content;
                this.alarmData.forEach((el) => {
                    el.typeCd === "REPORT_MANAGE" ? el.typeCd = "REPORT" : el.typeCd;
                });
                console.log(this.totalPage);
                console.log(this.alarmData);
            } catch (error) {
                console.log(error);
            }
        }
    },
};
</script>
<style scoped></style>
