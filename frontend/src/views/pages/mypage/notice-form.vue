<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
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
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="Y"
                                    checked
                                    v-model="noticeDetail.noticeYn"
                                    @click="noticeCheck($event)"
                                />
                                <i></i>
                                <span class="txt">고정</span>
                            </span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="N"
                                    v-model="noticeDetail.noticeYn"
                                    @click="noticeCheck($event)"
                                />
                                <i></i>
                                <span class="txt">비고정</span>
                            </span>
                        </label>
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
import {
    getCustomerDetail,
    getCustomerList,
    postNotice,
    putNotice,
} from '@/api/customer';

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
                noticeYn: null,
            },
        };
    },
    mounted() {
        this.getNoticeList();
        if (this.$route.meta.modify) {
            this.getNoticeDetail();
        }
    },
    activated() {
        this.getNoticeList();
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
                }
            } catch (error) {
                console.log(error);
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
                        noticeYn: this.noticeDetail.noticeYn,
                        title: this.noticeDetail.title,
                        useYn: this.useYn,
                    });

                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/notice');
                    }
                } catch (error) {
                    console.log(error);
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
                    return el.noticeYn === 'Y';
                });
            } catch (error) {
                console.log(error);
            }
        },

        //공지사항 상세
        async getNoticeDetail() {
            console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                this.noticeDetail = response;
            } catch (error) {
                console.log(error);
            }
        },

        //중요공지 체크
        noticeCheck(e) {
            if (
                this.noticeDetail.noticeYn === 'N' &&
                this.noticeYnLength.length >= 3
            ) {
                e.preventDefault();
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
    },
};
</script>
<style scoped></style>
