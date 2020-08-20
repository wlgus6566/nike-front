<template>
    <el-dialog
            title="상세보기"
            class="modal-wrap"
            :visible="visible"
            :append-to-body="true"
            @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <img :src="filePopupFile" />
                <p v-text="filePopupName"></p>
            </div>
            <div></div>
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
        data() {
            return {
                orderComment: '',
            };
        },
        props: ['visible' ,'filePopupFile','filePopupName'],
        mounted() {
        },
        methods: {},
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
