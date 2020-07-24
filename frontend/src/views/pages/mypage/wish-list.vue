<template>
    <div>
        <h2 class="page-title">
            <span class="ko">위시리스트</span>
        </h2>
        <div class="all-box">
            <!-- todo 전체선택 스크립트 작업 필요  -->
            <label class="check-label">
                <span class="checkbox">
                    <input type="checkbox" v-model="checkAll" v-on:change="allCheckFn" />
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>
            <p class="desc"><em>1</em>개의 파일이 선택됨</p>
            <!-- todo select 스크립트 작업 필요  -->
            <div class="btn-box">
                <button type="button" class="btn-s-lightgray-sm">
                    <i class="icon-cart"></i>
                    <span>선택 CART 담기</span>
                </button>
                <button type="button" class="btn-s-lightgray-sm">
                    <i class="icon-del"></i>
                    <span>선택삭제</span>
                </button>
            </div>
        </div>
        <!-- wish list -->
        <WishList
            :listData="wishListData"
            @wishDelete="wishDelete"
            @updateChecked="updateChecked"
        />
    </div>
</template>

<script>
import { getWishList, deleteWishList, deleteWishListAll } from '@/api/wish-list';
export default {
    name: 'wish-list',
    components: {
        WishList: () => import('@/components/wish-list/index'),
    },
    data() {
        return {
            wishListData: null,
            loadingData: false,
            page: 0,
            itemLength: 20,
            checkAll: false,
            //check: this.items.state,
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        updateChecked(value, seq) {
            console.log(value);
            // const idx = this.wishListData.findIndex(el => {
            //     return el.wishListSeq === seq;
            // });
            this.wishListData[seq].checked = value;
        },
        async fetchData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getWishList({
                    page: this.page,
                    size: this.itemLength,
                });
                this.wishListData = response.content;
                this.wishListData.forEach(el => {
                    el.checked = false;
                });
                this.loadingData = false;
                return;
            } catch (error) {
                console.log(error);
            }
        },
        async wishDelete(seq) {
            try {
                await deleteWishList(seq);
                this.fetchData();
                return;
            } catch (error) {
                console.log(error);
            }
        },
        allCheckFn() {
            this.wishListData.forEach(el => {
                el.checked = this.checkAll;
            });
        },
    },
};
</script>

<style></style>
