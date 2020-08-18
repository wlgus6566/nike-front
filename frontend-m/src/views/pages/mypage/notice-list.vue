<template>
    <div>
        <div class="sorting-area">
            <p class="total">전체 <strong>({{totalElements}})</strong></p>
            <div class="search-input" v-bind:class="{ active: isActive }">
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." @keyup.enter="searchInputActive" v-model="keyword"/>
                    <button type="button" class="search" @click="searchInputActive"><span>검색</span></button>
                </div>
                <div class="btn-txt" @click="searchInputInactive"><span>취소</span></div>
            </div>
        </div>
        <ul class="notice-list">
            <li v-for="item in noticeData">
                <a :href="`/mypage/notice/detail/${item.noticeArticleSeq}`">
                    <span class="label-noti" v-if="item.noticeYn === 'Y'">중요</span>
                    <span class="title">{{item.title}}</span>
                    <span class="data">{{item.updateDt}}</span>
                </a>
            </li>
        </ul>
        <Pagination
                v-if="noticeData.length"
                :itemLength="itemLength"
                :pageCount="pageCount"
                :totalItem="totalElements"
                @handleCurrentChange="handleCurrentChange"
        />
    </div>
</template>
<script>
import { getCustomerList } from '@/api/customer/';

export default {
    name: 'notice-list',
    data() {
        return {
            noticeList: {},
            noticeData: [],
            page: 0,
            pageCount: 11,
            itemLength: 10,
            keyword: '',
            totalElements: 0,
            isActive: false
        }
    },
    components: {
        Pagination: () => import('@/components/pagination/')
    },
    mounted() {
        this.getNoticeList();
    },
    methods: {
        async getNoticeList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList("NOTICE", {
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.keyword
                });
                this.noticeList = response;
                this.noticeData = response.content;
                this.totalElements = response.totalElements;
            } catch (error) {
                console.log(error);
            }
        },
        searchInputActive: function (event) {
            if (this.isActive) {
                this.getNoticeList();
            } else {
                this.isActive = true;
            }
        },
        searchInputInactive: function (event) {
            this.isActive = false;
            this.keyword = "";
            this.getNoticeList();
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getNoticeList();
        }
    }
};
</script>
<style scoped></style>
