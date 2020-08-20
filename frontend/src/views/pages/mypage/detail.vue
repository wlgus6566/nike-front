<template>
    <div>
        <BtnArea @delete="deleteBoard" @edit="modifyRoute" />
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{ noticeDetail.title }}</h2>
                <span class="date">{{ noticeDetail.updateDt }}</span>
            </div>
            <div class="detail-cont" v-html="noticeDetail.contents"></div>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-black" @click="$router.go(-1)">
                <span>목록으로 가기</span>
            </button>
        </div>
    </div>
</template>

<script>
import { getCustomerDetail, deleteCustomer } from '@/api/customer';

export default {
    name: 'notice-view',
    data() {
        return {
            noticeArticleSectionCode: null,
            noticeDetail: {
                title: '',
                registrationDt: '',
                contents: '',
            },
        };
    },
    mounted() {
        this.getNoticeDetail();
    },
    components: {
        BtnArea: () => import('@/components/asset-view/btn-area.vue'),
    },
    methods: {
        //공지사항 상세
        async getNoticeDetail() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                console.log(response);
                this.noticeDetail = response;
                this.noticeArticleSectionCode =
                    response.noticeArticleSectionCode;
            } catch (error) {
                console.log(error);
            }
        },

        //게시판 수정페이지 이동
        modifyRoute() {
            if (this.noticeArticleSectionCode === 'NOTICE') {
                this.$router.push(
                    `/mypage/notice/modify/${this.$route.params.id}`
                );
            } else if (this.noticeArticleSectionCode === 'NEWS') {
                this.$router.push(
                    `/mypage/news/modify/${this.$route.params.id}`
                );
            }
        },

        //게시판 삭제
        async deleteBoard() {
            if (!confirm('삭제 하시겠습니까?')) {
                return false;
            }
            try {
                const response = await deleteCustomer(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.$router.go(-1);
                }
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
