<template>
    <el-dialog
        title=""
        class="modal-wrap"
        :visible="visible"
        width="600px"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
                    <div class="title-wrap">
                        <h3 class="form-title mt0">계정 관리</h3>
                        <div class="right">
                            <button type="button" class="txt-btn-orange">
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
                            <div class="form-column">
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
                                <div class="select-cascader">
                                    <el-cascader
                                        v-model="addAuthority.value"
                                        :options="addAuthority.options"
                                        :props="{ checkStrictly: true }"
                                    />
                                </div>
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <div class="btn-area">
                        <button type="button" class="btn-s">
                            <span>취소</span>
                        </button>
                        <button
                            type="button"
                            class="btn-s-black"
                            @click="
                                $emit(
                                    'addAuthData',
                                    addAuthority.value,
                                    addUserData
                                )
                            "
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
    data() {
        return {};
    },
    props: {
        visible: Boolean,
        receipt: Object,
        addUserData: Object,
        addAuthority: Object,
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
