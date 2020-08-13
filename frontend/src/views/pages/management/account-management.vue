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
                                    @click="userDelete"
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
                                <div class="form-column">
                                    <div class="id-check">
                                        <input
                                            type="text"
                                            v-model="addUserData.userId"
                                        />
                                        <button
                                            class="btn-form-gray"
                                            @click="userIdCheck"
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
                <button type="button" class="btn-s-black" @click="addAuthData">
                    <span>저장</span>
                </button>
            </div>
        </div>
    </el-dialog>
</template>

<script>
import CascaderSelect from '@/components/cascader-select';
import bus from '@/utils/bus';
export default {
    data() {
        return {};
    },
    components: {
        CascaderSelect,
    },
    watch: {
        'addUserData.authName'() {
            this.addAuthority.value = [7];
        },
    },
    props: ['visible', 'receipt', 'addUserData', 'addAuthority'],
    methods: {
        userIdCheck() {
            bus.$emit('userIdCheck', this.addUserData.userId);
        },
        addAuthData() {
            bus.$emit('addAuthData', this.addAuthority.value, this.addUserData);
        },
        userDelete() {
            bus.$emit('userDelete', this.addUserData.userSeq);
        },
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
</style>
