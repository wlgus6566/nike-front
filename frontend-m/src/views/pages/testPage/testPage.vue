<template>
    <div>
        <button type="button" @click="openModal">팝업</button> <br />
        <button type="button" @click="openModal2">팝업 full</button>
        <div class="modal-wrap">
            <ModalEx :visible.sync="visible.modalEx" />
            <ModalFull :visible.sync="visible.modalFull" />
        </div>
    </div>
</template>
<script>
import ModalEx from '@/components/modal-ex';
import ModalFull from '@/components/modal-ex/full.vue';
export default {
    data() {
        return {
            visible: {
                modalEx: false,
                modalFull: false,
            },
        };
    },
    components: {
        ModalEx,
        ModalFull,
    },
    mounted() {},
    methods: {
        openModal() {
            this.visible.modalEx = true;
        },
        openModal2() {
            this.visible.modalFull = true;
        },
    },
};
</script>
<style scoped></style>
