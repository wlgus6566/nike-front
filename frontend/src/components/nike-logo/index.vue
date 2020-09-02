<template>
    <div class="nike-logo">
        <lottie
            class="lottie"
            :width="width"
            :height="height"
            :options="defaultOptions"
            @animCreated="handleAnimation"
        />
    </div>
</template>
<script>
import Lottie from '@/components/lottie';
import * as animationData from '@/assets/lottie/nike-logo.json';

export default {
    name: 'loading',
    components: {
        lottie: Lottie,
    },
    props: ['loadingStyle', 'height', 'width'],
    data() {
        return {
            defaultOptions: { animationData: animationData.default },
            animationSpeed: 1,
        };
    },
    methods: {
        handleAnimation: function (anim) {
            this.anim = anim;
            this.anim.setSpeed(0.5);
        },

        stop: function () {
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
.nike-logo {
    z-index: 4;
    position: absolute;
    top: 50%;
    left: 50%;
    width: 500px;
    height: 500px;
    transform: translate(-50%, -50%);
    animation: logoAni 4s ease-in-out 0s forwards;
}
@keyframes logoAni {
    to {
        opacity: 0;
    }
}
</style>
