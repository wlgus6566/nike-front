<template>
    <div>
        {{ checkWishItem }}
        {{ deleteLoading }}
        <h2 class="page-title">
            <span class="ko">위시리스트</span>
        </h2>
        <div class="all-box" v-if="wishListData.length !== 0">
            <!-- todo 전체선택 스크립트 작업 필요  -->
            <label class="check-label">
                <span class="checkbox">
                    <input type="checkbox" v-model="checkAll" v-on:change="allCheckFn" />
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>
            <p class="desc">
                <em>{{ checkWishItem.length }}</em
                >개의 파일이 선택됨
            </p>
            <div class="btn-box">
                <button type="button" class="btn-s-lightgray-sm">
                    <i class="icon-cart"></i>
                    <span>선택 CART 담기</span>
                </button>
                <button
                    type="button"
                    class="btn-s-lightgray-sm"
                    @click="checkedDelete(checkWishItem)"
                >
                    <i class="icon-del"></i>
                    <span>선택삭제</span>
                </button>
            </div>
        </div>
        <!-- wish list -->
        <WishList
            v-if="wishListData.length !== 0"
            :listData="wishListData"
            :checkWishItem="checkWishItem"
            :deleteLoading="deleteLoading"
            @wishDelete="checkedDelete"
            @checkedWish="checkedWish"
        />
        <WishListNodata v-else />
        <Loading v-if="loadingData" />
    </div>
</template>

<script>
import { getWishList, deleteWishList, deleteWishListAll } from '@/api/wish-list';
export default {
    name: 'wish-list',
    components: {
        WishList: () => import('@/components/wish-list/index'),
        WishListNodata: () => import('@/components/wish-list/nodata'),
        Loading: () => import('@/components/wish-list/loading'),
    },
    data() {
        return {
            wishListData: null,
            loadingData: false,
            page: 0,
            itemLength: 20,
            checkAll: false,
            checkWishItem: [],
            deleteLoading: [],
            //check: this.items.state,
        };
    },
    mounted() {
        this.fetchData(true);
    },
    methods: {
        checkedWish(seq, del) {
            const indexOfChecked = this.checkWishItem.findIndex(el => el === seq);
            if (indexOfChecked === -1 && !del) {
                this.checkWishItem.push(seq);
            } else {
                this.checkWishItem = this.checkWishItem.filter(el => {
                    return el !== seq;
                });
            }
            console.log(this.checkWishItem.length);
            console.log(this.wishListData.length);
            this.checkAll = this.checkWishItem.length === this.wishListData.length;
        },
        allCheckFn() {
            //시퀀스 배열을 받아옴
            if (this.checkAll) {
                this.wishListData.forEach(el => {
                    const indexOfChecked = this.checkWishItem.findIndex(
                        elChecked => elChecked === el.wishListSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkWishItem.push(el.wishListSeq);
                    }
                });
            } else {
                this.checkWishItem = [];
            }
        },
        checkedDelete(seqArr) {
            this.deleteLoading = seqArr;
            seqArr.forEach(el => {
                this.wishDelete(el);
            });
        },
        async fetchData(ddd) {
            if (ddd) this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getWishList({
                    page: this.page,
                    size: this.itemLength,
                });
                this.wishListData = response.content;
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
                this.checkedWish(seq, true);
                return;
            } catch (error) {
                console.log(error);
            }
        },
        async wishDeleteAll() {
            try {
                await deleteWishListAll();
                this.fetchData();
                return;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>

<style></style>
