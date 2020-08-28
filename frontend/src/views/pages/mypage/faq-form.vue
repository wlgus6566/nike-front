<template>
    <div>
        <h2 class="page-title">
            <span class="ko">자주 묻는 질문}</span>
        </h2>
        <form @submit.prevent="submitData">
            <h3 class="form-title mt20">등록/수정</h3>
            <hr class="hr-black" />
            <ul class="form-list">
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">카테고리</span>
                    </div>
                    <div class="form-column">
                        <span class="select">
                            <el-select
                                v-model="categoryCodeList.value"
                                required
                            >
                                <el-option
                                    v-for="(item,
                                    index) in categoryCodeList.listSortOptions"
                                    :key="index"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">질문</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea
                                cols="100"
                                rows="2"
                                style="height: 60px;"
                                v-model="faqDetail.title"
                                required
                            ></textarea>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">답변</label>
                    </div>
                    <div class="form-column">
                        <ckeditor
                            v-model="faqDetail.contents"
                            :config="editorConfig"
                            style="width: 100%;"
                        />
                        <!--                        <span class="textarea">
                            <textarea
                                cols="100"
                                rows="2"
                                style="height: 300px;"
                                v-model="faqDetail.contents"
                                required
                            ></textarea>
                        </span>-->
                    </div>
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
import { getCustomerDetail, postFaq, putFaq } from '@/api/customer';
import { getCode } from '@/api/code';
import { getAuthFromCookie } from '@/utils/cookies';

export default {
    name: 'faq-form',
    data() {
        return {
            noticeArticleSeq: null,
            useYn: this.useYn,
            categoryCodeList: {
                listSortOptions: [],
                value: '',
            },
            faqDetail: {
                title: '',
                contents: '',
                noticeArticleCategoryCode: null,
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
        this.editorConfig.filebrowserImageUploadUrl =
            process.env.VUE_APP_API_URL +
            `/api/customer/${this.$route.meta.sectionCode}/images`;
        this.editorConfig.fileTools_requestHeaders.Authorization =
            this.$store.state.token || getAuthFromCookie();
    },
    mounted() {
        this.getCategoryCode();
        if (this.$route.meta.modify) {
            this.getFaqDetail();
        }
    },
    methods: {
        submitData() {
            if (this.$route.meta.modify) {
                this.modifyData();
            } else {
                this.saveData();
            }
        },
        // FAQ 저장
        async saveData() {
            if (!confirm('저장하시겠습니까?')) {
                return false;
            }
            try {
                const response = await postFaq({
                    contents: this.faqDetail.contents,
                    noticeArticleCategoryCode: this.categoryCodeList.value,
                    noticeArticleSectionCode: 'QNA',
                    title: this.faqDetail.title,
                    useYn: this.useYn,
                });
                //console.log(response);
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.detailDataReset();
                    console.log(this.faqDetail.noticeArticleCategoryCode);
                    console.log(this.categoryCodeList);
                    this.$router.push('/mypage/faq');
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error(error);
            }
        },

        //FAQ 수정
        async modifyData() {
            if (!confirm('수정하시겠습니까?')) {
                return false;
            }
            if (this.$route.meta.modify) {
                try {
                    const response = await putFaq(this.$route.params.id, {
                        title: this.faqDetail.title,
                        contents: this.faqDetail.contents,
                        noticeArticleCategoryCode: this.categoryCodeList.value,
                        noticeArticleSectionCode: 'QNA',
                        noticeArticleSeq: this.$route.params.id,
                        useYn: this.useYn,
                    });
                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/faq');
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    console.error(error);
                }
            }
        },

        // 카테고리 코드 목록 조회
        async getCategoryCode() {
            try {
                const {
                    data: { data: response },
                } = await getCode('NOTICE_CATEGORY_CODE');
                //console.log(response);

                response.forEach((el) => {
                    this.categoryCodeList.listSortOptions.push({
                        value: el.code,
                        label: el.codeName,
                    });
                });
            } catch (error) {
                console.error(error);
            }
        },

        //FAQ 상세
        async getFaqDetail() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                this.faqDetail = response;
                this.categoryCodeList.value =
                    response.noticeArticleCategoryCode;
                //console.log(response);
            } catch (error) {
                console.error(error);
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
            this.faqDetail.title = '';
            this.faqDetail.contents = '';
            this.faqDetail.noticeArticleCategoryCode = null;
            this.categoryCodeList.value = '';
            this.noticeArticleSeq = '';
        },
    },
};
</script>
<style scoped></style>
