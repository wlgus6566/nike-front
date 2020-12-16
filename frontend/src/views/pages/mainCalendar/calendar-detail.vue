<template>
    <el-dialog
        title=""
        class="modal-wrap calendal-modal"
        :modal="false"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
                    <h3 class="title">{{ calendarData.title }}</h3>
                    <div>
                        <div>
                            <span class="key">일정</span>
                            <span class="val">
                                {{ calendarData.startStr }} ~
                                {{ calendarData.endStr }}
                            </span>
                        </div>
                        <div>
                            <span class="key">설명</span>
                            <span class="val">
                                {{ calendarData.constraint }}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </el-scrollbar>
        <div>
            <button type="button">
                <span>수정하기</span>
            </button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    props: ['visible', 'calendarData'],
    data() {
        return {
            sortName: null,
        };
    },

    watch: {
        calendarData() {
            const sortName = this.calendarData.classNames[0];
            if (sortName === 'edu') {
                this.sortName = '교육';
            } else if (sortName === 'campaign') {
                this.sortName = '캠페인';
            } else if (sortName === 'upload') {
                this.sortName = '업로드';
            } else {
                this.sortName = '오피셜';
            }
        },
    },
    methods: {},
};
</script>
<style>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
    background: none;
}
.el-dialog {
    max-width: 600px;
}
.modal-wrap .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
.form-list .check-label:last-child {
    margin-right: 0;
}
.check-label {
    margin-right: 16px;
}
.check-label:last-child {
    margin-right: 0;
}
.check-label .radio {
    margin: 0;
}
</style>
