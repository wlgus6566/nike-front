<template>
    <el-dialog
        title="상세보기"
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-contents">
            모달 컨텐츠 등록
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
<style scoped></style>
