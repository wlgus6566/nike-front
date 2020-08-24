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
                        <h3 class="form-title mt0">AGENCY 관리</h3>
                        <div class="right">
                            <button
                                type="button"
                                class="txt-btn-orange"
                                v-if="agencySeq"
                                v-on:click="
                                    $emit('delAgencyManagement', agencySeq)
                                "
                            >
                                <span>AGENCY 삭제</span>
                            </button>
                        </div>
                    </div>
                    <hr class="hr-black mt10" />
                    <ul class="form-list">
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required"
                                    >AGENCY 명</label
                                >
                            </div>
                            <div class="form-column">
                                <input
                                    type="text"
                                    v-model="addAgencyData.agencyName"
                                    required
                                />
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required"
                                    >AGENCY 설명</label
                                >
                            </div>
                            <div class="form-column">
                                <span class="textarea">
                                    <textarea
                                        cols="100"
                                        rows="2"
                                        v-model="
                                            addAgencyData.agencyDescription
                                        "
                                        required
                                    >
                                    </textarea>
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title">대표번호</label>
                            </div>
                            <div class="form-column">
                                <input
                                    type="tel"
                                    v-model="addAgencyData.telephoneNumber"
                                />
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required"
                                    >대표 E-MAIL</label
                                >
                            </div>
                            <div class="form-column">
                                <input
                                    type="email"
                                    v-model="addAgencyData.email"
                                    required
                                />
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <div class="btn-area">
                        <button
                            type="button"
                            class="btn-s"
                            @click="$emit('cancelFn')"
                        >
                            <span>취소</span>
                        </button>
                        <button
                            type="button"
                            class="btn-s-black"
                            v-if="agencySeq"
                            v-on:click="
                                $emit('modifyAgencyManagement', agencySeq)
                            "
                        >
                            <span>수정</span>
                        </button>
                        <button
                            type="button"
                            class="btn-s-black"
                            v-else
                            v-on:click="$emit('addAgencyManagement')"
                        >
                            <span>저장</span>
                        </button>
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
        addAgencyData: Object,
        agencySeq: false,
    },
    created() {},
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
    width: 600px;
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
</style>
