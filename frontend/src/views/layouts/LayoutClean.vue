<template>
    <div id="wrap">
        <section id="container">
            <transition name="component-fade" mode="out-in">
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
