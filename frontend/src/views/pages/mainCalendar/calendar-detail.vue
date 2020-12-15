<template>
    <el-dialog
        title=""
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
                    <div class="title-wrap">
                        <h3 class="form-title mt0">CALENDAR 관리</h3>
                    </div>
                    <hr class="hr-black mt10" />
                    <ul class="form-list">
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">구분</label>
                            </div>
                            <div class="form-column">
                              {{ sortName}}
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required"
                                    >일정 명</label
                                >
                            </div>
                            <div class="form-column">
                              {{ calendarData.title }}
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">기간</label>
                            </div>
                            <div class="form-column">
                             {{calendarData.startStr}} ~
                              {{calendarData.endStr}}
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title">메모</label>
                            </div>
                            <div class="form-column">
                              {{calendarData.constraint}}
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <div class="btn-area">
                        <button
                            type="button"
                            class="btn-s"
                        >
                            <span>수정</span>
                        </button>
                        <button
                            type="button"
                            class="btn-s-black"
                        >
                            <span>확인</span>
                        </button>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>

export default {
    props: ['visible' , 'calendarData'],
    data() {
        return {
          sortName : null
        };
    },

    watch: {
      calendarData(){
        const sortName = this.calendarData.classNames[0]
        if ( sortName === 'edu') {
          this.sortName =  '교육'
        } else if (sortName === 'campaign') {
          this.sortName =   '캠페인'
        } else if (sortName  === 'upload') {
          this.sortName =   '업로드'
        } else {
          this.sortName =   '오피셜'
        }
      }
    },
    methods: {
    },
};
</script>
<style>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
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
