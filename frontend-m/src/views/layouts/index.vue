<template>
    <div id="wrap">
        <section class="contents">
            <transition mode="out-in" name="page-change">
                <router-view></router-view>
            </transition>
        </section>
        <Navigation></Navigation>
    </div>
</template>
<script>
import Navigation from '@/components/app-nav';
export default {
    name: 'LayoutIndex',
    components: {
        Navigation,
    },
};
</script>
<style scoped></style>
