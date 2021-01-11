<template>
    <el-dialog
        title="상세보기"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('closeModal')"
    >
        <div class="modal-contents">
            <div class="folder-detail">
                <div
                    class="inner"
                    v-if="
                        filePopupFile.fileKindCode === 'VIDEO' ||
                            filePopupFile.fileContentType.split('/')[0] ===
                                'VIDEO'
                    "
                >
                    <div class="thumbnail">
                        <div class="video-filePopupFile">
                            <template v-if="filePopupFile.url">
                                <youtube
                                    :video-id="videoCheck(filePopupFile.url).id"
                                    :player-vars="{
                                        autoplay: 1,
                                    }"
                                ></youtube>
                            </template>
                            <template v-else
                                ><video controls>
                                    <source
                                        :src="filePopupFile.filePhysicalName"
                                        type="video/mp4"
                                    />
                                </video>
                            </template>
                        </div>
                    </div>
                </div>
                <div class="inner" v-else>
                    <span class="thumbnail">
                        <span
                            class="etc"
                            v-if="
                                filePopupFile.fileContentType.split('/')[0] !==
                                    'IMAGE'
                            "
                        >
                            <i class="icon-file"></i>
                            <span class="txt">
                                해당파일은 미리보기를<br />
                                제공하지 않습니다.
                            </span>
                        </span>
                        <img
                            :src="filePopupFile.filePhysicalName"
                            :alt="filePopupFile.fileName"
                            v-else
                        />
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
    methods: {
        videoCheck(url) {
            url.match(
                /(http:|https:|)\/\/(player.|www.)?(vimeo\.com|youtu(be\.com|\.be|be\.googleapis\.com))\/(video\/|embed\/|watch\?v=|v\/)?([A-Za-z0-9._%-]*)(\&\S+)?/
            );
            if (RegExp.$3.indexOf('youtu') > -1) {
                return {
                    type: 'youtube',
                    id: RegExp.$6,
                };
            } else if (RegExp.$3.indexOf('vimeo') > -1) {
                return {
                    type: 'vimeo',
                    id: RegExp.$6,
                };
            } else if (url.indexOf('brightcove') > -1) {
                return {
                    type: 'brightcove',
                    id: url,
                };
            } else {
                return {
                    type: 'mp4',
                    id: url,
                };
            }
        },
    },
};
</script>
<style scoped>
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
