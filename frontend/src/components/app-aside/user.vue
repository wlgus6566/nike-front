<template>
    <div class="user">
        <div class="user-info">
            <span class="store-name">
                {{ userNickname }}
            </span>
            <div class="side">
                <template v-if="alarmDataListCont">
                    <button type="button" class="logout" @click="logout">
                        로그아웃
                    </button>
                    <button
                        type="button"
                        class="btn-alarm"
                        :class="{ active: alarmDataListCont.length }"
                        @click="openAlarm"
                    >
                        <span>알람</span>
                    </button>
                    <!-- <div class="alarm-box">
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
                                    v-for="(item, index) in alarmDataListCont"
                                    :key="index"
                                    @click="delAlarmData(item.alarmSeq)"
                                >
                                    <router-link
                                        :to="setUrl(item)"
                                        class="txt"
                                        v-if="item.typeAction === 'NEW'"
                                    >
                                        새로 등록된
                                        <em
                                            v-if="
                                                item.typeCd !== 'REPORT_MANAGE'
                                            "
                                        >
                                            {{ item.typeCd }}
                                        </em>
                                        <em v-else>REPORT</em>
                                        ({{ item.folderName }})이 있습니다.
                                    </router-link>
                                    <router-link
                                        :to="setUrl(item)"
                                        class="txt"
                                        v-else
                                    >
                                        <em
                                            v-if="
                                                item.typeCd !== 'REPORT_MANAGE'
                                            "
                                        >
                                            {{ item.typeCd }}
                                        </em>
                                        <em v-else>REPORT</em>
                                        ({{ item.folderName }})
                                        <span
                                            v-if="
                                                item.typeAction !== 'FEEDBACK'
                                            "
                                        >
                                            이(가) 업데이트 되었습니다.
                                        </span>
                                        <span v-else>
                                            에 피드백이 등록되었습니다.
                                        </span>
                                        <span class="date">
                                            {{ item.registrationDt }}
                                        </span>
                                    </router-link>
                                </li>
                            </transition-group>
                        </el-scrollbar>
                        <Loading
                            class="list-loading"
                            :width="172"
                            :height="172"
                            v-if="loadingData"
                        />
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
                {{ userAuthName }}
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
    getAuthNameFromCookie,
} from '@/utils/cookies';

export default {
    name: 'UserInfo.vue',
    data() {
        return {
            page: 0,
            size: 10,
            alarmDataList: [],
            alarmDataListCont: [],
            alarmActive: false,
            totalPage: 0,
            loadingData: false,
            folderName: '',
        };
    },
    mounted() {
        this.initFetchData();
    },
    components: {
        Loading,
    },
    watch: {
        $route(to, from) {
            this.initFetchData();
        },
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
    activated() {},
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
            this.totalPage = null;
            this.page = 0;
            this.alarmDataListCont = null;
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
                this.alarmData(true);
            }
        },
        endPage() {
            alert('마지막 페이지 입니다.');
        },
        // 알람목록
        async alarmData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: 0,
                    size: this.size,
                });
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
                console.error(error);
            }
        },
        // 알람삭제
        async delAlarmData(seq) {
            try {
                const {
                    data: { data: response },
                } = await delAlarm(seq);
                //console.log(response);
                await this.alarmData();
                this.alarmActive = false;
            } catch (error) {
                console.error(error);
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
                return `/${topCode}/detail/${item.folderSeq}`.toLocaleLowerCase();
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
    display: block;
    max-width: 180px;
    line-height: 16px;
    font-size: 14px;
    font-weight: bold;
    color: #000;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.user-info .side {
    position: absolute;
    top: -2px;
    right: 0;
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: flex-end;
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
.user-info .side .logout {
    position: relative;
    display: inline-flex;
    margin: 0 10px;
    padding: 0 10px;
    font-size: 11px;
    color: #000;
}
.user-info .side .logout:after {
    position: absolute;
    top: 50%;
    right: 0;
    content: '';
    display: inline-block;
    width: 1px;
    height: 10px;
    background-color: #ddd;
    transform: translateY(-50%);
}
/*.user-info .side .btn-out {*/
/*    background-image: url('../../assets/images/svg/icon-logout.svg');*/
/*}*/
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
    display: block;
    height: 14px;
    line-height: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.space-info .mail {
    color: #888;
    display: flex;
    line-height: 14px;
    margin-top: 5px;
    align-content: center;
    letter-spacing: -0.5px;
}
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
