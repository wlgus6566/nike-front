<template>
    <div><button type="button" @click="openPop">123</button></div>
</template>
<script>
import testmodal from '@/components/modal-comp/testmodal';
export default {
    methods: {
        openPop() {
            this.$modal.show(testmodal);
        },
    },
};
</script>
<style scoped></style>
