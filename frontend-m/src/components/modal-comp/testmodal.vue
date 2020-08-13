<template>
    <ModalComp @close="thisClose">
        <template v-slot:modal-header>
            <div class="modal-header">
                <h1>상세보기</h1>
            </div>
        </template>
        <template v-slot:modal-content>컨텐츠영역</template>
        <template v-slot:modal-footer>
            footer 영역 없으면 안써도 됩니다
        </template>
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
