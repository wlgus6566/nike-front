<template>
    <div>
        <BtnArea
            @goToList="goToList"
            @delete="deleteReport"
            @edit="modifyReport"
        />
        <div class="folder-wrap">
            <h2 class="folder-title">
                {{ reportDetailData.reportName }}
            </h2>
            <div class="inner">
                <p class="folder-desc">
                    {{ reportDetailData.nickname }}
                </p>
                <span class="folder-date">
                    {{
                        $moment(reportDetailData.registrationDt).format(
                            'YYYY.MM.DD'
                        )
                    }}
                </span>
            </div>
        </div>

        <div class="feedback-wrap">
            <strong class="title">FEEDBACK</strong>
            <template v-if="answerList">
                <FeedbackList
                    :answerList="answerList"
                    :userId="userId"
                    v-if="answerList.length"
                    @reportAnswerDelete="reportAnswerDelete"
                />
            </template>
            <Loading
                class="list-loading"
                :width="172"
                :height="172"
                v-if="loadingData"
            />
            <div class="textarea">
                <textarea
                    v-model="answerData.answerContents"
                    placeholder="댓글을 입력해주세요."
                ></textarea>
                <button
                    type="button"
                    class="btn-form-gray"
                    @click="addReportAnswerData"
                >
                    댓글등록
                </button>
            </div>
        </div>
        <div class="sorting-list-wrap">
            <SortingList
                :sectionCode="sectionCode"
                @sectionCodeChange="sectionCodeChange"
            />
        </div>
        <ReportItem
            :reportFileData="reportFileData"
            :checkAll="checkAll"
            :reportFileListTotal="reportFileListTotal"
            :checkContentsFileList="checkContentsFileList"
            @allCheckFn="allCheckFn"
            @checkContentsFile="checkContentsFile"
            @addReportBasket="addReportBasket"
        />
    </div>
</template>
<script>
import {
    getReportDetail,
    delReport,
    getAnswerList,
    postAnswerData,
    deleteAnswerList,
    postReportBasket,
    getReportFile,
} from '@/api/report';
import BtnArea from '@/components/asset-view/btn-area.vue';
import Loading from '@/components/loading';
import FeedbackList from '@/components/feedback-list';
import SortingList from '@/components/asset-view/sorting-list.vue';
import ReportItem from '@/components/report-view/report-Item.vue';
import { getUserIdFromCookie } from '@/utils/cookies';
export default {
    name: 'detail-view',
    data() {
        return {
            totalPage: null,
            loadingData: false,
            page: 0,
            itemLength: 10,
            size: 9999,
            checkAll: false,
            reportFileListTotal: 0,
            checkContentsFileList: [],
            reportDetailData: {},
            answerList: {},
            answerData: {
                answerContents: null,
                reportSeq: null,
            },
            reportFileData: {},
            sectionCode: {
                listSortOptions: [
                    {
                        value: 'FILE',
                        title: 'FILE',
                    },
                ],
                value: 'FILE',
            },
        };
    },
    components: {
        BtnArea,
        FeedbackList,
        Loading,
        SortingList,
        ReportItem,
    },
    computed: {
        userId() {
            return this.$store.state.user || getUserIdFromCookie();
        },
    },
    created() {
        this.reportDetailView();
        this.reportAnswerList();
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        if (this.$store.state.reload) {
            this.$store.dispatch('getReportListBasket');
            this.reportDetailView();
            this.reportAnswerList();
            this.initFetchData();
            this.$store.commit('SET_RELOAD', false);
        }
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        goToList() {
            this.$router.push(`/report/management`);
        },
        //리포트 삭제
        async deleteReport() {
            if (
                !confirm(
                    '삭제 시 등록한 내용이 전부 삭제 됩니다. 삭제하시겠습니까?'
                )
            )
                return;
            if (!confirm('정말 삭제하시겠습니까?')) return;
            try {
                const response = await delReport(this.$route.params.id);
                this.$store.commit('SET_RELOAD', true);
                await this.$store.dispatch('getReportListBasket');
                if (response.data.success) {
                    await this.$router.push('/report/management');
                }
                console.log(response);
            } catch (error) {
                console.error(error);
            }
        },
        //리포트 수정
        modifyReport() {
            this.$router.push(`/report/modify/${this.$route.params.id}`);
        },
        // 파일 선택 담기
        async addReportBasket(seq) {
            try {
                await postReportBasket(seq);
                await this.$store.dispatch('getReportListBasket');
            } catch (error) {
                console.error(error);
            }
        },
        allCheckFn() {
            this.checkAll = !this.checkAll;
            if (this.checkAll) {
                this.reportFileData.forEach((el) => {
                    const indexOfChecked = this.checkContentsFileList.findIndex(
                        (elChecked) => elChecked === el.reportFileSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkContentsFileList.push(el.reportFileSeq);
                    }
                });
            } else {
                this.checkContentsFileList = [];
            }
        },
        checkContentsFile(seq) {
            const indexOfChecked = this.checkContentsFileList.findIndex(
                (el) => el === seq
            );
            if (indexOfChecked === -1) {
                this.checkContentsFileList.push(seq);
            } else {
                this.checkContentsFileList = this.checkContentsFileList.filter(
                    (el) => {
                        return el !== seq;
                    }
                );
            }
            this.checkAll =
                this.checkContentsFileList.length ===
                this.reportFileData.length;
        },
        sectionCodeChange(value) {
            this.sectionCode.value = value;
        },
        // 리포트 상세 데이터
        async reportDetailView() {
            try {
                const {
                    data: { data: response },
                } = await getReportDetail(this.$route.params.id);
                this.reportDetailData = response;
            } catch (error) {
                console.error(error);
            }
        },
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.reportFileData.length >= this.itemLength &&
                this.reportFileData.length !== 0
            ) {
                this.reportFileList(true);
            }
        },
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.contentsFileList = null;
            this.reportFileList();
        },
        // 리포트 파일 리스트
        async reportFileList(infinite) {
            this.loadingData = true;
            this.checkAll = false;
            this.checkContentsFileList = [];
            try {
                const {
                    data: { data: response },
                } = await getReportFile(this.$route.params.id, {
                    page: this.page,
                    size: this.size,
                });
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    this.reportFileData = this.reportFileData.concat(
                        response.content
                    );
                } else {
                    this.reportFileData = response.content;
                    this.reportFileListTotal = response.totalElements;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },

        // 리포트 댓글 목록 리스트
        async reportAnswerList() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getAnswerList(this.$route.params.id);
                this.answerList = response;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
        // 리포트 댓글 등록
        async addReportAnswerData() {
            console.log(1);
            try {
                await postAnswerData({
                    answerContents: this.answerData.answerContents,
                    reportSeq: this.reportDetailData.reportSeq,
                });
                alert('등록 되었습니다');
                await this.reportAnswerList();
                this.answerData.answerContents = null;
            } catch (error) {
                console.error(error);
            }
        },

        // 리포트 댓글 단건 삭제
        async reportAnswerDelete(seq) {
            const delAnswer = confirm('FEEDBACK을 삭제 하시겠습니까?');
            if (delAnswer) {
                try {
                    const response = await deleteAnswerList(seq);
                    console.log(response);
                    await this.reportAnswerList();
                } catch (error) {
                    console.error(error);
                }
            }
        },
    },
};
</script>
<style scoped></style>
