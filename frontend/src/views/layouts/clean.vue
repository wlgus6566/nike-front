<template>
    <div id="wrap">
        <transition mode="out-in" name="page-change">
            <router-view></router-view>
        </transition>
    </div>
</template>
<script>
export default {
    name: 'LayoutClean',
};
</script>
<style scoped></style>
