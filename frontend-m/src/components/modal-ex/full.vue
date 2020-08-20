<template>
    <el-dialog
        title="상세보기"
        class="modal full"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                모달 컨텐츠 등록
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <el-button
                @click="$emit('update:visible', false)"
                type="info"
                round
            >
                확인
            </el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {
            orderComment: '',
        };
    },
    props: ['visible'],
    mounted() {},
    methods: {},
};
</script>
<style></style>
