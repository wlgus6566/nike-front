<template>
    <div>
        <h2 class="page-title">
            {{ this.$route.meta.title }}
        </h2>
        <form action="" @submit.prevent="addProduct">
            <h3 class="form-title mt20">폴더 설정</h3>
            <hr class="hr-black" />
            <ul
                class="form-list-thumb"
                :class="{ 'min-height': this.detailData.imageBase64 !== null }"
            >
                <li class="form-row thumb-row">
                    <Thumbnail
                        @cropImage="cropImage"
                        :imageFilePhysicalName="
                            detailData.imageFilePhysicalName
                        "
                        :imageFileName="detailData.imageFileName"
                    ></Thumbnail>
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
                                <span></span>
                            </span>
                            <span>
                                {{ radio.title }}
                                {{ reportSection.value }}
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
                                v-model="detailData.reportName"
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
                                                    <span></span>
                                                </span>
                                                <span class="txt"
                                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg</span
                                                >
                                            </label>
                                        </li>
                                        <li>
                                            <label>
                                                <span class="checkbox">
                                                    <input type="checkbox" />
                                                    <span></span>
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
                                                    <span></span>
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
                                                    <span></span>
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
                                                    <span></span>
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
    import Thumbnail from '@/components/thumbnail/index';
    import {postReport} from '@/api/report';
    import store from '@/store';

    export default {
    name: 'upload',
    data() {
        return {
            detailData: {
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
        Thumbnail,
    },
    methods: {
        cropImage(imageBase64, imgName) {
            this.detailData.imageBase64 = imageBase64;
            this.detailData.imageFileName = imgName;
        },
        //상품 등록
        async addProduct() {
            const data = {
                imageBase64: this.detailData.imageBase64,
                reportFileSaveDTOList: [
                    {
                        detailThumbnailFileName:
                            'graphic_file_name_detail_thumbnail.jpg',
                        detailThumbnailFilePhysicalName:
                            'http://cdnUrl/file/contents/graphic_file_name_detail_thumbnail.jpg',
                        detailThumbnailFileSize: '700',
                        fileContentType: 'image/jpeg',
                        fileExtension: 'JPG',
                        fileName: 'file.jpg',
                        filePhysicalName: '/upload/report/path',
                        fileSize: '150',
                        reportFileSeq: 1,
                        thumbnailFileName: 'graphic_file_name_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            'http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg',
                        thumbnailFileSize: '100',
                    },
                ],
                reportName: this.detailData.reportName,
                reportSectionCode: this.reportSectionCode.value,
            };
            if (this.$route.params.id) {
                let addAlert = confirm('수정하시겠습니까');
                if (addAlert) {
                    try {
                        const { data: response } = await putProduct(
                            this.$route.params.id,
                            data
                        );
                        // await getExistMsg(response);
                        console.log(response);
                        await this.$router.push('/order/management');
                        await store.dispatch('basketList');
                    } catch (error) {
                        console.log(error);
                    }
                }
            } else {
                let addAlert = confirm('저장하시겠습니까');
                if (addAlert) {
                    try {
                        const { data: response } = await postReport(data);
                        // await getExistMsg(response);
                        //RESET
                        await this.$router.push('/report/management');
                    } catch (error) {
                        console.log(error);
                    }
                }
            }
        },
    },
};
</script>
<style scoped></style>
