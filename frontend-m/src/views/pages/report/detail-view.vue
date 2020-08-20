<template>
    <div>
        <div class="folder-wrap">
            <h2 class="folder-title" v-text="reportDetail.nickname"></h2>
            <div class="inner">
                <p class="folder-desc" v-text="reportDetail.reportName">
                    나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가
                    업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가
                    업데이트 되었습니다.
                </p>
                <span class="folder-date" v-text="reportDetail.updateDt">2020.01.01</span>
                <!--<p class="folder-memo">[중요!!!] 2020.10.10까지 시공 완료 필요함!</p>-->
            </div>
        </div>

        <div class="feedback-wrap">
            <strong class="title">FEEDBACK </strong>
            <ul class="feedback-list" >
                <li class="feedback-item" v-for="item in feedbackList" :key="item.answerSeq">
                    <p class="txt" v-text="item.answerContents">일산 라페스타점 점장님, 시공보고서 확인완료 했습니다.</p>
                    <div class="info">
                        <span class="name" v-text="item.nickname">NIKE MKT 01</span>
                        <span class="date" v-text="item.updateDt">2020. 06. 04.</span>
                    </div>
                    <button v-if="item.userId === loginUserId" type="button" class="del" @click="delFeedBack(item.answerSeq)"><span>삭제</span></button>
                </li>
            </ul>
            <div class="textarea">
                <textarea v-model="answerContents"></textarea>
                <button
                    type="button"
                    class="btn-form-gray"
                    @click="postFeedback()"
                >
                    댓글등록
                </button>
            </div>
        </div>

        <ul class="sorting-list">
            <li class="active">
                <div><span>FILE</span></div>
            </li>
            <!-- 탭일시
            <li>
                <button type="button"><span>FILE</span></button>
            </li>
             -->
        </ul>
        <ul class="file-item-list">
            <li class="file-item" v-for="item in fileList" :key="item.reportFileSeq">
                <a href="#" @click="fileDetailModal(item.reportFileSeq,item.filePhysicalName,item.fileName)" >
                    <span class="thumbnail">
                        <img v-if="item.fileContentType.search('image') > -1" :src="item.thumbnailFilePhysicalName" :alt="item.fileExtension" />
                        <!--<img v-else-if="item.fileExtension === 'PDF'" src="/assets/images/svg/icon-illust-file-pdf.svg" alt="PDF" />-->
                        <!--<img v-else-if="item.fileExtension === 'MOV'" src="/assets/images/svg/icon-illust-file-pdf.svg" alt="PDF" />
                        <img v-else-if="item.fileExtension === 'TTF'" src="/assets/images/svg/icon-illust-file-pdf.svg" alt="PDF" />
                        <img v-else-if="item.fileExtension === 'PDF'" src="/assets/images/svg/icon-illust-file-pdf.svg" alt="PDF" />
                        <img v-else-if="item.fileExtension === 'PDF'" src="/assets/images/svg/icon-illust-file-pdf.svg" alt="PDF" />-->
                    </span>
                    <span class="info-box">
                        <strong class="title" v-text="item.fileName"></strong>
                    </span>
                </a>
            </li>
        </ul>
        <fileDetailPopup :visible.sync="visible.modalEx" :filePopupFile="filePopupFile" :filePopupName="filePopupName" />
    </div>
</template>
<script>
    import {deleteReportFeedback, getReportDetail, getReportDetailFeedbackList, getReportDetailFileList, postReportFeedback} from "@/api/report";
    import {getUserIdFromCookie} from "@/utils/cookies"
    import fileDetailPopup from "@/views/pages/report/file-Detail-Popup";

    export default {
    name: 'detail-view',
    data() {
        return {
            loading: false,
            reportDetail : '',
            feedbackList : [],
            fileList : [],
            answerContents : "",
            fileListSize:10,
            fileListPage:0,
            fileListLast:false,
            loginUserId : getUserIdFromCookie(),
            visible: {
                modalEx: false,
            },
            filePopupFile:'',
            filePopupName:''
        }
    }
    , mounted() {
        this.fetchData();
    },
    components: {
        fileDetailPopup: fileDetailPopup,
    },
    methods: {
        fetchData() {
            this.getDetailData();
            this.getAnswerList();
            this.fileListPage = 0;
            this.getFileList();
        },
        async getDetailData() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getReportDetail(this.$route.params.id);
                this.reportDetail = response;
            } catch (error) {
                console.log(error);
            }
        },
        async getAnswerList() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getReportDetailFeedbackList(this.$route.params.id);
                this.feedbackList = response;
            } catch (error) {
                console.log(error);
            }
        },
        async getFileList() {
            this.loading = true;
            try {
                let data = {
                    page: this.fileListPage,
                    size: this.fileListSize,
                };
                const {
                    data: { data: response },
                } = await getReportDetailFileList(this.$route.params.id, data);
                this.fileList = response.content;
                this.fileListLast = response.last;
                console.log(this.fileList);
            } catch (error) {
                console.log(error);
            }
        },
        async postFeedback() {
            if (this.answerContents === '') {
                alert('FEEDBACK을 등록해 주세요');
                return;
            }
            let data = {
                answerContents: this.answerContents,
                reportSeq: this.reportDetail.reportSeq,
            };
            try {
                const {
                    data: { data: response },
                } = await postReportFeedback(data);
                this.fetchData();
                alert('등록 되었습니다.');
                this.answerContents = '';
            } catch (error) {
                console.log(error);
            }
        },
        async delFeedBack(id){
            if(confirm("FEEDBACK을 삭제 하시겠습니까?")) {
                try {
                    const {data: {data: response}} = await deleteReportFeedback(id);
                    this.fetchData();
                } catch (error) {
                    console.log(error);
                }
            }
        },
        fileDetailModal(reportFileSeq, filePhysicalName, fileName) {
            this.filePopupFile = filePhysicalName;
            this.filePopupName = fileName;
            this.visible.modalEx = true;
        }
    },
};
</script>
<style scoped></style>
