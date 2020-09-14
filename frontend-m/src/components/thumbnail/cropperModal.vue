<template>
    <el-dialog
        title="이미지 등록"
        class="modal full img-crop-modal"
        fullscreen
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <!-- <el-scrollbar wrap-class="modal-scroll" :native="false">
            &lt;!&ndash;            <div class="dialog-header">&ndash;&gt;
            &lt;!&ndash;                <h2 class="dialog-title">이미지등록</h2>&ndash;&gt;
            &lt;!&ndash;            </div>&ndash;&gt;

        </el-scrollbar>-->
        <div class="modal-contents">
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
        </div>

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
            const contents = document.querySelector('.el-dialog');
            contents.classList.add('full');
        },
        pageRemoveClass() {
            const contents = document.querySelector('.el-dialog');
            contents.classList.remove('full');
        },
    },
};
</script>
<style scoped></style>
