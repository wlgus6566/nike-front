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
            <div class="detail-cont" v-html="noticeDetail.contents"></div>
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
            <ul class="news-file-list">
                <li>
                    <button type="button" @click="accordion()">
                        P20_Nsw_Nike_Gallery_graphic_1_700x1000.pdf
                    </button>
                    <transition
                        @enter="itemOpen"
                        @leave="itemClose"
                        :css="false"
                    >
                        <div class="detail" v-if="openFile">
                            <div class="inner">
                                <div class="img-item">
                                    <img
                                        src="https://devupload.nikespace.co.kr/BANNER/202010133880004esdadOHdU.JPG?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZGV2dXBsb2FkLm5pa2VzcGFjZS5jby5rci9CQU5ORVIvMjAyMDEwMTMzODgwMDA0ZXNkYWRPSGRVLkpQRyIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTYxMDAwOTQ0MX0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE2MTAwMDkzMjF9fX1dfQ__&Signature=MVZVlq5HnSRpHh680U4-GVyRVqEyJ0F0--WioZSAmi6176MK2VOJBZ8cQ~2GTXOqehsAOObRoLjfiEUSd7THsOyBFGBUiXdR3ew3mS7l2Rvs48emwzAC4F~vMKbyuO3VjXn0uEeW~PHrpRanzSfcUm-PFrr1iXX4P-ca~6YRyAijjOp00dsgEiZ8TLPkEJK5AXfhZ1XY0vqiIcSzOKOPaaJxjCaQRhffWZhtOCCZDd1n85XzPRAiGKUoT2I8gjaIX~otvadP9V7tkr3dH-s6qzBXlJnMDtRylikwT71ka0KXb2zoT8eD1FIZSYDRoLo3koN7yVNwsgJ41FLo2ehqsA__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA"
                                        alt=""
                                    />
                                </div>
                            </div>
                        </div>
                    </transition>
                </li>
                <li>
                    <button type="button">
                        P20_Nsw_Nike_Gallery_graphic_1_700x1000.pdf
                    </button>
                    <transition
                        @enter="itemOpen"
                        @leave="itemClose"
                        :css="false"
                    >
                        <div class="detail" v-if="openFile">
                            <div class="inner">
                                <div class="video-item">
                                    <video controls>
                                        <source src="" type="video/mp4" />
                                    </video>
                                </div>
                            </div>
                        </div>
                    </transition>
                </li>
                <li>
                    <button type="button">
                        P20_Nsw_Nike_Gallery_graphic_1_700x1000.pdf
                    </button>
                    <transition
                        @enter="itemOpen"
                        @leave="itemClose"
                        :css="false"
                    >
                        <div class="detail" v-if="openFile">
                            <div class="inner">
                                <div class="video-item">
                                    <iframe
                                        width="560"
                                        height="315"
                                        src="https://www.youtube.com/embed/R19OFo8Wivs"
                                        frameborder="0"
                                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                        allowfullscreen
                                    ></iframe>
                                </div>
                            </div>
                        </div>
                    </transition>
                </li>
            </ul>
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
import { Cubic, gsap } from 'gsap/all';

export default {
    name: 'notice-view',
    data() {
        return {
            openFile: false,
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
        accordion(seq) {
            this.openFile = !this.openFile;
            //this.openFile = this.openFile === seq ? null : seq;
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
            });
            gsap.from(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: function() {
                    el.style.height = 'auto';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
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
                //console.log(response);

                this.noticeDetail = response;
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
