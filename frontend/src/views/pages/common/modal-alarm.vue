<template>
    <el-dialog
        title=""
        class="modal-wrap"
        width="600px"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar wrap-class="wrap" view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <h3 class="form-title mt0">알림메일 전송</h3>
                <hr class="hr-black mt10" />
                <h4 class="alarm-title">
                    <span>NIKE SPACE</span><br />컨텐츠 업데이트 알림
                </h4>
                <p class="alarm-txt">
                    <strong>ASSET(SP21 NSW MULTI AM EXCEE)</strong><br />
                    이(가) 업데이트 되었습니다.
                </p>
                <hr class="hr-gray mt40" />
                <h3 class="sub-title mt30">COMMENT</h3>
                <div class="mt10">
                    <span class="textarea">
                        <textarea
                            style="height: 60px;"
                            v-model="alarmComment"
                        />
                    </span>
                </div>
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <button
                type="button"
                class="btn-s-black"
                @click="$emit('sendEmail', alarmComment)"
            >
                <span>알림메일 전송</span>
            </button>
        </div>
    </el-dialog>
</template>
<script>
export default {
    name: 'modalAlarm',
    data() {
        return {
            alarmComment: null,
        };
    },

    props: ['visible'],
};
</script>
<style scoped>
.alarm-title {
    display: block;
    margin-top: 30px;
    font-size: 30px;
    line-height: 43px;
    font-weight: bold;
    color: #000;
}
.alarm-title span {
    letter-spacing: -0.5px;
}
.alarm-txt {
    display: block;
    margin-top: 25px;
    font-size: 14px;
    line-height: 24px;
    color: #000;
}
.hr-gray {
    background: #eee;
}
</style>
