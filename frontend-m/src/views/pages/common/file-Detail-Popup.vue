<template>
    <el-dialog
        title="상세보기"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        :lock-scroll="false"
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
                            :src="filePopupFile.thumbnailFilePhysicalName"
                            :alt="filePopupFile.thumbnailFileName"
                            v-if="filePopupFile.thumbnailFilePhysicalName"
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
                <div class="inner" v-else>
                    <template v-if="filePopupFile.contentsSeq">
                        <div
                            class="thumbnail"
                            v-if="
                                videoCheck(filePopupFile.url).type === 'vimeo'
                            "
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
                            v-else-if="filePopupFile.fileKindCode === 'VR'"
                        >
                            <span class="etc">
                                <i class="icon-file"></i>
                                <span class="txt">
                                    해당파일은 미리보기를<br />
                                    제공하지 않습니다.
                                </span>
                            </span>
                        </div>
                        <div class="thumbnail" v-else>
                            <div class="video-filePopupFile">
                                <youtube
                                    :video-id="videoCheck(filePopupFile.url).id"
                                    :player-vars="{
                                        autoplay: 1,
                                    }"
                                ></youtube>
                            </div>
                        </div>
                    </template>
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
            let type;
            if (RegExp.$3.indexOf('youtu') > -1) {
                type = 'youtube';
            } else if (RegExp.$3.indexOf('vimeo') > -1) {
                type = 'vimeo';
            }
            return {
                type: type,
                id: RegExp.$6,
            };
            /*let ampersandPosition = video_id.indexOf('&');
            if (ampersandPosition != -1) {
                video_id = video_id.substring(0, ampersandPosition);
            }*/
            //return video_id;
        },
    },
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
