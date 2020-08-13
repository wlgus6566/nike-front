<template>
    <transition mode="out-in" name="page-change">
        <router-view></router-view>
    </transition>
</template>
<script>
export default {
    name: 'LayoutClean',
};
</script>
<style scoped></style>
