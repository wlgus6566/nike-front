<template>
    <el-dialog
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-content">
            <el-scrollbar view-class="view-box" :native="false">
                <div class="el-dialog__inner">
                    <div>
                        <div class="title-wrap">
                            <h3 class="form-title mt0">계정 관리</h3>
                            <div class="right" v-if="addUserData.userSeq">
                                <button
                                    type="button"
                                    class="txt-btn-orange"
                                    @click="
                                        $emit('userDelete', addUserData.userSeq)
                                    "
                                >
                                    <span>계정 삭제</span>
                                </button>
                            </div>
                        </div>
                        <hr class="hr-black mt10" />
                        <ul class="form-list">
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title required"
                                        >계정명</label
                                    >
                                </div>
                                <div class="form-column">
                                    <input
                                        type="text"
                                        v-model="addUserData.nickname"
                                    />
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title required"
                                        >ID(E-MAIL)</label
                                    >
                                </div>
                                <div
                                    class="form-column"
                                    v-if="addUserData.userSeq"
                                >
                                    <div class="id-check">
                                        <input
                                            type="text"
                                            v-model="addUserData.userId"
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div class="form-column" v-else>
                                    <div class="id-check">
                                        <input
                                            type="text"
                                            v-model="addUserData.userId"
                                        />
                                        <button
                                            class="btn-form-gray"
                                            @click="
                                                $emit(
                                                    'userIdCheck',
                                                    addUserData.userId
                                                )
                                            "
                                        >
                                            <span>ID 중복체크</span>
                                        </button>
                                    </div>
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title required"
                                        >권한그룹</label
                                    >
                                </div>
                                <div class="form-column">
                                    <CascaderSelect
                                        :class="{
                                            readonly:
                                                addAuthority.value[0] === null,
                                        }"
                                        :listCascader="addAuthority"
                                    />
                                </div>
                            </li>
                        </ul>
                        <hr class="hr-gray" />
                    </div>
                </div>
            </el-scrollbar>
        </div>

        <div class="modal-footer">
            <div class="btn-area">
                <button
                    type="button"
                    class="btn-s"
                    @click="$emit('update:visible', false)"
                >
                    <span>취소</span>
                </button>
                <button
                    type="button"
                    class="btn-s-black"
                    @click="
                        $emit(
                            'modifyAuthData',
                            addUserData.userSeq,
                            addAuthority.value,
                            addUserData
                        )
                    "
                    v-if="addUserData.userSeq"
                >
                    <span>수정</span>
                </button>
                <button
                    type="button"
                    class="btn-s-black"
                    @click="
                        $emit('addAuthData', addAuthority.value, addUserData)
                    "
                    v-else
                >
                    <span>저장</span>
                </button>
            </div>
        </div>
    </el-dialog>
</template>

<script>
import CascaderSelect from '@/components/cascader-select';
export default {
    data() {
        return {};
    },
    components: {
        CascaderSelect,
    },
    watch: {
        'addUserData.authName'() {
            //this.addAuthority.value = null;
        },
        'addAuthority.value'(val) {
            //console.log(val);
            if (val[0] === null) {
                if (this.addUserData.authSeqArray === null) return;
                if (this.addUserData.authSeqArray.length) {
                    this.addAuthority.value = this.addUserData.authSeqArray;
                }
            }
        },
    },
    props: ['visible', 'receipt', 'addUserData', 'addAuthority'],
    methods: {
        thisClose() {
            this.$emit('close');
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
.cascader-select .el-input--suffix .el-input__inner {
    height: 39px;
    vertical-align: top;
}
.cascader-select .el-input--suffix .el-input__inner[aria-expanded='false'] {
    color: #888 !important;
}
.cascader-select .el-input--suffix .el-input__inner::placeholder {
    color: #888 !important;
}
.cascader-select .el-input--suffix .el-input__inner::-webkit-input-placeholder {
    color: #888 !important;
}
.cascader-select.readonly .el-input__inner {
    color: #888;
}
</style>
