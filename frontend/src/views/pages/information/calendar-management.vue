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
                        <div class="right" v-if="calendarDetail.calendarSeq">
                            <button
                                type="button"
                                class="txt-btn-orange"
                                @click="onClickToDelete"
                            >
                                <span>일정 삭제</span>
                            </button>
                        </div>
                    </div>
                    <hr class="hr-black mt10" />
                    <ul class="form-list">
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">구분</label>
                            </div>
                            <div class="form-column">
                                <label
                                    class="check-label"
                                    v-for="item in calenderSectionCodeList"
                                    :key="item.code"
                                >
                                    <span class="radio">
                                        <input
                                            type="radio"
                                            v-model="
                                                detailData.calendarSectionCode
                                            "
                                            name="calendarSectionCode"
                                            :value="item.code"
                                        />
                                        <i></i>
                                        <span class="txt"
                                            >{{ item.codeName }}
                                        </span>
                                    </span>
                                </label>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required"
                                    >일정 명</label
                                >
                            </div>
                            <div class="form-column">
                                <input
                                    ref="scheduleName"
                                    type="text"
                                    v-model="detailData.scheduleName"
                                />
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">기간</label>
                            </div>
                            <div class="form-column">
                                <div class="date-picker-group">
                                    <div class="date-picker">
                                        <el-date-picker
                                            ref="dataPeriod"
                                            v-model="beginDt"
                                            type="date"
                                            placeholder="YYYY.MM.DD"
                                            format="yyyy.MM.dd"
                                            :picker-options="pickerBeginOption"
                                        >
                                        </el-date-picker>
                                    </div>
                                    <div class="date-picker">
                                        <el-date-picker
                                            v-model="endDt"
                                            type="date"
                                            placeholder="YYYY.MM.DD"
                                            format="yyyy.MM.dd"
                                            :picker-options="pickerEndOption"
                                        >
                                        </el-date-picker>
                                    </div>
                                </div>

                                <!--  <div class="data-picker">
                                    <el-date-picker
                                        ref="dataPeriod"
                                        v-model="dataPeriod"
                                        type="daterange"
                                        value-format="yyyy.MM.dd"
                                        range-separator="-"
                                        start-placeholder="Start date"
                                        end-placeholder="End date"
                                    >
                                    </el-date-picker>
                                </div>-->
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title">메모</label>
                            </div>
                            <div class="form-column">
                                <span class="textarea">
                                    <textarea
                                        cols="100"
                                        rows="2"
                                        v-model="detailData.contents"
                                    ></textarea>
                                </span>
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <div class="btn-area">
                        <button
                            type="button"
                            class="btn-s"
                            @click="closeDialog"
                        >
                            <span>취소</span>
                        </button>
                        <button
                            type="button"
                            class="btn-s-black"
                            @click="onClickToSave"
                        >
                            <span>저장</span>
                        </button>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
export default {
    props: {
        visible: Boolean,
        statusCode: String,
        calendarSeq: Number,
        calendarDetail: Object,
        calenderSectionCodeList: Array,
        // pickerBeginOption: {
        //     firstDayOfWeek: 7,
        //     cellClassName: (date) => {
        //         if (new Date(date).getDay() === 0) {
        //             return 'el-holiday';
        //         }
        //     },
        //     disabledDate: (time) => {
        //         console.log('ss')
        //         const minDt = new Date();
        //         minDt.setMonth(minDt.getMonth() - 3);
        //         return (
        //             time.getTime() > this.endDt.getTime() ||
        //             time.getTime() < minDt ||
        //             time.getTime() > new Date()
        //         );
        //     },
        // },
        // pickerEndOption: {
        //     firstDayOfWeek: 7,
        //     cellClassName: (date) => {
        //         if (new Date(date).getDay() === 0) {
        //             return 'el-holiday';
        //         }
        //     },
        //     disabledDate: (time) => {
        //         const minDt = new Date();
        //         minDt.setMonth(minDt.getMonth() - 3);
        //         return (
        //             time.getTime() < this.beginDt.getTime() ||
        //             time.getTime() < minDt ||
        //             time.getTime() > new Date()
        //         );
        //     },
        // },
    },
    data() {
        return {
            detailData: Object,
            beginDt: new Date(),
            endDt: new Date(),
            today: new Date(),
            calendarDialogInitData: {
                calendarSectionCode: 'EDUCATION',
                scheduleName: null,
                beginDt: null,
                endDt: null,
                contents: null,
            },
            pickerBeginOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.EndDt) {
                        return time.getTime() > this.EndDt.getTime();
                    }
                },
            },
            pickerEndOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.BeginDt) {
                        return time.getTime() < this.BeginDt.getTime();
                    }
                },
            },
        };
    },
    watch: {
        calendarDetail() {
            if (!!this.calendarSeq) {
                // beginDt date fomat 변환
                const beginsecDate = this.calendarDetail.beginDt; /// 문자열 or  숫자 데이터
                const beginyear = beginsecDate.substr(0, 4);
                const beginmonth = beginsecDate.substr(5, 2) - 1;
                const beginday = beginsecDate.substr(8, 2);
                const beginDtdate = new Date(beginyear, beginmonth, beginday); // date로 변경
                // endDt date fomat 변환
                const endDtsecDate = this.calendarDetail.endDt; /// 문자열 or  숫자 데이터
                const endDtyear = endDtsecDate.substr(0, 4);
                const endDtmonth = endDtsecDate.substr(5, 2) - 1;
                const endDtday = endDtsecDate.substr(8, 2);
                const endDtDtdate = new Date(endDtyear, endDtmonth, endDtday); // date로 변경

                this.detailData = this.calendarDetail;
                this.beginDt = beginDtdate;
                this.endDt = endDtDtdate;
            } else {
                this.detailData = this.calendarDetail;
                this.beginDt = this.today;
                this.endDt = this.today;
            }
        },
    },
    methods: {
        validationData() {
            if (!this.detailData.scheduleName) {
                alert('일정 명을 입력해 주세요.');
                this.$refs.scheduleName.focus();
                return false;
            } else if (this.beginDt === undefined || this.endDt === undefined) {
                alert('기간을 선택해 주세요.');
                this.$refs.dataPeriod.focus();
                return false;
            }
            return true;
        },
        onClickToSave() {
            if (this.validationData() && confirm('일정을 등록하시겠습니까?')) {
                // console.log(this.$moment(this.beginDt).format('YYYY-MM-DD'));
                // console.log(this.$moment(this.endDt).format('YYYY-MM-DD'));
                this.detailData = {
                    calendarSectionCode: this.detailData.calendarSectionCode,
                    scheduleName: this.detailData.scheduleName,
                    contents: this.detailData.contents,
                    beginDt: this.$moment(this.beginDt).format('YYYY.MM.DD'),
                    endDt: this.$moment(this.endDt).format('YYYY.MM.DD'),
                };
                if (this.statusCode === 'EDIT') {
                    this.$emit(
                        'modifyCalendar',
                        this.calendarSeq,
                        this.detailData
                    );
                } else {
                    this.$emit('createCalendar', this.detailData);
                }
            }
        },
        onClickToDelete() {
            if (confirm('선택한 일정을 삭제하시겠습니까?')) {
                this.$emit('delCalendar', this.calendarSeq);
            }
        },
        closeDialog() {
            this.$emit('closeDialog');
        },
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
    padding-right: 0;
}
</style>
