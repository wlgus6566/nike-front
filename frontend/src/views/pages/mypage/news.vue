<template>
    <div>
        <h2 class="page-title">NEWS</h2>
        <div class="sorting-area">
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="newsDataContent">
            <newsList
                :newsData="newsDataContent"
                v-if="newsDataContent.length"
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
                <router-link to="/mypage/news/form" class="btn-form-gray">
                    <span>등록</span>
                </router-link>
            </div>
        </div>
        <Pagination
            v-if="newsDataContent.length"
            :itemLength="itemLength"
            :pageCount="pageCount"
            :totalItem="totalItem"
            @handleCurrentChange="handleCurrentChange"
        />
    </div>
</template>

<script>
import { getCustomerList } from '@/api/customer';

export default {
    name: 'news',
    data() {
        return {
            newsData: null,
            newsDataContent: [],
            page: 0,
            pageCount: 11,
            totalItem: 0,
            itemLength: 10,
            searchKeyword: '',
            loadingData: false,
        };
    },
    components: {
        newsList: () => import('@/components/news/list'),
        SearchInput: () => import('@/components/search-input/index'),
        NoData: () => import('@/components/no-data'),
        Loading: () => import('@/components/loading'),
        Pagination: () => import('@/components/pagination/'),
    },
    mounted() {
        this.getNewsList();
    },
    activated() {
        this.serchReset();
        if (this.$store.state.reload) {
            this.getNewsList();
            this.$store.commit('SET_RELOAD', false);
        }
    },
    methods: {
        //검색후 페이지 이동 리셋
        serchReset() {
            this.searchKeyword = '';
            this.searchSubmit(this.searchKeyword);
        },
        //뉴스 리스트
        async getNewsList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList(this.$route.meta.sectionCode, {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.searchKeyword,
                });
                console.log(response);
                this.newsData = response;
                this.newsDataContent = response.content;
                this.totalItem = this.newsData.totalElements;

                //게시물 번호 //총게시물 - (현재 페이지 * 한 페이지 게시물 수) -  index = number
                this.newsDataContent.forEach((el, index) => {
                    el.number =
                        this.totalItem - this.page * this.itemLength - index;
                });
            } catch (error) {
                console.error(error);
            }
        },
        //검색
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getNewsList();
        },
        // 페이징
        handleCurrentChange(val) {
            this.page = val;
            this.getNewsList();
        },
    },
};
</script>
<style scoped></style>
