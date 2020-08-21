<template>
    <div>
        <h2 class="page-title">{{ this.title }}</h2>
        <form action="" @submit.prevent="uploadFiles">
            <h3 class="form-title mt20">{{ pageOptionName }} 설정</h3>
            <hr class="hr-black" />
            <ul class="form-list-thumb" v-if="folderDetail">
                <li class="form-row thumb-row">
                    <thumbnail
                        :size="1 / 1"
                        @cropImage="cropImage"
                        :imageBase64="folderDetail.imageFilePhysicalName"
                    />
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required"
                            >{{ pageOptionName }} 상태</span
                        >
                    </div>
                    <div class="form-column">
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="folderDetail.exposureYn"
                                    value="Y"
                                />
                                <i></i>
                                <span class="txt">노출</span>
                            </span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="folderDetail.exposureYn"
                                    value="N"
                                />
                                <i></i>
                                <span class="txt">미노출</span>
                            </span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">업로드 위치</span>
                    </div>
                    <div class="form-column">
                        <label
                            class="check-label"
                            v-for="item in pageMenuCode"
                            :key="item"
                        >
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="menuCode"
                                    :value="item"
                                />
                                <i></i>
                                <span class="txt">{{ item }}</span>
                            </span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required"
                            >{{ pageOptionName }} 명</label
                        >
                    </div>
                    <div class="form-column">
                        <el-input v-model="folderDetail.folderName"></el-input>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required"
                            >{{ pageOptionName }} 상세</label
                        >
                    </div>
                    <div class="form-column">
                        <el-input
                            type="textarea"
                            :rows="2"
                            v-model="folderDetail.folderContents"
                        >
                        </el-input>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title">기간</span>
                    </div>
                    <div class="form-column">
                        <div>
                            <label class="check-label">
                                <span class="radio">
                                    <input
                                        type="radio"
                                        v-model="
                                            folderDetail.campaignPeriodSectionCode
                                        "
                                        value="SELECT"
                                    />
                                    <i></i>
                                    <span class="txt">날짜선택</span>
                                </span>
                            </label>
                            <label class="check-label">
                                <span class="radio">
                                    <input
                                        type="radio"
                                        v-model="
                                            folderDetail.campaignPeriodSectionCode
                                        "
                                        value="EVERY"
                                    />
                                    <i></i>
                                    <span class="txt">365</span>
                                </span>
                            </label>
                            <div
                                class="date-picker-group"
                                v-if="
                                    folderDetail.campaignPeriodSectionCode ===
                                    'SELECT'
                                "
                            >
                                <div class="date-picker">
                                    <v-date-picker
                                        v-model="folderDetail.campaignBeginDt"
                                        locale="en-us"
                                        color="orange"
                                        :input-props="{
                                            placeholder: 'YYYY.MM.DD',
                                        }"
                                        :attributes="attrs"
                                        :min-date="minDate('from')"
                                        :max-date="maxDate('from')"
                                    />
                                </div>
                                <div class="date-picker">
                                    <v-date-picker
                                        v-model="folderDetail.campaignEndDt"
                                        locale="en-us"
                                        color="orange"
                                        :input-props="{
                                            placeholder: 'YYYY.MM.DD',
                                        }"
                                        :attributes="attrs"
                                        :min-date="minDate('to')"
                                        :max-date="maxDate('to')"
                                    />
                                </div>
                            </div>
                        </div>
                        <!-- todo 추가 스크립트 작업 필요  -->
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">메모</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea
                                cols="100"
                                rows="2"
                                style="height: 80px;"
                                v-model="folderDetail.memo"
                            ></textarea>
                            <span class="count">
                                <strong>100</strong> / <em>150</em>byte
                            </span>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">권한 설정</label>
                    </div>
                    <div class="form-column">
                        <button
                            type="button"
                            class="btn-form-gray min100"
                            @click="ModalAuthOpen"
                        >
                            <span>권한설정</span>
                        </button>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <FileSettings
                ref="fileSet"
                :pageFileSectionCodeName="pageFileSectionCodeName"
                @FileListUpdate="FileListUpdate"
                @submitForm="submitForm"
            />
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
import FileSettings from '@/components/file-settings/index.vue';
import ModalAuth from '@/views/pages/common/modal-auth';
import {
    getContentsView,
    getContentsViewFile,
    postContents,
    putContents,
} from '@/api/contents';
export default {
    name: 'UPLOAD',
    data() {
        return {
            folderSet: {
                asset: {
                    folderOptionName: '캠페인',
                    fileSectionCodeName: ['ASSET', 'GUIDE'],
                    menuCode: ['SP', 'SU', 'FA', 'HO'],
                },
                toolkit: {
                    folderOptionName: '툴킷',
                    fileSectionCodeName: ['GUIDE', 'VIDEO'],
                    menuCode: ['VMS', 'EKIN', 'SOCIAL', 'RB'],
                },
                foundation: {
                    folderOptionName: '폴더',
                    fileSectionCodeName: ['GUIDE', 'VIDEO'],
                    menuCode: ['VMS', 'EKIN', 'DIGITAL', 'RB'],
                },
            },
            title: this.$route.meta.title,
            itemLength: 20,
            totalPage: null,
            page: 0,
            loadingData: false,
            menuCode: 'SP',
            attrs: [
                {
                    key: 'today',
                    highlight: 'gray',
                    dates: new Date(),
                    class: 'vc-today',
                    contentClass: 'vc-today',
                },
            ],

            folderDetail: {
                campaignBeginDt: null,
                campaignEndDt: null,
                campaignPeriodSectionCode: 'SELECT',
                checks: [
                    {
                        authSeq: 0,
                        detailAuthYn: 'N',
                        emailReceptionYn: 'N',
                    },
                ],
                contentsFileList: [],
                exposureYn: 'Y',
                folderContents: '',
                folderName: '',
                imageBase64: '',
                memo: '',
            },
        };
    },
    computed: {
        pageMenuCode() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .menuCode;
        },
        pageOptionName() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .folderOptionName;
        },
        pageFileSectionCodeName() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .fileSectionCodeName;
        },
    },
    components: {
        thumbnail,
        FileSettings,
    },
    created() {
        if (this.$route.params.id) {
            this.getFolderDetail();
        }
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        FileListUpdate(fileList) {
            this.folderDetail.contentsFileList = fileList;
        },
        //이미지 받아오기
        cropImage(imageBase64) {
            this.folderDetail.imageBase64 = imageBase64;
        },
        ModalAuthOpen() {
            this.$modal.show(ModalAuth, {
                groupTreeData: this.groupTreeData,
                checks: this.folderDetail.checks,
            });
        },
        minDate(tt) {
            if (tt === 'to') {
                return this.folderDetail.campaignBeginDt;
            }
            if (tt === 'from') {
                return null;
            }
        },
        maxDate(tt) {
            if (tt === 'to') {
                return null;
            }
            if (tt === 'from') {
                return this.folderDetail.campaignEndDt;
            }
        },
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        uploadFiles() {
            this.$refs.fileSet.uploadFiles();
        },
        async submitForm() {
            const uploadFn = this.$route.params.id ? putContents : postContents;
            try {
                const { data: response } = await uploadFn(
                    this.$route.meta.topMenuCode,
                    this.menuCode,
                    this.folderDetail,
                    this.$route.params.id
                );
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.success) {
                    this.$router.go(-1);
                }
                console.log(response);
            } catch (e) {
                console.log(e);
            }
        },
        async getFolderDetail() {
            try {
                const { data: response } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch,
                    this.$route.params.id
                );
                if (response.existMsg) {
                    alert(response.msg);
                }
                console.log(response);
                this.menuCode = response.data.menuCode;
                this.folderDetail = {
                    ...response.data,
                    imageBase64: response.data.imageFilePhysicalName,
                    campaignBeginDt: response.data.campaignBeginDt
                        ? new Date(response.data.campaignBeginDt)
                        : null,
                    campaignEndDt: response.data.campaignEndDt
                        ? new Date(response.data.campaignEndDt)
                        : null,
                    contentsFileList: [],
                };
                await this.$refs.fileSet.getFolderDetailFile();
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
