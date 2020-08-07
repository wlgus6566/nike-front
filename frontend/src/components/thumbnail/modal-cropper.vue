<template> </template>
<script>
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
import bus from '@/utils/bus';

export default {
    name: 'modalCropper',
    props: ['imgSrc', 'size'],
    components: { VueCropper },
    mounted() {
        //console.log(this.$refs);
    },
    create() {
        bus.$on('cropperReplace', src => {
            this.$refs.cropper.replace(src);
            alert(src);
        });
    },
    methods: {
        cropImage() {
            this.$emit(
                'cropImgUpdate',
                this.$refs.cropper.getCroppedCanvas().toDataURL()
            );
        },
        flipX() {
            const dom = this.$refs.flipX;
            let scale = dom.getAttribute('data-scale');
            scale = scale ? -scale : -1;
            this.$refs.cropper.scaleX(scale);
            dom.setAttribute('data-scale', scale);
        },
        flipY() {
            const dom = this.$refs.flipY;
            let scale = dom.getAttribute('data-scale');
            scale = scale ? -scale : -1;
            this.$refs.cropper.scaleY(scale);
            dom.setAttribute('data-scale', scale);
        },
        move(offsetX, offsetY) {
            this.$refs.cropper.move(offsetX, offsetY);
        },
        reset() {
            this.$refs.cropper.reset();
        },
        rotate(deg) {
            this.$refs.cropper.rotate(deg);
        },
        /*setImage(e) {
            const file = e.target.files[0];
            if (file.type.indexOf('image/') === -1) {
                alert('Please select an image file');
                return;
            }

            if (typeof FileReader === 'function') {
                const reader = new FileReader();
                this.imgName = this.imgSrc.substring(
                    this.imgSrc.lastIndexOf('/') + 1
                );
                reader.onload = event => {
                    this.imgSrc = event.target.result;
                    // rebuild cropperjs with the updated source
                    this.$refs.cropper.replace(event.target.result);
                };
                reader.readAsDataURL(file);
            } else {
                alert('Sorry, FileReader API not supported');
            }
            this.dialogVisible = true;
        },*/
        showFileChooser() {
            this.$refs.input.click();
        },
        zoom(percent) {
            this.$refs.cropper.relativeZoom(percent);
        },
    },
};
</script>
<style scoped></style>
