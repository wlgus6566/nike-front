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
                        <div>
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
                                        :disabled="pageMenuCodeDisabled"
                                    />
                                    <i></i>
                                    <span class="txt">{{ item }}</span>
                                </span>
                            </label>
                        </div>
                        <div class="form-desc-box">
                            <p class="form-desc-red">
                                * 업로드 위치를 변경하면 권한설정이 초기화
                                됩니다.
                            </p>
                        </div>
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
                <li class="form-row" v-if="pageOptionName === '캠페인'">
                    <div class="form-column">
                        <span class="label-title"
                            >{{ pageOptionName }} 기간</span
                        >
                    </div>
                    <div class="form-column">
                        <div class="form-date-picker">
                            <div class="form-desc-wrap">
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
                                <p class="form-desc-red">
                                    * Asset은 6개월 이후에 자동삭제 됩니다.
                                </p>
                            </div>
                            <div
                                class="date-picker-range"
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
                                <i class="dash"></i>
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
                    </div>
                </li>
                <li class="form-row" v-else>
                    <div class="form-column">
                        <span class="label-title">기간</span>
                    </div>
                    <div class="form-column">
                        <div class="form-date-picker">
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
                                class="date-picker-range"
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
                                <i class="dash"></i>
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
                                ref="maxTextarea"
                                cols="100"
                                rows="2"
                                style="height: 80px;"
                                v-model="folderDetail.memo"
                                @input="maxLengthCheck"
                            />
                            <span class="count">
                                <strong>{{ memoLength }}</strong> /
                                <em>{{ maxMemo }}</em
                                >byte
                            </span>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">권한 설정</label>
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
                            ref="modalAuth"
                            :checks="folderDetail.checks"
                            :menuCode="menuCode"
                            :visible.sync="visible.ModalAuth"
                            @checksUpdate="checksUpdate"
                        />
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <FileSettings
                v-if="pageFileSectionCodeName"
                ref="fileSet"
                :pageFileSectionCodeName="pageFileSectionCodeName"
                :menuCode="menuCode"
                @FileListUpdate="FileListUpdate"
                @submitForm="submitForm"
                @getAuthList="getAuthList"
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
import {
    getContentsAuthList,
    getContentsView,
    postContents,
    putContents,
} from '@/api/contents';
import { joinInit, joinDelete } from '@/api/join';
import bus from '@/utils/bus';
import { getLoginUpdate } from '@/api/mypage';
export default {
    name: 'UPLOAD',
    data() {
        return {
            routeId: this.$route.params.id,
            fileUploadingInterval: null,
            occupyInterval: null,
            maxMemo: 150,
            memoLength: 0,
            visible: {
                ModalAuth: false,
            },
            folderSet: {
                asset: {
                    folderOptionName: '캠페인',
                    menuCodeList: ['SP', 'SU', 'FA', 'HO'],
                    fileSectionCodeName: {
                        SP: [
                            'ATTRACT',
                            'ENGAGE',
                            'RN',
                            'TR',
                            'NSW',
                            'FB',
                            'BB',
                            'JD',
                            'KIDS',
                            'OTHERS',
                            'VIDEO',
                            'GUIDE',
                        ],
                        SU: [
                            'ATTRACT',
                            'ENGAGE',
                            'RN',
                            'TR',
                            'NSW',
                            'FB',
                            'BB',
                            'JD',
                            'KIDS',
                            'OTHERS',
                            'VIDEO',
                            'GUIDE',
                        ],
                        FA: [
                            'ATTRACT',
                            'ENGAGE',
                            'RN',
                            'TR',
                            'NSW',
                            'FB',
                            'BB',
                            'JD',
                            'KIDS',
                            'OTHERS',
                            'VIDEO',
                            'GUIDE',
                        ],
                        HO: [
                            'ATTRACT',
                            'ENGAGE',
                            'RN',
                            'TR',
                            'NSW',
                            'FB',
                            'BB',
                            'JD',
                            'KIDS',
                            'OTHERS',
                            'VIDEO',
                            'GUIDE',
                        ],
                    },
                },
                toolkit: {
                    folderOptionName: '툴킷',
                    menuCodeList: ['VMS', 'EKIN', 'SOCIAL', 'RB'],
                    fileSectionCodeName: {
                        VMS: ['GUIDE', 'VIDEO', 'VR'],
                        EKIN: ['GUIDE', 'VIDEO'],
                        SOCIAL: ['GUIDE', 'ASSET'],
                        RB: ['GUIDE', 'ASSET'],
                    },
                },
                foundation: {
                    folderOptionName: '폴더',
                    menuCodeList: ['VMS', 'EKIN', 'DIGITAL', 'RB'],
                    fileSectionCodeName: {
                        VMS: ['GUIDE', 'VIDEO'],
                        EKIN: ['GUIDE', 'VIDEO'],
                        DIGITAL: ['GUIDE', 'ASSET'],
                        RB: ['GUIDE', 'ASSET'],
                    },
                },
            },
            title: this.$route.meta.title,
            itemLength: 20,
            totalPage: null,
            page: 0,
            loadingData: false,
            menuCode: null,
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
                        authDepth: 0,
                        authName: '전체',
                        authSeq: 0,
                        checkBoxYn: 'Y',
                        detailAuthYn: 'N',
                        emailReceptionYn: 'N',
                        roleType: null,
                        subAuths: [],
                        viewYn: null,
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
            pageMenuCode: [],
        };
    },
    computed: {
        /*pageMenuCode() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .menuCodeList;
        },*/
        pageOptionName() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .folderOptionName;
        },
        pageFileSectionCodeName() {
            return this.folderSet[this.$route.meta.topMenuCode.toLowerCase()]
                .fileSectionCodeName[this.menuCode];
        },
        pageMenuCodeDisabled() {
            return this.folderDetail.contentsFileList.some((el) => {
                return (
                    (el.fileKindCode !== 'FILE' &&
                        (el.url !== '' || el.title !== '')) ||
                    (el.fileKindCode === 'FILE' && el.fileName !== '')
                );
            });
        },
    },
    watch: {
        menuCode() {
            this.getAuthList(this.$route.params.id);
        },
        '$store.state.gnbMenuListData'() {
            this.pageMenuCodeAuth(this.$route.meta.topMenuCode, 'CREATE');
        },
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
        this.$store.state.saveFolder = false;
        this.pageMenuCodeAuth(this.$route.meta.topMenuCode, 'CREATE');
        this.folderSetting();
        clearInterval(this.occupyInterval);
        if (this.routeId) {
            this.joinOccupyFn();
            this.occupyInterval = setInterval(() => {
                this.joinOccupyFn();
            }, 1000 * 60 * 4);
        }
    },
    activated() {
        this.$store.state.saveFolder = false;
        this.pageMenuCodeAuth(this.$route.meta.topMenuCode, 'CREATE');
        this.folderSetting();
        clearInterval(this.occupyInterval);
        if (this.routeId) {
            this.joinOccupyFn();
            this.occupyInterval = setInterval(() => {
                this.joinOccupyFn();
            }, 1000 * 60 * 4);
        }
    },
    destroyed() {
        if (this.routeId) {
            this.deleteOccupyFn();
            clearInterval(this.occupyInterval);
        }
    },
    deactivated() {
        if (this.routeId) {
            this.deleteOccupyFn();
            clearInterval(this.occupyInterval);
        }
    },
    methods: {
        async getAuthList(seq) {
            try {
                const topMenuCode = this.$route.meta.topMenuCode;
                const { data: response } = await getContentsAuthList(
                    topMenuCode,
                    this.menuCode,
                    {
                        contentsSeq: seq,
                    }
                );
                this.folderDetail.checks = response;
            } catch (error) {
                console.error(error);
            }
        },
        pageMenuCodeAuth(topMenuCode, skillCodes) {
            const index = this.$store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === topMenuCode
            );
            if (this.$store.state.gnbMenuListData[index]) {
                const a = this.$store.state.gnbMenuListData[index].menus.filter(
                    (aa) => {
                        return aa.skillCodes.some((bb) => {
                            return (
                                bb.code === skillCodes && bb.menuRoleSeq !== 0
                            );
                        });
                    }
                );
                this.pageMenuCode = a.map((el) => el.menuName);
                this.menuCode = this.pageMenuCode[0];
            }
        },

        async loginUpdate() {
            const response = await getLoginUpdate();
        },
        async joinOccupyFn() {
            console.log('joinOccupyFn');
            const response = await joinInit({
                menuName: this.$route.meta.topMenuCode,
                seq: this.$route.params.id,
            });
            //console.log(response);
        },
        async deleteOccupyFn() {
            const response = await joinDelete({
                menuName: this.$route.meta.topMenuCode,
                seq: this.routeId,
            });
            //console.log(response);
        },

        /**
         * 바이트 문자 입력가능 문자수 체크
         */
        maxLengthCheck() {
            const obj = this.$refs.maxTextarea;
            if (Number(this.byteCheck(obj)) > Number(this.maxMemo)) {
                alert('입력가능문자수를 초과하였습니다.');
                this.folderDetail.memo = obj.value.substr(0, 150);
                obj.focus();
            }
            this.memoLength = this.folderDetail.memo.length;
        },
        /**
         * 바이트수 반환
         * @param el : tag jquery object
         * @returns {Number}
         */
        byteCheck(el) {
            var codeByte = 0;
            for (var idx = 0; idx < el.value.length; idx++) {
                var oneChar = escape(el.value.charAt(idx));
                if (oneChar.length == 1) {
                    codeByte++;
                } else if (oneChar.indexOf('%u') != -1) {
                    codeByte += 2;
                } else if (oneChar.indexOf('%') != -1) {
                    codeByte++;
                }
            }
            return codeByte;
        },

        folderSetting() {
            this.$store.state.saveFolder = false;
            if (this.$route.params.id) {
                this.getFolderDetail();
            }
        },
        FileListUpdate(fileList) {
            this.folderDetail.contentsFileList = fileList;
        },
        //이미지 받아오기
        cropImage(imageBase64) {
            this.folderDetail.imageBase64 = imageBase64;
        },
        ModalAuthOpen() {
            this.visible.ModalAuth = true;
            this.$refs.modalAuth.dataInit(this.folderDetail.checks);
        },
        uploadFiles() {
            if (!this.folderDetail.imageBase64) {
                alert('썸네일을 선택해 주세요.');
                return;
            }
            if (!this.folderDetail.folderName) {
                alert('캠페인 명을 입력해 주세요.');
                return;
            }
            if (!this.folderDetail.folderContents) {
                alert('캠페인 상세를 입력해 주세요.');
                return;
            }
            if (this.folderDetail.campaignPeriodSectionCode === 'SELECT') {
                if (!this.folderDetail.campaignBeginDt) {
                    alert('시작일을 입력해 주세요.');
                    return;
                }
                if (!this.folderDetail.campaignEndDt) {
                    alert('종료일을 입력해 주세요.');
                    return;
                }
            }
            bus.$emit('pageLoading', true);

            clearInterval(this.fileUploadingInterval);
            this.fileUploadingInterval = setInterval(() => {
                this.loginUpdate();
            }, 1000 * 60 * 10);

            this.$refs.fileSet.uploadFiles();
        },
        checksUpdate(checksArr) {
            this.folderDetail.checks = checksArr;
            this.visible.ModalAuth = false;
        },
        async submitForm() {
            try {
                this.folderDetail.campaignBeginDt = this.$moment(
                    this.BeginDt
                ).format('YYYY.MM.DD');
                this.folderDetail.campaignEndDt = this.$moment(
                    this.EndDt
                ).format('YYYY.MM.DD');
                let response;

                if (this.$route.params.id) {
                    response = await putContents(
                        this.$route.meta.topMenuCode,
                        this.menuCode,
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
                clearInterval(this.fileUploadingInterval);
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
                if (response.data.success) {
                    this.$store.state.saveFolder = true;
                    this.$store.commit('SET_RELOAD', true);
                    if (this.$route.params.id) {
                        await this.deleteOccupyFn();
                        await this.$router.push(
                            `/${this.$route.meta.topMenuCode.toLowerCase()}/${this.menuCode.toLowerCase()}/${
                                this.$route.params.id
                            }`
                        );
                    } else {
                        await this.$router.push(
                            `/${this.$route.meta.topMenuCode.toLowerCase()}/${this.menuCode.toLowerCase()}`
                        );
                    }
                }
            } catch (error) {
                console.error(error.data);
                clearInterval(this.fileUploadingInterval);
                this.$store.state.saveFolder = false;
                bus.$emit('pageLoading', false);
                if (this.$route.params.id) {
                    await this.deleteOccupyFn();
                }
                if (error.data.code === 'NO_AUTH') {
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            }
        },
        async getFolderDetail() {
            try {
                const { data: response } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id
                );

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
                    ? this.newDt(response.data.campaignBeginDt)
                    : null;
                this.EndDt = response.data.campaignEndDt
                    ? this.newDt(response.data.campaignEndDt)
                    : null;
                await this.$refs.fileSet.getFolderDetailFile();
            } catch (error) {
                console.error(error);
            }
        },
        newDt(val) {
            const year = val.substr(0, 4);
            const month = val.substr(5, 2) - 1;
            const day = val.substr(8, 2);
            const _date = new Date(year, month, day); // date로 변경
            return _date;
        },
        cancelBack() {
            /*if (!confirm('작성을 취소하시겠습니까?')) {
                return false;
            }*/
            this.$router.go(-1);
        },
        dataReset() {
            this.BeginDt = null;
            this.EndDt = null;
            this.folderDetail.campaignBeginDt = null;
            this.folderDetail.campaignEndDt = null;
            this.folderDetail.imageFilePhysicalName = '';
            this.folderDetail.exposureYn = 'Y';
            this.folderDetail.folderName = '';
            this.folderDetail.folderContents = '';
            this.folderDetail.campaignPeriodSectionCode = 'SELECT';
            this.folderDetail.memo = '';
        },
    },
    beforeRouteLeave(to, from, next) {
        if (!this.$store.state.saveFolder) {
            const answer = window.confirm(
                '이 페이지에서 나가시겠습니까?\n작업중인 내역은 저장되지 않습니다.'
            );
            if (answer) {
                next();
            } else {
                next(false);
            }
        } else {
            next();
        }
    },
};
</script>
<style></style>
