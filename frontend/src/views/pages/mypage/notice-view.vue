<template>
    <div>
        <BtnArea @delete="deleteBoard" @edit="modifyRoute" />
        <cumtomerView :noticeDetail="noticeDetail" />
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
    },
    components: {
        cumtomerView: () => import('@/components/customer/view.vue'),
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
            this.$router.push(`/mypage/notice/modify/${this.$route.params.id}`);
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
