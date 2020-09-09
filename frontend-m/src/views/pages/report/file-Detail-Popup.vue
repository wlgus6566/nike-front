<template>
    <el-dialog
        title="상세보기"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        :lock-scroll="false"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-contents">
            <div class="folder-detail">
                <div class="inner">
                    <span class="thumbnail">
                        <img
                            :src="filePopupFile.detailThumbnailFilePhysicalName"
                            :alt="filePopupFile.detailThumbnailFileName"
                            v-if="filePopupFile.detailThumbnailFilePhysicalName"
                        />
                        <span class="etc" v-else>
                            <i class="icon-file"></i>
                            <span class="txt">
                                해당파일은 미리보기를<br />
                                제공하지 않습니다.
                            </span>
                        </span>
                    </span>
                </div>
                <span class="info-box">
                    <em class="title">
                        {{ filePopupFile.title || filePopupFile.fileName }}
                    </em>
                </span>
            </div>
        </div>
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {
            orderComment: '',
        };
    },
    props: ['visible', 'filePopupFile'],
    mounted() {},
    methods: {},
};
</script>
<style>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-filepopupfiles: center;
}

.modal-wrap .el-dialog {
    margin: 0 !important;
}

.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
</style>
