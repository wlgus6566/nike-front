<template>
    <div>
        <label class="thumb-file" :class="{ 'file-upload': cropImg }">
            <input
                ref="input"
                type="file"
                name="image"
                accept="image/*"
                @change="inputChangeEvent"
            />
            <span class="thumb">
                <img v-if="cropImg" :src="cropImg" :alt="imgName" />
            </span>
            <span class="txt" v-if="cropImg">
                썸네일 이미지 재등록
            </span>
            <span class="txt" v-else>썸네일 이미지 등록</span>
        </label>

        <cropperModal
            :visible.sync="visible.cropperModal"
            :imgSrc="this.imgSrc"
            :cropImg="this.cropImg"
            :imgName="this.imgName"
            :size="this.size"
            @cropImage="cropImage"
        />
    </div>
</template>

<script>
import 'cropperjs/dist/cropper.css';
const Compress = require('compress.js');
import cropperModal from './cropperModal';

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
        };
    },
    mounted() {
        this.cropImg = this.imageBase64;
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
    props: ['imageBase64', 'imageFileName', 'size'],

    computed: {},
    methods: {
        cropImage(cropperUrl) {
            this.cropImg = cropperUrl;
            this.$emit('cropImage', this.cropImg, this.imgName);
            this.visible.cropperModal = false;
        },
        inputChangeEvent(e) {
            const file = e.target.files[0];
            if (file.type.indexOf('image/') === -1) {
                alert('Please select an image file');
                return;
            }
            const compress = new Compress();
            compress
                .compress([file], {
                    size: 4, // the max size in MB, defaults to 2MB
                    quality: 1, // the quality of the image, max is 1,
                    maxWidth: 1000, // the max width of the output image, defaults to 1920px
                    maxHeight: 1000, // the max height of the output image, defaults to 1920px
                    resize: true, // defaults to true, set false if you do not want to resize the image width and height
                })
                .then((data) => {
                    const url = `${data[0].prefix}${data[0].data}`;
                    this.imgSrc = url;
                    this.imgName = file.name;
                    this.popupOpen();
                })
                .catch((e) => {
                    console.log(e);
                });
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
