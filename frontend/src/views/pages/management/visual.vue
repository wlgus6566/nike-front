<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ $route.meta.title }}</span>
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
                            <thumbnail
                                class="pc-upload"
                                :size="2 / 1"
                                @cropImage="pCropImage"
                                :imageBase64="bannerData.imageFilePhysicalName"
                                :imageFileName="bannerData.imageFileName"
                                :imgHeight="1600"
                                :imgWidth="800"
                            >
                                <template slot="txt-up">
                                    PC 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는 최소 1600*800,<br />
                                        최대 3200*1600입니다.
                                    </span>
                                </template>
                                <template slot="txt">
                                    PC 이미지 재등록
                                </template>
                            </thumbnail>
                            <thumbnail
                                class="mobile-upload"
                                :size="1 / 1"
                                @cropImage="mCropImage"
                                :imageBase64="
                                    bannerData.mobileImageFilePhysicalName
                                "
                                :imageFileName="bannerData.mobileImageFileName"
                                :imgHeight="1200"
                                :imgWidth="1200"
                            >
                                <span slot="txt-up">
                                    Mobile 이미지 등록
                                    <span class="sub">
                                        권장 사이즈는 최소 1200*1200,<br />
                                        최대 4000*4000입니다.
                                    </span>
                                </span>
                                <span slot="txt">
                                    Mobile 이미지 재등록
                                </span>
                            </thumbnail>
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
                                        {{ urlCheck.value }}
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
                <button type="button" class="btn-s-white">
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
import thumbnail from '@/components/thumbnail/index';
import { postBanner } from '@/api/banner';
export default {
    name: 'visual',
    data() {
        return {
            bannerData: {
                contents: '',
                imageFileName: '',
                imageFilePhysicalName: '',
                imageFileSize: '',
                linkUrl: '',
                linkUrlTypeCode: 'ASSET',
                mobileImageFileName: '',
                mobileImageFilePhysicalName: '',
                mobileImageFileSize: '',
                title: '',
            },
            urlCheck: {
                checkItem: [
                    { value: 'ASSET', title: '기본(ASSET > ALL 메뉴로 연결)' },
                    { value: 'N', title: 'URL 입력' },
                ],
                name: 'url',
                value: 'ASSET',
            },
        };
    },
    components: {
        thumbnail,
    },
    watch: {
        'urlCheck.value'(val) {
            if (val !== 'ASSET') {
                this.bannerData.linkUrlTypeCode = null;
            } else {
                this.bannerData.linkUrlTypeCode = val;
            }
        },
    },
    methods: {
        //이미지 받아오기
        pCropImage(imageBase64, imgName) {
            this.bannerData.imageFilePhysicalName = imageBase64;
            this.bannerData.imageFileName = imgName;
        },
        mCropImage(imageBase64, imgName) {
            this.bannerData.mobileImageFilePhysicalName = imageBase64;
            this.bannerData.mobileImageFileName = imgName;
        },
        async addBanner() {
            try {
                const response = await postBanner({
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
                });
                console.log(response);
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
