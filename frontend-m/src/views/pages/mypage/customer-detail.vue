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
            <template
                v-if="
                    customerData.fileList &&
                        customerData.noticeArticleSectionCode === 'NOTICE'
                "
            >
                <div
                    class="detail-file"
                    v-if="customerData.fileList.length > 0"
                >
                    <ul class="detail-file-list">
                        <li
                            v-for="(item, index) in customerData.fileList"
                            :key="index"
                        >
                            <a :href="item.filePhysicalName">{{
                                item.fileName
                            }}</a>
                        </li>
                    </ul>
                </div>
            </template>
            <template
                v-if="
                    customerData.fileList &&
                        customerData.noticeArticleSectionCode === 'NEWS'
                "
            >
                <ul
                    class="news-file-list"
                    v-if="customerData.fileList.length > 0"
                >
                    <li
                        v-for="item in customerData.fileList"
                        :key="item.customerData"
                    >
                        <template>
                            <button
                                type="button"
                                v-if="item.fileName"
                                @click="fileDetailModal(item)"
                            >
                                {{ item.fileName }}
                            </button>
                            <button
                                type="button"
                                v-if="item.title"
                                @click="fileDetailModal(item)"
                            >
                                {{ item.title }}
                            </button>
                        </template>
                    </li>
                </ul>
            </template>
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
        <fileDetailPopup
            v-if="visible.modalEx"
            :visible.sync="visible.modalEx"
            :filePopupFile="filePopupFile"
            @closeModal="closeModal"
        />
    </div>
</template>
<script>
import { getCustomerDetail } from '@/api/customer/';
import fileDetailPopup from '@/views/pages/mypage/file-Detail-Popup.vue';

export default {
    name: 'news-detail',
    data() {
        return {
            customerData: {},
            visible: {
                modalEx: false,
            },
            filePopupFile: '',
        };
    },
    mounted() {
        this.getNoticeDetail();
    },
    components: {
        fileDetailPopup,
    },
    methods: {
        fileDetailModal(item) {
            console.log(item);
            this.filePopupFile = item;
            this.visible.modalEx = true;
        },
        closeModal() {
            this.visible.modalEx = false;
            this.filePopupFile = '';
        },
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
