<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ title }}</span>
        </h2>
        <h3 class="form-title mt20">메인 비주얼 등록</h3>
        <hr class="hr-black" />
        <form action="" @submit.prevent="addBanner">
            <ul class="form-list">
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">TITLE</label>
                    </div>
                    <div class="form-column">
                        <input type="text" v-model="bannerData.title" />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">DESCRIPTION</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea v-model="bannerData.contents"></textarea>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">이미지 등록</label>
                    </div>
                    <div class="form-column">
                        <div class="main-visual-upload">
                            <span
                                class="thumb-file pc-upload"
                                :class="{
                                    'file-upload':
                                        bannerData.imageFilePhysicalName,
                                }"
                            >
                                <input
                                    type="file"
                                    ref="pcBanner"
                                    @change="imageChange($event, 'pc')"
                                />
                                <span class="thumb">
                                    <img
                                        :src="bannerData.pcImageUrl"
                                        :alt="bannerData.imageFileName"
                                        v-if="bannerData.pcImageUrl"
                                    />
                                    <img
                                        :src="bannerData.imageFilePhysicalName"
                                        :alt="bannerData.imageFileName"
                                        v-if="
                                            !bannerData.pcImageUrl &&
                                            bannerData.imageFilePhysicalName
                                        "
                                    />
                                </span>
                                <span class="txt">
                                    PC 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는 최소 1600*800,<br />
                                        최대 3200*1600입니다.
                                    </span>
                                </span>
                            </span>
                            <span
                                class="thumb-file mobile-upload"
                                :class="{
                                    'file-upload':
                                        bannerData.mobileImageFileName,
                                }"
                                ref="moBanner"
                            >
                                <input
                                    type="file"
                                    @change="imageChange($event, 'mo')"
                                />
                                <span class="thumb">
                                    <img
                                        :src="bannerData.mobileImageUrl"
                                        :alt="bannerData.mobileImageFileName"
                                        v-if="bannerData.mobileImageUrl"
                                    />
                                    <img
                                        :src="
                                            bannerData.mobileImageFilePhysicalName
                                        "
                                        :alt="bannerData.mobileImageFileName"
                                        v-if="
                                            !bannerData.mobileImageUrl &&
                                            bannerData.mobileImageFilePhysicalName
                                        "
                                    />
                                </span>
                                <span class="txt">
                                    Mobile 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는 최소 1200*1200,<br />
                                        최대 4000*4000입니다.
                                    </span>
                                </span>
                            </span>
                        </div>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required"
                            >연결 URL (PC)</label
                        >
                    </div>
                    <div class="form-column">
                        <div class="main-visual-upload">
                            <label
                                class="check-label"
                                v-for="(radio, index) in urlCheck.checkItem"
                                :key="index"
                            >
                                <span class="radio">
                                    <input
                                        type="radio"
                                        v-model="urlCheck.value"
                                        :name="urlCheck.name"
                                        :value="radio.value"
                                    />
                                    <i></i>
                                    <span class="txt">
                                        {{ radio.title }}
                                    </span>
                                </span>
                            </label>
                        </div>
                    </div>
                </li>
                <li class="form-row" v-if="urlCheck.value === 'N'">
                    <div class="form-column">
                        <label class="label-title required">URL</label>
                    </div>
                    <div class="form-column">
                        <input type="text" v-model="bannerData.linkUrl" />
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <button type="button" class="btn-s-white" @click="detailBanner">
                    <span>취소</span>
                </button>
                <button type="submit" class="btn-s-black">
                    <span>저장</span>
                </button>
            </div>
        </form>
    </div>
