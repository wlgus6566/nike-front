<template>
    <div>
        <h2 class="page-title">
            <span class="ko">위시리스트</span>
        </h2>
        <div class="all-box type2" v-if="wishListData">
            <label class="check-label">
                <span class="checkbox">
                    <input
                        type="checkbox"
                        v-model="checkAll"
                        v-on:change="allCheckFn"
                    />
                    <i></i>
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
                    선택 CART 담기
                </button>
                <button
                    type="button"
                    class="btn-s-lightgray-sm"
                    @click="checkedWishDelete"
                >
                    <i class="icon-del"></i>
                    선택삭제
                </button>
            </div>
        </div>
        <!-- wish list -->
        <template v-if="wishListData">
            <WishList
                v-if="wishListData.length"
                :listData="wishListData"
                :checkWishItem="checkWishItem"
                :deleteLoading="deleteLoading"
                @addBasket="addBasket"
                @wishDelete="wishDelete"
                @checkedWish="checkedWish"
                @showDetailView="showDetailView"
            />
            <WishListNodata v-if="wishListData && wishListData.length === 0" />
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
        <detailView
            :visible.sync="visible.detailView"
            :productDetailData="productDetailData"
            @addWishList="addWishList"
        />
    </div>
</template>

<script>
import {
    deleteWishList,
    deleteWishListCheck,
    getWishList,
    postWishList,
} from '@/api/wish-list';
// import { postBasketSaveList } from '@/api/basket';
import { addBasketList, addProductBasket } from '@/utils/basket';

export default {
    name: 'wish-list',
    components: {
        WishList: () => import('@/components/wish-list/index'),
        WishListNodata: () => import('@/components/wish-list/nodata'),
        detailView: () => import('@/views/pages/product/detail-view'),
        Loading: () => import('@/components/loading'),
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
            //basketList: [],
            visible: {
                detailView: false,
            },
            productDetailData: {},
            //check: this.items.state,
        };
    },
    mounted() {
        this.fetchData();
        this.checkAll = false;
        this.checkWishItem = [];
    },
    computed: {
        basketList() {
            if (!!this.$store.state.basketListData) {
                return this.$store.state.basketListData;
            } else {
                return [];
            }
        },
    },
    methods: {
        // 상세 팝업
        showDetailView(goodsSeq) {
            this.visible.detailView = true;
            const findIndex = this.wishListData.findIndex(
                (el) => el.goodsSeq === goodsSeq
            );
            this.productDetailData = this.wishListData[findIndex].product;
        },
        // 위시리스트에 상품 추가
        async addWishList(goodsSeq) {
            try {
                const findIndex = this.wishListData.findIndex(
                    (el) => el.goodsSeq === goodsSeq.goodsSeq
                );
                if (findIndex == -1) {
                    await postWishList({
                        goodsSeq: goodsSeq.goodsSeq,
                    });
                    await this.getWishiList();
                    alert(
                        '위시리스트에 추가 되었습니다.\n위시리스트는 마이페이지에서 확인가능합니다.'
                    );
                } else {
                    alert('이미 담긴 상품입니다.');
                }
            } catch (error) {
                console.error(error);
            }
        },
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
                        (elChecked) => elChecked === el.goodsSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkWishItem.push(el.goodsSeq);
                    }
                });
            } else {
                this.checkWishItem = [];
            }
        },
        async checkedWishDelete() {
            if (this.checkWishItem.length !== 0) {
                if (!confirm('위시리스트에서 삭제 됩니다. 삭제하시겠습니까?')) {
                    return false;
                }
                this.deleteLoading = this.checkWishItem;
                try {
                    await deleteWishListCheck({
                        goodsSeqList: this.checkWishItem,
                    });
                    await this.fetchData();
                    this.checkWishItem.forEach((seq) => {
                        this.checkedWish(seq, true);
                    });
                    this.checkAll = false;
                    this.deleteLoading = [];
                } catch (error) {
                    console.error(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            } else {
                alert('하나 이상의 상품을 선택해 주세요.');
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
                console.error(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        async fetchData() {
            if (this.loadingData) return;
            try {
                const {
                    data: { data: response },
                } = await getWishList({
                    page: this.page,
                    size: this.itemLength,
                });
                this.wishListData = response.content;
                await this.$store.dispatch('basketList');
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
        async addBasket(item) {
            const basketItem = this.basketList.findIndex((el) => {
                return el.goodsSeq === item.goodsSeq;
            });
            if (basketItem === -1) {
                if (confirm('CART에 담으시겠습니까?')) {
                    await addProductBasket(
                        item.goodsSeq,
                        item.product.minimumOrderQuantity,
                        false
                    );
                    this.$router.push('/order');
                } else {
                    return false;
                }
            } else {
                alert('이미 담긴 상품입니다');
            }
        },
        async checkedWishBasket() {
            if (this.checkWishItem.length !== 0) {
                const basketItem = this.basketList.map((el) => {
                    return el.goodsSeq;
                });
                const basketCheck = this.checkWishItem.filter(function (seq) {
                    return basketItem.includes(seq);
                });
                if (basketCheck.length !== 0) {
                    alert(
                        'CART에 이미 담긴 상품을 제외한 나머지 상품만 추가됩니다.'
                    );
                }
                if (basketCheck.length === this.checkWishItem.length) return;
                if (confirm('CART에 담으시겠습니까?')) {
                    const goodsSeq = [];
                    const minimumOrder = [];
                    this.wishListData.forEach((el) => {
                        const indexOfChecked = this.checkWishItem.findIndex(
                            (item) => {
                                return item === el.goodsSeq;
                            }
                        );
                        if (indexOfChecked !== -1) {
                            goodsSeq.push(el.goodsSeq);
                            minimumOrder.push(el.product.minimumOrderQuantity);
                        }
                    });
                    await addBasketList(goodsSeq, minimumOrder);
                    this.checkWishItem = [];
                    this.$router.push('/order');
                } else {
                    return false;
                }
            } else {
                alert('하나 이상의 상품을 선택해 주세요.');
            }
        },
    },
    created() {},
};
</script>
