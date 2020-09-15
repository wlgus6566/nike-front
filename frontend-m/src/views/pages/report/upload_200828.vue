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
                        <template slot="txt-up">
                            썸네일 이미지 등록
                        </template>
                        <template slot="txt">
                            썸네일 이미지 재등록
                        </template>
                    </thumbnail>
                </li>
                <li class="upload-item">
                    <div class="form-column">
                        <label class="label-title required">구분</label>
                        <ul class="upload-list">
                            <li class="upload-item thumbnail-file">
                                <label
                                    class="thumb-file"
                                    :class="{ 'file-upload': cropImg }"
                                >
                                    <input type="file" />
                                    <div class="thumb">
                                        <!-- <img src="http://placehold.it/320x320" alt="샘플" />-->
                                    </div>
                                    <p class="txt">
                                        썸네일 이미지 등록
                                    </p>
                                    <!-- 업로드시 나타나는 텍스트 -->
                                    <p class="txt-btm">
                                        <span>썸네일 이미지 재등록</span>
                                    </p>
                                </label>
                                <div class="upload-file-box">
                                    <div class="fine-file">
                                        <input type="file" />
                                        <!-- 파일 등록 전 -->
                                        <!-- <div class="txt">파일등록</div>-->
                                        <!-- 파일 등록 후 -->
                                        <div class="number">6/10</div>
                                    </div>
                                    <ul class="upload-file-list">
                                        <!-- 이미지 첨부한 타입 -->
                                        <li class="upload-file-item">
                                            <img
                                                src="http://placehold.it/130x130"
                                                alt="샘플이미지"
                                            />
                                            <button
                                                type="button"
                                                class="file-delete"
                                            >
                                                삭제
                                            </button>
                                        </li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                        <li class="upload-file-item"></li>
                                    </ul>
                                </div>
                                <p class="desc">
                                    * 이미지는 최대 10개까지 등록 가능합니다.
                                </p>
                            </li>
                            <li class="upload-item">
                                <div class="form-column">
                                    <label class="label-title required"
                                        >구분</label
                                    >
                                </div>
                                <div class="form-column">
                                    <div class="radio-box">
                                        <label class="check-item">
                                            <input type="radio" />
                                            <span class="txt">SP</span>
                                        </label>
                                        <label class="check-item">
                                            <input type="radio" />
                                            <span class="txt">SU</span>
                                        </label>
                                        <label class="check-item">
                                            <input type="radio" />
                                            <span class="txt">FA</span>
                                        </label>
                                        <label class="check-item auto">
                                            <input type="radio" />
                                            <span class="txt">HO</span>
                                        </label>
                                    </div>
                                    <div class="form-column">
                                        <div class="radio-box">
                                            <label
                                                class="check-item"
                                                v-for="(radio,
                                                index) in reportSectionCodeList"
                                                :key="index"
                                            >
                                                <input
                                                    type="radio"
                                                    v-model="
                                                        reportDetailData.reportSectionCode
                                                    "
                                                    :value="radio"
                                                />
                                                <span class="txt" v-text="radio"
                                                    >SP</span
                                                >
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="upload-item">
                                <div class="form-column">
                                    <label class="label-title required"
                                        >보고서 명</label
                                    >
                                </div>
                                <div class="form-column">
                                    <div class="txtarea">
                                        <textarea
                                            v-model="
                                                reportDetailData.reportName
                                            "
                                        ></textarea>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <div class="btn-area">
                            <!--<button type="button" class="btn-s-sm">취소</button>-->
                            <button type="submit" class="btn-s-black-sm">
                                저장
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </form>
    </div>
</template>
<script>
import thumbnail from '@/components/thumbnail/index';
import {
    getReportDetail,
    getReportFile,
    postReport,
    putReport,
} from '@/api/report';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';

export default {
    name: 'upload',
    data() {
        return {
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
        removeFile() {
            this.checkedFile.forEach(a => {
                this.reportDetailData.reportFileSaveDTOList = this.reportDetailData.reportFileSaveDTOList.filter(
                    b => b.fileOrder !== a
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

            if (mergeArray.length > 10) {
                alert('10개 이상 못해');
                mergeArray.splice(10, 9999);
            }

            mergeArray.forEach(el => {
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
            bus.$emit('pageLoading', true);
            //const uploadFn = this.$route.params.id ? putReport : postReport;
            try {
                if (this.$route.params.id) {
                    await putReport(
                        this.reportDetailData,
                        this.$route.params.id
                    );
                } else {
                    await postReport(this.reportDetailData);
                }
                bus.$emit('pageLoading', false);
                this.$store.commit('SET_RELOAD', true);
                if (this.$route.params.id) {
                    await this.$router.push(
                        `/report/detail/${this.$route.params.id}`
                    );
                } else {
                    await this.$router.push(`/report/management`);
                }
            } catch (error) {
                console.error(error);
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
                this.uploadFiles();
            }
        },
    },
};
</script>
<style scoped></style>
