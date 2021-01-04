<template>
    <div>
        <h2 class="page-title">
            <span class="ko">공지사항</span>
        </h2>
        <form @submit.prevent="submitData">
            <h3 class="form-title mt20">등록/수정</h3>
            <hr class="hr-black" />
            <ul class="form-list">
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title">상단 고정 여부</span>
                    </div>
                    <div class="form-column">
                        <span class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="Y"
                                    checked
                                    @click="noticeCheck($event, true)"
                                    v-model="noticeDetail.noticeYn"
                                />
                                <i></i>
                                <span class="txt">고정</span>
                            </span>
                        </span>
                        <span class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="N"
                                    v-model="noticeDetail.noticeYn"
                                />
                                <i></i>
                                <span class="txt">비고정</span>
                            </span>
                        </span>
                    </div>
                    <div class="form-column agr">
                        <span class="form-desc"
                            >* 목록 상단에 고정되며 [중요] 항목으로
                            표시됩니다.</span
                        >
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">제목</label>
                    </div>
                    <div class="form-column">
                        <input
                            type="text"
                            v-model="noticeDetail.title"
                            required
                        />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">내용</label>
                    </div>
                    <div class="form-column">
                        <ckeditor
                            v-model="noticeDetail.contents"
                            :config="editorConfig"
                            @blur="onEditorInput"
                            style="width: 100%;"
                        />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">파일 찾기</label>
                    </div>
                    <div class="form-column">
                        <div class="upload-file-box">
                            <!--active-->
                            <ul
                                class="upload-file-list"
                                :class="{
                                    'is-file': uploadFileList.length > 0,
                                }"
                            >
                                <li
                                    v-for="item in noticeDetail.fileList"
                                    :key="item.fileOrder"
                                >
                                    <label>
                                        <span class="checkbox">
                                            <input
                                                type="checkbox"
                                                :value="item.fileOrder"
                                                v-model="checkedFile"
                                            />
                                            <i></i>
                                        </span>
                                        <span class="txt">
                                            {{ item.fileName }}
                                        </span>
                                    </label>
                                </li>
                            </ul>
                            <div class="btn-box">
                                <div class="fine-file">
                                    <span class="btn-form-gray"
                                        ><span>찾기</span></span
                                    >
                                    <input
                                        type="file"
                                        ref="fileInput"
                                        multiple
                                        @change="uploadIptChange"
                                    />
                                </div>
                                <button
                                    type="button"
                                    class="btn-form"
                                    @click="removeFile"
                                >
                                    <span>삭제</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <router-link to="/mypage/notice" class="btn-s-white">
                    <span>취소</span>
                </router-link>
                <button type="submit" class="btn-s-black">
                    <span>저장</span>
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import {
    getCustomerDetail,
    getCustomerList,
    postNotice,
    putNotice,
} from '@/api/customer';

import { getAuthFromCookie } from '@/utils/cookies';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';

