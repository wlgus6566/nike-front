<template>
    <el-dialog
        title=""
        class="modal-wrap"
        width="700px"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('popupClose')"
    >
        <el-scrollbar wrap-class="modal-scroll" :native="false">
            <div class="dialog-header">
                <h2 class="dialog-title">이미지등록</h2>
            </div>
            <div class="crop-editor">
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
                @click="$emit('popupClose')"
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
        crop() {
            this.$emit(
                'cropImage',
                this.$refs.cropper.getCroppedCanvas().toDataURL()
            );
        },
    },
};
</script>
<style scoped></style>
