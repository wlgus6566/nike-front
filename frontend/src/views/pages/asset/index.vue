<template>
    <transition name="page-change" mode="out-in" appear>
        <keep-alive>
            <router-view />
        </keep-alive>
    </transition>
</template>
<script>
export default {
    name: 'asset',
};
</script>
<style scoped></style>
