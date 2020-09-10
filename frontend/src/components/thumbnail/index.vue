<template>
    <div>
        <label class="thumb-file" :class="{ 'file-upload': cropImg }">
            <input
                ref="input"
                type="file"
                name="image"
                accept=".png,.gif,.jpeg,.jpg,.bmp"
                @change="inputChangeEvent"
            />
            <span class="thumb">
                <img v-if="cropImg" :src="cropImg" :alt="imgName" />
            </span>
            <span class="txt" v-if="cropImg">
                <slot name="txt"></slot>
            </span>
            <span class="txt" v-else>
                <slot name="txt-up"></slot>
            </span>
        </label>

        <cropperModal
            ref="cModal"
            :visible.sync="visible.cropperModal"
            :imgSrc="this.imgSrc"
            :cropImg="this.cropImg"
            :imgName="this.imgName"
            :size="this.size"
            @cropImage="cropImage"
            @popupClose="popupClose"
        />
    </div>
</template>

<script>
import 'cropperjs/dist/cropper.css';
import Compress from 'compress.js';
import cropperModal from './cropperModal';
import bus from '@/utils/bus';

export default {
    name: 'thumbnailCropper',
    data() {
        return {
            visible: {
                cropperModal: false,
            },
            imgSrc: null,
            cropImg: null,
            imgName: null,
            width: 1000,
            height: 1000,
        };
    },
    mounted() {
        this.cropImg = this.imageBase64;
        this.height = this.imgHeight;
        this.width = this.imgWidth;
    },
    activated() {
        this.cropImg = this.imageBase64;
    },
    watch: {
        imageBase64() {
            this.cropImg = this.imageBase64;
        },
        imageFileName() {
            this.imgName = this.imageFileName;
        },
    },
    components: { cropperModal },
    props: ['imageBase64', 'imageFileName', 'size', 'imgHeight', 'imgWidth'],

    computed: {},
    methods: {
        inputReset() {
            this.$refs.input.value = '';
            this.$refs.input.type = 'radio';
            this.$refs.input.type = 'file';
        },
        cropImage(cropperUrl) {
            this.imgName = null;
            this.imgSrc = null;
            this.cropImg = cropperUrl;
            this.$emit('cropImage', this.cropImg, this.imgName);
            this.popupClose();
        },
        inputChangeEvent(e) {
            const file = e.target.files[0];
            if (file.length === 0) return;
            bus.$emit('pageLoading', true);

            if (file.type.indexOf('image/') === -1) {
                alert('jpg png bmp jpeg 파일만 등록 가능 합니다.');
                return;
            }

            new Compress()
                .compress([file], {
                    size: 4, // the max size in MB, defaults to 2MB
                    quality: 1, // the quality of the image, max is 1,
                    maxWidth: this.width, // the max width of the output image, defaults to 1920px
                    maxHeight: this.height, // the max height of the output image, defaults to 1920px
                    resize: true, // defaults to true, set false if you do not want to resize the image width and height
                })
                .then((data) => {
                    bus.$emit('pageLoading', false);
                    this.popupOpen();
                    let url = `${data[0].prefix}${data[0].data}`;
                    this.imgSrc = url;
                    this.imgName = file.name;
                    this.$refs.cModal.$refs.cropper.replace(url);
                })
                .catch((e) => {
                    bus.$emit('pageLoading', false);
                    console.log(e);
                });
        },
        popupClose() {
            this.inputReset();
            this.visible.cropperModal = false;
        },
        popupOpen() {
            this.visible.cropperModal = true;
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
