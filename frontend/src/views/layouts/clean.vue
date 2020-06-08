<template>
    <div id="wrap">
        <section id="container">
            <div class="contents">
                <transition name="contents-ani" mode="out-in">
                    <router-view></router-view>
                </transition>
            </div>
        </section>
    </div>
</template>
<script>
export default {
    name: 'cleanLayout',
};
</script>
<style scoped></style>