</template>
<script>
import { getBanner, postBanner, putBanner } from '@/api/banner';
import { fileUpLoad } from '@/api/file';
export default {
    name: 'visual',
    data() {
        return {
            title: this.$route.meta.title,
            bannerData: {
                contents: '',
                imageFileName: '',
                imageFilePhysicalName: '',
                linkUrl: '',
                linkUrlTypeCode: 'ASSET',
                mobileImageFileName: '',
                mobileImageFilePhysicalName: '',
                title: '',
            },
            pcFormFile: [],
            moFormFile: [],
            urlCheck: {
                checkItem: [
                    { value: 'ASSET', title: '기본(ASSET > ALL 메뉴로 연결)' },
                    { value: 'N', title: 'URL 입력' },
                ],
                name: 'url',
                value: 'ASSET',
            },
            detailData: false,
        };
    },
    components: {},
    watch: {
        'urlCheck.value'(val) {
            if (val !== 'ASSET') {
                this.bannerData.linkUrlTypeCode = 'URL';
            } else {
                this.bannerData.linkUrlTypeCode = val;
            }
        },
        bannerData() {
            this.detailData = true;
        },
        'bannerData.linkUrlTypeCode'(val) {
            if (val !== 'ASEET') {
                this.urlCheck.value = 'N';
            }
        },
        pcFormFile(val) {
            console.log(val);
        },
    },
    created() {
        this.detailBanner();
    },
    mounted() {},
    methods: {
        //이미지 페이지에 삽입
        imageChange(e, device) {
            this.uploadFiles(e.target.files[0], device);
            if (device === 'pc') {
                console.log(device);
                const imaName = e.target.files[0].name;
                const reader = new FileReader();
                reader.onloadend = () => {
                    this.bannerData.imageFilePhysicalName = reader.result;
                    this.bannerData.imageFileName = imaName;
                };
                if (e.target.files[0]) {
                    reader.readAsDataURL(e.target.files[0]);
                } else {
                    this.bannerData.imageFilePhysicalName = '';
                    this.bannerData.imageFileName = '';
                }
            } else {
                console.log(device);
                const imaName = e.target.files[0].name;
                const reader = new FileReader();
                reader.onloadend = () => {
                    this.bannerData.mobileImageFilePhysicalName = reader.result;
                    this.bannerData.mobileImageFileName = imaName;
                };
                if (e.target.files[0]) {
                    reader.readAsDataURL(e.target.files[0]);
                } else {
                    this.bannerData.mobileImageFilePhysicalName = '';
                    this.bannerData.mobileImageFileName = '';
                }
            }
        },

        //이미지 폼데이터로 변환
        async uploadFiles(file, device) {
            console.log(device);
            const formData = new FormData();
            formData.append('uploadFile', file, file.name);
            try {
                const {
                    data: { data: response },
                } = await fileUpLoad(formData);
                if (device === 'pc') {
                    this.pcFormFile = response;
                    console.log(this.pcFormFile);
                } else {
                    this.moFormFile = response;
                    console.log(this.moFormFile);
                }
            } catch (e) {
                console.log(e);
            }
        },

        //배너 등록
        async addBanner() {
            if (!this.detailData) {
                try {
                    const response = await postBanner({
                        contents: this.bannerData.contents,
                        imageFileName: this.pcFormFile.fileName,
                        imageFilePhysicalName: this.pcFormFile.filePhysicalName,
                        imageFileSize: this.pcFormFile.fileSize,
                        linkUrl: this.bannerData.linkUrl,
                        linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                        mobileImageFileName: this.moFormFile.fileName,
                        mobileImageFilePhysicalName: this.moFormFile
                            .filePhysicalName,
                        mobileImageFileSize: this.moFormFile.fileSize,
                        title: this.bannerData.title,
                    });
                    alert(response.data.msg);
                    if (response.data.success) {
                        await this.$router.push('/');
                    }
                } catch (error) {
                    console.log(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            } else {
                try {
                    let modifyData = {};
                    if (
                        this.moFormFile.length === 0 &&
                        this.pcFormFile.length === 0
                    ) {
                        console.log('pc , mo 이미지가 등록되지 않음');
                        modifyData = {
                            contents: this.bannerData.contents,
                            imageFileName: this.bannerData.imageFileName,
                            imageFilePhysicalName: this.bannerData
                                .imageFilePhysicalName,
                            imageFileSize: this.bannerData.imageFileSize,
                            linkUrl: this.bannerData.linkUrl,
                            linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                            mobileImageFileName: this.bannerData
                                .mobileImageFileName,
                            mobileImageFilePhysicalName: this.bannerData
                                .mobileImageFilePhysicalName,
                            mobileImageFileSize: this.bannerData
                                .mobileImageFileSize,
                            title: this.bannerData.title,
                        };
                    } else if (this.pcFormFile.length === 0) {
                        console.log('pc이미지가 등록되지 않음');
                        modifyData = {
                            contents: this.bannerData.contents,
                            imageFileName: this.bannerData.imageFileName,
                            imageFilePhysicalName: this.bannerData
                                .imageFilePhysicalName,
                            imageFileSize: this.bannerData.imageFileSize,
                            linkUrl: this.bannerData.linkUrl,
                            linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                            mobileImageFileName: this.moFormFile
                                .detailThumbnailFileName,
                            mobileImageFilePhysicalName: this.moFormFile
                                .detailThumbnailFilePhysicalName,
                            mobileImageFileSize: this.moFormFile.fileSize,
                            title: this.bannerData.title,
                        };
                    } else if (this.moFormFile.length === 0) {
                        console.log('mo이미지가 등록되지 않음');
                        modifyData = {
                            contents: this.bannerData.contents,
                            imageFileName: this.pcFormFile
                                .detailThumbnailFileName,
                            imageFilePhysicalName: this.pcFormFile
                                .detailThumbnailFilePhysicalName,
                            imageFileSize: this.pcFormFile
                                .detailThumbnailFileSize,
                            linkUrl: this.bannerData.linkUrl,
                            linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                            mobileImageFileName: this.bannerData
                                .mobileImageFileName,
                            mobileImageFilePhysicalName: this.bannerData
                                .mobileImageFilePhysicalName,
                            mobileImageFileSize: this.bannerData
                                .mobileImageFileSize,
                            title: this.bannerData.title,
                        };
                    } else {
                        console.log('이미지가 전부 변경됨');
                        modifyData = {
                            contents: this.bannerData.contents,
                            imageFileName: this.pcFormFile.fileName,
                            imageFilePhysicalName: this.pcFormFile
                                .filePhysicalName,
                            imageFileSize: this.pcFormFile.fileSize,
                            linkUrl: this.bannerData.linkUrl,
                            linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                            mobileImageFileName: this.moFormFile.fileName,
                            mobileImageFilePhysicalName: this.moFormFile
                                .filePhysicalName,
                            mobileImageFileSize: this.moFormFile.fileSize,
                            title: this.bannerData.title,
                        };
                    }
                    console.log(modifyData);
                    const response = await putBanner(
                        this.bannerData.bannerSeq,
                        {
                            modifyData,
                        }
                    );
                    alert(response.data.msg);
                    if (response.data.success) {
                        await this.$router.push('/');
                    }
                } catch (error) {
                    console.log(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            }
        },

        //배너 상세
        async detailBanner() {
            try {
                const {
                    data: { data: response },
                } = await getBanner();
                this.bannerData = response;
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
    },
};
</script>
<style scoped></style>
