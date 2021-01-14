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
            <ul class="file-setting-list">
                <FileItem
                    v-for="file in FileList"
                    :listLength="FileList.length"
                    :file="file"
                    :key="file.fileOrder"
                    :errorFile="errorFile"
                    @fileSelect="fileSelect"
                    @fileDelete="fileDelete(file)"
                />
            </ul>
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
import { getContentsViewFile } from '@/api/contents';
import { getCustomerDetail } from '@/api/customer';
export default {
    name: 'FileSettings',
    data() {
        return {
            uploadFile: [],
            errorFile: [],

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
        getFolderDetailFile(file) {
            if (file && file.fileList.length) {
                this.FileList = file.fileList;
            } else {
                const obj = JSON.parse(JSON.stringify(this.defaultFileData));
                this.FileList = [obj];
            }
            this.emitFileList();
        },

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

            if (mergeArray.length !== files.length) {
                alert('이미 등록된 파일입니다.');
            }
            let maxLength = this.uploadFile.length;
            if (this.$route.meta.modify) {
                let fileLength = 0;
                this.FileList.forEach((aa, index) => {
                    if (aa.fileName || aa.title) {
                        fileLength = index + 1;
                    }
                });

                maxLength = this.uploadFile.length + Number(fileLength);
            }

            if (mergeArray.length + maxLength > 5) {
                alert('파일은 최대 5개까지 등록 가능합니다.');
                let maxNum = 5;
                if (maxLength === 5) return;
                if (mergeArray.length + maxLength > 5) {
                    maxNum = 5 - (mergeArray.length + maxLength);
                }
                mergeArray.splice(Number(maxNum), 9999);
            }

            mergeArray.forEach(el => {
                const idx = this.FileList.findIndex(item => {
                    return item.fileKindCode === 'FILE' && !item.fileName;
                });

                if (idx !== -1) {
                    this.FileList[idx].fileContentType = el.type;
                    this.FileList[idx].fileName = el.name;
                    this.FileList[idx].fileSize = el.size;
                } else {
                    this.FileList.push({
                        fileKindCode: 'FILE',
                        fileContentType: el.type,
                        fileName: el.name,
                        fileOrder: this.FileList.length,
                        fileSize: el.size,
                        title: '',
                        url: '',
                    });
                }
            });
            this.emitFileList();
            this.uploadFile = this.uploadFile.concat(mergeArray);
            //this.uploadFiles(mergeArray);
        },

        async uploadFiles() {
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
                        bus.$emit('pageLoading', true);
                        if (this.$route.meta.modify) {
                            this.$emit('modifyForm');
                        } else {
                            this.$emit('submitForm');
                        }
                    } else {
                        console.log(2);
                        bus.$emit('pageLoading', false);
                    }
                })
                .catch(e => {
                    this.uploadFile = [];
                    //this.$emit('submitForm');
                });
        },
        fileAdd() {
            if (this.FileList.length < 5) {
                this.FileList.push({ ...this.defaultFileData });
                this.emitFileList();
            } else {
                alert('첨부파일 5개 이상 등록 불가');
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
