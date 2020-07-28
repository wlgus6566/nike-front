<template>
    <div class="upload-file-box">
        <el-upload-custom
            ref="upload"
            class="upload-file-list"
            action="https://jsonplaceholder.typicode.com//"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :on-exceed="handleExceed"
            :file-list="fileList"
            :auto-upload="false"
        >
        </el-upload-custom>
        <div class="btn-box">
            <button
                type="button"
                class="btn-form-gray"
                @click.prevent="selectFile"
            >
                찾기
            </button>
            <button type="button" class="btn-form" @click.prevent="deleteFile">
                삭제
            </button>
        </div>
    </div>
</template>
<script>
import ElUploadCustom from '@/components/el-upload-custom/index.vue';
import Button from '@/views/pages/pub/button';
export default {
    name: 'FileUpload',
    components: { Button, ElUploadCustom },
    data() {
        return {
            fileList: [
                {
                    name: 'food.jpeg',
                    url:
                        'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
                    check: false,
                },
                {
                    name: 'food2.jpeg',
                    url:
                        'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
                    check: true,
                },
                {
                    name: 'food3.jpeg',
                    url: require('@/assets/images/bg_login.jpg'),
                    check: false,
                },
            ],
        };
    },
    computed: {
        checkedList() {
            return this.fileList.filter((el) => {
                return !el.check;
            });
        },
    },
    methods: {
        handleRemove(file, fileList) {},
        handlePreview(file) {},
        handleExceed(files, fileList) {
            this.$message.warning(
                `The limit is 3, you selected ${
                    files.length
                } files this time, add up to ${
                    files.length + fileList.length
                } totally`
            );
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`Cancel the transfert of ${file.name} ?`);
        },
        selectFile() {
            this.$refs['upload'].$refs['upload-inner'].handleClick();
        },
        deleteFile() {
            this.fileList = this.checkedList;
        },
    },
};
</script>
<style>
/* upload-file-box */
.upload-file-box {
    display: flex;
    width: 100%;
}
.upload-file-list {
    flex-grow: 1;
    flex-shrink: 1;
    flex-basis: auto;
    height: 130px;
    padding: 10px 15px;
    border: 1px solid #ddd;
    overflow: auto;
}
.upload-file-list.actice {
    background: #f7f7f7;
}
.upload-file-list li label {
    display: flex;
    align-items: center;
    padding: 5px 0;
    font-size: 14px;
    color: #000;
}
.upload-file-list li.el-upload-list__item {
    margin-top: 0;
}
.upload-file-list li label .checkbox {
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 20px;
    margin-right: 10px;
}
.upload-file-list li label .txt {
    display: block;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.upload-file-list .el-upload {
    position: absolute;
}
.upload-file-box .btn-box {
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 54px;
    margin-left: 10px;
}
.upload-file-box .btn-box [class^='btn-'] {
    min-width: 54px;
    padding: 0;
    margin: 0;
}
.upload-file-box .btn-box [class^='btn-'] + [class^='btn-'] {
    margin-top: 5px;
}
</style>
