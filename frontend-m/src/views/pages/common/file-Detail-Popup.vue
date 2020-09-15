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
                <div class="inner" v-if="filePopupFile.fileKindCode === 'FILE'">
                    <div
                        class="thumbnail"
                        v-if="
                            filePopupFile.fileContentType.indexOf('video') === 0
                        "
                    >
                        <div class="video-filePopupFile">
                            <video controls class="video">
                                <source
                                    :src="filePopupFile.filePhysicalName"
                                    type="video/mp4"
                                />
                            </video>
                        </div>
                    </div>

                    <span class="thumbnail" v-else>
                        <img
                            :src="filePopupFile.detailThumbnailFilePhysicalName"
                            :alt="filePopupFile.detailThumbnailFileName"
                            v-if="filePopupFile.detailThumbnailFilePhysicalName"
                        />
                        <span
                            class="etc"
                            :class="[
                                `${filePopupFile.fileExtension.toLowerCase()}`,
                            ]"
                            v-else
                        >
                            <i class="icon-file"></i>
                            <span class="txt">
                                해당파일은 미리보기를<br />
                                제공하지 않습니다.
                            </span>
                        </span>
                    </span>
                </div>
                <div
                    class="inner"
                    v-else-if="filePopupFile.fileKindCode === 'VR'"
                >
                    <div class="thumbnail">
                        <span class="etc">
                            <i class="icon-file"></i>
                            <span class="txt">
                                해당파일은 미리보기를<br />
                                제공하지 않습니다.
                            </span>
                        </span>
                    </div>
                </div>
                <div
                    class="inner"
                    v-else-if="filePopupFile.fileKindCode === 'VIDEO'"
                >
                    <div
                        class="thumbnail"
                        v-if="videoCheck(filePopupFile.url).type === 'vimeo'"
                    >
                        <vimeo-player
                            class="video-filePopupFile"
                            :video-id="videoCheck(filePopupFile.url).id"
                            :player-height="height"
                            :player-width="width"
                        ></vimeo-player>
                    </div>
                    <div
                        class="thumbnail"
                        v-if="videoCheck(filePopupFile.url).type === 'youtube'"
                    >
                        <div class="video-filePopupFile">
                            <youtube
                                :video-id="videoCheck(filePopupFile.url).id"
                                :player-vars="{
                                    autoplay: 1,
                                }"
                            ></youtube>
                        </div>
                    </div>

                    <div
                        class="thumbnail"
                        v-if="
                            videoCheck(filePopupFile.url).type === 'brightcove'
                        "
                    >
                        <div class="video-filePopupFile">
                            <iframe
                                :src="videoCheck(filePopupFile.url).id"
                                allowfullscreen
                                webkitallowfullscreen
                                mozallowfullscreen
                            ></iframe>
                        </div>
                    </div>
                    <div
                        class="thumbnail"
                        v-if="videoCheck(filePopupFile.url).type === 'mp4'"
                    >
                        <div class="video-filePopupFile">
                            <video controls>
                                <source
                                    :src="videoCheck(filePopupFile.url).id"
                                    type="video/mp4"
                                />
                            </video>
                        </div>
                    </div>
                </div>

                <span class="info-box">
                    <em class="title">{{
                        filePopupFile.title || filePopupFile.fileName
                    }}</em>
                    <span class="btn-area" v-if="filePopupFile.url">
                        <a :href="filePopupFile.url" class="btn-s-white-sm">
                            <i class="icon-link"></i><span>LINK</span>
                        </a>
                    </span>
                </span>
            </div>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name: 'fileDetailPopup',
    data() {
        return {
            orderComment: '',
            playState: false,
            height: 'auto',
            width: '600',
        };
    },
    props: ['visible', 'filePopupFile'],
    mounted() {
        // console.log(this.filePopupFile);
    },
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
