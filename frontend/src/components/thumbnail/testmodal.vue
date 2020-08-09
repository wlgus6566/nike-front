<template>
    <ModalComp>
        <template v-slot:modal-header>123</template>
        <template v-slot:modal-content>123</template>
        <template v-slot:modal-footer>123</template>
        <button
            type="button"
            class="modal-close"
            @click.prevent="this.$emit('close')"
        >
            modal close
        </button>
    </ModalComp>
</template>
<script>
import ModalComp from '@/components/modal-comp/index';
export default {
    name: 'modal',
    components: {
        ModalComp,
    },
};
</script>
<style scoped></style>
