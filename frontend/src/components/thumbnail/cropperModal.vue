<template>
    <el-dialog
        title="이미지등록"
        class="modal-wrap"
        width="700px"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar wrap-class="modal-scroll" :native="false">
            <div class="crop-editor type2">
                <div class="preview-original">
                    <h2 class="title">
                        원본 이미지
                    </h2>
                    <div class="img-cropper">
                        <VueCropper
                            ref="cropper"
                            :aspect-ratio="size"
                            :src="imgSrc"
                            preview=".preview"
                        />
                    </div>
                </div>
                <div class="preview-crop">
                    <h2 class="title">
                        썸네일 이미지
                    </h2>
                    <div class="frame">
                        <div class="preview" />
                    </div>
                </div>
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <button
                type="button"
                class="btn-s"
                role="button"
                @click="$emit('update:visible', false)"
            >
                취소
            </button>
            <button
                type="button"
                class="btn-s-black"
                role="button"
                @click.prevent="crop"
            >
                확인
            </button>
        </div>

        <button
            class="modal-close"
            type="button"
            @click.prevent="$emit('update:visible', false)"
        >
            Close
        </button>
    </el-dialog>
</template>
<script>
import VueCropper from 'vue-cropperjs';

export default {
    name: 'cropperModal',
    props: ['visible', 'imgSrc', 'cropImg', 'imgName', 'size'],
    components: {
        VueCropper,
    },
    methods: {
        test(url) {
            this.$refs.cropper.replace(url);
        },
        crop() {
            console.log(this.$refs.cropper);
            this.$emit(
                'cropImage',
                this.$refs.cropper.getCroppedCanvas().toDataURL()
            );
        },
    },
};
</script>
<style scoped></style>
