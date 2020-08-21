<template>
    <div>
        <h2 class="page-title">REPORT UPLOAD</h2>
        <form action="" @submit.prevent="addReport">
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
                        :imageBase64="reportDetailData.imageFilePhysicalName"
                        :imageFileName="reportDetailData.imageFileName"
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
                            v-for="(radio, index) in reportSection.checkItem"
                            :key="index"
                        >
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="reportSection.value"
                                    :name="reportSection.name"
                                    :value="radio.value"
                                />
                                <i></i>
                                <span class="txt">
                                    {{ radio.title }}
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
                                v-model="reportDetailData.reportName"
                            ></textarea>
                        </span>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <h3 class="form-title">파일 설정</h3>
            <hr class="hr-black" />
            <div class="file-setting">
                <ul class="form-list">
                    <!-- todo 추가 스크립트 작업 필요  -->
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title">파일 찾기</span>
                        </div>
                        <div class="form-column">
                            <div class="upload-file-box">
                                <div class="upload-file-list actice">
                                    <ul>
                                        <li>
                                            <label>
                                                <span class="checkbox">
                                                    <input type="checkbox" />
                                                    <i></i>
                                                </span>
                                                <span class="txt">
                                                    P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg
                                                    sw_Nike_Gallery_graphic_1_700x1000.jpgsw_Nike_Gallery_graphic_1_700x1000.jpg
                                                    sw_Nike_Gallery_graphic_1_700x1000.jpgsw_Nike_Gallery_graphic_1_700x1000.jpg
                                                    sw_Nike_Gallery_graphic_1_700x1000.jpgsw_Nike_Gallery_graphic_1_700x1000.jpg
                                                </span>
                                            </label>
                                        </li>
                                        <li>
                                            <label>
                                                <span class="checkbox">
                                                    <input type="checkbox" />
                                                    <i></i>
                                                </span>
                                                <span class="txt"
                                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                                >
                                            </label>
                                        </li>
                                        <li>
                                            <label>
                                                <span class="checkbox">
                                                    <input type="checkbox" />
                                                    <i></i>
                                                </span>
                                                <span class="txt"
                                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                                >
                                            </label>
                                        </li>
                                        <li>
                                            <label>
                                                <span class="checkbox">
                                                    <input type="checkbox" />
                                                    <i></i>
                                                </span>
                                                <span class="txt"
                                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                                >
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                                <div class="btn-box">
                                    <div class="fine-file">
                                        <span class="btn-form-gray"
                                            ><span>찾기</span></span
                                        >
                                        <input type="file" />
                                    </div>
                                    <button type="button" class="btn-form">
                                        <span>삭제</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
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
import { getReportDetail, postReport, putReport } from '@/api/report';

export default {
    name: 'upload',
    data() {
        return {
            reportDetailData: {
                reportName: null,
                imageBase64: null,
                imageFileName: null,
                reportSectionCode: null,
            },
            reportSection: {
                checkItem: [
                    { value: 'SP', title: 'SP' },
                    { value: 'SU', title: 'SU' },
                    { value: 'FA', title: 'FA' },
                    { value: 'HO', title: 'HO' },
                ],
                name: 'reportSection',
                value: 'SP',
            },
        };
    },
    components: {
        thumbnail,
    },
    created() {
        this.reportDetailView();
    },
    methods: {
        cropImage(imageBase64, imgName) {
            this.reportDetailData.imageBase64 = imageBase64;
            this.reportDetailData.imageFileName = imgName;
        },
        // 리포트 상세 데이터
        async reportDetailView() {
            try {
                const {
                    data: { data: response },
                } = await getReportDetail(this.$route.params.id);
                this.reportDetailData = response;
                this.reportSection.value = this.reportDetailData.reportSectionCode;
                console.log(this.reportDetailData);
            } catch (error) {
                console.log(error);
            }
        },
        // 리포트 등록
        async addReport() {
            const data = {
                imageBase64: this.reportDetailData.imageBase64,
                reportFileSaveDTOList: [
                    {
                        fileName: 'K-062.png',
                        filePhysicalName: '/temp/20200814586000aMilVgOPwc.png',
                        fileSize: 613642,
                        fileContentType: 'image/png',
                        fileExtension: 'png',
                        thumbnailFileName: 'K-062_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814586000aMilVgOPwc_thumbnail.jpg',
                        thumbnailSize: 3379,
                        detailThumbnailFileName: 'K-062_detail.jpg',
                        detailThumbnailFileSize: 66211,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814586000aMilVgOPwc_detail.jpg',
                    },
                ],
                reportName: this.reportDetailData.reportName,
                reportSectionCode: this.reportSection.value,
            };
            if (Object.values(data).some((el) => el === '' || el === null)) {
                alert('필수 입력 값이 누락 되었습니다.');
                return;
            }
            if (this.$route.params.id) {
                let addAlert = confirm('수정하시겠습니까');
                console.log(data);
                if (addAlert) {
                    try {
                        const { data: response } = await putReport(
                            this.$route.params.id,
                            data
                        );
                        await this.$router.push('/report/management');
                        await this.$store.dispatch('getReportListBasket');
                        this.reportDetailData.imageBase64 = null;
                    } catch (error) {
                        console.log(error);
                    }
                }
            } else {
                let addAlert = confirm('저장하시겠습니까');
                if (addAlert) {
                    try {
                        const {
                            data: { data: response },
                        } = await postReport(data);
                        this.reportDataReset();
                        await this.$router.push('/report/management');
                    } catch (error) {
                        console.log(error);
                    }
                }
            }
        },
        reportDataReset() {
            this.reportDetailData = {
                reportName: null,
                imageBase64: null,
                imageFileName: null,
                reportSectionCode: null,
            };
        },
    },
};
</script>
<style scoped></style>
