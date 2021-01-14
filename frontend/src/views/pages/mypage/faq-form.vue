<script src="src/utils/uploadAdapter.js"></script>
<template>
    <div>
        <h2 class="page-title">
            <span class="ko">자주 묻는 질문</span>
        </h2>
        <form @submit.prevent="submitData">
            <h3 class="form-title mt20">등록/수정</h3>
            <hr class="hr-black" />
            <ul class="form-list">
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">카테고리</span>
                    </div>
                    <div class="form-column">
                        <span class="select">
                            <el-select
                                v-model="categoryCodeList.value"
                                required
                            >
                                <el-option
                                    v-for="(item,
                                    index) in categoryCodeList.listSortOptions"
                                    :key="index"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">질문</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea
                                cols="100"
                                rows="2"
                                style="height: 60px;"
                                v-model="faqDetail.title"
                                required
                            ></textarea>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">답변</label>
                    </div>
                    <div class="form-column">
<!--                        <ckeditor-->
<!--                            v-model="faqDetail.contents"-->
<!--                            :config="editorConfig"-->
<!--                            @blur="onEditorInput"-->
<!--                            style="width: 100%;"-->
<!--                        />-->

                      <ckeditor
                          :editor="editor"
                          v-model="faqDetail.contents"
                          :config="editorConfig"
                          style="width: 100%;"
                      >
                      </ckeditor>
                        <!--                        <span class="textarea">
                            <textarea
                                cols="100"
                                rows="2"
                                style="height: 300px;"
                                v-model="faqDetail.contents"
                                required
                            ></textarea>
                        </span>-->
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <router-link to="/mypage/faq" class="btn-s-white">
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
import { getCustomerDetail, postFaq, putFaq } from '@/api/customer';
import { getCode } from '@/api/code';
import { getAuthFromCookie } from '@/utils/cookies';

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
import Indent from '@ckeditor/ckeditor5-indent/src/indent.js';

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

//
import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials.js';
import SimpleUploadAdapter from '@ckeditor/ckeditor5-upload/src/adapters/simpleuploadadapter';

export default {
    name: 'faq-form',
    // watch: {
    //     $route() {
    //         this.$destroy();
    //     },
    // },
    data() {
        return {
            noticeArticleSeq: null,
            useYn: this.useYn,
            categoryCodeList: {
                listSortOptions: [],
                value: '',
            },
            faqDetail: {
                title: '',
                contents: '',
                noticeArticleCategoryCode: null,
            },
            // 에디터 업로드 설정
            // editorConfig: {
            //     // TODO url에 NOTICE 부분 noticeArticleSectionCode에 맞게 변경 필요
            //     filebrowserImageUploadUrl: '',
            //     // TODO 현재 로그인한 계정의 auth값 가져오기
            //     fileTools_requestHeaders: {
            //         Authorization: '',
            //     },
            // },

          // CKEditor5 설정
            editor: ClassicEditor,
            editorData: '',
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
                ImageResize
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
    created() {
        this.$store.state.saveFolder = false;
        // this.editorConfig.filebrowserImageUploadUrl =
        //     process.env.VUE_APP_API_URL +
        //     `/api/customer/${this.$route.meta.sectionCode}/images`;
        // this.editorConfig.fileTools_requestHeaders.Authorization =
        //     this.$store.state.token || getAuthFromCookie();

        // 업로드 설정 추가
        this.editorConfig.simpleUpload.uploadUrl =
            process.env.VUE_APP_API_URL +
            `/api/customer/${this.$route.meta.sectionCode}/images`;
        this.editorConfig.simpleUpload.headers.Authorization =
            this.$store.state.token || getAuthFromCookie();
    },
    mounted() {
        this.getCategoryCode();
        if (this.$route.meta.modify) {
            this.getFaqDetail();
        }
    },
    activated() {
        this.$store.state.saveFolder = false;
    },
    methods: {
        submitData() {
            if (this.$route.meta.modify) {
                this.modifyData();
            } else {
                this.saveData();
            }
        },
        // FAQ 저장
        async saveData() {
            if (!confirm('저장하시겠습니까?')) {
                return false;
            }
            try {
                const response = await postFaq({
                    contents: this.faqDetail.contents,
                    noticeArticleCategoryCode: this.categoryCodeList.value,
                    noticeArticleSectionCode: 'QNA',
                    title: this.faqDetail.title,
                    useYn: this.useYn,
                });
                //console.log(response);
                this.$store.state.saveFolder = true;
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.detailDataReset();
                    console.log(this.faqDetail.noticeArticleCategoryCode);
                    console.log(this.categoryCodeList);
                    this.$router.push('/mypage/faq');
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                this.$store.state.saveFolder = false;
                console.error(error);
            }
        },

        //FAQ 수정
        async modifyData() {
            if (!confirm('수정하시겠습니까?')) {
                return false;
            }
            if (this.$route.meta.modify) {
                try {
                    const response = await putFaq(this.$route.params.id, {
                        title: this.faqDetail.title,
                        contents: this.faqDetail.contents,
                        noticeArticleCategoryCode: this.categoryCodeList.value,
                        noticeArticleSectionCode: 'QNA',
                        noticeArticleSeq: this.$route.params.id,
                        useYn: this.useYn,
                    });
                    this.$store.state.saveFolder = true;
                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/faq');
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    this.$store.state.saveFolder = false;
                    console.error(error);
                }
            }
        },

        // 카테고리 코드 목록 조회
        async getCategoryCode() {
            try {
                const {
                    data: { data: response },
                } = await getCode('NOTICE_CATEGORY_CODE');
                //console.log(response);

                response.forEach((el) => {
                    this.categoryCodeList.listSortOptions.push({
                        value: el.code,
                        label: el.codeName,
                    });
                });
            } catch (error) {
                console.error(error);
            }
        },

        //FAQ 상세
        async getFaqDetail() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.$route.meta.sectionCode,
                    this.$route.params.id
                );
                this.faqDetail = response;
                this.categoryCodeList.value =
                    response.noticeArticleCategoryCode;
                //console.log(response);
            } catch (error) {
                console.error(error);
            }
        },
        detailDataReset() {
            this.faqDetail.title = '';
            this.faqDetail.contents = '';
            this.faqDetail.noticeArticleCategoryCode = null;
            this.categoryCodeList.value = '';
            this.noticeArticleSeq = '';
        },
        onEditorInput: function (e) {
          console.log(e.editor)
            this.faqDetail.contents = this.editorData;
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
<style scoped>
::v-deep .el-input__suffix {
    right: 10px;
}
::v-deep .ck.ck-content.ck-editor__editable {
  min-height: 500px;
}
</style>