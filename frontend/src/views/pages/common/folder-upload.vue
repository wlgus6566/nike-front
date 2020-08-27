<template>
    <div>
        <h2 class="page-title">{{ title }}</h2>
        <form action="" @submit.prevent="uploadFiles">
            <h3 class="form-title mt20">{{ pageOptionName }} 설정</h3>
            <hr class="hr-black" />
            <ul class="form-list-thumb" v-if="folderDetail">
                <li class="form-row thumb-row">
                    <thumbnail
                        :size="1 / 1"
                        @cropImage="cropImage"
                        :imageBase64="folderDetail.imageFilePhysicalName"
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
                                    <el-date-picker
                                        v-model="BeginDt"
                                        type="date"
                                        placeholder="YYYY.MM.DD"
                                        format="yyyy.MM.dd"
                                        :picker-options="pickerBeginOption"
                                    >
                                    </el-date-picker>
                                </div>
                                <div class="date-picker">
                                    <el-date-picker
                                        v-model="EndDt"
                                        type="date"
                                        placeholder="YYYY.MM.DD"
                                        format="yyyy.MM.dd"
                                        :picker-options="pickerEndOption"
                                    >
                                    </el-date-picker>
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
                        <ModalAuth
                            :checks="folderDetail.checks"
                            :visible.sync="visible.ModalAuth"
                            @checksUpdate="checksUpdate"
                        />
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
                <button type="button" class="btn-s-white" @click="cancelBack()">
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
import { getContentsView, postContents, putContents } from '@/api/contents';
import bus from '@/utils/bus';
import { postReport, putReport } from '@/api/report';
export default {
    name: 'UPLOAD',
    data() {
        return {
            visible: {
                ModalAuth: false,
            },
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

            BeginDt: null,
            EndDt: null,

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
            pickerBeginOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.EndDt) {
                        return time.getTime() > this.EndDt.getTime();
                    }
                },
            },
            pickerEndOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.BeginDt) {
                        return time.getTime() < this.BeginDt.getTime();
                    }
                },
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
    watch: {
        BeginDt(date) {
            this.folderDetail.campaignBeginDt = this.$moment(date).format(
                'yyyy.mm.dd'
            );
        },
        EndDt(date) {
            this.folderDetail.campaignEndDt = this.$moment(date).format(
                'yyyy.mm.dd'
            );
        },
    },
    components: {
        thumbnail,
        FileSettings,
        ModalAuth,
    },
    created() {
        this.menuCode = this.folderSet[
            this.$route.meta.topMenuCode.toLowerCase()
        ].menuCode[0];
        if (this.$route.params.id) {
            this.getFolderDetail();
        }
    },
    activated() {
        this.menuCode = this.folderSet[
            this.$route.meta.topMenuCode.toLowerCase()
        ].menuCode[0];
        if (this.$route.params.id) {
            this.getFolderDetail();
        }
    },
    /*activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    /*deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },*/
    methods: {
        FileListUpdate(fileList) {
            this.folderDetail.contentsFileList = fileList;
        },
        //이미지 받아오기
        cropImage(imageBase64) {
            this.folderDetail.imageBase64 = imageBase64;
        },
        ModalAuthOpen() {
            this.visible.ModalAuth = true;
        },

        // minDate(tt) {
        //     if (tt === 'to') {
        //         return this.folderDetail.campaignBeginDt;
        //     }
        //     if (tt === 'from') {
        //         return null;
        //     }
        // },
        // maxDate(tt) {
        //     if (tt === 'to') {
        //         return null;
        //     }
        //     if (tt === 'from') {
        //         return this.folderDetail.campaignEndDt;
        //     }
        // },
        uploadFiles() {
            console.log(this.menuCode);

            if (!this.folderDetail.imageBase64) {
                alert('썸네일');
                return;
            }
            if (!this.folderDetail.folderName) {
                alert('캠페인 명');
                return;
            }
            if (!this.folderDetail.folderContents) {
                alert('캠페인 상세');
                return;
            }
            if (this.folderDetail.campaignPeriodSectionCode === 'SELECT') {
                if (!this.folderDetail.campaignBeginDt) {
                    alert('시작일');
                    return;
                }
                if (!this.folderDetail.campaignEndDt) {
                    alert('종료일');
                    return;
                }
            }

            this.$refs.fileSet.uploadFiles();
        },
        checksUpdate(checksArr) {
            this.folderDetail.checks = checksArr[0].subAuths;
            this.visible.ModalAuth = false;
        },
        async submitForm() {
            try {
                bus.$emit('pageLoading', true);
                this.folderDetail.campaignBeginDt = this.$moment(
                    this.BeginDt
                ).format('YYYY.MM.DD');
                this.folderDetail.campaignEndDt = this.$moment(
                    this.EndDt
                ).format('YYYY.MM.DD');
                console.log(this.$route);
                let response;
                if (this.$route.params.id) {
                    response = await putContents(
                        this.$route.meta.topMenuCode,
                        this.$route.params.pathMatch,
                        this.$route.params.id,
                        this.folderDetail
                    );
                } else {
                    response = await postContents(
                        this.$route.meta.topMenuCode,
                        this.menuCode,
                        this.folderDetail
                    );
                }
                bus.$emit('pageLoading', false);
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
                if (response.data.success) {
                    this.$store.commit('SET_RELOAD', true);
                    if (this.$route.params.id) {
                        await this.$router.push(
                            `/${this.$route.meta.topMenuCode.toLowerCase()}/${
                                this.$route.params.pathMatch
                            }/${this.$route.params.id}`
                        );
                    } else {
                        await this.$router.push(
                            `/${this.$route.meta.topMenuCode.toLowerCase()}/${this.menuCode.toLowerCase()}`
                        );
                    }
                }
            } catch (error) {
                console.error(error);
            }
        },
        async getFolderDetail() {
            try {
                const { data: response } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch,
                    this.$route.params.id
                );
                console.log(response);
                if (response.existMsg) {
                    alert(response.msg);
                }
                this.menuCode = response.data.menuCode;
                this.folderDetail = {
                    ...response.data,
                    imageBase64: response.data.imageFilePhysicalName,
                    contentsFileList: [],
                };
                this.BeginDt = response.data.campaignBeginDt
                    ? new Date(response.data.campaignBeginDt)
                    : null;
                this.EndDt = response.data.campaignEndDt
                    ? new Date(response.data.campaignEndDt)
                    : null;
                await this.$refs.fileSet.getFolderDetailFile();
            } catch (error) {
                console.error(error);
            }
        },
        cancelBack() {
            if (!confirm('작성을 취소하시겠습니까?')) {
                return false;
            }
            this.$router.go(-1);
        },
    },
};
</script>
<style></style>
