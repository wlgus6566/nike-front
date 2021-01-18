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
        filebrowserImageUploadUrl: '',
        fileTools_requestHeaders: {
          Authorization: '',
        },
        extraPlugins: 'font, colorbutton',
        fontSize_sizes: '8px/8px;8pt/8pt;9px/9px;9pt/9pt;10px/10px;10pt/10pt;11px/11px;11pt/11pt;12px/12px;12pt/12pt;13px/13px;13pt/13pt;14px/14px;14pt/14pt;15px/15px;15pt/15pt;16px/16px;16pt/16pt;18px/18px;18pt/18pt;20px/20px;20pt/20pt;22px/22px;/22pt/22pt;24px/24px;24pt/24pt;26px/26px;26pt/26pt;28px/28px;28pt/28pt;36px/36px;36pt/36pt;48px/48px;48pt/48pt;72px/72px;72pt/72pt;',
        toolbarGroups: [
          { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
          { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
          { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
          { name: 'forms', groups: [ 'forms' ] },
          '/',
          { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
          { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
          { name: 'links', groups: [ 'links' ] },
          { name: 'insert', groups: [ 'insert' ] },
          '/',
          { name: 'styles', groups: [ 'styles' ] },
          { name: 'colors', groups: [ 'colors' ] },
          { name: 'tools', groups: [ 'tools' ] },
          { name: 'others', groups: [ 'others' ] }
        ]

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
