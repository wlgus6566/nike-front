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
import { fileUpLoad } from '@/api/file';
export default {
    name: 'FileSettings',
    data() {
        return {
            fileList: null,
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
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.inputFileValue = files;
            console.log(files);
            files.forEach((el) => {
                this.fileList.push({
                    fileKindCode: 'FILE',
                    fileName: el.name,
                    fileSectionCode: 'GUIDE', //파일구분

                    fileExtension: el.type,
                    filePhysicalName: '',
                    fileSize: el.size,
                });
            });
            this.uploadFiles(files);
        },
        uploadFiles() {
            this.inputFileValue.forEach(async (el) => {
                const data = new FormData();
                data.append('uploadFile', el);
                const config = {
                    onUploadProgress: (progressEvent) => {
                        const percentCompleted = Math.round(
                            (progressEvent.loaded * 100) / progressEvent.total
                        );
                        el.test = percentCompleted;
                        //return percentCompleted;
                        console.log(el.test);
                    },
                };
                const response = await fileUpLoad(data, config);
                console.log(response);
                console.log(response.data);
            });
        },
        fileAdd() {
            this.fileList.push({
                fileKindCode: 'FILE',
                fileName: '',
                file fileSectionCode: 'ASSET',
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
            this.PhysicalName: '/cdn/file/path',
                $refs.uploadIpt.click();
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
