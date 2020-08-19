<template>
    <div>
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{noticeData.title}}</h2>
                <span class="date">{{noticeData.updateDt}}</span>
            </div>
            <div class="detail-cont">
                {{noticeData.contents}}
            </div>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-sm-black" v-on:click="goToNoticeList"><span>목록</span></button>
        </div>
    </div>
</template>
<script>
import { getCustomerDetail } from '@/api/customer/';

export default {
    name: 'notice-detail',
    data() {
        return {
            noticeData: {}
        }
    },
    mounted() {
        this.getNoticeDetail();
    },
    methods: {
        async getNoticeDetail() {
            try {
                const { data: response } = await getCustomerDetail("NOTICE", this.$route.params.id);
                this.noticeData = response.data;
            } catch (error) {
                console.log(error);
            }
        },
        goToNoticeList: function () {
            this.$router.go(-1);
        }

    }
};
</script>
<style scoped></style>
