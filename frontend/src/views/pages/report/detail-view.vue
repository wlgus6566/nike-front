<template>
    <div>
        <div class="btn-detail">
            <router-link to="/report/management" class="btn-list">
                <span>목록으로 가기</span>
            </router-link>
            <div class="btn-box">
                <button type="button" class="btn-o-white">
                    <span>삭제</span>
                </button>
                <button type="button" class="btn-o-white">
                    <span>수정</span>
                </button>
            </div>
        </div>

        <div class="folder-wrap">
            <h2 class="folder-title">계정명 없음</h2>
            <div class="inner">
                <p class="folder-desc">
                    {{ reportDetailData.reportName }}
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
            <template>
                <FeedbackList
                    :answerList="answerList"
                    v-if="answerList.length"
                    @reportAnswerDelete="reportAnswerDelete"
                />
                <template v-else>
                    <NoData>
                        <i class="icon-file"></i>
                        <p class="desc">등록된 댓글이 없습니다.</p>
                    </NoData>
                </template>
            </template>
            <Loading :loadingStyle="loadingStyle" v-if="loadingData" />
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
            <div class="btn-box">
                <button type="button" class="btn-s-lightgray-sm">
                    <span>선택 담기</span>
                </button>
            </div>
        </div>
        <fileItem
            :contentsFileList="reportDetailData.reportFileList"
            :checkAll="checkAll"
            :checkContentsFileList="checkContentsFileList"
            @allCheckFn="allCheckFn"
            @checkContentsFile="checkContentsFile"
        />
    </div>
</template>
<script>
import {
    getReportDetail,
    getAnswerList,
    postAnswerData,
    deleteAnswerList,
} from '@/api/report';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import FeedbackList from '@/components/feedback-list';
import SortingList from '@/components/asset-view/sorting-list.vue';
import fileItem from '@/components/asset-view/file-Item.vue';
export default {
    name: 'detail-view',
    data() {
        return {
            checkAll: false,
            checkContentsFileList: [],
            reportDetailData: {},
            answerList: {},
            answerData: {
                answerContents: null,
                reportSeq: null,
            },
            loadingData: false,
            loadingStyle: {
                width: this.width ? `${this.width}px` : '100%',
                height: this.height ? `${this.height}px` : '100%',
                overflow: 'hidden',
                margin: '0 auto',
            },
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
        FeedbackList,
        Loading,
        NoData,
        SortingList,
        fileItem,
    },
    created() {
        this.reportDetailView();
        this.reportAnswerList();
    },
    methods: {
        allCheckFn() {
            this.checkAll = !this.checkAll;
            if (this.checkAll) {
                this.reportDetailData.reportFileList.forEach((el) => {
                    const indexOfChecked = this.checkContentsFileList.findIndex(
                        (elChecked) => elChecked === el.contentsFileSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkContentsFileList.push(el.contentsFileSeq);
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
                this.reportDetailData.reportFileList.length;
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
                console.log(error);
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
                console.log(error);
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
                console.log(error);
            }
        },

        // 리포트 댓글 단건삭제
        async reportAnswerDelete(seq) {
            console.log(seq);
            try {
                const response = await deleteAnswerList(seq);
                console.log(response);
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
