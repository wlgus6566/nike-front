<template>
    <div id="wrap">
        <Header></Header>
        <section class="contents">
            <transition mode="out-in" name="page-change">
                <router-view></router-view>
            </transition>
        </section>
        <Footer></Footer>
        <Navigation></Navigation>
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
};
</script>
<style scoped></style>
