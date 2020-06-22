<template>
    <div id="wrap">
        <section id="container">
            <transition mode="out-in" name="page-change">
                <router-view></router-view>
            </transition>
        </section>
    </div>
</template>
<script>
export default {
    name: 'LayoutClean',
};
</script>
<style scoped></style>
