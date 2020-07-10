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
            <button type="button" class="btn-s-black" @click="$emit('update:visible', false)">
                확인
            </button>
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
