<template>
    <div>
        <h3 class="form-title">첨부파일</h3>
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
import FileItem from '@/components/news-file-upload/file-item.vue';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';
export default {
    name: 'FileSettings',
    data() {
        return {
            uploadFile: [],
            errorFile: [],

            test: {
                fileContentType: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                fileName: '',
                filePhysicalName: '',
                fileSize: 0,
                title: '',
                url: '',
            },
            FileList: [
                {
                    fileContentType: '',
                    fileExtension: '',
                    fileKindCode: 'FILE',
                    fileName: '',
                    filePhysicalName: '',
                    fileSize: 0,
                    title: '',
                    url: '',
                },
            ],
            defaultFileData: {
                fileContentType: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                fileName: '',
                filePhysicalName: '',
                fileSize: 0,
                title: '',
                url: '',
            },
        };
    },
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
        this.emitFileList();
    },
    activated() {
        this.fileReset();
    },
    watch: {},
    components: {
        FileItem,
        draggable,
    },
    methods: {
        fileReset() {
            this.FileList = [
                {
                    fileContentType: '',
                    fileExtension: '',
                    fileKindCode: 'FILE',
                    fileName: '',
                    filePhysicalName: '',
                    fileSize: 0,
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
            let mergeArray = Array.from(files).filter(item => {
                return this.FileList.every(el => {
                    return (
                        item.name !== el.fileName || item.size !== el.fileSize
                    );
                });
            });

            if (mergeArray.length + files.length > 5) {
                alert('첨부파일은 최대 5개까지 등록 가능합니다.');
                if (files.length === 5) return;
                let maxNum = 5;
                if (files.length > 0) {
                    maxNum = 5 - files.length;
                }
                mergeArray.splice(maxNum, 9999);
            }
            if (mergeArray.length !== files.length) {
                alert('이미 등록된 파일입니다.');
            }

            mergeArray.forEach(el => {
                const idx = this.FileList.findIndex(el => {
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
        async uploadFiles() {
            console.log(1);
            if (this.$route.meta.modify) {
                if (!confirm('수정하시겠습니까?')) {
                    return false;
                }
            } else {
                if (!confirm('저장하시겠습니까?')) {
                    return false;
                }
            }
            this.errorFile = [];
            Promise.all(
                this.uploadFile.map(async el => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {};
                        formData.append('menuCode', 'news');
                        const response = await fileUpLoad(formData, config);
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
                                    progress: 100,
                                    title: '',
                                    url: '',
                                    ...response.data.data,
                                };
                                this.emitFileList();
                            }
                        });
                    } catch (error) {
                        this.FileList.forEach(item => {
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
                .then(values => {
                    if (!this.errorFile.length) {
                        this.uploadFile = [];
                        if (this.$route.meta.modify) {
                            this.$emit('modifyForm');
                        } else {
                            this.$emit('submitForm'``);
                        }
                    } else {
                        bus.$emit('pageLoading', false);
                    }
                })
                .catch(e => {
                    this.uploadFile = [];
                    //this.$emit('submitForm');
                });
        },
        fileAdd() {
            if (this.FileList.length > 4) {
                alert('첨부파일은 최대 5개까지 등록 가능합니다.');
            } else {
                this.FileList.push({ ...this.defaultFileData });
                this.emitFileList();
            }
        },
        fileDelete(file) {
            const idx = this.FileList.findIndex(el => {
                return el.fileOrder === file.fileOrder;
            });
            this.FileList.splice(idx, 1);
            if (!this.FileList.length) {
                this.FileList.push({ ...this.defaultFileData });
            }

            this.uploadFile = this.uploadFile.filter(a => {
                return this.FileList.some(b => {
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
