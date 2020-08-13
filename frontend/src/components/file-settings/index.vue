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
                v-model="folderDetail.contentsFileList"
                v-bind="dragOptions"
                tag="ul"
                @start="isDragging = true"
                @end="isDragging = false"
            >
                <transition-group type="transition" name="flip-list">
                    <FileItem
                        v-for="(file, index) in folderDetail.contentsFileList"
                        :listLength="folderDetail.contentsFileList.length"
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
            inputFileValue: [],
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
    props: ['folderDetail'],
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
        draggable,
    },
    methods: {
        progress(file) {
            return file;
        },
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            /*let mergeArray = Array.from(files).filter((item) => {
                return this.inputFileValue.every((el) => {
                    return (
                        item.name !== el.name ||
                        item.type !== el.type ||
                        item.size !== el.size
                    );
                });
            });

            this.inputFileValue = this.inputFileValue.concat(mergeArray);*/

            let mergeArray = Array.from(files).filter((item) => {
                return this.folderDetail.contentsFileList.every((el) => {
                    return (
                        item.name !== el.fileName ||
                        item.type !== el.fileExtension ||
                        item.size !== el.fileSize
                    );
                });
            });

            mergeArray.forEach((el) => {
                this.folderDetail.contentsFileList.push({
                    fileContentType: el.type,
                    fileName: el.name,
                    fileSize: el.size,
                    fileKindCode: 'FILE',
                    fileSectionCode: 'GUIDE',
                    progress: 0,
                });
            });
            this.uploadFiles(mergeArray);
        },
        uploadFiles(arr) {
            arr.forEach(async (el) => {
                const formData = new FormData();
                formData.append('uploadFile', el);
                const config = {
                    onUploadProgress: (progressEvent) => {
                        const percentCompleted = Math.round(
                            (progressEvent.loaded * 100) / progressEvent.total
                        );
                        this.folderDetail.contentsFileList.forEach((item) => {
                            if (
                                item.fileName === el.name &&
                                item.fileContentType === el.type &&
                                item.fileSize === el.size
                            ) {
                                item.progress = percentCompleted;
                                console.log(percentCompleted);
                            }
                        });
                    },
                };
                const {
                    data: { data: response },
                } = await fileUpLoad(formData, config);

                console.log(response);
                /* this.folderDetail.contentsFileList.forEach((item) => {
                    if (
                        item.fileName === response.fileName &&
                        item.fileContentType === response.fileContentType &&
                        item.fileSize === response.fileSize
                    ) {
                        console.log(123123);
                        item = {
                            ...response,
                            fileKindCode: 'FILE',
                            fileOrder: 1,
                            fileSectionCode: 'GUIDE',
                            title: '',
                            url: '',
                        };
                        console.log(item);
                    }
                });*/
            });
        },
        fileAdd() {
            this.folderDetail.contentsFileList.push({
                fileKindCode: 'FILE',
                fileName: '',
                fileSectionCode: 'ASSET',
                fileSize: 600,
                title: '',
                url: '',
            });
        },
        fileDelete(file) {
            const idx = this.folderDetail.contentsFileList.findIndex((el) => {
                return (
                    el.fileName === file.fileName &&
                    el.fileExtension === file.fileExtension &&
                    el.fileSize === file.fileSize
                );
            });
            this.folderDetail.contentsFileList.splice(idx, 1);
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