export default {
    name: 'notice-form',
    data() {
        return {
            //noticeArticleSectionCode: 'NOTICE',
            //noticeYn: null,
            useYn: 'Y',
            noticeYnLength: [],
            noticeDetail: {
                title: '',
                contents: '',
                noticeYn: 'N',
                noticeArticleSeq: null,
                fileList: [],
            },
            // 에디터 업로드 설정
            editorConfig: {
                // TODO url에 NOTICE 부분 noticeArticleSectionCode에 맞게 변경 필요
                filebrowserImageUploadUrl: '',
                // TODO 현재 로그인한 계정의 auth값 가져오기
                fileTools_requestHeaders: {
                    Authorization: '',
                },
            },
            checkedFile: [],
            uploadFileList: [],
        };
    },
    created() {
        this.$store.state.saveFolder = false;
        /* console.log(this.$route.name);*/
        this.editorConfig.filebrowserImageUploadUrl =
            process.env.VUE_APP_API_URL +
            `/api/customer/${this.$route.meta.sectionCode}/images`;
        this.editorConfig.fileTools_requestHeaders.Authorization =
            this.$store.state.token || getAuthFromCookie();
    },
    components: {},
    watch: {
        'noticeDetail.noticeYn'(val) {
            if (!val) {
                this.noticeDetail.noticeYn = 'N';
            }
        },
        $route() {
            this.$destroy();
        },
    },
    mounted() {
        this.getNoticeList();
        //this.noticeDetail.noticeYn = 'N';
        if (this.$route.meta.modify) {
            this.getNoticeDetail();
        }
    },
    activated() {
        this.$store.state.saveFolder = false;
        this.getNoticeList();
        this.detailDataReset();
    },
    methods: {
        //file 업로드
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter(item => {
                return this.noticeDetail.fileList.every(el => {
                    return (
                        item.name !== el.fileName && item.size !== el.fileSize
                    );
                });
            });
            console.log(mergeArray.length);
            if (mergeArray.length + this.uploadFileList.length > 5) {
                alert('5개 이상 등록 할 수 없습니다.');
                if (this.uploadFileList.length === 5) return;
                let maxNum = 5;
                if (this.uploadFileList.length > 0) {
                    maxNum = 5 - this.uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach(el => {
                this.noticeDetail.fileList.push({
                    fileOrder: this.noticeDetail.fileList.length,
                    fileName: el.name,
                    fileSize: el.size,
                    fileContentType: el.type,
                    progress: 0,
                });
            });
            this.uploadFileList = this.uploadFileList.concat(mergeArray);
        },
        async uploadFiles() {
            bus.$emit('pageLoading', true);
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
                                this.noticeDetail.fileList.forEach(item => {
                                    if (
                                        item.fileName === el.name &&
                                        item.fileContentType === el.type &&
                                        item.fileSize === el.size
                                    ) {
                                        item.progress = percentCompleted;
                                    }
                                });
                            },
                        };
                        formData.append('menuCode', 'notice');
                        const response = await fileUpLoad(formData, config);
                        //console.log(response);
                        if (response.existMsg) {
                            alert(response.msg);
                        }
                        this.noticeDetail.fileList.forEach(
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
                        console.log(error);
                    }
                })
            );
            if (this.$route.meta.modify) {
                await this.modifyData();
            } else {
                await this.saveData();
            }

            this.uploadFileList = [];
        },
        removeFile() {
            this.checkedFile.forEach(a => {
                this.noticeDetail.fileList = this.noticeDetail.fileList.filter(
                    b => b.fileOrder !== a
                );
            });
            this.checkedFile = [];
            this.fileOrderSet();
        },
        fileOrderSet() {
            this.uploadFileList = this.uploadFileList.filter(a => {
                return this.noticeDetail.fileList.some(b => {
                    return (
                        a.name === b.fileName &&
                        a.type === b.fileContentType &&
                        a.size === b.fileSize
                    );
                });
            });
            this.noticeDetail.fileList.forEach((el, index) => {
                el.fileOrder = index;
            });
        },
        submitData() {
            if (this.$route.meta.modify) {
                this.$store.state.saveFolder = false;
                if (!confirm('수정하시겠습니까?')) {
                    return false;
                }
                if (this.noticeDetail.fileList.length > 0) {
                    this.uploadFiles();
                } else {
                    this.modifyData();
                }
            } else {
                if (!confirm('저장하시겠습니까?')) {
                    return false;
                }
                if (this.noticeDetail.fileList.length > 0) {
                    this.uploadFiles();
                } else {
                    this.saveData();
                }
            }
        },
        async saveData() {
            try {
                const response = await postNotice({
                    contents: this.noticeDetail.contents,
                    fileList: this.noticeDetail.fileList,
                    noticeArticleSectionCode: 'NOTICE',
                    noticeYn: this.noticeDetail.noticeYn,
                    title: this.noticeDetail.title,
                    useYn: this.useYn,
                });
                this.$store.state.saveFolder = true;
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.detailDataReset();
                    this.$router.push('/mypage/notice');
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                this.$store.state.saveFolder = false;
                console.error(error);
            }
        },

        //공지사항 수정
        async modifyData() {
            if (this.$route.meta.modify) {
                try {
                    const response = await putNotice(this.$route.params.id, {
                        contents: this.noticeDetail.contents,
                        fileList: this.noticeDetail.fileList,
                        noticeArticleSectionCode: 'NOTICE',
                        noticeArticleSeq: this.noticeArticleSeq,
                        noticeYn: this.noticeDetail.noticeYn,
                        title: this.noticeDetail.title,
                        useYn: this.useYn,
                    });
                    this.$store.state.saveFolder = true;
                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/notice');
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    this.$store.state.saveFolder = false;
                    console.error(error);
                }
            }
        },

        //공지사항 리스트
        async getNoticeList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList(this.$route.meta.sectionCode, {
                    page: 0,
                    size: 20,
                    keyword: '',
                });
                this.noticeYnLength = response.content.filter(el => {
                    //console.log(el.noticeArticleSeq);
                    //console.log(this.$route.params.id * 1);
                    return (
                        el.noticeYn === 'Y' &&
                        el.noticeArticleSeq !== this.$route.params.id * 1
                    );
                });
                //console.log(this.noticeYnLength);
            } catch (error) {
                console.error(error);
            }
        },

        //공지사항 상세
        async getNoticeDetail() {
            //console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.$route.meta.sectionCode,
                    this.$route.params.id
                );
                //console.log(response);
                this.noticeDetail = response;
                this.noticeArticleSeq = response.noticeArticleSeq;
                this.fileOrderSet();
            } catch (error) {
                console.error(error);
            }
        },

        //중요공지 체크
        noticeCheck(e, YN) {
            if (YN && this.noticeYnLength.length >= 3) {
                e.preventDefault();
                this.noticeDetail.noticeYn = 'N';
                alert('상단 고정은 최대 3개까지만 설정가능합니다.');
            }
        },

        //작성 취소
        cancelBack() {
            this.$router.go(-1);
        },
        detailDataReset() {
            this.noticeDetail.title = '';
            this.noticeDetail.contents = '';
            this.noticeDetail.noticeYn = null;
        },
        onEditorInput: function(e) {
            this.noticeDetail.contents = e.editor._.editable.$.innerHTML;
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
<style scoped></style>
