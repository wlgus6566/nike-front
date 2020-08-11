<template>
    <div>
        <label class="thumb-file" :class="{ 'file-upload': cropImg }">
            <input
                ref="input"
                type="file"
                name="image"
                accept="image/*"
                @change="setImage"
            />
            <span class="thumb">
                <img v-if="cropImg" :src="cropImg" :alt="imgName" />
            </span>
            <span class="txt" v-if="cropImg">
                썸네일 이미지 재등록
            </span>
            <span class="txt" v-else>썸네일 이미지 등록</span>
        </label>

        <modal
            classes="modal"
            name="modal-cropper"
            height="auto"
            width="800"
            :reset="true"
            :scrollable="true"
        >
            <div class="modal-container">
                <div class="modal-header">
                    이미지 등록
                </div>
                <div class="modal-content">
                    <!--<div style="height: 900px;"></div>-->
                    <el-scrollbar wrap-class="modal-scroll" :native="false">
                        <div class="img-cropper">
                            <VueCropper
                                ref="cropper"
                                :aspect-ratio="size"
                                :src="imgSrc"
                                preview=".preview"
                            />
                        </div>
                        <div class="preview" />
                    </el-scrollbar>
                </div>
                <div class="modal-footer">
                    <button
                        type="button"
                        class="btn-s"
                        role="button"
                        @click.prevent="cropImage"
                    >
                        취소
                    </button>
                    <button
                        type="button"
                        class="btn-s-black"
                        role="button"
                        @click.prevent="cropImage"
                    >
                        확인
                    </button>
                </div>
            </div>
            <button class="modal-close" @click="this.closeModal">Close</button>
        </modal>
    </div>
</template>

<script>
import 'cropperjs/dist/cropper.css';
import VueCropper from 'vue-cropperjs';

export default {
    name: 'index',
    data() {
        return {
            imgSrc: require('@/assets/images/@test1.jpg'),
            cropImg: null,
            imgName: null,
            data: null,
        };
    },
    components: { VueCropper },
    props: ['imageFilePhysicalName', 'imageFileName', 'size'],
    mounted() {
        this.cropImg = this.imageFilePhysicalName;
    },
    activated() {
        this.cropImg = this.imageFilePhysicalName;
    },
    watch: {
        imageFilePhysicalName() {
            this.cropImg = this.imageFilePhysicalName;
        },
        imageFileName() {
            this.imgName = this.imageFileName;
        },
    },
    computed: {},
    methods: {
        setImage(e) {
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
                reader.onload = (event) => {
                    this.imgSrc = event.target.result;
                    // rebuild cropperjs with the updated source
                    this.$refs.cropper.replace(event.target.result);
                };
                reader.readAsDataURL(file);

                // modal open
                this.$modal.show('modal-cropper', {
                    imageFilePhysicalName: this.imageFilePhysicalName,
                    imageFileName: this.imageFileName,
                    size: this.size,
                });
            } else {
                alert('Sorry, FileReader API not supported');
            }
        },
        cropImage() {
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            this.dialogVisible = false;
            this.$emit('cropImage', this.cropImg, this.imgName);
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
            this.data = JSON.stringify(
                this.$refs.cropper.getCropBoxData(),
                null,
                4
            );
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
