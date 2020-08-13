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
            />
            <draggable
                ref="fileListUl"
                v-model="FileList"
                v-bind="dragOptions"
                tag="ul"
                @start="isDragging = true"
                @end="isDragging = false"
            >
                <transition-group type="transition" name="flip-list">
                    <FileItem
                        v-for="(file, index) in FileList"
                        :listLength="FileList.length"
                        :file="file"
                        :key="index"
                        @fileSelect="fileSelect"
                        @fileDelete="fileDelete(file)"
                    />
                </transition-group>
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
export default {
    name: 'FileSettings',
    data() {
        return {
            FileList: null,
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
        this.FileList = this.contentsFileList;
    },
    props: ['contentsFileList'],
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
        draggable,
    },
    methods: {
        progress(file) {
            return file;
        },
        emitFileList() {
            this.$emit('FileListUpdate', this.FileList);
        },
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter((item) => {
                return this.FileList.every((el) => {
                    return (
                        item.name !== el.fileName ||
                        item.type !== el.fileExtension ||
                        item.size !== el.fileSize
                    );
                });
            });

            mergeArray.forEach((el) => {
                const idx = this.FileList.findIndex((el) => {
                    return el.fileKindCode === 'FILE' && !el.fileName;
                });
                const obj = {
                    fileContentType: el.type,
                    fileName: el.name,
                    fileSize: el.size,
                    fileKindCode: 'FILE',
                    fileSectionCode: 'GUIDE',
                    progress: 0,
                };
                if (idx !== -1) {
                    this.FileList[idx].fileContentType = el.type;
                    this.FileList[idx].fileName = el.name;
                    this.FileList[idx].fileSize = el.size;
                } else {
                    this.FileList.push(obj);
                }
                this.emitFileList();
            });
            this.uploadFiles(mergeArray);
        },
        uploadFiles(arr) {
            arr.forEach(async (el) => {
                try {
                    const formData = new FormData();
                    formData.append('uploadFile', el);
                    const config = {
                        onUploadProgress: (progressEvent) => {
                            const percentCompleted = Math.round(
                                (progressEvent.loaded * 100) /
                                    progressEvent.total
                            );
                            this.FileList.forEach((item) => {
                                if (
                                    item.fileName === el.name &&
                                    item.fileContentType === el.type &&
                                    item.fileSize === el.size
                                ) {
                                    item.progress = percentCompleted;
                                    console.log(percentCompleted);
                                }
                            });
                            this.emitFileList();
                        },
                    };
                    const {
                        data: { data: response },
                    } = await fileUpLoad(formData, config);

                    console.log(response);
                    this.FileList.forEach((item, idx, array) => {
                        if (
                            item.fileName === el.name &&
                            item.fileContentType === el.type &&
                            item.fileSize === el.size
                        ) {
                            array[idx] = { ...response };
                            this.emitFileList();
                            console.log(item);
                        }
                    });
                } catch (e) {
                    console.log(e);
                }
            });
        },
        fileAdd() {
            this.FileList.push({
                fileKindCode: 'FILE',
                fileName: '',
                fileSectionCode: 'ASSET',
                fileSize: 600,
                title: '',
                url: '',
            });
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
            /*const fileListUl = this.$refs.fileListUl;
            fileListUl.insertAdjacentHTML(
                'afterend',
                `<div id="select-width">${this.cloneTxt}</div>`
            );*/
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
