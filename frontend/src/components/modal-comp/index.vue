<template>
    <section class="modal">
        <slot name="modal-header"></slot>
        <el-scrollbar
            class="modal-content"
            wrap-class="modal-scroll"
            :native="false"
        >
            <slot name="modal-content"></slot>
        </el-scrollbar>
        <slot name="modal-footer"></slot>
        <slot name="modal-close"></slot>
        <button
            type="button"
            class="modal-close"
            @click.prevent="$emit('close')"
        >
            modal close
        </button>
    </section>
</template>
<script>
export default {};
</script>
<style scoped></style>
