<template>
    <el-dialog
        title="상세보기"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-contents">
            모달 컨텐츠 등록
        </div>

        <!--        <div slot="footer" class="dialog-footer">-->
        <!--            <el-button @click="$emit('update:visible', false)" type="info" round>-->
        <!--                확인-->
        <!--            </el-button>-->
        <!--        </div>-->
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
