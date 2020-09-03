<template>
    <el-dialog
        title="자동로그아웃안내"
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <h2 class="title">자동로그아웃안내</h2>
                <div class="logout-box">
                    <i class="icon-logout"></i>
                    <div class="time-box">
                        <span class="txt">남은시간</span>
                        <strong class="time">{{ time }}</strong>
                    </div>
                    <p class="desc">
                        5분 후 로그인 유지시간이 만료될 예정입니다.<br />
                        계속 이용 하시려면 로그인 시간을 연장해주세요.
                    </p>
                    <div class="btn-area">
                        <button
                            type="button"
                            class="btn-s-black"
                            @click="loginUpdate"
                        >
                            <span>연장</span>
                        </button>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
import { getLoginUpdate } from '@/api/mypage';
export default {
    name: 'ModalLogoutConfirm',
    data() {
        return {
            orderComment: '',
        };
    },
    computed: {
        visible() {
            return this.$store.state.logoutTimer.modalVisible;
        },
        time() {
            return this.$store.state.logoutTimer.count;
        },
    },
    methods: {
        async loginUpdate() {
            await getLoginUpdate();
            this.close();
        },
        close() {
            this.$store.commit('LOGOUT_MODAL_STATE', false);
        },
    },
};
</script>
<style scoped>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.modal-wrap .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
::v-deep .el-dialog__headerbtn {
    display: none;
}
.title {
    margin-top: -10px;
    font-size: 18px;
    color: #000;
    line-height: 47px;
    border-bottom: 2px solid #000;
}
.logout-box {
    margin-top: 50px;
}
.icon-logout {
    display: block;
    width: 160px;
    height: 160px;
    margin: 0 auto;
    background: url('../../../assets/images/svg/illust-page-time.svg') no-repeat
        center;
}
.time-box {
    display: flex;
    align-items: center;
    justify-content: center;
}
.time-box .txt {
    display: inline-flex;
    line-height: 29px;
    font-size: 20px;
    color: #333;
    font-weight: bold;
}
.time-box .time {
    display: inline-flex;
    margin-left: 8px;
    line-height: 29px;
    font-size: 30px;
    font-weight: bold;
    color: #fa5400;
    letter-spacing: 0;
}
.desc {
    margin-top: 10px;
    font-size: 12px;
    color: #888;
    line-height: 18px;
    text-align: center;
}
</style>
