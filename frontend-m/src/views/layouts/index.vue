<template>
    <div id="wrap">
        <transition
            mode="out-in"
            @enter="pageEnter"
            @leave="pageLeave"
            name="page-change"
        >
            <router-view></router-view>
        </transition>
        <Navigation></Navigation>
    </div>
</template>
<script>
import Navigation from '@/components/app-nav';
import { Cubic, gsap } from 'gsap/all';
export default {
    name: 'LayoutIndex',
    components: {
        Navigation,
    },
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
    },
};
</script>
<style scoped></style>
