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
        <template v-if="noticeData">
            <ul class="notice-list" v-if="noticeData.length > 0">
                <li v-for="(item, index) in noticeData" :key="index">
                    <a :href="`/mypage/notice/detail/${item.noticeArticleSeq}`">
                        <span class="label-noti" v-if="item.noticeYn === 'Y'"
                            >중요</span
                        >
                        <span class="title">{{ item.title }}</span>
                        <span class="data">{{ item.updateDt }}</span>
                    </a>
                </li>
            </ul>
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
        <template v-if="noticeData">
            <Pagination
                v-if="noticeData.length"
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
    name: 'notice-list',
    data() {
        return {
            reset:false,
            noticeList: {},
            noticeData: null,
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
            this.noticeData = null;
            this.getNoticeList();
        },
        // 검색 취소
        cancelSearch() {
          this.isActive = false;
          this.keyword = null;
          this.initListData();
        },
        async getNoticeList() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getCustomerList('NOTICE', {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.keyword,
                });
                this.noticeList = response;
                this.noticeData = response.content;
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
            this.noticeData = null;
            this.getNoticeList();
        },
    },
};
</script>
<style scoped></style>
