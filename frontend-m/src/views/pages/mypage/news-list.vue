<template>
    <div>
        <div class="sorting-area">
            <p class="total">전체 <strong>({{totalElements}})</strong></p>
            <div class="search-input" :class="{ active: isActive }">
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." @keyup.enter="searchInputActive" v-model="keyword"/>
                    <button type="button" class="search" @click="searchInputActive"><span>검색</span></button>
                </div>
                <div class="btn-txt" @click="searchInputInactive"><span>취소</span></div>
            </div>
        </div>
        <div class="news-list" v-if="newsData.length > 0">
            <div class="news-list-item" v-for="item in newsData">
                <a :href="`/mypage/news/detail/${item.noticeArticleSeq}`">
                    <span class="thumbnail">
                        <img :src="item.thumbnailFilePhysicalName" alt="">
                    </span>
                    <span class="info-box">
                        <p class="title">{{item.title}}</p>
                        <span class="date">{{item.updateDt}}</span>
                    </span>
                </a>
            </div>
        </div>
        <div class="no-data-wrap" v-else-if="newsData.length === 0 && keyword !== ''">
            <div class="no-data">
                <i class="icon-search"></i>
                <p class="desc">검색 결과가 없습니다.</p>
            </div>
        </div>
        <Pagination
                v-if="newsData.length"
                :itemLength="itemLength"
                :pageCount="pageCount"
                :totalItem="totalElements"
                @handleCurrentChange="handleCurrentChange"
        />
        <Loading v-if="loadingData" />
    </div>
</template>
<script>
import {getCustomerList} from "@/api/customer/";

export default {
    name: 'news-list',
    data() {
        return {
            newsList: {},
            newsData: [],
            page: 0,
            pageCount: 11,
            itemLength: 10,
            keyword: '',
            totalElements: 0,
            isActive: false,
            loadingData: false
        }
    },
    components: {
        Pagination: () => import('@/components/pagination/'),
        Loading: () => import('@/components/loading/')
    },
    mounted() {
        this.getNewsList();
    },
    methods: {
        async getNewsList() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getCustomerList("NEWS", {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.keyword
                });
                this.newsList = response;
                this.newsData = response.content;
                this.totalElements = response.totalElements;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        searchInputActive: function () {
            if (this.isActive) {
                this.getNewsList();
            } else {
                this.isActive = true;
            }
        },
        searchInputInactive: function () {
            this.isActive = false;
            this.keyword = "";
            this.getNewsList();
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getNewsList();
        }
    }
};
</script>
<style scoped></style>
