<template>
    <div>
        <form action="" @submit.prevent="submitReport">
            <ul class="upload-list">
                <li class="upload-item thumbnail-file">
                    <thumbnail
                        :size="1 / 1"
                        @cropImage="cropImage"
                        :imageBase64="reportDetailData.imageBase64"
                    >
                        <template slot="txt-up"> 썸네일 이미지 등록 </template>
                        <template slot="txt"> 썸네일 이미지 재등록 </template>
                    </thumbnail>
                    <div class="upload-file-box">
                        <div class="fine-file">
                            <input
                                type="file"
                                ref="fileInput"
                                accept="image/*"
                                multiple
                                @change="uploadIptChange"
                            />
                            <div class="txt" v-if="!uploadFileViewer">
                                파일등록
                            </div>
                            <div
                                class="number"
                                v-else
                                v-text="uploadFileSize + '/10'"
                            >
                                0/10
                            </div>
                        </div>
                        <ul class="upload-file-list">
                            <!-- 이미지 첨부한 타입 -->
                            <li
                                v-for="item in reportDetailData.reportFileSaveDTOList"
                                :key="item.fileOrder"
                                class="upload-file-item"
                            >
                                <span class="thumbnail">
                                    <img
                                        :src="item.thumbnailFilePhysicalName"
                                        alt="샘플이미지"
                                    />
                                </span>
                                <button
                                    type="button"
                                    class="file-delete"
                                    @click="removeFile(item.fileOrder)"
                                >
                                    삭제
                                </button>
                            </li>
                            <li class="upload-file-item" v-for="item in reportTempFileList"></li>
                        </ul>
                    </div>
                    <p class="desc">
                        * 이미지는 최대 10개까지 등록 가능합니다.
                    </p>
                </li>
                <li class="upload-item">
                    <div class="form-column">
                        <label class="label-title required">구분</label>
                    </div>
                    <div class="form-column">
                        <div class="radio-box">
                            <label
                                class="check-item"
                                v-for="(radio, index) in reportSectionCodeList"
                                :key="index"
                            >
                                <input
                                    type="radio"
                                    v-model="reportDetailData.reportSectionCode"
                                    :value="radio"
                                />
                                <i></i>
                                <span class="txt" v-text="radio">SP</span>
                            </label>
                        </div>
                    </div>
                </li>
                <li class="upload-item">
                    <div class="form-column">
                        <label class="label-title required">보고서 명</label>
                    </div>
                    <div class="form-column">
                        <div class="txtarea">
                            <textarea
                                v-model="reportDetailData.reportName"
                            ></textarea>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="btn-area">
                <!--<button type="button" class="btn-s-sm">취소</button>-->
                <button type="submit" class="btn-s-black-sm">저장</button>
            </div>
        </form>
    </div>
</template>
<script>
import thumbnail from '@/components/thumbnail/index';
import {getReportDetail, getReportFile, postReport, putReport,} from '@/api/report';
import {fileUpLoad} from '@/api/file';
import bus from '@/utils/bus';
import {getLoginUpdate} from '@/api/mypage';

export default {
    name: 'upload',
    data() {
        return {
            fileUploadingInterval: null,
            title: this.$route.meta.title,
            checkedFile: [],
            uploadFileList: [],
            uploadFileSize: 0,
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
            uploadFileViewer: false,
            reportTempFileList : [{},{},{},{},{},{},{},{},{},{}]
        };
    },
    components: {
        thumbnail,
    },
    created() {
        if (this.$route.params.id) {
            this.getReportDetailView();
        }
    },
    activated() {
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
        },
        fileOrderSet() {
            this.uploadFileList = this.uploadFileList.filter(a => {
                return this.reportDetailData.reportFileSaveDTOList.some(b => {
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
            if (this.checkedFile.some(el => el === seq)) {
                this.checkedFile = this.checkedFile.filter(el => el !== seq);
            } else {
                this.checkedFile.push(seq);
            }
        },
        removeFile(order) {
            this.reportDetailData.reportFileSaveDTOList = this.reportDetailData.reportFileSaveDTOList.filter(
                b => b.fileOrder !== order
            );
            this.uploadFileSize--;
            this.reportTempFileList.push([]);
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
                console.error(error);
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
                if (this.reportDetailData.reportFileSaveDTOList.length > 0) {
                    this.uploadFileViewer = true;
                    this.uploadFileSize = this.reportDetailData.reportFileSaveDTOList.length;
                }
              this.reportTempFileList.splice(0, this.reportDetailData.reportFileSaveDTOList.length);
                this.fileOrderSet();
            } catch (error) {
                console.error(error);
            }
        },

        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter(item => {
                return this.reportDetailData.reportFileSaveDTOList.every(el => {
                    return (
                        item.name !== el.fileName && item.size !== el.fileSize
                    );
                });
            });
            /*console.log('mergeArray > ' + mergeArray.length);*/
            if (mergeArray.length + this.uploadFileList.length > 10) {
                alert('10개 이상 등록 할 수 없습니다.');
                if (this.uploadFileList.length === 10) return;
                let maxNum = 10;
                if (this.uploadFileList.length > 0) {
                    maxNum = 10 - this.uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach(el => {
                let reader = new FileReader();
                reader.onloadend = e => {
                    //this.reportDetailData.reportFileSaveDTOList[this.reportDetailData.reportFileSaveDTOList.length-1].thumbnailFilePhysicalName = e.target.result;
                    this.reportDetailData.reportFileSaveDTOList.push({
                        fileOrder: this.reportDetailData.reportFileSaveDTOList
                            .length,
                        fileName: el.name,
                        fileSize: el.size,
                        fileContentType: el.type,
                        thumbnailFilePhysicalName: e.target.result,
                        progress: 0,
                    });

                    this.uploadFileSize++;
                };
                reader.readAsDataURL(el);
            });
            this.uploadFileList = this.uploadFileList.concat(mergeArray);
            this.reportTempFileList.splice(0, mergeArray.length);
            this.uploadFileViewer = true;
        },

        async uploadFiles() {
            await Promise.all(
                this.uploadFileList.map(async el => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {
                            onUploadProgress: progressEvent => {
                                const percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                this.reportDetailData.reportFileSaveDTOList.forEach(
                                    item => {
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
                        console.error(error);
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
                this.$store.commit('SET_RELOAD', true);
                this.reportDetailData = {
                    reportName: '',
                    reportSectionCode: 'SP',
                    imageBase64: null,
                    reportFileSaveDTOList: [],
                };
                this.uploadFileViewer = false;
                this.uploadFileSize = 0;
            } catch (error) {
                console.error(error);
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
};
</script>
<style scoped></style>
