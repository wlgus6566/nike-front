<template>
    <div>
        <button type="button" @click="openModal">팝업</button>
        <ModalEx :visible.sync="visible.modalEx" />
    </div>
</template>
<script>
import ModalEx from '@/components/modal-ex';
export default {
    data() {
        return {
            visible: {
                modalEx: false,
            },
        };
    },
    components: {
        ModalEx,
    },
    mounted() {},
    methods: {
        openModal() {
            this.visible.modalEx = true;
        },
    },
};
</script>
<style scoped></style>
