<template>
    <div>
        <h2 class="page-title">
            <span class="ko">공지사항</span>
        </h2>
        <div class="sorting-area">
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="noticeDataContent">
            <NoticeList
                v-if="noticeDataContent.length"
                :noticeData="noticeDataContent"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-data"></i>
                    <p class="desc">등록된 데이터가 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
        <div class="btn-tbl-box">
            <div class="right">
                <router-link
                    to="/mypage/notice/form"
                    class="btn-form-gray"
                    v-if="folderAuthCheck('CREATE')"
                >
                    <span>등록</span>
                </router-link>
            </div>
        </div>
        <Pagination
            v-if="noticeDataContent.length"
            :itemLength="itemLength"
            :pageCount="pageCount"
            :totalItem="totalItem"
            @handleCurrentChange="handleCurrentChange"
        />
    </div>
</template>

<style scoped></style>

<script>
import { getCustomerList } from '@/api/customer';
import { authCheck } from '@/utils/authCheck';
export default {
    name: 'notice-list',
    mixins: [authCheck],
    data() {
        return {
            noticeData: null,
            noticeDataContent: [],
            page: 0,
            pageCount: 11,
            totalItem: 0,
            itemLength: 20,
            searchKeyword: '',
            loadingData: false,
        };
    },
    components: {
        NoticeList: () => import('@/components/notice/'),
        Pagination: () => import('@/components/pagination/'),
        SearchInput: () => import('@/components/search-input'),
        NoData: () => import('@/components/no-data'),
        Loading: () => import('@/components/loading'),
    },
    created() {},
    mounted() {
        this.getNoticeList();
    },
    activated() {
        //initializationData
        if (this.$store.state.reload) {
            this.getNoticeList();
            this.$store.commit('SET_RELOAD', false);
        }
    },
    methods: {
        //검색후 페이지 이동 리셋
        /* initializationData() {
            this.searchKeyword = '';
            this.searchSubmit(this.searchKeyword);
        },*/
        //검색
        searchSubmit(val) {
            //console.log(val);
            this.searchKeyword = val;
            this.getNoticeList();
        },
        // 페이징
        handleCurrentChange(val) {
            this.page = val;
            this.getNoticeList();
        },
        //공지사항 리스트
        async getNoticeList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList(this.$route.meta.sectionCode, {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.searchKeyword,
                });
                this.noticeData = response;
                this.noticeDataContent = response.content;
                this.totalItem = this.noticeData.totalElements;

                //게시물 번호 //총게시물 - (현재 페이지 * 한 페이지 게시물 수) -  index = number
                this.noticeDataContent.forEach((el, index) => {
                    el.number =
                        this.totalItem - this.page * this.itemLength - index;
                });
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>
