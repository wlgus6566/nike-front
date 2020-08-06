<template>
    <div>
        <BtnArea @delete="deleteBoard" @edit="editBoard" />
        <mypageView :noticeDetail="noticeDetail" />
    </div>
</template>

<script>
import { getCustomerDetail, deleteCustomer } from '@/api/customer';

export default {
    name: 'notice-view',
    data() {
        return {
            noticeDetail: null,
        };
    },
    mounted() {
        this.getNoticeDetail();
    },
    components: {
        mypageView: () => import('@/components/customer/view.vue'),
        BtnArea: () => import('@/components/asset-view/btn-area.vue'),
    },
    methods: {
        async editBoard() {
            console.log('editBoard');
        },

        //공지사항 리스트
        async getNoticeDetail() {
            console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(this.$route.params.id);
                this.noticeDetail = response;
                // console.log(this.noticeDetail);
            } catch (error) {
                console.log(error);
            }
        },

        //게시판 삭제
        async deleteBoard() {
            if (!confirm('삭제 하시겠습니까?')) {
                return false;
            }
            try {
                console.log('ㅁㅁ');
                const {
                    data: { data: response },
                } = await deleteCustomer(this.$route.params.id);
                console.log(response);
                this.$router.go(-1);
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
