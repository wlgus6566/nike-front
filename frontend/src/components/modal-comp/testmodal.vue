<template>
    <modal name="testModal" :width="800" :height="auto" :adaptive="true">
        <ModalComp @close="close" :name="test">
            <template v-slot:modal-header>
                <div class="modal-header">
                    <h1>123</h1>
                </div>
            </template>
            <template v-slot:modal-content>123</template>
            <template v-slot:modal-footer>
                <div @click="$modal.hide('test')">123123123</div>
            </template>
        </ModalComp>
    </modal>
</template>
<script>
import ModalComp from '@/components/modal-comp/index';
export default {
    name: 'testModal',
    methods: {},
    components: { ModalComp },
};
</script>
<style scoped></style>
