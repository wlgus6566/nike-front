<template>
    <div>
        <h2 class="page-title">
            <span class="ko">위시리스트</span>
        </h2>
        <div class="all-box type2" v-if="wishListData">
            <!-- todo 전체선택 스크립트 작업 필요  -->
            <label class="check-label">
                <span class="checkbox">
                    <input
                        type="checkbox"
                        v-model="checkAll"
                        v-on:change="allCheckFn"
                    />
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>

            <p class="desc">
                <span class="fc-black" v-if="checkAll">
                    전체 파일이 선택됨
                </span>
                <span v-else :class="{ 'fc-black': checkWishItem.length > 0 }">
                    <em>{{ checkWishItem.length }}</em>
                    개의 파일이 선택됨
                </span>
            </p>
            <div class="btn-box">
                <button
                    type="button"
                    class="btn-s-lightgray-sm"
                    @click="checkedWishBasket"
                >
                    <i class="icon-cart"></i>
                    <span>선택 CART 담기</span>
                </button>
                <button
                    type="button"
                    class="btn-s-lightgray-sm"
                    @click="checkedWishDelete"
                >
                    <i class="icon-del"></i>
                    <span>선택삭제</span>
                </button>
            </div>
        </div>
        <!-- wish list -->

        <WishList
            v-if="wishListData"
            :listData="wishListData"
            :checkWishItem="checkWishItem"
            :deleteLoading="deleteLoading"
            @addBasket="addBasket"
            @wishDelete="wishDelete"
            @checkedWish="checkedWish"
        />
        <WishListNodata v-if="wishListData && wishListData.length === 0" />
        <Loading v-if="loadingData" />
    </div>
</template>

<script>
    import {deleteWishList, deleteWishListCheck, getWishList,} from '@/api/wish-list';
    // import { postBasketSaveList } from '@/api/basket';
    import {addBasketList, addProductBasket} from '@/utils/basket';

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
            basketList: [],
            //check: this.items.state,
        };
    },
    activated() {
        this.fetchData(true);
    },
    methods: {
        checkedWish(seq, del) {
            const indexOfChecked = this.checkWishItem.findIndex(
                (el) => el === seq
            );
            if (!del && indexOfChecked === -1) {
                this.checkWishItem.push(seq);
            } else {
                this.checkWishItem = this.checkWishItem.filter((el) => {
                    return el !== seq;
                });
            }
            this.checkAll =
                this.checkWishItem.length === this.wishListData.length;
        },
        allCheckFn() {
            if (this.checkAll) {
                this.wishListData.forEach((el) => {
                    const indexOfChecked = this.checkWishItem.findIndex(
                        (elChecked) => elChecked === el.wishListSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkWishItem.push(el.wishListSeq);
                    }
                });
            } else {
                this.checkWishItem = [];
            }
        },
        async checkedWishDelete() {
            if (!confirm('위시리스트에서 삭제 됩니다. 삭제하시겠습니까?')) {
                return false;
            }
            this.deleteLoading = this.checkWishItem;
            try {
                await deleteWishListCheck({
                    wishListSeqList: this.checkWishItem,
                });
                await this.fetchData();
                this.checkWishItem.forEach((seq) => {
                    this.checkedWish(seq, true);
                });
                this.deleteLoading = [];
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        async wishDelete(seq) {
            if (!confirm('위시리스트에서 삭제 됩니다. 삭제하시겠습니까?')) {
                return false;
            }
            this.deleteLoading.push(seq);
            try {
                await deleteWishList(seq);
                await this.fetchData();
                this.checkedWish(seq, true);
                this.deleteLoading = [];
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        async fetchData(mounted) {
            if (mounted) this.loadingData = true;
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
        async addBasket(item) {
            if (confirm('CART에 담으시겠습니까?')) {
                await addProductBasket(
                    item.goodsSeq,
                    item.product.minimumOrderQuantity
                );
                this.$router.push('/order');
            } else {
                return false;
            }
        },
        async checkedWishBasket() {
            if (confirm('CART에 담으시겠습니까?')) {
                const goodsSeq = [];
                const minimumOrder = [];
                this.wishListData.forEach((el) => {
                    const indexOfChecked = this.checkWishItem.findIndex(
                        (item) => {
                            return item === el.wishListSeq;
                        }
                    );
                    if (indexOfChecked !== -1) {
                        goodsSeq.push(el.goodsSeq);
                        minimumOrder.push(el.product.minimumOrderQuantity);
                    }
                });
                await addBasketList(goodsSeq, minimumOrder);
                this.$router.push('/order');
            } else {
                return false;
            }
        },
    },
    created() {
        this.$store.dispatch('basketList');
    },
};
</script>

<style></style>
