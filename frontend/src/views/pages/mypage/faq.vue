<template>
    <div>
        <h2 class="page-title">
            <span class="ko">자주 묻는 질문</span>
        </h2>

        <faqTab
            :faqTabData="faqTabData"
            :categoryCodeList="categoryCodeList"
            @tabFnc="tabFnc"
        />
        <faqList
            v-if="faqDataContent.length"
            :faqData="faqDataContent"
            :isActive="isActive"
            @edit="modifyRoute"
            @faqToggle="faqToggle"
            @delete="deleteBoard"
        />
        <NoData v-else />
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
        <div class="btn-tbl-box">
            <div class="right">
                <router-link to="/mypage/faq/form" class="btn-form-gray">
                    <span>등록</span>
                </router-link>
            </div>
        </div>
        <Pagination
            v-if="faqDataContent.length"
            :itemLength="itemLength"
            :pageCount="pageCount"
            :totalItem="totalItem"
            @handleCurrentChange="handleCurrentChange"
        />
    </div>
</template>

<script>
import { getCustomerList, deleteCustomer } from '@/api/customer';
import { getCode } from '@/api/code';

export default {
    name: 'faq',
    data() {
        return {
            faqData: null,
            faqDataContent: [],
            faqTabData: null,
            noticeArticleSeq: null,
            page: 0,
            pageCount: 11,
            totalItem: 0,
            itemLength: 10,
            loadingData: false,
            isActive: null,
            categoryCodeList: {
                listSortOptions: [{ value: '', label: 'ALL' }],
                value: '',
            },
            noticeArticleCategoryCode: null,
        };
    },
    components: {
        faqList: () => import('@/components/faq/list'),
        faqTab: () => import('@/components/faq/tab'),
        NoData: () => import('@/components/faq/nodata'),
        Pagination: () => import('@/components/pagination/'),
    },
    mounted() {
        this.getFaqList();
        this.getCategoryCode();
    },
    activated() {
        if (this.$store.state.reload) {
            this.getFaqList();
            this.$store.commit('SET_RELOAD', false);
        }
    },
    watch: {
        'categoryCodeList.value'() {
            this.getFaqList();
        },
    },
    methods: {
        //FAQ 리스트
        async getFaqList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList(this.$route.meta.sectionCode, {
                    page: this.page,
                    size: this.itemLength,
                    noticeArticleCategoryCode: this.categoryCodeList.value,
                });
                this.faqData = response;
                this.faqDataContent = response.content;
                this.totalItem = this.faqData.totalElements;
                //console.log(response);

                //게시물 번호 //총게시물 - (현재 페이지 * 한 페이지 게시물 수) -  index = number
                this.faqDataContent.forEach((el, index) => {
                    el.number =
                        this.totalItem - this.page * this.itemLength - index;
                });
            } catch (error) {
                console.log(error);
            }
        },
        //게시물 삭제
        async deleteBoard(id) {
            if (!confirm('삭제 하시겠습니까?')) {
                return false;
            }
            try {
                await deleteCustomer(this.noticeArticleSectionCode, id);
                await this.getFaqList();
            } catch (error) {
                console.log(error);
            }
        },

        //게시판 수정페이지 이동
        modifyRoute(seq) {
            this.$router.push(`/mypage/faq/modify/${seq}`);
        },

        //리스트 아코디언
        faqToggle(seq) {
            if (seq === this.isActive) {
                this.isActive = null;
            } else {
                this.isActive = seq;
            }
        },

        //Tab
        tabFnc(val) {
            this.categoryCodeList.value = val;
        },

        // 페이징
        handleCurrentChange(val) {
            this.page = val;
            this.getFaqList();
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
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
