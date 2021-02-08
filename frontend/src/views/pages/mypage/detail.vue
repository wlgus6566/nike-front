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
                    <span class="name">
                        {{ noticeDetail.nickname }}
                    </span>
                    <span class="date">{{ noticeDetail.updateDt }}</span>
                </div>
            </div>
            <div class="detail-cont">
                <div
                    class="cont-unit ck-content"
                    v-html="noticeDetail.contents"
                ></div>
            </div>
            <template
                v-if="
                    noticeDetail.fileList &&
                    noticeArticleSectionCode === 'NOTICE'
                "
            >
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
            <template
                v-if="
                    noticeDetail.fileList && noticeArticleSectionCode === 'NEWS'
                "
            >
                <ul
                    class="news-file-list"
                    v-if="noticeDetail.fileList.length > 0"
                >
                    <li
                        v-for="item in noticeDetail.fileList"
                        :key="item.noticeFileSeq"
                        :class="{ active: openFile === item.noticeFileSeq }"
                    >
                        <button
                            type="button"
                            @click="accordion(item.noticeFileSeq)"
                            v-if="item.fileName"
                            :disabled="
                                item.fileContentType.split('/')[0] !==
                                    'IMAGE' &&
                                item.fileContentType.split('/')[0] !== 'VIDEO'
                            "
                        >
                            <template>
                                {{ item.fileName }}
                            </template>
                        </button>
                        <button
                            type="button"
                            @click="accordion(item.noticeFileSeq)"
                            v-if="item.title"
                        >
                            {{ item.title }}
                        </button>
                        <transition
                            @enter="itemOpen"
                            @leave="itemClose"
                            :css="false"
                        >
                            <div
                                class="detail"
                                v-if="openFile === item.noticeFileSeq"
                            >
                                <div class="inner">
                                    <div
                                        class="video-item"
                                        v-if="
                                            item.fileKindCode === 'VIDEO' ||
                                            item.fileContentType.split(
                                                '/'
                                            )[0] === 'VIDEO'
                                        "
                                    >
                                        <youtube
                                            v-if="
                                                videoCheck(item.url).type ===
                                                'youtube'
                                            "
                                            :video-id="videoCheck(item.url).id"
                                            :player-vars="{
                                                autoplay: 1,
                                            }"
                                        ></youtube>

                                        <vimeo-player
                                            v-else-if="
                                                videoCheck(item.url).type ===
                                                'vimeo'
                                            "
                                            class="video-item"
                                            :video-id="videoCheck(item.url).id"
                                            :player-height="height"
                                            :player-width="width"
                                        ></vimeo-player>
                                        <iframe
                                            v-else-if="
                                                videoCheck(item.url).type ===
                                                'brightcove'
                                            "
                                            :src="videoCheck(item.url).id"
                                            allowfullscreen
                                            webkitallowfullscreen
                                            mozallowfullscreen
                                        ></iframe>
                                        <video controls v-else>
                                            <source
                                                :src="videoCheck(item.url).id"
                                                type="video/mp4"
                                            />
                                        </video>
                                    </div>
                                    <div class="img-item" v-else>
                                        <img
                                            :src="
                                                item.detailThumbnailFilePhysicalName
                                            "
                                            :alt="item.fileName"
                                        />
                                    </div>
                                </div>
                            </div>
                        </transition>
                    </li>
                </ul>
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
            height: 'auto',
            width: '600',
        };
    },

    mounted() {
        this.getNoticeDetail();
    },
    components: {
        BtnArea: () => import('@/components/asset-view/btn-area.vue'),
    },
    methods: {
        videoCheck(url) {
            url.match(
                /(http:|https:|)\/\/(player.|www.)?(vimeo\.com|youtu(be\.com|\.be|be\.googleapis\.com))\/(video\/|embed\/|watch\?v=|v\/)?([A-Za-z0-9._%-]*)(\&\S+)?/
            );
            if (RegExp.$3.indexOf('youtu') > -1) {
                return {
                    type: 'youtube',
                    id: RegExp.$6,
                };
            } else if (RegExp.$3.indexOf('vimeo') > -1) {
                return {
                    type: 'vimeo',
                    id: RegExp.$6,
                };
            } else if (url.indexOf('brightcove') > -1) {
                return {
                    type: 'brightcove',
                    id: url,
                };
            } else {
                return {
                    type: 'mp4',
                    id: url,
                };
            }
        },
        accordion(seq) {
            this.openFile = this.openFile === seq ? null : seq;
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
            });
            gsap.from(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: function () {
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
