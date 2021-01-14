<template>
    <div>
        <h2 class="page-title">NEWS</h2>
        <form @submit.prevent="submitData" ref="form">
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
                            :imageBase64="newsDetail.thumbnailFilePhysicalName"
                            :imageFileName="newsDetail.thumbnailFileName"
                        >
                            <template slot="txt-up">
                                NEWS 이미지 등록
                                <span class="sub">
                                    권장 사이즈는 최소 660*330,<br />
                                    최대 3000*1500입니다.
                                </span>
                            </template>
                            <template slot="txt"> NEWS 이미지 재등록 </template>
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
                            :config="editorConfig"
                            @blur="onEditorInput"
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
            <newFileUplad
                ref="fileSet"
                @FileListUpdate="FileListUpdate"
                @submitForm="submitForm"
                @modifyForm="modifyForm"
            />
            <div class="btn-area">
                <router-link to="/mypage/news" class="btn-s-white">
                    <span>취소</span>
                </router-link>
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
import NewFileUplad from '@/components/news-file-upload/index.vue';
import { getAuthFromCookie } from '@/utils/cookies';
import bus from '@/utils/bus';

export default {
    name: 'notice-form',
    watch: {
        $route() {
            this.$destroy();
        },
    },
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
                fileList: [],
            },
            file: '',
            msg: null,
            // 에디터 업로드 설정
            editorConfig: {
                // TODO url에 NOTICE 부분 noticeArticleSectionCode에 맞게 변경 필요
                filebrowserImageUploadUrl: '',
                // TODO 현재 로그인한 계정의 auth값 가져오기
                fileTools_requestHeaders: {
                    Authorization: '',
                },
            },
        };
    },
    components: {
        thumbnail,
        NewFileUplad,
    },
    created() {
        this.$store.state.saveFolder = false;
        this.editorConfig.filebrowserImageUploadUrl =
            process.env.VUE_APP_API_URL +
            `/api/customer/${this.$route.meta.sectionCode}/images`;
        this.editorConfig.fileTools_requestHeaders.Authorization =
            this.$store.state.token || getAuthFromCookie();
    },
    activated() {
        this.$store.state.saveFolder = false;
        this.$refs.form.reset();
        this.detailDataReset();
    },
    mounted() {
        //console.log(this.noticeArticleSectionCode);
        if (this.$route.meta.modify) {
            this.getNewsDetail();
        }
    },
    methods: {
        FileListUpdate(fileList) {
            this.newsDetail.fileList = fileList;
        },
        //이미지 받아오기
        cropImage(imageBase64, imgName) {
            this.newsDetail.imageBase64 = imageBase64;
            this.newsDetail.thumbnailFileName = imgName;
        },
        validateSelect(e, prop) {
            this.$refs.form.validateField(prop, error => {
                if (!error) {
                    alert('submit!');
                } else {
                    console.log('error on selector');
                    return false;
                }
            });
        },
        submitData() {
            this.$refs.fileSet.uploadFiles();
        },
        async submitForm() {
            console.log('submitForm');

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
                    fileList: this.newsDetail.fileList,
                });

                //console.log(response);
                this.$store.state.saveFolder = true;
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    //console.log('성공');
                    this.detailDataReset();

                    this.$router.push('/mypage/news');
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                this.$store.state.saveFolder = false;
                console.error(error);
            }
        },

        //공지사항 수정
        async modifyForm() {
            if (this.$route.meta.modify) {
                try {
                    const response = await putNews(this.$route.params.id, {
                        contents: this.newsDetail.contents,
                        imageBase64: this.newsDetail.imageBase64,
                        noticeArticleSectionCode: this.newsDetail
                            .noticeArticleSectionCode,
                        thumbnailFileName: this.newsDetail.thumbnailFileName,
                        thumbnailFilePhysicalName: this.newsDetail
                            .thumbnailFilePhysicalName,
                        thumbnailFileSize: this.newsDetail.thumbnailFileSize,
                        title: this.newsDetail.title,
                        useYn: this.useYn,
                        fileList: this.newsDetail.fileList,
                    });
                    //console.log(response);
                    console.log(this.newsDetail.imageBase64);
                    this.$store.state.saveFolder = true;
                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/news');
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    this.$store.state.saveFolder = false;
                    console.error(error);
                }
            }
        },
        //뉴스 상세
        async getNewsDetail() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.noticeArticleSectionCode,
                    this.$route.params.id
                );
                this.newsDetail = response;
                await this.$refs.fileSet.getFolderDetailFile(response);
            } catch (error) {
                console.error(error);
            }
        },
        detailDataReset() {
            this.newsDetail.title = null;
            this.newsDetail.contents = null;
            this.newsDetail.imageBase64 = null;
            this.newsDetail.thumbnailFileName = null;
            this.newsDetail.thumbnailFilePhysicalName = null;
            this.newsDetail.thumbnailFileSize = null;
            this.newsDetail.fileList = [];
        },
        onEditorInput: function(e) {
            this.newsDetail.contents = e.editor._.editable.$.innerHTML;
        },
    },
    beforeRouteLeave(to, from, next) {
        if (!this.$store.state.saveFolder) {
            const answer = window.confirm(
                '이 페이지에서 나가시겠습니까?\n작업중인 내역은 저장되지 않습니다.'
            );
            if (answer) {
                next();
            } else {
                next(false);
            }
        } else {
            next();
        }
    },
};
</script>
<style scoped></style>
