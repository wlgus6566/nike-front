<template>
    <div>
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
            <button
                type="button"
                class="modal-close"
                @click.prevent="this.$emit('close')"
            >
                modal close
            </button>
        </section>
    </div>
</template>
<script>
export default {
    name: 'modal-comp',
};
</script>
<style scoped></style>
