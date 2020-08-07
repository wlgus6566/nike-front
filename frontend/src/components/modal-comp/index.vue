<template>
    <section class="modal">
        <div class="modal-header">
            <slot name="header" />
        </div>
        <el-scrollbar
            class="modal-content"
            wrap-class="modal-scroll"
            :native="false"
        >
            <slot name="content" />
        </el-scrollbar>
        <div class="modal-footer">
            <slot name="footer" />
        </div>
        <button class="modal-close" @click="$emit('close')">
            Close
        </button>
    </section>
</template>
<script>
export default {
    name: 'modal-comp',
    data() {
        return {
            modal: true,
        };
    },
    methods: {
        openModal() {
            this.modal = true;
        },
        closeModal() {
            this.modal = false;
        },
    },
};
</script>
<style scoped></style>
