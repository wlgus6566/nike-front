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
                        <div class="right">
                            <button type="button" class="txt-btn-orange">
                                <span>일정 삭제</span>
                            </button>
                        </div>
                    </div>
                    <hr class="hr-black mt10" />
                    <ul class="form-list">
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">AGENCY 명</label>
                            </div>
                            <div class="form-column">
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="sortation" checked />
                                        <span></span>
                                    </span>
                                    <span>교육</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="sortation" />
                                        <span></span>
                                    </span>
                                    <span>캠페인</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="sortation" />
                                        <span></span>
                                    </span>
                                    <span>기타 공개일정</span>
                                </label>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">일정 명</label>
                            </div>
                            <div class="form-column">
                                <input type="text" />
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">기간</label>
                            </div>
                            <div class="form-column">
                                <div class="data-picker">
                                    <el-date-picker
                                        v-model="value1"
                                        type="daterange"
                                        range-separator="-"
                                        start-placeholder="Start date"
                                        end-placeholder="End date"
                                    >
                                    </el-date-picker>
                                </div>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">메모</label>
                            </div>
                            <div class="form-column">
                                <span class="textarea">
                                    <textarea cols="100" rows="2"> </textarea>
                                </span>
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <div class="btn-area">
                        <button type="button" class="btn-s"><span>취소</span></button>
                        <button type="button" class="btn-s-black"><span>저장</span></button>
                    </div>
                </div>
            </div>
        </el-scrollbar>

        <!--        <div slot="footer" class="dialog-footer">-->
        <!--            <el-button @click="$emit('update:visible', false)" type="info" round>-->
        <!--                확인-->
        <!--            </el-button>-->
        <!--        </div>-->
    </el-dialog>
</template>

<script>
export default {
    props: {
        visible: Boolean,
        receipt: Object,
    },

    methods: {
        removeBodyClass(className) {
            const body = document.body;
            body.classList.remove(className);
        },
        print() {
            this.removeBodyClass('print-detail');
            window.print();
        },
    },
};
</script>
<style>
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
</style>
