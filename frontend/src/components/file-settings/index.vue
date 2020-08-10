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
                v-model="fileList"
                v-bind="dragOptions"
                tag="ul"
                @start="isDragging = true"
                @end="isDragging = false"
            >
                <transition-group type="transition" name="flip-list">
                    <FileItem
                        v-for="(file, index) in fileList"
                        :file="file"
                        :key="index"
                        v-on:fileSelect="fileSelect"
                        v-on:fileDelete="fileDelete(index)"
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
export default {
    name: 'FileSettings',
    data() {
        return {
            fileList: [
                {
                    fileKindCode: 'FILE',
                    fileName: 'graphic_file_name.jpg',
                    filePhysicalName: '/cdn/file/path',
                    fileSectionCode: 'GUIDE',
                    fileSize: 600,
                    title: 'Attract window graphic 1',
                    url: 'www.nike.co.kr',
                },
            ],
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
    props: {
        beforeUpload: Function,
    },
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
        draggable,
    },
    methods: {
        uploadIptChange(ev) {
            const files = ev.target.files;
            if (!files) return;
            this.uploadFiles(files);
        },
        uploadFiles(files) {
            let postFiles = Array.prototype.slice.call(files);

            if (postFiles.length === 0) {
                return;
            }

            postFiles.forEach(rawFile => {
                this.upload(rawFile);
            });
        },
        upload(rawFile) {
            this.$refs.uploadIpt.value = null;
            this.post(rawFile);
            /*const before = this.beforeUpload(rawFile);
            if (before && before.then) {
                before.then(
                    (processedFile) => {
                        const fileType = Object.prototype.toString.call(processedFile);
                        console.log(fileType);

                        if (fileType === '[object File]' || fileType === '[object Blob]') {
                            if (fileType === '[object Blob]') {
                                processedFile = new File([processedFile], rawFile.name, {
                                    type: rawFile.type,
                                });
                            }
                            for (const p in rawFile) {
                                if (rawFile.hasOwnProperty(p)) {
                                    processedFile[p] = rawFile[p];
                                }
                            }
                            this.post(processedFile);
                        } else {
                            this.post(rawFile);
                        }
                    },
                    () => {
                        this.onRemove(null, rawFile);
                    }
                );
            } else if (before !== false) {
                this.post(rawFile);
            } else {
                this.onRemove(null, rawFile);
            }*/
        },
        fileAdd() {
            this.fileList.push({
                fileKindCode: 'FILE',
                fileName: '',
                filePhysicalName: '/cdn/file/path',
                fileSectionCode: 'ASSET',
                fileSize: 600,
                title: '',
                url: '',
            });
        },
        fileDelete(idx) {
            this.fileList.splice(idx, 1);
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
