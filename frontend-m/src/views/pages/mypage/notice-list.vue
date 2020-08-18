<template>
    <div>
        <div class="sorting-area">
            <p class="total">전체 <strong>({{totalElements}})</strong></p>
            <div class="search-input" v-bind:class="{ active: isActive }"> <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." v-model="keyword"/>
                    <button type="button" class="search" v-on:click="searchInputActive"><span>검색</span></button>
                </div>
                <div class="btn-txt" v-on:click="searchInputInactive"><span>취소</span></div>
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
    </div>
</template>
<script>
import {getCustomerList} from '@/api/customer';

export default {
    name: 'notice-list',
    data() {
        return {
            noticeList: {},
            noticeData: [],
            pageNumber: 0,
            totalItem: 0,
            itemSize: 10,
            keyword: '',
            totalElements: 0,
            isActive: false
        }
    },
    mounted() {
        this.getNoticeList();
    },
    methods: {
        async getNoticeList() {
            console.log("this is getNoticeList");
            try {
                const {
                    data: { data: response },
                } = await getCustomerList("NOTICE", {
                    page: this.pageNumber,
                    size: this.itemSize,
                    keyword: this.keyword
                });
                this.noticeList = response;
                this.noticeData = response.content;
                this.totalElements = response.totalElements;
                console.log(this.noticeList);
                console.log(this.noticeData);
                console.log(this.totalElements);
            } catch (error) {
                console.log(error);
            }
        },
        searchInputActive: function (event) {
            console.log("searchInputActive");
            this.isActive = true;
        },
        searchInputInactive: function (event) {
            console.log("searchInputInactive");
            this.isActive = false;
        }
    }
};
</script>
<style scoped></style>
