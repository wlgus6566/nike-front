<template>
    <el-dialog
        title="이미지 등록"
        class="modal-wrap"
        width="100%"
        :visible="visible"
        :append-to-body="true"
    >
        <el-scrollbar wrap-class="modal-scroll" :native="false">
            <!--            <div class="dialog-header">-->
            <!--                <h2 class="dialog-title">이미지등록</h2>-->
            <!--            </div>-->
            <div class="crop-editor">
                <div class="preview-original">
                    <div class="img-cropper">
                        <VueCropper
                            ref="cropper"
                            :aspect-ratio="size"
                            :src="imgSrc"
                            preview=".preview"
                        />
                    </div>
                </div>
                <!--                <div class="preview-crop">-->
                <!--                    <h2 class="title">-->
                <!--                        썸네일 이미지-->
                <!--                    </h2>-->
                <!--                    <div class="frame">-->
                <!--                        <div class="preview" />-->
                <!--                    </div>-->
                <!--                </div>-->
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <button
                type="button"
                class="btn-s-black"
                role="button"
                @click.prevent="crop"
            >
                확인
            </button>
        </div>
        <!--        <button-->
        <!--            class="modal-close"-->
        <!--            type="button"-->
        <!--            @click.prevent="$emit('update:visible', false)"-->
        <!--        >-->
        <!--            Close-->
        <!--        </button>-->
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
    mounted() {
        this.pageAddClass();
    },
    beforeRouteLeave(to, from, next) {
        this.pageRemoveClass();
        next();
    },
    methods: {
        test(url) {
            this.$refs.cropper.replace(url);
        },
        crop() {
            this.$emit(
                'cropImage',
                this.$refs.cropper.getCroppedCanvas().toDataURL()
            );
        },
        pageAddClass() {
            console.log('클래스추가');
            const contents = document.querySelector('.el-dialog');
            contents.classList.add('full');
        },
        pageRemoveClass() {
            console.log('떠남');
            const contents = document.querySelector('.el-dialog');
            contents.classList.remove('full');
        },
    },
};
</script>
<style scoped></style>
