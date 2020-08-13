<template>
    <div>
        <h2 class="page-title">{{ this.title }}</h2>
        <form action="" @submit.prevent="submitForm">
            <h3 class="form-title mt20">폴더 설정</h3>
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
                        <span class="label-title required">캠페인 상태</span>
                    </div>
                    <div class="form-column">
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="folderDetail.exposureYn"
                                    value="Y"
                                />
                                <span></span>
                            </span>
                            <span>노출</span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="folderDetail.exposureYn"
                                    value="N"
                                />
                                <span></span>
                            </span>
                            <span>미노출</span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">업로드 위치</span>
                    </div>
                    <div class="form-column">
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="menuCode"
                                    value="SP"
                                />
                                <span></span>
                            </span>
                            <span>SP</span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="menuCode"
                                    value="SU"
                                />
                                <span></span>
                            </span>
                            <span>SU</span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="menuCode"
                                    value="FA"
                                />
                                <span></span>
                            </span>
                            <span>FA</span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="menuCode"
                                    value="HO"
                                />
                                <span></span>
                            </span>
                            <span>HO</span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">폴더 명</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="folderDetail.folderName"></el-input>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">폴더 상세</label>
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
                        <span class="label-title">캠페인 기간</span>
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
                                        value="selectDate"
                                    />
                                    <span></span>
                                </span>
                                <span>날짜선택</span>
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
                                    <span></span>
                                </span>
                                <span>365</span>
                            </label>
                        </div>
                        <!-- todo 추가 스크립트 작업 필요  -->
                        <div
                            class="date-picker-group"
                            v-if="
                                folderDetail.campaignPeriodSectionCode ===
                                'selectDate'
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
                :contentsFileList="folderDetail.contentsFileList"
                @FileListUpdate="FileListUpdate"
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
import bus from '@/utils/bus';
export default {
    name: 'upload',
    data() {
        return {
            title: this.$route.meta.title,
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
                campaignBeginDt: '',
                campaignEndDt: '',
                campaignPeriodSectionCode: 'selectDate',
                checks: [
                    {
                        authSeq: 0,
                        detailAuthYn: 'N',
                        emailReceptionYn: 'N',
                    },
                ],
                contentsFileList: [
                    {
                        detailThumbnailFileName: '',
                        detailThumbnailFilePhysicalName: '',
                        detailThumbnailFileSize: '',
                        fileContentType: '',
                        fileExtension: '',
                        fileKindCode: 'FILE',
                        fileName: '',
                        fileOrder: 1,
                        filePhysicalName: '',
                        fileSectionCode: 'GUIDE',
                        fileSize: 0,
                        thumbnailFileName: '',
                        thumbnailFilePhysicalName: '',
                        thumbnailFileSize: '',
                        title: '',
                        url: '',
                    },
                ],
                exposureYn: 'Y',
                folderContents: '',
                folderName: '',
                imageBase64: '',
                memo: '',
            },
        };
    },
    components: {
        thumbnail,
        FileSettings,
    },
    created() {
        if (this.$route.params.id) {
            this.getFolderDetail();
            this.initFetchData();
        }
        bus.$on('detailAuthYnUpdate', (seq) => {
            const idx = this.folderDetail.checks.findIndex((el) => {
                return el.authSeq === seq;
            });
            const value = this.checks[idx].detailAuthYn === 'Y' ? 'N' : 'Y';
            this.checks[idx].detailAuthYn = value;
        });
        bus.$on('emailReceptionYnUpdate', (seq) => {});

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
        async submitForm() {
            this.folderDetail.campaignBeginDt = this.$moment(
                this.folderDetail.campaignBeginDt
            ).format('YYYY.MM.DD');
            this.folderDetail.campaignEndDt = this.$moment(
                this.folderDetail.campaignEndDt
            ).format('YYYY.MM.DD');
            const uploadFn = this.$route.params.id ? putContents : postContents;
            try {
                const { data: response } = await uploadFn(
                    this.$route.meta.topMenuCode,
                    this.folderDetail.menuCode,
                    this.folderDetail
                );
                if (response.existMsg) {
                    alert(response.msg);
                }
                console.log(response);
            } catch (e) {
                console.log(e);
            }
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
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.contentsFileList = null;
            //this.getFolderDetailFile();
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
                this.menuCode = response.data.menuCode;
                this.folderDetail = {
                    ...response.data,
                    imageBase64: response.data.imageFilePhysicalName,
                    campaignBeginDt: new Date(response.data.campaignBeginDt),
                    campaignEndDt: new Date(response.data.campaignEndDt),
                    contentsFileList: [],
                };
                await this.getFolderDetailFile();
            } catch (error) {
                console.log(error);
            }
        },
        async getFolderDetailFile(infinite) {
            this.loadingData = true;
            this.checkAll = false;
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id,
                    {
                        page: this.page,
                        size: this.itemLength,
                    }
                );
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    this.folderDetail.contentsFileList = this.folderDetail.contentsFileList.concat(
                        response.content
                    );
                } else {
                    console.log(response.content[0]);
                    this.folderDetail.contentsFileList = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
