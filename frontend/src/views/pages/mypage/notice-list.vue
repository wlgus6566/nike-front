<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
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
                    <i class="icon-file"></i>
                    <p class="desc">등록한 공지사항이 없습니다</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading v-if="loadingData" />
        <div class="btn-tbl-box">
            <div class="right">
                <router-link to="/mypage/notice/form" class="btn-form-gray">
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
import SearchInput from '@/components/search-input';

export default {
    name: 'notice-list',
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
        SearchInput,
        NoticeList: () => import('@/components/notice/'),
        Pagination: () => import('@/components/pagination/'),

        NoData: () => import('@/components/no-data'),
        Loading: () => import('@/components/loading'),
    },
    created() {},
    mounted() {
        this.getNoticeList();
    },
    activated() {
        if (this.$store.state.reload) {
            this.getNoticeList();
            this.$store.commit('SET_RELOAD', false);
        }
    },
    methods: {
        //검색
        searchSubmit(val) {
            console.log(val);
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
                console.log(error);
            }
        },
    },
};
</script>
