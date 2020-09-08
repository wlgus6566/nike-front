<template>
    <youtube :video-id="videoId" ref="youtube" @playing="playing"></youtube>
</template>
<script>
export default {
    data() {
        return {
            videoId: 'lG0Ys-2d4MA',
        };
    },
    methods: {
        playVideo() {
            this.player.playVideo();
        },
        playing() {
            console.log('\o/ we are watching!!!');
        },
    },
    computed: {
        player() {
            return this.$refs.youtube.player;
        },
    },
};
</script>
<style scoped></style>
