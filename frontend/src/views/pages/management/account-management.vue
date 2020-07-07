<template>
    <el-dialog
        title="Title"
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
                123123<br />
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <el-button @click="$emit('update:visible', false)" type="info" round>
                확인
            </el-button>
        </div>
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
