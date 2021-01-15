<template>
    <div>
        <BtnArea
            @goToList="goToList"
            @delete="deleteBoard"
            @edit="modifyRoute"
        />
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{ noticeDetail.title }}</h2>
                <div class="info">
                    <span
                        class="name"
                        v-if="
                            noticeDetail.noticeArticleSectionCode === 'NOTICE'
                        "
                        >{{ noticeDetail.nickname }}</span
                    >
                    <span class="date">{{ noticeDetail.updateDt }}</span>
                </div>
            </div>
            <div class="detail-cont">
                <div class="cont-unit ck-content"  v-html="noticeDetail.contents"></div>

            </div>
            <template v-if="noticeDetail.fileList">
                <div
                    class="detail-file"
                    v-if="noticeDetail.fileList.length > 0"
                >
                    <ul class="detail-file-list">
                        <li
                            v-for="(item, index) in noticeDetail.fileList"
                            :key="index"
                        >
                            <a :href="item.filePhysicalName">{{
                                item.fileName
                            }}</a>
                        </li>
                    </ul>
                </div>
            </template>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-black" @click="listRoute">
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
        goToList() {
            this.$router.push(
                `/mypage/${this.$route.meta.sectionCode}`.toLowerCase()
            );
        },
        //목록이동
        listRoute() {
            if (this.noticeArticleSectionCode === 'NOTICE') {
                this.$router.push('/mypage/notice');
            } else if (this.noticeArticleSectionCode === 'NEWS') {
                this.$router.push('/mypage/news');
            }
        },
        //공지사항 상세
        async getNoticeDetail() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.$route.meta.sectionCode,
                    this.$route.params.id
                );
                this.noticeDetail = response;
                this.noticeDetail.contents
                    = this.noticeDetail.contents.replaceAll('<figure', '<div class="image-box"> <figure').replaceAll('</figure>', '</figure> </div>');
                console.log('ddddddd after : ', this.noticeDetail.contents)
                this.noticeArticleSectionCode =
                    response.noticeArticleSectionCode;
            } catch (error) {
                console.error(error);
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
                console.error(error);
            }
        },
    },
};
</script>
<style scoped></style>
