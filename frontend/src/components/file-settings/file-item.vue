<template>
    <div class="file-setting">
        <ul class="form-list">
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">파일 구분</span>
                </div>
                <div class="form-column">
                    <el-radio v-model="file.fileSectionCode" label="ASSET">
                        ASSET
                    </el-radio>
                    <el-radio v-model="file.fileSectionCode" label="GUIDE">
                        GUIDE
                    </el-radio>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">파일 종류</span>
                </div>
                <div class="form-column">
                    <el-radio v-model="file.fileKindCode" label="FILE">
                        파일
                    </el-radio>
                    <el-radio v-model="file.fileKindCode" label="VIDEO">
                        동영상(URL)
                    </el-radio>
                    <el-radio v-model="file.fileKindCode" label="VR">
                        VR
                    </el-radio>
                </div>
            </li>
            <!-- todo 추가 스크립트 작업 필요  -->
            <li class="form-row" v-if="file.fileKindCode === 'FILE'">
                <div class="form-column">
                    <span class="label-title">업로드 된 파일</span>
                </div>
                <div class="form-column">
                    <button type="button" class="btn-form-gray" v-on:click="$emit('fileSelect')">
                        찾기
                    </button>
                    <!--<UploadFile />-->
                </div>
            </li>
            <template v-else>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">파일 타이틀</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="file.title" />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">URL</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="file.url" />
                    </div>
                </li>
            </template>
        </ul>
        <button class="btn-del" v-on:click.prevent="$emit('fileDelete')"><span>삭제</span></button>
    </div>
</template>
<script>
import UploadFile from './upload-file';
export default {
    name: 'file-item',
    data() {
        return {
            fileKindCode: 'VIDEO',
            fileName: 'graphic_file_name.jpg',
            filePhysicalName: '/cdn/file/path',
            fileSectionCode: 'GUIDE',
            fileSize: 600,
            title: 'Attract window graphic 1',
            url: 'www.nike.co.kr',
        };
    },
    props: {
        file: Object,
    },
    components: { UploadFile },
};
</script>
<style scoped></style>
