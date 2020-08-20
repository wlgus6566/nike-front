<template>
    <div>
        <h2 class="page-title">
            <span>{{ this.$route.meta.title }}</span>
        </h2>
        <form @submit.prevent="submitData">
            <h3 class="form-title mt20">등록/수정</h3>
            <hr class="hr-black" />
            <ul class="form-list news">
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">썸네일 이미지</span>
                    </div>
                    <div class="form-column">
                        <thumbnail
                            class="new-upload"
                            :size="2 / 1"
                            @cropImage="cropImage"
                            :thumbnailFileName="newsDetail.thumbnailFileName"
                            :thumbnailFilePhysicalName="
                                newsDetail.thumbnailFilePhysicalName
                            "
                        >
                            <template slot="txt-up">
                                NEWS 이미지 등록
                                <span class="sub">
                                    권장 사이즈는 최소 660*330,<br />
                                    최대 3000*1500입니다.
                                </span>
                            </template>
                            <template slot="txt">
                                NEWS 이미지 재등록
                            </template>
                        </thumbnail>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">제목</label>
                    </div>
                    <div class="form-column">
                        <input
                            type="text"
                            v-model="newsDetail.title"
                            required
                        />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">내용</label>
                    </div>
                    <div class="form-column">
                        <ckeditor
                            v-model="newsDetail.contents"
                            style="width: 100%;"
                        />
                        <!--<span class="textarea">
                            <textarea
                                required
                                cols="100"
                                rows="2"
                                style="height: 300px;"
                                v-model="newsDetail.contents"
                            ></textarea>
                        </span>-->
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <button type="button" class="btn-s-white" @click="cancelBack()">
                    <span>취소</span>
                </button>
                <button type="submit" class="btn-s-black">
                    <span>저장</span>
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import { getCustomerDetail, postNews, putNews } from '@/api/customer';
import thumbnail from '@/components/thumbnail/index';

export default {
    name: 'notice-form',
    data() {
        return {
            noticeArticleSectionCode: 'NEWS',
            useYn: 'Y',
            newsDetail: {
                title: null,
                contents: null,
                imageBase64: null,
                thumbnailFileName: null,
                thumbnailFilePhysicalName: null,
                thumbnailFileSize: null,
            },
            file: '',
            msg: null,
        };
    },
    components: {
        thumbnail,
    },
    mounted() {
        console.log(this.$route.meta.sectionCode);
        if (this.$route.meta.modify) {
            this.getNewsDetail();
        }
    },
    activated() {
        //this.getNewsList();
    },
    methods: {
        // fileChange(target) {
        //     console.log(target.files);
        // },

        //이미지 받아오기
        cropImage(imageBase64, imgName) {
            this.newsDetail.imageBase64 = imageBase64;
            this.newsDetail.thumbnailFileName = imgName;
        },
        validateSelect(e, prop) {
            this.$refs.form.validateField(prop, (error) => {
                if (!error) {
                    alert('submit!');
                } else {
                    console.log('error on selector');
                    return false;
                }
            });
        },
        submitData() {
            if (this.$route.meta.modify) {
                this.modifyData();
            } else {
                //console.log(this.newsDetail.thumbnailFileName);
                this.saveData();
            }
        },
        async saveData() {
            if (!confirm('저장하시겠습니까?')) {
                return false;
            }
            try {
                const response = await postNews({
                    noticeArticleSectionCode: this.noticeArticleSectionCode,
                    title: this.newsDetail.title,
                    contents: this.newsDetail.contents,
                    imageBase64: this.newsDetail.imageBase64,
                    thumbnailFileName: this.newsDetail.thumbnailFileName,
                    thumbnailFilePhysicalName: this.newsDetail
                        .thumbnailFilePhysicalName,
                    thumbnailFileSize: this.newsDetail.thumbnailFileSize,
                    useYn: this.useYn,
                    msg: this.errMsg,
                });

                console.log(response);
                console.log(response.data.msg);

                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.newsDetail.title = '';
                    this.newsDetail.contents = '';
                    this.newsDetail.imageBase64 = '';
                    this.$router.push('/mypage/news');
                }
            } catch (error) {
                console.log(error);
            }
        },

        //공지사항 수정
        async modifyData() {
            if (!confirm('수정하시겠습니까?')) {
                return false;
            }
            if (this.$route.meta.modify) {
                try {
                    const response = await putNews(this.$route.params.id, {
                        title: this.newsDetail.title,
                        contents: this.newsDetail.contents,
                        imageBase64: this.newsDetail.imageBase64,
                        noticeArticleSectionCode: this.newsDetail
                            .noticeArticleSectionCode,
                        thumbnailFileName: this.newsDetail.thumbnailFileName,
                        thumbnailFilePhysicalName: this.newsDetail
                            .thumbnailFilePhysicalName,
                        thumbnailFileSize: this.newsDetail.thumbnailFileSize,
                        useYn: this.useYn,
                    });

                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/news');
                    }
                } catch (error) {
                    console.log(error);
                }
            }
        },

        //뉴스 리스트
        // async getNewsList() {
        //     try {
        //         const {
        //             data: { data: response },
        //         } = await getCustomerList(this.$route.meta.sectionCode, {
        //             page: 0,
        //             size: 20,
        //             keyword: '',
        //         });
        //         console.log(response);
        //     } catch (error) {
        //         console.log(error);
        //     }
        // },

        //뉴스 상세
        async getNewsDetail() {
            console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                this.newsDetail = response;
            } catch (error) {
                console.log(error);
            }
        },

        //작성 취소
        cancelBack() {
            if (!confirm('작성을 취소하시겠습니까?')) {
                return false;
            }
            this.detailDataReset();
            this.$router.go(-1);
        },
        detailDataReset() {
            this.newsDetail.title = '';
            this.newsDetail.contents = '';
            this.newsDetail.imageBase64 = '';
            this.newsDetail.thumbnailFileName = '';
            this.newsDetail.thumbnailFilePhysicalName = '';
            this.newsDetail.thumbnailFileSize = '';
        },
    },
};
</script>
<style scoped></style>
