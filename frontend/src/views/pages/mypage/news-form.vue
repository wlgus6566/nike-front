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
                      <div class="ckeditor-wrap" onselectstart="event.cancelBubble=true;">
                        <ckeditor
                            :editor="editor"
                            v-model="newsDetail.contents"
                            :config="editorConfig"
                            style="width: 100%;">
                        </ckeditor>
                      </div>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
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
import { getAuthFromCookie } from '@/utils/cookies';

// CKEditor5 설정
import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor.js';

import Alignment from '@ckeditor/ckeditor5-alignment/src/alignment.js';
import AutoFormat from '@ckeditor/ckeditor5-autoformat/src/autoformat.js';
import BlockQuote from '@ckeditor/ckeditor5-block-quote/src/blockquote.js';
import Bold from '@ckeditor/ckeditor5-basic-styles/src/bold.js';
import CKFinder from '@ckeditor/ckeditor5-ckfinder/src/ckfinder.js';
import CKFinderUploadAdapter from '@ckeditor/ckeditor5-adapter-ckfinder/src/uploadadapter.js';
import FontBackgroundColor from '@ckeditor/ckeditor5-font/src/fontbackgroundcolor.js';
import FontColor from '@ckeditor/ckeditor5-font/src/fontcolor.js';
import FontFamily from '@ckeditor/ckeditor5-font/src/fontfamily.js';
import FontSize from '@ckeditor/ckeditor5-font/src/fontsize.js';
import Heading from '@ckeditor/ckeditor5-heading/src/heading.js';
import Image from '@ckeditor/ckeditor5-image/src/image.js';
import ImageCaption from '@ckeditor/ckeditor5-image/src/imagecaption.js';
import ImageStyle from '@ckeditor/ckeditor5-image/src/imagestyle.js';
import ImageToolbar from '@ckeditor/ckeditor5-image/src/imagetoolbar.js';
import ImageUpload from '@ckeditor/ckeditor5-image/src/imageupload.js';
import Indent from '@ckeditor/ckeditor5-indent/src/indent';
import IndentBlock from '@ckeditor/ckeditor5-indent/src/indentblock';

import Italic from '@ckeditor/ckeditor5-basic-styles/src/italic';
import Link from '@ckeditor/ckeditor5-link/src/link.js';
import List from '@ckeditor/ckeditor5-list/src/list.js';
import PageBreak from '@ckeditor/ckeditor5-page-break/src/pagebreak.js';
import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph.js';
import PasteFromOffice from '@ckeditor/ckeditor5-paste-from-office/src/pastefromoffice';
import SpecialCharacters from '@ckeditor/ckeditor5-special-characters/src/specialcharacters.js';
import Strikethrough from '@ckeditor/ckeditor5-basic-styles/src/strikethrough.js';
import Table from '@ckeditor/ckeditor5-table/src/table.js';
import TableCellProperties from '@ckeditor/ckeditor5-table/src/tablecellproperties';
import TableProperties from '@ckeditor/ckeditor5-table/src/tableproperties';
import TableToolbar from '@ckeditor/ckeditor5-table/src/tabletoolbar.js';
import TextTransformation from '@ckeditor/ckeditor5-typing/src/texttransformation.js';
import TodoList from '@ckeditor/ckeditor5-list/src/todolist';
import Underline from '@ckeditor/ckeditor5-basic-styles/src/underline.js';
import ImageResize from '@ckeditor/ckeditor5-image/src/imageresize';

import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials.js';
import SimpleUploadAdapter from '@ckeditor/ckeditor5-upload/src/adapters/simpleuploadadapter';

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
          },
          file: '',
          msg: null,
          // CKEditor5 설정
          editor: ClassicEditor,
          editorConfig: {
            /**
             * 기존내용 추가
             */
            fileTools_requestHeaders: {
              Authorization: '',
            },
            plugins: [
              Alignment,
              AutoFormat,
              BlockQuote,
              Bold,
              CKFinder,
              CKFinderUploadAdapter,
              Essentials,
              FontBackgroundColor,
              FontColor,
              FontFamily,
              FontSize,
              Heading,
              Image,
              ImageCaption,
              ImageStyle,
              ImageToolbar,
              ImageUpload,
              Indent,
              Italic,
              Link,
              List,
              PageBreak,
              Paragraph,
              PasteFromOffice,
              SpecialCharacters,
              Strikethrough,
              Table,
              TableCellProperties,
              TableProperties,
              TableToolbar,
              TextTransformation,
              TodoList,
              Underline,
              SimpleUploadAdapter,
              ImageResize,
              IndentBlock
            ],
            toolbar: {
              items: [
                'heading',
                '|',
                'bold',
                'italic',
                'link',
                'bulletedList',
                'numberedList',
                '|',
                'indent',
                'outdent',
                '|',
                'imageUpload',
                'blockQuote',
                'insertTable',
                'undo',
                'redo',
                'alignment',
                'fontSize',
                'fontColor',
                'fontBackgroundColor',
                'fontFamily',
                'underline',
                'strikethrough',
                'specialCharacters'
              ],
              shouldNotGroupWhenFull: true
            },
            language: 'en',
            table: {
              contentToolbar: [
                'tableColumn',
                'tableRow',
                'mergeTableCells',
                'tableCellProperties',
                'tableProperties'
              ]
            },
            simpleUpload: {
              uploadUrl: '',
              withCredentials: true,
              headers: {
                'X-CSRF-TOKEN': 'CSRF-Token',
                Authorization: ''
              }
            },
            indentBlock: {
              offset: 1,
              unit: 'em'
            },
            image: {
              styles: [
                'alignLeft', 'alignCenter', 'alignRight'
              ],
              resizeOptions: [
                {
                  name: 'imageResize:original',
                  label: 'Original',
                  value: null
                },
                {
                  name: 'imageResize:50',
                  label: '50%',
                  value: '50'
                },
                {
                  name: 'imageResize:75',
                  label: '75%',
                  value: '75'
                }
              ],
              toolbar: [
                'imageStyle:alignLeft', 'imageStyle:alignCenter', 'imageStyle:alignRight',
                '|',
                'imageResize',
                '|',
                'imageTextAlternative'
              ]
            }
          }
        };
    },
    components: {
        thumbnail,
    },
    created() {
      this.$store.state.saveFolder = false;
      // 업로드 설정 추가
      this.editorConfig.simpleUpload.uploadUrl =
          process.env.VUE_APP_API_URL +
          `/api/customer/${this.$route.meta.sectionCode}/images`;
      this.editorConfig.simpleUpload.headers.Authorization =
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
        async modifyData() {
            if (!confirm('수정하시겠습니까?')) {
                return false;
            }
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
                //console.log(response);
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
        },
        // onEditorInput: function (e) {
        //     this.newsDetail.contents = e.editor._.editable.$.innerHTML;
        // },
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
<style scoped>
::v-deep .ck.ck-content.ck-editor__editable {
  min-height: 400px;
}
</style>
