<template>
    <div>
        <h2 class="page-title">
            <span class="ko">공지사항</span>
        </h2>
        <form @submit.prevent="submitData">
            <h3 class="form-title mt20">등록/수정</h3>
            <hr class="hr-black" />
            <ul class="form-list">
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title">상단 고정 여부</span>
                    </div>
                    <div class="form-column">
                        <span class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="Y"
                                    checked
                                    @click="noticeCheck($event, true)"
                                    v-model="noticeDetail.noticeYn"
                                />
                                <i></i>
                                <span class="txt">고정</span>
                            </span>
                        </span>
                        <span class="check-label">
                            <span class="radio">
                                <input
                                    type="radio"
                                    value="N"
                                    v-model="noticeDetail.noticeYn"
                                />
                                <i></i>
                                <span class="txt">비고정</span>
                            </span>
                        </span>
                    </div>
                    <div class="form-column agr">
                        <span class="form-desc"
                            >* 목록 상단에 고정되며 [중요] 항목으로
                            표시됩니다.</span
                        >
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">제목</label>
                    </div>
                    <div class="form-column">
                        <input
                            type="text"
                            v-model="noticeDetail.title"
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
                            v-model="noticeDetail.contents"
                            :config="editorConfig"
                            style="width: 100%;">
                        </ckeditor>
                      </div>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">파일 찾기</label>
                    </div>
                    <div class="form-column">
                        <div class="upload-file-box">
                            <!--active-->
                            <ul
                                class="upload-file-list"
                                :class="{
                                    'is-file': uploadFileList.length > 0,
                                }"
                            >
                                <li
                                    v-for="item in noticeDetail.fileList"
                                    :key="item.fileOrder"
                                >
                                    <label>
                                        <span class="checkbox">
                                            <input
                                                type="checkbox"
                                                :value="item.fileOrder"
                                                v-model="checkedFile"
                                            />
                                            <i></i>
                                        </span>
                                        <span class="txt">
                                            {{ item.fileName }}
                                        </span>
                                    </label>
                                </li>
                            </ul>
                            <div class="btn-box">
                                <div class="fine-file">
                                    <span class="btn-form-gray"
                                        ><span>찾기</span></span
                                    >
                                    <input
                                        type="file"
                                        ref="fileInput"
                                        multiple
                                        @change="uploadIptChange"
                                    />
                                </div>
                                <button
                                    type="button"
                                    class="btn-form"
                                    @click="removeFile"
                                >
                                    <span>삭제</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <router-link to="/mypage/notice" class="btn-s-white">
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
import {
    getCustomerDetail,
    getCustomerList,
    postNotice,
    putNotice,
} from '@/api/customer';

import { getAuthFromCookie } from '@/utils/cookies';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';

// CKEditor5 설정
import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor.js';

