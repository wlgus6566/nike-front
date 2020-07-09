<template>
    <el-upload-custom
        ref="upload"
        action="https://jsonplaceholder.typicode.com/posts/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :on-exceed="handleExceed"
        :file-list="fileList"
        :auto-upload="false"
    >
        <el-button slot="trigger" size="small" type="primary">select file</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload"
            >upload to server</el-button
        >
        <div slot="tip" class="el-upload__tip">jpg/png files with a size less than 500kb</div>
    </el-upload-custom>
</template>
<script>
import ElUploadCustom from '@/components/el-upload-custom/';
export default {
    name: 'FileUpload',
    components: { ElUploadCustom },
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
            ],
        };
    },
    methods: {
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning(
                `The limit is 3, you selected ${files.length} files this time, add up to ${
                    files.length + fileList.length
                } totally`
            );
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`Cancel the transfert of ${file.name} ?`);
        },
        submitUpload() {
            this.$refs.upload.submit();
        },
    },
};
</script>
