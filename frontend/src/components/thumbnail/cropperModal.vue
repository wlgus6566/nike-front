<template>
    <el-dialog
        title="test"
        class="modal-wrap"
        width="800px"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-header">
            이미지 등록
        </div>
        <div class="modal-content">
            <el-scrollbar wrap-class="modal-scroll" :native="false">
                <div class="img-cropper">
                    <VueCropper
                        ref="cropper"
                        :aspect-ratio="size"
                        :src="imgSrc"
                        preview=".preview"
                    />
                </div>
                <div
                    class="preview"
                    style="height: 100px; width: 100px; overflow: hidden;"
                />
            </el-scrollbar>
        </div>

        <span slot="footer" class="dialog-footer">
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
        </span>

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
