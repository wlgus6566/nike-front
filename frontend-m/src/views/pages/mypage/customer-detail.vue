<template>
    <div>
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{ customerData.title }}</h2>
                <div class="info">
                    <span
                        class="name"
                        v-if="
                            customerData.noticeArticleSectionCode === 'NOTICE'
                        "
                    >
                        {{ customerData.nickname }}
                    </span>
                    <span class="date">{{ customerData.updateDt }}</span>
                </div>
            </div>
            <div class="detail-cont" v-html="customerData.contents"></div>
        </div>
        <div class="btn-area">
            <button
                type="button"
                class="btn-s-sm-black"
                @click="goToNoticeList"
            >
                <span>목록</span>
            </button>
        </div>
    </div>
</template>
<script>
import { getCustomerDetail } from '@/api/customer/';

export default {
    name: 'news-detail',
    data() {
        return {
            customerData: {},
        };
    },
    mounted() {
        this.getNoticeDetail();
    },
    methods: {
        async getNoticeDetail() {
            try {
                const { data: response } = await getCustomerDetail(
                    this.$route.params.sectionCode.toUpperCase(),
                    this.$route.params.id
                );
                this.customerData = response.data;
            } catch (error) {
                console.log(error);
            }
        },
        goToNoticeList: function() {
            if (this.$route.params.sectionCode === 'notice') {
                this.$router.push('/mypage/notice');
            } else if (this.$route.params.sectionCode === 'news') {
                this.$router.push('/mypage/news');
            }
        },
    },
};
</script>
<style scoped></style>
