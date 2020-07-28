<template>
    <div>
        <span class="thumb-file file-upload">
            <input
                ref="input"
                id="test"
                type="file"
                name="image"
                accept="image/*"
                @change="setImage"
            />
            <span class="thumb">
                <img v-if="cropImg" :src="cropImg" alt="Cropped Image" />
            </span>
            <span class="txt" @click="inputReset">썸네일 이미지 재등록</span>
        </span>

        <el-dialog
            title="Tips"
            :visible.sync="dialogVisible"
            width="800px"
            :before-close="handleClose"
        >
            <div>
                <vue-cropper
                    ref="cropper"
                    :aspect-ratio="1 / 1"
                    :aspectRatio="1 / 1"
                    :viewMode="1"
                    :responsive="false"
                    :guides="false"
                    :center="false"
                    :src="imgSrc"
                    :minContainerWidth="700"
                    :minContainerHeight="700"
                />
            </div>
            <a href="#" role="button" @click.prevent="cropImage">
                Crop
            </a>
        </el-dialog>
    </div>
</template>

<script>
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';

export default {
    name: 'index',
    data() {
        return {
            dialogVisible: false,
            imgSrc: require('@/assets/images/@test1.jpg'),
            cropImg: null, //require('@/assets/images/@test1.jpg')
            data: null,
        };
    },
    components: { VueCropper },
    methods: {
        inputReset() {
            console.log(this.$refs.input);
            this.cropImg = null;
            this.$refs.input.value = null;
        },
        handleClose(done) {
            this.dialogVisible = false;
            /*this.$confirm('Are you sure to close this dialog?')
                .then((_) => {
                    done();
                })
                .catch((_) => {});*/
        },
        cropImage() {
            // get image data for post processing, e.g. upload or setting image src
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            this.dialogVisible = false;
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
        getCropBoxData() {
            this.data = JSON.stringify(this.$refs.cropper.getCropBoxData(), null, 4);
        },
        getData() {
            this.data = JSON.stringify(this.$refs.cropper.getData(), null, 4);
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
        setCropBoxData() {
            if (!this.data) return;
            this.$refs.cropper.setCropBoxData(JSON.parse(this.data));
        },
        setData() {
            if (!this.data) return;
            this.$refs.cropper.setData(JSON.parse(this.data));
        },
        setImage(e) {
            const file = e.target.files[0];
            if (file.type.indexOf('image/') === -1) {
                alert('Please select an image file');
                return;
            }

            if (typeof FileReader === 'function') {
                const reader = new FileReader();
                reader.onload = (event) => {
                    this.imgSrc = event.target.result;
                    // rebuild cropperjs with the updated source
                    this.$refs.cropper.replace(event.target.result);
                };
                reader.readAsDataURL(file);
            } else {
                alert('Sorry, FileReader API not supported');
            }
            this.dialogVisible = true;
        },
        showFileChooser() {
            this.$refs.input.click();
        },
        zoom(percent) {
            this.$refs.cropper.relativeZoom(percent);
        },
    },
};
</script>
<style scoped>
.preview-area {
    width: 307px;
}

.preview-area p {
    font-size: 1.25rem;
    margin: 0;
    margin-bottom: 1rem;
}

.preview-area p:last-of-type {
    margin-top: 1rem;
}

.preview {
    width: 100%;
    height: calc(372px * (9 / 16));
    overflow: hidden;
}
</style>
