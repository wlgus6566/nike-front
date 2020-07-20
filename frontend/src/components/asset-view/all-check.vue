<template></template>
<script>
export default {
    name: 'allCheck',
    props: ['isIndeterminate', 'checkAll', 'checkLength'],
    created() {},
    methods: {
        handleCheckAllChange() {
            this.$emit('handleCheckAllChange');
        },
    },
};
</script>
<style scoped></style>
