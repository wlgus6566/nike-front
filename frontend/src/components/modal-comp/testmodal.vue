<template>
    <ModalComp @close="thisClose">
        <template v-slot:modal-header>
            <div class="modal-header">
                <h1>123</h1>
            </div>
        </template>
        <template v-slot:modal-content>123</template>
        <template v-slot:modal-footer>123</template>
    </ModalComp>
</template>
<script>
import ModalComp from '@/components/modal-comp/index';
export default {
    methods: {
        thisClose() {
            this.$emit('close');
        },
    },
    components: { ModalComp },
};
</script>
<style scoped></style>
