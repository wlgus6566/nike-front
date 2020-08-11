<template> </template>
<script>
import VueCropper from 'vue-cropperjs';

export default {
    name: 'Cropper',
    components: { VueCropper },
    props: ['imageFilePhysicalName', 'imageFileName', 'size', 'file'],
    mounted() {
        this.setImage();
    },
    methods: {
        setImage() {
            const reader = new FileReader();
            this.imgName = this.imgSrc.substring(
                this.imgSrc.lastIndexOf('/') + 1
            );
            reader.onload = (event) => {
                this.imgSrc = event.target.result;
                // rebuild cropperjs with the updated source
                this.$refs.cropper.replace(event.target.result);
            };
            reader.readAsDataURL(this.file);
        },
    },
};
</script>
<style scoped></style>
