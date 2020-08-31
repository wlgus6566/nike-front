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
                    <!--                    <div class="form-column">-->
                    <!--                        <span class="textarea">-->
                    <!--                            <textarea-->
                    <!--                                required-->
                    <!--                                cols="100"-->
                    <!--                                rows="2"-->
                    <!--                                style="height: 300px;"-->
                    <!--                                v-model="noticeDetail.contents"-->
                    <!--                                id="p_content"-->
                    <!--                            ></textarea>-->
                    <!--                        </span>-->
                    <!--                    </div>-->
                </li>
            </ul>
            <hr class="hr-gray" />
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
    import {getCustomerDetail, getCustomerList, postNotice, putNotice,} from '@/api/customer';

    import {getAuthFromCookie} from '@/utils/cookies';

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
        };
    },
    created() {
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
        this.getNoticeList();
        this.detailDataReset();
    },
    methods: {
        submitData() {
            if (this.$route.meta.modify) {
                this.modifyData();
            } else {
                this.saveData();
            }
        },
        async saveData() {
            if (!confirm('저장하시겠습니까?')) {
                return false;
            }
            try {
                const response = await postNotice({
                    contents: this.noticeDetail.contents,
                    noticeArticleSectionCode: 'NOTICE',
                    noticeYn: this.noticeDetail.noticeYn,
                    title: this.noticeDetail.title,
                    useYn: this.useYn,
                });
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.detailDataReset();
                    this.$router.push('/mypage/notice');
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error(error);
            }
        },

        //공지사항 수정
        async modifyData() {
            if (!confirm('수정하시겠습니까?')) {
                return false;
            }
            if (this.$route.meta.modify) {
                try {
                    const response = await putNotice(this.$route.params.id, {
                        contents: this.noticeDetail.contents,
                        noticeArticleSectionCode: 'NOTICE',
                        noticeArticleSeq: this.noticeArticleSeq,
                        noticeYn: this.noticeDetail.noticeYn,
                        title: this.noticeDetail.title,
                        useYn: this.useYn,
                    });

                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/notice');
                    } else {
                        alert(response.data.msg);
                    }
                    console.log(response);
                    console.log('시퀀스');
                    console.log(this.noticeArticleSeq);
                } catch (error) {
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
                this.noticeYnLength = response.content.filter((el) => {
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
                console.log(response);
                this.noticeDetail = response;
                this.noticeArticleSeq = response.noticeArticleSeq;
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
            if (!confirm('작성을 취소하시겠습니까?')) {
                return false;
            }
            this.detailDataReset();
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
};
</script>
<style scoped></style>
