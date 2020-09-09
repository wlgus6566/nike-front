<template>
    <vimeo-player
        ref="player"
        :video-id="videoID"
        @ready="onReady"
        :player-height="height"
    ></vimeo-player>
</template>
<script>
export default {
    data() {
        return {
            videoID: '191517674',
            height: 500,
            options: {},
            playerReady: false,
        };
    },
    methods: {
        onReady() {
            this.playerReady = true;
        },
        play() {
            this.$refs.player.play();
        },
        pause() {
            this.$refs.player.pause();
        },
    },
};
</script>
