<template>
    <lottie
        class="loading"
        :options="defaultOptions"
        @animCreated="handleAnimation"
    />
</template>
<script>
import Lottie from '@/components/lottie';
import * as animationData from '@/assets/lottie/loading.json';

export default {
    name: 'loading',
    components: {
        lottie: Lottie,
    },
    props: ['loadingStyle'],
    data() {
        return {
            defaultOptions: { animationData: animationData.default },
            animationSpeed: 1,
        };
    },
    methods: {
        handleAnimation: function (anim) {
            this.anim = anim;
            console.log(this.anim);
        },

        stop: function () {
            console.log(this.anim);
            this.anim.stop();
        },

        play: function () {
            this.anim.play();
        },

        pause: function () {
            this.anim.pause();
        },

        onSpeedChange: function () {
            this.anim.setSpeed(this.animationSpeed);
        },
    },
};
</script>
<style scoped>
.loading {
    height: 100%;
    background: rgba(255, 255, 255, 0.5);
}
</style>
