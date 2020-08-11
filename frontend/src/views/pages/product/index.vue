<template>
    <router-view />
</template>
<script>
export default {
    name: 'order',
    mounted() {
        this.$store.dispatch('basketList');
    },
};
</script>
<style scoped></style>
