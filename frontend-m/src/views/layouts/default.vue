<template>
    <div id="wrap">
        <Header v-if="!$route.meta.hideHeaderFooter" />
        <section class="contents" v-if="!$route.meta.hideHeaderFooter">
            <transition mode="out-in" name="page-change">
                <router-view></router-view>
            </transition>
        </section>
        <transition mode="out-in" name="page-change" v-else>
            <router-view></router-view>
        </transition>
        <Footer v-if="!$route.meta.hideHeaderFooter" />
        <Navigation />
    </div>
</template>
<script>
import Header from '@/components/app-header';
import Footer from '@/components/app-footer';
import Navigation from '@/components/app-nav';
export default {
    name: 'LayoutDefault',
    components: {
        Header,
        Footer,
        Navigation,
    },
    mounted() {},
};
</script>
<style scoped></style>
