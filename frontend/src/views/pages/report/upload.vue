<template>
    <div>
        <h2 class="page-title" v-html="title"></h2>
        <form action="" @submit.prevent="submitReport">
            <h3 class="form-title mt20">폴더 설정</h3>
            <hr class="hr-black" />
            <ul
                class="form-list-thumb"
                :class="{
                    'min-height': this.reportDetailData.imageBase64 !== null,
                }"
            >
                <li class="form-row thumb-row">
                    <thumbnail
                        :size="1 / 1"
                        @cropImage="cropImage"
                        :imageBase64="reportDetailData.imageBase64"
                    >
                        <template slot="txt-up">
                            썸네일 이미지 등록
                        </template>
                        <template slot="txt">
                            썸네일 이미지 재등록
                        </template>
                    </thumbnail>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">구분</span>
                    </div>
                    <div class="form-column">
                        <label
                            class="check-label"
                            v-for="(radio, index) in reportSectionCodeList"
                            :key="index"
                        >
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="reportDetailData.reportSectionCode"
                                    :value="radio"
                                />
                                <i></i>
                                <span class="txt">
                                    {{ radio }}
                                </span>
                            </span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">보고서 명</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea
                                rows="5"
                                v-model="reportDetailData.reportName"
                            ></textarea>
                        </span>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <h3 class="form-title">파일 설정</h3>
            <hr class="hr-black" />
            <div class="file-setting-wrap">
                <div class="file-setting">
                    <ul class="form-list">
                        <!-- todo 추가 스크립트 작업 필요  -->
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">파일 찾기</span>
                            </div>
                            <div class="form-column">
                                <div class="upload-file-box">
                                    <!--active-->
                                    <ul
                                        class="upload-file-list"
                                        :class="{
                                            'is-file':
                                                uploadFileList.length > 0,
                                        }"
                                    >
                                        <li
                                            v-for="item in reportDetailData.reportFileSaveDTOList"
                                            :key="item.fileOrder"
                                        >
                                            <label>
                                                <span class="checkbox">
                                                    <input
                                                        type="checkbox"
                                                        :value="item.fileOrder"
                                                        v-model="checkedFile"
                                                    />
                                                    <i></i>
                                                </span>
                                                <span class="txt">
                                                    {{ item.fileName }}
                                                </span>
                                            </label>
                                            <!--<span
                                                class="progress"
                                                :style="{
                                                    width: `${item.progress}%`,
                                                }"
                                            ></span>-->
                                        </li>
                                    </ul>
                                    <div class="btn-box">
                                        <div class="fine-file">
                                            <span class="btn-form-gray"
                                                ><span>찾기</span></span
                                            >
                                            <input
                                                type="file"
                                                ref="fileInput"
                                                multiple
                                                @change="uploadIptChange"
                                            />
                                        </div>
                                        <button
                                            type="button"
                                            class="btn-form"
                                            @click="removeFile"
                                        >
                                            <span>삭제</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="btn-area">
                <button type="submit" class="btn-s-black">
                    <span>저장</span>
                </button>
            </div>
        </form>
    </div>
</template>
<script>
import thumbnail from '@/components/thumbnail/index';
import {
    getReportDetail,
    postReport,
    putReport,
    getReportFile,
} from '@/api/report';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';
import { getLoginUpdate } from '@/api/mypage';