import Alignment from '@ckeditor/ckeditor5-alignment/src/alignment.js';
import AutoFormat from '@ckeditor/ckeditor5-autoformat/src/autoformat.js';
import BlockQuote from '@ckeditor/ckeditor5-block-quote/src/blockquote.js';
import Bold from '@ckeditor/ckeditor5-basic-styles/src/bold.js';
import CKFinder from '@ckeditor/ckeditor5-ckfinder/src/ckfinder.js';
import CKFinderUploadAdapter from '@ckeditor/ckeditor5-adapter-ckfinder/src/uploadadapter.js';
import Font from "@ckeditor/ckeditor5-font/src/font";
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
    data() {
        return {
            //noticeArticleSectionCode: 'NOTICE',
            //noticeYn: null,
            useYn: 'Y',
            noticeYnLength: [],
            noticeDetail: {
                title: '',
                contents: '',
                noticeYn: 'N',
                noticeArticleSeq: null,
                fileList: [],
            },
            checkedFile: [],
            uploadFileList: [],
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
                IndentBlock,
                Font
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
              },
              fontSize: {
                options: [
                  9,
                  10,
                  11,
                  12,
                  13,
                  14,
                  15,
                  16,
                  17,
                  18,
                  19,
                  20,
                  21,
                  23,
                  25,
                  27,
                  29,
                  31,
                  33,
                  35
                ]
              },
          }
        };
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
    components: {},
    watch: {
        'noticeDetail.noticeYn'(val) {
            if (!val) {
                this.noticeDetail.noticeYn = 'N';
            }
        },
        $route() {
            this.$destroy();
        },
    },
    mounted() {
        this.getNoticeList();
        //this.noticeDetail.noticeYn = 'N';
        if (this.$route.meta.modify) {
            this.getNoticeDetail();
        }
    },
    activated() {
        this.$store.state.saveFolder = false;
        this.getNoticeList();
        this.detailDataReset();
    },
    methods: {
        //file 업로드
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter(item => {
                return this.noticeDetail.fileList.every(el => {
                    return (
                        item.name !== el.fileName && item.size !== el.fileSize
                    );
                });
            });
            console.log(mergeArray.length);
            if (mergeArray.length + this.uploadFileList.length > 5) {
                alert('첨부파일은 최대 5개까지 등록 가능합니다.');
                if (this.uploadFileList.length === 5) return;
                let maxNum = 5;
                if (this.uploadFileList.length > 0) {
                    maxNum = 5 - this.uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach(el => {
                this.noticeDetail.fileList.push({
                    fileOrder: this.noticeDetail.fileList.length,
                    fileName: el.name,
                    fileSize: el.size,
                    fileContentType: el.type,
                    progress: 0,
                });
            });
            this.uploadFileList = this.uploadFileList.concat(mergeArray);
        },
        async uploadFiles() {
            bus.$emit('pageLoading', true);
            await Promise.all(
                this.uploadFileList.map(async el => {
                    try {
                        const formData = new FormData();

                        formData.append('uploadFile', el);
                        const config = {
                            onUploadProgress: progressEvent => {
                                const percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                this.noticeDetail.fileList.forEach(item => {
                                    if (
                                        item.fileName === el.name &&
                                        item.fileContentType === el.type &&
                                        item.fileSize === el.size
                                    ) {
                                        item.progress = percentCompleted;
                                    }
                                });
                            },
                        };
                        formData.append('menuCode', 'notice');
                        const response = await fileUpLoad(formData, config);
                        //console.log(response);
                        if (response.existMsg) {
                            alert(response.msg);
                        }
                        this.noticeDetail.fileList.forEach(
                            (item, idx, array) => {
                                if (
                                    item.fileName === el.name &&
                                    item.fileContentType === el.type &&
                                    item.fileSize === el.size
                                ) {
                                    array[idx] = {
                                        progress: 100,
                                        fileOrder: idx,
                                        ...response.data.data,
                                    };
                                }
                            }
                        );
                    } catch (error) {
                        console.log(error);
                    }
                })
            );
            if (this.$route.meta.modify) {
                await this.modifyData();
            } else {
                await this.saveData();
            }

            this.uploadFileList = [];
        },
        removeFile() {
            this.checkedFile.forEach(a => {
                this.noticeDetail.fileList = this.noticeDetail.fileList.filter(
                    b => b.fileOrder !== a
                );
            });
            this.checkedFile = [];
            this.fileOrderSet();
        },
        fileOrderSet() {
            this.uploadFileList = this.uploadFileList.filter(a => {
                return this.noticeDetail.fileList.some(b => {
                    return (
                        a.name === b.fileName &&
                        a.type === b.fileContentType &&
                        a.size === b.fileSize
                    );
                });
            });
            this.noticeDetail.fileList.forEach((el, index) => {
                el.fileOrder = index;
            });
        },
        submitData() {
            if (this.$route.meta.modify) {
                this.$store.state.saveFolder = false;
                if (!confirm('수정하시겠습니까?')) {
                    return false;
                }
                if (this.noticeDetail.fileList.length > 0) {
                    this.uploadFiles();
                } else {
                    this.modifyData();
                }
            } else {
                if (!confirm('저장하시겠습니까?')) {
                    return false;
                }
                if (this.noticeDetail.fileList.length > 0) {
                    this.uploadFiles();
                } else {
                    this.saveData();
                }
            }
        },
        async saveData() {
            try {
                const response = await postNotice({
                    contents: this.noticeDetail.contents,
                    fileList: this.noticeDetail.fileList,
                    noticeArticleSectionCode: 'NOTICE',
                    noticeYn: this.noticeDetail.noticeYn,
                    title: this.noticeDetail.title,
                    useYn: this.useYn,
                });
                this.$store.state.saveFolder = true;
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.detailDataReset();
                    this.$router.push('/mypage/notice');
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
            if (this.$route.meta.modify) {
                try {
                    const response = await putNotice(this.$route.params.id, {
                        contents: this.noticeDetail.contents,
                        fileList: this.noticeDetail.fileList,
                        noticeArticleSectionCode: 'NOTICE',
                        noticeArticleSeq: this.noticeArticleSeq,
                        noticeYn: this.noticeDetail.noticeYn,
                        title: this.noticeDetail.title,
                        useYn: this.useYn,
                    });
                    this.$store.state.saveFolder = true;
                    this.$store.commit('SET_RELOAD', true);
                    if (response.data.success) {
                        this.detailDataReset();
                        this.$router.push('/mypage/notice');
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    this.$store.state.saveFolder = false;
                    console.error(error);
                }
            }
        },

        //공지사항 리스트
        async getNoticeList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList(this.$route.meta.sectionCode, {
                    page: 0,
                    size: 20,
                    keyword: '',
                });
                this.noticeYnLength = response.content.filter(el => {
                    //console.log(el.noticeArticleSeq);
                    //console.log(this.$route.params.id * 1);
                    return (
                        el.noticeYn === 'Y' &&
                        el.noticeArticleSeq !== this.$route.params.id * 1
                    );
                });
                //console.log(this.noticeYnLength);
            } catch (error) {
                console.error(error);
            }
        },

        //공지사항 상세
        async getNoticeDetail() {
            //console.log(this.$route.params.id);
            try {
                const {
                    data: { data: response },
                } = await getCustomerDetail(
                    this.$route.meta.sectionCode,
                    this.$route.params.id
                );
                //console.log(response);
                this.noticeDetail = response;
                this.noticeArticleSeq = response.noticeArticleSeq;
                this.fileOrderSet();
            } catch (error) {
                console.error(error);
            }
        },

        //중요공지 체크
        noticeCheck(e, YN) {
            if (YN && this.noticeYnLength.length >= 3) {
                e.preventDefault();
                this.noticeDetail.noticeYn = 'N';
                alert('상단 고정은 최대 3개까지만 설정가능합니다.');
            }
        },

        //작성 취소
        cancelBack() {
            this.$router.go(-1);
        },
        detailDataReset() {
            this.noticeDetail.title = '';
            this.noticeDetail.contents = '';
            this.noticeDetail.noticeYn = null;
        },
        onEditorInput: function(e) {
            this.noticeDetail.contents = e.editor._.editable.$.innerHTML;
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
::v-deep .check-label {
    margin-right: 50px;
}
::v-deep .ck.ck-content.ck-editor__editable {
  min-height: 400px;
}
</style>
