<template>
    <div>
        <h3 class="form-title">파일 설정</h3>
        <hr class="hr-black" />
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click.prevent="fileAdd">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
        <div>
            <input
                type="file"
                ref="uploadIpt"
                multiple
                @change="uploadIptChange"
                style="position: absolute; left: -9999px;"
            />
            <draggable
                ref="fileListUl"
                v-model="FileList"
                v-bind="dragOptions"
                @start="isDragging = true"
                @end="isDragging = false"
                class="file-setting-list"
                tag="ul"
            >
                <FileItem
                    v-for="file in FileList"
                    :listLength="FileList.length"
                    :file="file"
                    :key="file.fileOrder"
                    :pageFileSectionCodeName="pageFileSectionCodeName"
                    @fileSelect="fileSelect"
                    @fileDelete="fileDelete(file)"
                />
            </draggable>
        </div>
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click.prevent="fileAdd">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
    </div>
</template>
<script>
import draggable from 'vuedraggable';
import { fileUpLoad } from '@/api/file';
import { getContentsViewFile } from '@/api/contents';
export default {
    name: 'FileSettings',
    data() {
        return {
            uploadFile: [],

            test: {
                detailThumbnailFileName: '',
                detailThumbnailFilePhysicalName: '',
                detailThumbnailFileSize: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                filePhysicalName: '',
                fileSectionCode: 'GUIDE',
                thumbnailFileName: '',
                thumbnailFilePhysicalName: '',
                thumbnailFileSize: '',
                progress: 0,
                title: '',
                url: '',
            },
            FileList: [
                {
                    progress: 0,
                    detailThumbnailFileName: '',
                    detailThumbnailFilePhysicalName: '',
                    detailThumbnailFileSize: '',
                    fileContentType: '',
                    fileExtension: '',
                    fileKindCode: 'FILE',
                    fileName: '',
                    fileOrder: 0,
                    filePhysicalName: '',
                    fileSectionCode: 'GUIDE',
                    fileSize: 0,
                    thumbnailFileName: '',
                    thumbnailFilePhysicalName: '',
                    thumbnailFileSize: '',
                    title: '',
                    url: '',
                },
            ],
            defaultFileData: {
                progress: 0,
                detailThumbnailFileName: '',
                detailThumbnailFilePhysicalName: '',
                detailThumbnailFileSize: '',
                fileContentType: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                fileName: '',
                fileOrder: 0,
                filePhysicalName: '',
                fileSectionCode: 'GUIDE',
                fileSize: 0,
                thumbnailFileName: '',
                thumbnailFilePhysicalName: '',
                thumbnailFileSize: '',
                title: '',
                url: '',
            },
        };
    },
    props: ['pageFileSectionCodeName'],
    computed: {
        dragOptions() {
            return {
                animation: 100,
                group: 'description',
                disabled: false,
                ghostClass: 'ghost',
            };
        },
    },
    created() {},
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
        draggable,
    },
    methods: {
        emitFileList() {
            this.FileList.forEach((el, index) => {
                el.fileOrder = index;
            });
            this.$emit('FileListUpdate', this.FileList);
        },
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter((item) => {
                return this.FileList.every((el) => {
                    return (
                        item.name !== el.fileName &&
                        item.type !== el.fileExtension &&
                        item.size !== el.fileSize
                    );
                });
            });

            mergeArray.forEach((el) => {
                const idx = this.FileList.findIndex((el) => {
                    return el.fileKindCode === 'FILE' && !el.fileName;
                });

                if (idx !== -1) {
                    this.FileList[idx].fileContentType = el.type;
                    this.FileList[idx].fileName = el.name;
                    this.FileList[idx].fileSize = el.size;
                } else {
                    this.FileList.push({
                        fileContentType: el.type,
                        fileName: el.name,
                        fileOrder: this.FileList.length,
                        fileSize: el.size,
                        title: '',
                        url: '',
                        ...this.test,
                    });
                }
            });
            this.emitFileList();
            this.uploadFile = this.uploadFile.concat(mergeArray);
            //this.uploadFiles(mergeArray);
        },
        progressUpdate(percentCompleted, el) {
            this.FileList.forEach((item, index, array) => {
                if (
                    item.fileName === el.name &&
                    item.fileContentType === el.type &&
                    item.fileSize === el.size
                ) {
                    array[index].progress = percentCompleted;
                }
            });
        },
        async uploadFiles() {
            Promise.all(
                this.uploadFile.map(async (el) => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {
                            onDownloadProgress: (progressEvent) => {
                                let percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                console.log(percentCompleted);
                            },

                            onUploadProgress: (progressEvent) => {
                                let percentCompleted = Math.round(
                                    (progressEvent.loaded * 95) /
                                        progressEvent.total
                                );
                                this.progressUpdate(percentCompleted, el);
                                this.emitFileList();
                            },
                        };

                        const response = await fileUpLoad(formData, config);
                        this.progressUpdate(100, el);
                        if (response.data.existMsg) {
                            alert(response.data.msg);
                            return;
                        }
                        this.FileList.forEach((item, idx, array) => {
                            if (
                                item.fileName === el.name &&
                                item.fileContentType === el.type &&
                                item.fileSize === el.size
                            ) {
                                array[idx] = {
                                    fileKindCode: 'FILE',
                                    fileSectionCode: 'GUIDE',
                                    progress: 100,
                                    title: '',
                                    url: '',
                                    ...response.data.data,
                                };
                                this.emitFileList();
                            }
                        });
                        console.log(3);
                    } catch (error) {
                        console.error(error);
                    }
                })
            )
                .then((values) => {
                    console.log(values);
                    this.uploadFile = [];
                    this.$emit('submitForm');
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        fileAdd() {
            this.FileList.push({ ...this.defaultFileData });
            this.emitFileList();
        },
        fileDelete(file) {
            const idx = this.FileList.findIndex((el) => {
                return (
                    el.fileName === file.fileName &&
                    el.fileExtension === file.fileExtension &&
                    el.fileSize === file.fileSize
                );
            });
            this.FileList.splice(idx, 1);
            this.emitFileList();
        },
        fileSelect() {
            this.$refs.uploadIpt.value = null;
            this.$refs.uploadIpt.click();
        },

        async getFolderDetailFile() {
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id,
                    {
                        page: this.page,
                        size: this.itemLength,
                    }
                );

                if (response.content && response.content.length) {
                    this.FileList = response.content;
                }

                this.emitFileList();
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>
<style scoped>
.flip-list-move {
    transition: transform 0.5s;
}
.no-move {
    transition: transform 0s;
}
.ghost {
    opacity: 0.5;
    background: #fff;
}
</style>