export default {
    name: 'upload',
    data() {
        return {
            fileUploadingInterval: null,
            title: this.$route.meta.title,
            checkedFile: [],
            uploadFileList: [],

            reportSeq: null,
            imageFilePhysicalName: null,
            readCount: null,
            nickname: null,
            updateDt: null,

            reportDetailData: {
                reportName: '',
                reportSectionCode: 'SP',
                imageBase64: null,
                reportFileSaveDTOList: [],
            },
            reportSectionCodeList: ['SP', 'SU', 'FA', 'HO'],
        };
    },
    components: {
        thumbnail,
    },
    created() {
        this.$store.state.saveFolder = false;
        if (this.$route.params.id) {
            this.getReportDetailView();
        }
    },
    activated() {
        this.$store.state.saveFolder = false;
        this.reportDetailData = {
            reportName: '',
            reportSectionCode: 'SP',
            imageBase64: null,
            reportFileSaveDTOList: [],
        };
        if (this.$route.params.id) {
            this.getReportDetailView();
        }
    },
    methods: {
        async loginUpdate() {
            const response = await getLoginUpdate();
            //console.log(response);
        },
        fileOrderSet() {
            this.uploadFileList = this.uploadFileList.filter((a) => {
                return this.reportDetailData.reportFileSaveDTOList.some((b) => {
                    return (
                        a.name === b.fileName &&
                        a.type === b.fileContentType &&
                        a.size === b.fileSize
                    );
                });
            });
            this.reportDetailData.reportFileSaveDTOList.forEach((el, index) => {
                el.fileOrder = index;
            });
        },
        cropImage(imageBase64) {
            this.reportDetailData.imageBase64 = imageBase64;
        },

        selectFile(seq) {
            if (this.checkedFile.some((el) => el === seq)) {
                this.checkedFile = this.checkedFile.filter((el) => el !== seq);
            } else {
                this.checkedFile.push(seq);
            }
        },
        removeFile() {
            this.checkedFile.forEach((a) => {
                this.reportDetailData.reportFileSaveDTOList = this.reportDetailData.reportFileSaveDTOList.filter(
                    (b) => b.fileOrder !== a
                );
            });
            this.checkedFile = [];
            this.fileOrderSet();
        },

        // 리포트 상세 데이터
        async getReportDetailView() {
            try {
                const {
                    data: { data: response },
                } = await getReportDetail(this.$route.params.id);

                this.reportSeq = response.reportSeq;
                this.readCount = response.readCount;
                this.nickname = response.nickname;
                this.updateDt = response.updateDt;

                this.reportDetailData.reportName = response.reportName;
                this.reportDetailData.reportSectionCode =
                    response.reportSectionCode;
                this.reportDetailData.imageBase64 =
                    response.imageFilePhysicalName;
                await this.getReportFileData();
            } catch (error) {
                console.log(error);
            }
        },
        // 리포트 상세 파일 데이터
        async getReportFileData() {
            try {
                const {
                    data: { data: response },
                } = await getReportFile(this.$route.params.id, {
                    page: 0,
                    size: 1000,
                });
                this.reportDetailData.reportFileSaveDTOList = response.content;
                this.fileOrderSet();
            } catch (error) {
                console.log(error);
            }
        },

        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter((item) => {
                return this.reportDetailData.reportFileSaveDTOList.every(
                    (el) => {
                        return (
                            item.name !== el.fileName &&
                            item.size !== el.fileSize
                        );
                    }
                );
            });
            if (mergeArray.length + this.uploadFileList.length > 10) {
                alert('10개 이상 등록 할 수 없습니다.');
                if (this.uploadFileList.length === 10) return;
                let maxNum = 10;
                if (this.uploadFileList.length > 0) {
                    maxNum = 10 - this.uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach((el) => {
                this.reportDetailData.reportFileSaveDTOList.push({
                    fileOrder: this.reportDetailData.reportFileSaveDTOList
                        .length,
                    fileName: el.name,
                    fileSize: el.size,
                    fileContentType: el.type,
                    progress: 0,
                });
            });
            this.uploadFileList = this.uploadFileList.concat(mergeArray);
        },

        async uploadFiles() {
            await Promise.all(
                this.uploadFileList.map(async (el) => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {
                            onUploadProgress: (progressEvent) => {
                                const percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                this.reportDetailData.reportFileSaveDTOList.forEach(
                                    (item) => {
                                        if (
                                            item.fileName === el.name &&
                                            item.fileContentType === el.type &&
                                            item.fileSize === el.size
                                        ) {
                                            item.progress = percentCompleted;
                                        }
                                    }
                                );
                            },
                        };
                        const response = await fileUpLoad(formData, config);
                        //console.log(response);
                        if (response.existMsg) {
                            alert(response.msg);
                        }
                        this.reportDetailData.reportFileSaveDTOList.forEach(
                            (item, idx, array) => {
                                if (
                                    item.fileName === el.name &&
                                    item.fileContentType === el.type &&
                                    item.fileSize === el.size
                                ) {
                                    array[idx] = {
                                        progress: 100,
                                        fileOrder: idx,
                                        ...response.data.data,
                                    };
                                }
                            }
                        );
                    } catch (error) {
                        console.log(error);
                    }
                })
            );

            await this.submitData();
            this.uploadFileList = [];
        },
        async submitData() {
            //const uploadFn = this.$route.params.id ? putReport : postReport;
            let responseData = '';
            try {
                if (this.$route.params.id) {
                    responseData = await putReport(
                        this.reportDetailData,
                        this.$route.params.id
                    );
                } else {
                    responseData = await postReport(this.reportDetailData);
                }
                if (responseData.data.code) {
                    alert(responseData.data.msg);
                }
                bus.$emit('pageLoading', false);
                clearInterval(this.fileUploadingInterval);
                this.$store.state.saveFolder = true;
                this.$store.commit('SET_RELOAD', true);
                this.reportDetailData = {
                    reportName: '',
                    reportSectionCode: 'SP',
                    imageBase64: null,
                    reportFileSaveDTOList: [],
                };
                if (this.$route.params.id) {
                    await this.$router.push(
                        `/report/detail/${this.$route.params.id}`
                    );
                }
                /*if (this.$route.params.id) {
                    await this.$router.push(
                        `/report/detail/${this.$route.params.id}`
                    );
                } else {
                    await this.$router.push(`/report/management`);
                }*/
                this.$store.state.saveFolder = false;
            } catch (error) {
                console.log(error);
                this.$store.state.saveFolder = false;
                bus.$emit('pageLoading', false);
                clearInterval(this.fileUploadingInterval);
            }
        },

        // 리포트 등록
        submitReport() {
            if (!this.reportDetailData.imageBase64) {
                alert('썸네일 이미지가 누락 되었습니다.');
                return;
            }
            if (!this.reportDetailData.reportName) {
                alert('보고서 명이 누락 되었습니다.');
                return;
            }
            const addAlert = this.$route.params.id
                ? confirm('수정하시겠습니까?')
                : confirm('저장하시겠습니까?');
            if (addAlert) {
                bus.$emit('pageLoading', true);
                clearInterval(this.fileUploadingInterval);
                this.fileUploadingInterval = setInterval(() => {
                    this.loginUpdate();
                }, 1000 * 60 * 10);
                this.uploadFiles();
            }
        },
    },
    beforeRouteLeave(to, from, next) {
        if (!this.$store.state.saveFolder) {
            const answer = window.confirm(
                '이 페이지에서 나가시겠습니까?\n작업중인 내역은 저장되지 않습니다.'
            );
            if (answer) {
                next();
            } else {
                next(false);
            }
        } else {
            next();
        }
    },
};
</script>
<style scoped>
.file-setting-wrap {
    padding: 20px 20px 0;
}
</style>
