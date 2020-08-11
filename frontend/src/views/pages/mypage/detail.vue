<template>
    <div>
        <BtnArea @delete="deleteBoard" @edit="modifyRoute" />
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{ noticeDetail.title }}</h2>
                <span class="date">{{ noticeDetail.registrationDt }}</span>
            </div>
            <div class="detail-cont">{{ noticeDetail.contents }}</div>
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
            noticeDetail: {
                title: '',
                registrationDt: '',
                contents: '',
            },
        };
    },
    mounted() {
        this.getNoticeDetail();
        console.log(this.$route.meta.sectionCode);
    },
    components: {
        BtnArea: () => import('@/components/asset-view/btn-area.vue'),
    },
    methods: {
        //공지사항 상세
        async getNoticeDetail() {
            console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(this.$route.params.id);
                this.noticeDetail = response;
            } catch (error) {
                console.log(error);
            }
        },

        //게시판 수정페이지 이동
        modifyRoute() {
            if (this.$route.meta.sectionCode === 'NOTICE') {
                this.$router.push(
                    `/mypage/notice/modify/${this.$route.params.id}`
                );
            } else if (this.$route.meta.sectionCode === 'NEWS') {
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
                const response = await deleteCustomer(this.$route.params.id);
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
