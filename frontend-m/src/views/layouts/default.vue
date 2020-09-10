<template>
    <div id="wrap">
        <Header />
        <section class="contents">
            <transition
                mode="out-in"
                @enter="pageEnter"
                @leave="pageLeave"
                name="page-change"
            >
                <router-view></router-view>
            </transition>
        </section>
        <Footer />
        <Navigation />
    </div>
</template>
<script>
import Header from '@/components/app-header';
import Footer from '@/components/app-footer';
import Navigation from '@/components/app-nav';
import { Cubic, gsap } from 'gsap/all';
export default {
    name: 'LayoutDefault',
    data() {
        return {};
    },
    components: {
        Header,
        Footer,
        Navigation,
    },
    created() {
        this.gnbMenuList();
    },
    mounted() {},
    methods: {
        pageEnter(el, done) {
            this.pageAnimation(
                el,
                { translateY: '30px', opacity: 0 },
                { translateY: '0', opacity: 1 },
                done
            );
        },
        pageLeave(el, done) {
            this.pageAnimation(
                el,
                { translateY: '0', opacity: 1 },
                { translateY: '30px', opacity: 0 },
                done
            );
        },
        pageAnimation(el, fromVal, toVal, done) {
            gsap.fromTo(
                el,
                0.3,
                {
                    ...fromVal,
                    ease: Cubic.easeInOut,
                },
                {
                    ...toVal,
                    ease: Cubic.easeInOut,
                    onComplete: () => {
                        el.style.transform = 'none';
                        if (done) {
                            done();
                        }
                    },
                }
            );
        },
        async gnbMenuList() {
            try {
                await this.$store.dispatch('gnbMenuList');
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>
<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition-property: opacity;
    transition-duration: 0.25s;
}

.fade-enter-active {
    transition-delay: 0.25s;
}

.fade-enter,
.fade-leave-active {
    opacity: 0;
}
</style>
