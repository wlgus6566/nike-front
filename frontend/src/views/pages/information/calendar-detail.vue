<template>
    <el-dialog
        class="modal-wrap calendar-modal"
        :title="calendarData.title"
        :class="className"
        :modal="false"
        :visible="visible"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
                    <div class="calendar-info-box">
                        <div class="row">
                            <span class="key">일정</span>
                            <span class="val">
                                {{ calendarData.beginDt }} ~
                                {{ calendarData.endDt }}
                            </span>
                        </div>
                        <div class="row">
                            <span class="key">설명</span>
                            <span class="val">
                                {{ calendarData.constraint }}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </el-scrollbar>
        <div class="btn-box">
            <button
                type="button"
                class="link-txt"
                @click="$emit('onClickToEdit', calendarData)"
                v-if="
                    folderAuthCheck(
                        'CREATE',
                        'INFORMATION',
                        'INFORMATION_CALENDAR'
                    )
                "
            >
                <span>수정하기</span>
            </button>
        </div>
    </el-dialog>
</template>

<script>
import { authCheck } from '@/utils/authCheck';
export default {
    mixins: [authCheck],
    props: ['visible', 'calendarData'],
    data() {
        return {
            className: null,
        };
    },
    created() {
        this.classNameFn();
    },
    watch: {
        calendarData() {
            this.classNameFn();
        },
    },
    methods: {
        classNameFn() {
            if (this.calendarData.classNames) {
                const className = this.calendarData.classNames[0];
                if (className === 'edu') {
                    this.className = 'edu';
                } else if (className === 'campaign') {
                    this.className = 'campaign';
                } else if (className === 'upload') {
                    this.className = 'upload';
                } else {
                    this.className = 'official';
                }
            }
        },
    },
};
</script>
<style scoped>
.calendar-modal {
    justify-content: center;
    align-items: center;
    background: none;
}
::v-deep.calendar-modal .el-dialog {
    min-width: 386px;
    max-width: 386px;
    margin: 0 !important;
    padding-bottom: 0;
    background: #f7f7f7;
    box-shadow: 0 5px 5px 0 rgba(0, 0, 0, 0.1);
    border: 1px solid #ccc;
}

::v-deep.calendar-modal .el-scrollbar__wrap {
    max-height: 20vh;
}
::v-deep.calendar-modal .el-dialog__header {
    padding: 15px 25px;
    border-bottom: 1px solid #ddd;
}
::v-deep.calendar-modal .el-dialog__headerbtn {
    top: 5px;
    right: 10px;
    background: url('../../../assets/images/svg/icon-close-small.svg') no-repeat
        center;
}
::v-deep.calendar-modal .el-dialog__title {
    position: relative;
    display: block;
    padding: 0 0 0 11px;
    font-size: 14px;
    line-height: 20px;
    font-weight: bold;
}
::v-deep.calendar-modal .el-dialog__title:before {
    content: '';
    position: absolute;
    top: 7px;
    left: 0;
    display: block;
    width: 6px;
    height: 6px;
}
::v-deep.edu .el-dialog__title {
    color: #be1767;
}
::v-deep.edu .el-dialog__title:before {
    background: #be1767;
}
::v-deep.campaign .el-dialog__title {
    color: #007b68;
}
::v-deep.campaign .el-dialog__title:before {
    background: #007b68;
}
::v-deep.upload .el-dialog__title {
    color: #853405;
}
::v-deep.upload .el-dialog__title:before {
    background: #853405;
}
::v-deep.official .el-dialog__title {
    color: #2c0fb4;
}
::v-deep.official .el-dialog__title:before {
    background: #2c0fb4;
}
::v-deep.calendar-modal .el-dialog__body {
    padding: 15px 15px 15px 25px;
}
.calendar-info-box .row {
    overflow: hidden;
}
.calendar-info-box .row + .row {
    margin-top: 10px;
}
.calendar-info-box .row .key {
    float: left;
    display: block;
    width: 40px;
    line-height: 17px;
    font-size: 12px;
    color: #888888;
}
.calendar-info-box .row .val {
    float: left;
    display: block;
    max-width: calc(100% - 50px);
    word-break: break-all;
    line-height: 17px;
    font-size: 12px;
    color: #000;
}
.btn-box {
    text-align: right;
}
.btn-box .link-txt {
    display: inline-block;
    margin-right: 2px;
    padding-right: 14px;
    color: #555;
    font-size: 11px;
    font-weight: bold;
    background: url('../../../assets/images/svg/icon-more-small.svg') no-repeat
        right center;
}
</style>
