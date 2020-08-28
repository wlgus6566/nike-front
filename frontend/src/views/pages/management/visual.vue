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
                                    'file-upload': bannerData.pcImageUrl,
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
                                </span>
                                <span class="txt" v-if="bannerData.pcImageUrl">
                                    PC 이미지 재등록
                                    <span class="sub">
                                        (권장 사이즈 : 최대 3200*1600)
                                    </span>
                                </span>
                                <span class="txt" v-else>
                                    PC 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는 최대 3200*1600입니다.
                                    </span>
                                </span>
                            </span>
                            <span
                                class="thumb-file mobile-upload"
                                :class="{
                                    'file-upload': bannerData.mobileImageUrl,
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
                                </span>
                                <span
                                    class="txt"
                                    v-if="bannerData.mobileImageUrl"
                                >
                                    Mobile 이미지 재등록
                                    <span class="sub">
                                        (권장 사이즈 : 최대 4000*4000)
                                    </span>
                                </span>
                                <span class="txt" v-else>
                                    Mobile 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는<br />
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
                        <input type="text" v-model="linkUrl" />
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
import { getBanner, putBanner, postBanner } from '@/api/banner';
import { fileUpLoad } from '@/api/file';

export default {
    name: 'visualManage',
    data() {
        return {
            title: this.$route.meta.title,
            bannerData: {
                contents: '',
                imageFileName: '',
                imageFilePhysicalName: '',
                linkUrl: '',
                linkUrlTypeCode: '',
                mobileImageFileName: '',
                mobileImageFilePhysicalName: '',
                title: '',
                pcImageUrl: '',
                mobileImageUrl: '',
            },
            linkUrl: '',
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
                if (this.bannerData.linkUrl === '') {
                    this.linkUrl = '';
                    this.bannerData.linkUrl = '';
                }
                if (this.linkUrl !== '') {
                    this.bannerData.linkUrl = this.linkUrl;
                }
            } else {
                this.bannerData.linkUrlTypeCode = val;
                this.bannerData.linkUrl = '/asset/all';
            }
        },
        linkUrl(val) {
            this.bannerData.linkUrl = val;
        },
        'bannerData.linkUrlTypeCode'(val) {
            if (val !== undefined) {
                if (val !== 'ASSET') {
                    this.urlCheck.value = 'N';
                    if (this.linkUrl === '') {
                        this.bannerData.linkUrl = this.linkUrl;
                    }
                }
            }
        },
    },
    created() {
        this.detailBanner();
    },
    activated() {
        this.detailBanner();
    },
    mounted() {},
    methods: {
        //이미지 페이지에 삽입
        imageChange(e, device) {
            this.uploadFiles(e.target.files[0], device);
            if (device === 'pc') {
                const imaName = e.target.files[0].name;
                const reader = new FileReader();
                reader.onloadend = () => {
                    this.bannerData.pcImageUrl = reader.result;
                    this.bannerData.imageFileName = imaName;
                };
                if (e.target.files[0]) {
                    reader.readAsDataURL(e.target.files[0]);
                } else {
                    this.bannerData.pcImageUrl = '';
                    this.bannerData.imageFileName = '';
                }
            } else {
                const imaName = e.target.files[0].name;
                const reader = new FileReader();
                reader.onloadend = () => {
                    this.bannerData.mobileImageUrl = reader.result;
                    this.bannerData.mobileImageFileName = imaName;
                };
                if (e.target.files[0]) {
                    reader.readAsDataURL(e.target.files[0]);
                } else {
                    this.bannerData.mobileImageUrl = '';
                    this.bannerData.mobileImageFileName = '';
                }
            }
        },

        //이미지 폼데이터로 변환
        async uploadFiles(file, device) {
            const formData = new FormData();
            formData.append('uploadFile', file, file.name);
            try {
                const {
                    data: { data: response },
                } = await fileUpLoad(formData);
                if (device === 'pc') {
                    this.bannerData.imageFilePhysicalName =
                        response.filePhysicalName;
                    this.bannerData.imageFileName = response.fileName;
                    this.bannerData.imageFileSize = response.fileSize;
                } else {
                    this.bannerData.mobileImageFilePhysicalName =
                        response.filePhysicalName;
                    this.bannerData.mobileImageFileName = response.fileName;
                    this.bannerData.mobileImageFileSize = response.fileSize;
                }
            } catch (error) {
                console.error(error);
            }
        },

        //배너 등록
        async addBanner() {
            try {
                const data = {
                    contents: this.bannerData.contents,
                    imageFileName: this.bannerData.imageFileName,
                    imageFilePhysicalName: this.bannerData
                        .imageFilePhysicalName,
                    imageFileSize: this.bannerData.imageFileSize,
                    linkUrl: this.bannerData.linkUrl,
                    linkUrlTypeCode: this.bannerData.linkUrlTypeCode,
                    mobileImageFileName: this.bannerData.mobileImageFileName,
                    mobileImageFilePhysicalName: this.bannerData
                        .mobileImageFilePhysicalName,
                    mobileImageFileSize: this.bannerData.mobileImageFileSize,
                    title: this.bannerData.title,
                };
                let response = '';
                if (!this.detailData) {
                    response = await postBanner(data);
                } else {
                    response = await putBanner(this.bannerData.bannerSeq, data);
                }
                alert(response.data.msg);
                if (response.data.success) {
                    await this.$router.push('/');
                }
            } catch (error) {
                console.error(error);
            }
        },

        //배너 상세
        async detailBanner() {
            try {
                const {
                    data: { data: response },
                } = await getBanner();
                this.bannerData = response;
                if (this.bannerData.title === undefined) {
                    this.detailData = false;
                    this.bannerData = {
                        contents: '',
                        imageFileName: '',
                        imageFilePhysicalName: '',
                        linkUrl: '/asset/all',
                        linkUrlTypeCode: 'ASSET',
                        mobileImageFileName: '',
                        mobileImageFilePhysicalName: '',
                        title: '',
                        pcImageUrl: '',
                        mobileImageUrl: '',
                    };
                } else if (this.bannerData.title) {
                    this.detailData = true;
                    this.linkUrl = response.linkUrl;
                    if (response.linkUrlTypeCode === 'ASSET') {
                        this.linkUrl = '';
                    }
                }
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>
<style scoped>
.thumb-file.file-upload .txt .sub {
    display: block;
    font-size: 10px;
    color: #333;
    line-height: 10px;
}
</style>
