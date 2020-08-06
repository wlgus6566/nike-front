<template>
    <div>
        <!--        <h2 class="page-title">-->
        <!--            <span class="ko">{{ this.$route.meta.title }}</span>-->
        <!--        </h2>-->
        <h2 class="page-title"><span class="ko">공지사항</span></h2>
        <form @submit.prevent="saveData">
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
                                    name="noti"
                                    id="notiY"
                                    value="Y"
                                    checked
                                    v-model="noticeYn"
                                    @click="noticeCheck($event)"
                                />
                                <span></span>
                            </span>
                            <span>고정</span>
                        </label>
                        <label class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    name="noti"
                                    id="notiN"
                                    value="N"
                                    v-model="noticeYn"
                                />
                                <span></span>
                            </span>
                            <span>비고정</span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">제목</label>
                    </div>
                    <div class="form-column">
                        <input type="text" v-model="title" required />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">내용</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea
                                required
                                cols="100"
                                rows="2"
                                style="height: 300px;"
                                v-model="contents"
                            ></textarea>
                        </span>
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
import { getCustomerList, postNotice } from '@/api/customer';

export default {
    name: 'notice-form',
    data() {
        return {
            title: '',
            contents: '',
            noticeArticleSectionCode: 'NOTICE',
            noticeYn: 'N',
            useYn: 'Y',
            noticeYnLength: [],
        };
    },
    mounted() {
        this.getNoticeList();
    },
    methods: {
        async saveData() {
            if (!confirm('저장하시겠습니까')) {
                return false;
            }
            try {
                const {
                    data: { data: response },
                } = await postNotice({
                    contents: this.contents,
                    noticeArticleSectionCode: this.noticeArticleSectionCode,
                    noticeYn: this.noticeYn,
                    title: this.title,
                    useYn: this.useYn,
                });
                console.log(response);
                this.$router.go(-1);
            } catch (error) {
                console.log(error);
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
                console.log(response);
                this.noticeYnLength = response.content.filter((el) => {
                    return el.noticeYn === 'Y';
                });
            } catch (error) {
                console.log(error);
            }
        },
        noticeCheck(e) {
            if (this.noticeYn === 'N' && this.noticeYnLength.length >= 3) {
                e.preventDefault();
                alert('상단 고정은 최대 3개까지만 설정가능합니다.');
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
<style scoped></style>
