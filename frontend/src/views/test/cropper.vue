<template>
    <div id="transition-components-demo" class="demo">
        <input type="radio" value="v-a" id="a" v-model="view" />
        <label for="a">A</label>
        <input type="radio" value="v-b" id="b" v-model="view" />
        <label for="b">B</label>
        <transition name="component-fade" mode="out-in">
            <component v-bind:is="view"></component>
        </transition>
        <span>선택: {{ view }}</span>
    </div>
</template>
<script>
import Cropper from 'cropperjs'

const image = document.getElementById('image')
const cropper = new Cropper(image, {
    aspectRatio: 16 / 9,
    crop(event) {
        console.log(event.detail.x)
        console.log(event.detail.y)
        console.log(event.detail.width)
        console.log(event.detail.height)
        console.log(event.detail.rotate)
        console.log(event.detail.scaleX)
        console.log(event.detail.scaleY)
    },
})
</script>
<style scoped>
.component-fade-enter-active,
.component-fade-leave-active {
    transition: opacity 0.3s ease;
}
.component-fade-enter,
	.component-fade-leave-to
		/* .component-fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
