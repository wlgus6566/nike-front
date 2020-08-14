<template>
    <div class="user">
        <div class="user-info">
            <span class="store-name">홍대 SKNRS</span>
            <div class="side">
                <button type="button" class="btn-out" @click="logout">
                    <span>로그아웃</span>
                </button>
                <button type="button" class="btn-alarm" @click="openAlarm">
                    <span>알람</span>
                </button>
                <!--<div class="alarm-box">
                    <strong class="title">NEW</strong>
                    <div class="alarm-item">
                        <p class="txt">
                            에어맥스 2090 신제품 런칭 그래픽 자료가
                            업데이트되었습니다.
                        </p>
                    </div>
                    <button type="button" class="btn-close">
                        <span>닫기</span>
                    </button>
                </div>-->
                <div
                    class="alarm-box"
                    ref="alarm"
                    :class="{ active: alarmActive }"
                >
                    <strong class="title">NOTICE</strong>
                    <el-scrollbar
                        class="cart-list-scroll"
                        wrap-class="alarm-list-wrap"
                        :native="false"
                    >
                        <ul class="alarm-list">
                            <li
                                class="alarm-item active"
                                v-for="(item, index) in alarmDataList.content"
                                :key="index"
                            >
                                <p class="txt">
                                    {{ item.folderName }}
                                </p>
                                <span class="date">Now</span>
                            </li>
                        </ul>
                    </el-scrollbar>

                    <button type="button" class="btn-close" @click="alarmClose">
                        <span>닫기</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="space-info">
            <p class="store">WINWIN OFFICE</p>
            <a href="mailto:nike@win-win.co.kr" class="mail"
                >nike@win-win.co.kr</a
            >
        </div>
    </div>
</template>
<script>
import { getAlarm } from '@/api/alarm';
export default {
    name: 'UserInfo.vue',
    data() {
        return {
            page: 0,
            size: 10,
            alarmDataList: [],
            alarmActive: false,
        };
    },
    created() {
        this.alarmData();
    },
    methods: {
        openAlarm() {
            this.alarmActive = !this.alarmActive;
            if (this.alarmActive) {
                const alarmH =
                    this.$refs.alarm.querySelector('.alarm-list').offsetHeight +
                    40;
                this.$refs.alarm.style.height = alarmH + 'px';
                this.alarmLading = false;
            } else {
                this.$refs.alarm.style.height = '0px';
            }
        },
        alarmClose() {
            this.alarmActive = false;
            this.$refs.alarm.style.height = '0px';
        },
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
        async alarmData() {
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: this.page,
                    size: this.size,
                });
                this.alarmDataList = response;
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
    },
};
</script>
<style scoped>
.user-info {
    position: relative;
    height: 20px;
}
.user-info:after {
    content: '';
    clear: both;
    display: block;
    overflow: hidden;
}
.user-info .label {
    float: left;
    display: inline-flex;
    height: 16px;
    padding: 0 5px;
    border-radius: 2px;
    align-content: center;
    justify-content: center;
    background: #000;
    font-weight: bold;
    font-size: 10px;
    color: #fff;
    letter-spacing: 0;
}
.user-info .store-name {
    display: inline-flex;
    line-height: 16px;
    font-size: 14px;
    font-weight: bold;
    color: #000;
}
.user-info .side {
    position: absolute;
    top: -2px;
    right: 0;
    display: flex;
    width: 100%;
    justify-content: end;
}
.user-info .side [class^='btn-'] {
    display: block;
    width: 22px;
    height: 22px;
    background: no-repeat center;
}
.user-info .side [class^='btn-'] span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.user-info .side [class^='btn-']:first-child {
    margin-left: auto;
}
.user-info .side [class^='btn-'] + [class^='btn-'] {
    margin-right: -2px;
    margin-left: 10px;
}
.user-info .side .btn-out {
    background-image: url('../../assets/images/svg/icon-logout.svg');
}
.user-info .side .btn-alarm {
    background-image: url('../../assets/images/svg/icon-alarm-off.svg');
}
.user-info .side .btn-alarm.active {
    background-image: url('../../assets/images/svg/icon-alarm-on.svg');
}
::v-deep .el-scrollbar__bar {
    right: 18px;
}
.alarm-box {
    z-index: 2;
    position: absolute;
    top: 34px;
    left: 0;
    box-sizing: border-box;
    width: 100%;
    height: 0;
    max-height: 300px;
    border-radius: 2px;
    box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.1);
    background: #fff;
    overflow: hidden;
    transition: height ease-in-out 0.3s;
}
.alarm-box .title {
    display: block;
    padding: 0 20px;
    font-size: 14px;
    line-height: 40px;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    color: #000;
    letter-spacing: 0.05em;
}
.alarm-item:first-child {
    margin-top: 10px;
    padding-top: 0;
}
.alarm-item {
    padding: 12px 41px 12px 20px;
}
.alarm-item .date {
    display: block;
    margin-top: 4px;
    font-size: 10px;
    line-height: 15px;
    color: #888;
}
.alarm-item .txt {
    font-size: 11px;
    line-height: 17px;
    color: #333;
}
.alarm-item.active {
    position: relative;
}
.alarm-item.active:before {
    position: absolute;
    top: 17px;
    left: 20px;
    content: '';
    display: block;
    width: 4px;
    height: 4px;
    border-radius: 100%;
    background: #f36910;
}
.alarm-item + .alarm-item {
    border-top: 1px solid #eee;
}
.alarm-list {
    max-height: 260px;
}
.alarm-list .alarm-item:first-child:before {
    top: 5px;
}
.alarm-list .alarm-item {
    margin-top: 0;
    padding-left: 29px;
}
.alarm-box .btn-close {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 30px;
    height: 30px;
    border-radius: 0;
    background-image: url('../../assets/images/svg/icon-close-small.svg') !important;
}
.space-info {
    margin-top: 13px;
    font-weight: 300;
    font-size: 12px;
    color: #000;
}
.space-info .store {
    display: flex;
    height: 14px;
    align-content: center;
}
.space-info .mail {
    display: flex;
    height: 14px;
    margin-top: 5px;
    align-content: center;
    opacity: 0.6;
}
</style>
