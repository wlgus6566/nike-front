<template>
    <div>
        <span :class="{ 'thumb-file': true, 'file-upload': cropImg }">
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
        </span>

        <modal name="ModalCropper" width="800px" height="auto">
            <section class="modal">
                <div class="modal-header">
                    <h1>이얍</h1>
                </div>
                <el-scrollbar
                    class="modal-content"
                    wrap-class="modal-scroll"
                    :native="false"
                >
                    <vue-cropper
                        ref="cropper"
                        :aspect-ratio="size"
                        :viewMode="1"
                        :responsive="false"
                        :guides="false"
                        :center="false"
                        :src="imgSrc"
                        :minContainerWidth="300"
                        :minContainerHeight="200"
                        preview=".preview"
                    />
                    <div
                        class="preview"
                        style="height:100px; width:100px; overflow:hidden"
                    />
                    <a href="#" role="button" @click.prevent="cropImage">
                        Crop
                    </a>
                </el-scrollbar>
                <div class="modal-footer">
                    footer
                </div>
                <div slot="top-right">
                    <button
                        type="button "
                        class="modal-close"
                        @click.prevent="modalClose"
                    >
                        close modal
                    </button>
                </div>
            </section>
        </modal>
    </div>
</template>

<script>
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
const Compress = require('compress.js');

export default {
    name: 'index',
    data() {
        return {
            imgSrc: '',
            cropImg: '',
            imgName: null,
        };
    },
    props: ['imgSrc2', 'size'],
    components: { VueCropper },
    mounted() {
        console.log(this.$refs);
        this.imgSrc = this.imgSrc2;
        this.cropImg = this.imgSrc;
    },
    activated() {
        //this.imgDataReset();
    },
    watch: {
        imgSrc() {
            //this.cropImg = this.imgSrc;
        },
    },
    computed: {},
    methods: {
        modalClose() {
            this.$modal.hide('ModalCropper');
        },
        cropImage() {
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            this.modalClose();
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
                    size: 3, // the max size in MB, defaults to 2MB
                    quality: 1, // the quality of the image, max is 1,
                    maxWidth: 1000, // the max width of the output image, defaults to 1920px
                    maxHeight: 1000, // the max height of the output image, defaults to 1920px
                    resize: true, // defaults to true, set false if you do not want to resize the image width and height
                })
                .then(data => {
                    const url = `${data[0].prefix}${data[0].data}`;
                    this.modalOpen();
                    this.imgSrc = url;
                })
                .catch(e => {
                    console.log(e);
                });
        },
        modalOpen() {
            this.$modal.show('ModalCropper');
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
