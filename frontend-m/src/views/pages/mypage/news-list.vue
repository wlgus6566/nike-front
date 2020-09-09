<template>
    <div>
        <div class="sorting-area">
            <p class="total">
                전체 <strong>({{ totalElements }})</strong>
            </p>
            <form action="#" @submit.prevent="searchInputActive">
                <div class="search-input" :class="{ active: isActive }">
                    <div class="input-box">
                        <input
                            type="search"
                            placeholder="검색어를 입력해주세요."
                            @keyup.enter="searchInputActive"
                            v-model="keyword"
                        />
                        <button type="button" class="btn-del" v-if="keyword" @click="keywordDel">삭제</button>
                        <button type="submit" class="search">
                            <span>검색</span>
                        </button>
                    </div>
                    <div class="btn-txt" @click="cancelSearch">
                        <span>취소</span>
                    </div>
                </div>
            </form>
        </div>
        <template v-if="newsData">
            <div class="news-list" v-if="newsData.length > 0">
                <div
                    class="news-list-item"
                    v-for="(item, index) in newsData"
                    :key="index"
                >
                    <a :href="`/mypage/news/detail/${item.noticeArticleSeq}`">
                        <span class="thumbnail">
                            <img :src="item.thumbnailFilePhysicalName" alt="" />
                        </span>
                        <span class="info-box">
                            <p class="title">{{ item.title }}</p>
                            <span class="date">{{ item.updateDt }}</span>
                        </span>
                    </a>
                </div>
            </div>
            <template v-else>
                <NoData v-if="keyword === ''">
                    <i class="icon-upload"></i>
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
        <template v-if="newsData">
            <Pagination
                v-if="newsData.length"
                :itemLength="itemLength"
                :pageCount="pageCount"
                :totalItem="totalElements"
                @handleCurrentChange="handleCurrentChange"
            />
        </template>
    </div>
</template>
<script>
import { getCustomerList } from '@/api/customer/';

export default {
    name: 'news-list',
    data() {
        return {
            reset:false,
            newsList: null,
            newsData: null,
            page: 0,
            pageCount: 5,
            itemLength: 10,
            keyword: '',
            totalElements: 0,
            isActive: false,
            loadingData: false,
        };
    },
    components: {
        Pagination: () => import('@/components/pagination/'),
        Loading: () => import('@/components/loading/'),
        NoData: () => import('@/components/no-data'),
    },
    mounted() {
        this.initListData();
    },
    methods: {
        keywordDel(){
          this.keyword = null;
          if(this.reset){
            this.initListData();
            this.reset = false
          }
        },
        initListData() {
            this.totalElements = 0;
            this.page = 0;
            this.newsData = null;
            this.getNewsList();
        },
        // 검색 취소
        cancelSearch() {
          this.isActive = false;
          this.keyword = null;
          this.initListData();
        },
        async getNewsList() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getCustomerList('NEWS', {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.keyword,
                });
                this.newsList = response;
                this.newsData = response.content;
                this.totalElements = response.totalElements;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        searchInputActive: function() {
            this.isActive = true;
            if(this.keyword){
              this.reset = true;
            }
            if (!!this.keyword) {
              this.page = 0;
              this.newsData = null;
              this.initListData();
            }
        },
        searchInputInactive: function() {
            this.isActive = false;
            this.keyword = '';
            this.initListData();
        },
        handleCurrentChange(val) {
            this.page = val;
            this.newsData = null;
            this.getNewsList();
        },
    },
};
</script>
<style scoped></style>
