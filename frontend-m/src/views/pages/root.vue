<template>
    <keep-alive>
        <router-view :key="$route.fullPath"></router-view>
    </keep-alive>
</template>
<script>
export default {
    name: 'root',
};
</script>
<style scoped></style>
