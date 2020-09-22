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
                style="position: absolute; opacity: 0;"
            />
            <draggable
                ref="fileListUl"
                v-model="FileList"
                v-bind="dragOptions"
                @end="emitFileList"
                class="file-setting-list"
                tag="ul"
            >
                <FileItem
                    v-for="file in FileList"
                    :listLength="FileList.length"
                    :file="file"
                    :key="file.fileOrder"
                    :errorFile="errorFile"
                    :pageFileSectionCodeName="pageFileSectionCodeName"
                    :menuCode="menuCode"
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
import FileItem from '@/components/file-settings/file-item.vue';
import { fileUpLoad } from '@/api/file';
import { getContentsViewFile } from '@/api/contents';
import bus from '@/utils/bus';
export default {
    name: 'FileSettings',
    data() {
        return {
            uploadFile: [],
            errorFile: [],

            test: {
                detailThumbnailFileName: '',
                detailThumbnailFilePhysicalName: '',
                detailThumbnailFileSize: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                filePhysicalName: '',
                fileSectionCode: null,
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
                    fileSectionCode: null,
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
                fileSectionCode: null,
                fileSize: 0,
                thumbnailFileName: '',
                thumbnailFilePhysicalName: '',
                thumbnailFileSize: '',
                title: '',
                url: '',
            },
        };
    },
    props: ['pageFileSectionCodeName', 'menuCode'],
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
    created() {
        this.FileList[0].fileSectionCode = this.pageFileSectionCodeName[0];
        this.defaultFileData.fileSectionCode = this.pageFileSectionCodeName[0];
        this.emitFileList();
    },
    activated() {
        this.fileReset();
    },
    watch: {
        pageFileSectionCodeName() {
            //console.log(this.pageFileSectionCodeName);
        },
    },
    components: {
        FileItem,
        draggable,
    },
    methods: {
        fileReset() {
            this.FileList = [
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
                    fileSectionCode: null,
                    fileSize: 0,
                    thumbnailFileName: '',
                    thumbnailFilePhysicalName: '',
                    thumbnailFileSize: '',
                    title: '',
                    url: '',
                },
            ];
        },
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
                        item.name !== el.fileName || item.size !== el.fileSize
                    );
                });
            });

            if (mergeArray.length !== files.length) {
                alert('이미 등록된 파일입니다.');
            }

            mergeArray.forEach((el) => {
                const idx = this.FileList.findIndex((el) => {
                    return el.fileKindCode === 'FILE' && !el.fileName;
                });

                if (idx !== -1) {
                    this.FileList[idx].fileContentType = el.type;
                    this.FileList[idx].fileName = el.name;
                    this.FileList[idx].fileSize = el.size;
                } else {
                    this.test.fileSectionCode = this.pageFileSectionCodeName[0];
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
            this.errorFile = [];
            Promise.all(
                this.uploadFile.map(async (el) => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {
                            /* onDownloadProgress: (progressEvent) => {
                                let percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                console.log(percentCompleted);
                            },*/

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
                                    fileSectionCode: item.fileSectionCode,
                                    progress: 100,
                                    title: '',
                                    url: '',
                                    ...response.data.data,
                                };
                                this.emitFileList();
                            }
                        });
                    } catch (error) {
                        this.FileList.forEach((item) => {
                            if (
                                item.fileName === el.name &&
                                item.fileSize === el.size
                            ) {
                                alert(
                                    `${item.fileName} 파일 업로드에 실패 하였습니다.`
                                );
                                item.progress = true;
                                this.errorFile.push(item);
                            }
                        });
                        this.emitFileList();
                    }
                })
            )
                .then((values) => {
                    if (!this.errorFile.length) {
                        this.uploadFile = [];
                        this.$emit('submitForm');
                    } else {
                        bus.$emit('pageLoading', false);
                    }
                })
                .catch((e) => {
                    this.uploadFile = [];
                    //this.$emit('submitForm');
                });
        },
        fileAdd() {
            this.FileList.push({ ...this.defaultFileData });
            this.emitFileList();
        },
        fileDelete(file) {
            const idx = this.FileList.findIndex((el) => {
                return el.fileOrder === file.fileOrder;
            });
            this.FileList.splice(idx, 1);
            if (!this.FileList.length) {
                this.FileList.push({ ...this.defaultFileData });
            }

            this.uploadFile = this.uploadFile.filter((a) => {
                return this.FileList.some((b) => {
                    return (
                        a.name === b.fileName &&
                        a.type === b.fileContentType &&
                        a.size === b.fileSize
                    );
                });
            });

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
                        sectionCode: 'ALL',
                        orderType: 'ORDER',
                        fileExtension: '',
                    }
                );
                console.log(123123123123);
                if (response.content && response.content.length) {
                    this.FileList = response.content;
                } else {
                    this.FileList = [this.defaultFileData];
                }
                this.emitFileList();
                this.$emit('getAuthList', this.$route.params.id);
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
