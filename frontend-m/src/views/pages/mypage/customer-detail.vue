<template>
    <div>
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{customerData.title}}</h2>
                <span class="date">{{customerData.updateDt}}</span>
            </div>
            <div class="detail-cont">
                {{customerData.contents}}
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
    name: 'news-detail',
    data() {
        return {
            customerData: {}
        }
    },
    mounted() {
        this.getNoticeDetail();
    },
    methods: {
        async getNoticeDetail() {
            try {
                const { data: response } = await getCustomerDetail(this.$route.params.sectionCode, this.$route.params.id);
                this.customerData = response.data;
                console.log(this.$route.params.sectionCode);
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
