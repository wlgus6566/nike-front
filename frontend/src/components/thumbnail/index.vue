<template>
    <div>
        <button type="button" @click="popupOpen">열기</button>
        <span class="thumb-file" :class="{ 'file-upload': cropImg }">
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
    </div>
</template>

<script>
import ModalCropper from './modal-cropper';
import bus from '@/utils/bus';

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

    mounted() {
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
        cropImgUpdate(img) {
            this.cropImg = img;

            this.$modal.close('test');
            console.log(this.cropImg);
        },
        inputChangeEvent(e) {
            const file = e.target.files[0];
            if (file.type.indexOf('image/') === -1) {
                alert('Please select an image file');
                return;
            }

            if (typeof FileReader === 'function') {
                const reader = new FileReader();
                reader.onload = event => {
                    this.imgSrc = event.target.result;
                    bus.$emit('cropperReplace', event.target.result);
                    this.popupOpen();
                };
                reader.readAsDataURL(file);
            } else {
                alert('Sorry, FileReader API not supported');
            }
        },
        popupOpen() {
            this.$modal.show(
                ModalCropper,
                {
                    name: 'test',
                    modal: this.$modal,
                    imgSrc: this.imgSrc,
                    size: this.size,
                },
                {
                    width: '800px',
                }
            );
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
