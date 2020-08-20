<template>
    <div class="user">
        <div class="user-info">
            <span class="store-name">
                {{ userNickname }}
            </span>
            <div class="side">
                <template v-if="alarmDataListCont">
                    <button type="button" class="btn-out" @click="logout">
                        <span>로그아웃</span>
                    </button>
                    <button
                        type="button"
                        class="btn-alarm"
                        :class="{ active: alarmDataListCont.length }"
                        @click="openAlarm"
                    >
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
                        v-if="alarmDataListCont.length"
                        :class="{ active: alarmActive }"
                    >
                        <strong class="title">NOTICE</strong>
                        <el-scrollbar
                            class="cart-list-scroll"
                            wrap-class="alarm-list-wrap"
                            :native="false"
                        >
                            <transition-group
                                tag="ul"
                                class="alarm-list"
                                name="fade"
                            >
                                <li
                                    class="alarm-item active"
                                    v-for="item in alarmDataListCont"
                                    :key="item.alarmSeq"
                                    @click="delAlarmData(item.alarmSeq)"
                                >
                                    <router-link :to="setUrl(item)" class="txt">
                                        {{ item.folderName }}이(가) 업데이트
                                        되었습니다.
                                        <span class="date">
                                            {{ item.registrationDt }}
                                        </span>
                                    </router-link>
                                </li>
                            </transition-group>
                        </el-scrollbar>
                        <Loading v-if="loadingData" />
                        <button
                            type="button"
                            class="btn-close"
                            @click="alarmClose"
                        >
                            <span>닫기</span>
                        </button>
                    </div>
                </template>
            </div>
        </div>
        <div class="space-info">
            <div class="store">
                {{ userRole }}
            </div>
            <div class="mail">
                {{ userIdVal }}
            </div>
        </div>
    </div>
</template>
<script>
import Loading from '@/components/loading';
import { getAlarm, delAlarm } from '@/api/alarm';
import {
    getUserNickFromCookie,
    getUserIdFromCookie,
    getRoleFromCookie,
} from '@/utils/cookies';

export default {
    name: 'UserInfo.vue',
    data() {
        return {
            page: 0,
            size: 4,
            alarmDataList: [],
            alarmDataListCont: [],
            alarmActive: false,
            totalPage: 0,
            loadingData: false,
        };
    },
    mounted() {
        this.initFetchData();
    },
    components: {
        Loading,
    },
    watch: {},
    computed: {
        userNickname() {
            return this.$store.state.nick || getUserNickFromCookie();
        },
        userIdVal() {
            return this.$store.state.user || getUserIdFromCookie();
        },
        userRole() {
            return this.$store.state.role || getRoleFromCookie();
        },
    },
    methods: {
        openAlarm() {
            if (this.alarmDataList.content.length !== 0) {
                this.alarmActive = !this.alarmActive;
                this.$refs.alarm.style.height =
                    document.querySelector('.alarm-list').offsetHeight +
                    40 +
                    'px';
                this.handleScroll();
                document
                    .querySelector('.alarm-list-wrap')
                    .addEventListener('scroll', this.handleScroll);
            }
        },
        alarmClose() {
            this.alarmActive = false;
            this.$refs.alarm.style.height = '0';
        },
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
        initFetchData() {
            this.alarmData();
        },
        //클릭시 업로드 한 폴더 리스트 다시 불러오기
        handleScroll() {
            if (this.loadingData) return;
            const alarmList = document.querySelector('.alarm-list-wrap');
            if (
                alarmList.clientHeight + alarmList.scrollTop >=
                alarmList.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.alarmDataListCont.length >= this.size &&
                this.alarmDataListCont.length !== 0
            ) {
                console.log('infiniteScroll');
                this.alarmData(true);
            }
        },
        endPage() {
            alert('마지막 페이지');
        },
        // 알람목록
        async alarmData(infinite) {
            this.loadingData = true;
            console.log(this.page);
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: this.page,
                    size: this.size,
                });
                console.log(this.totalPage);
                this.totalPage = response.totalPages;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.alarmDataListCont = this.alarmDataListCont.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.alarmDataList = response;
                    this.alarmDataListCont = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        // 알람삭제
        async delAlarmData(seq) {
            console.log(seq);
            try {
                const {
                    data: { data: response },
                } = await delAlarm(seq);
                await this.alarmData();
                this.alarmActive = false;
            } catch (error) {
                //console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        setUrl(item) {
            const topCode =
                item.typeCd === 'REPORT_MANAGE'
                    ? 'report'
                    : item.typeCd === 'FOUNDATION'
                    ? 'foundation'
                    : item.typeCd === 'TOOLKIT'
                    ? 'toolkit'
                    : 'asset';
            if (topCode === 'report') {
                return `/${topCode}/${item.folderSeq}`.toLocaleLowerCase();
            } else {
                return `/${topCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
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
    width: 100%;
    text-align: right;
}
.user-info .side [class^='btn-'] {
    display: inline-flex;
    width: 22px;
    height: 22px;
    background: no-repeat center;
}
.user-info .side [class^='btn-'] span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
/*.user-info .side [class^='btn-']:first-child {*/
/*    margin-left: auto;*/
/*}*/
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
    max-height: 260px;
    border-radius: 2px;
    box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.1);
    background: #fff;
    overflow: hidden;
    transition: height ease-in-out 0.3s;
    text-align: left;
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
    display: block;
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
    max-height: 200px;
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
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
