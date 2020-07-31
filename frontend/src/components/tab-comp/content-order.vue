<template>
    <div class="aside-order">
        <el-scrollbar class="cart-item-wrap" :native="false">
            <template v-if="basketList">
                <transition-group
                    tag="ul"
                    class="cart-item-list"
                    v-if="basketList.length"
                    name="fade"
                >
                    <li
                        class="cart-item"
                        v-for="(item, index) in basketList"
                        :key="index"
                    >
                        <div class="thumbnail">
                            <img
                                :src="item.product.imageFilePhysicalName"
                                :alt="item.product.imageFileName"
                            />
                        </div>
                        <div class="info-box">
                            <p class="title">{{ item.product.goodsName }}</p>
                            <div class="quantity">
                                <el-input-number
                                    v-model="item.orderQuantity"
                                    @change="changeQuantity(item)"
                                    @focusout="changeQuantity(item)"
                                    :disabled="test"
                                    :min="item.product.minimumOrderQuantity"
                                />
                            </div>
                        </div>
                        <button
                            type="button"
                            class="del"
                            @click="deleteClick(item.goodsBasketSeq)"
                        >
                            <span>삭제</span>
                        </button>
                    </li>
                </transition-group>
                <NoData v-else>
                    <i class="icon-drop"></i>
                    <p class="txt">더욱 빠르게 파일 받기</p>
                    <p class="desc">
                        이곳에 끌어다 놓으면 파일을 바로<br />
                        다운받을 수 있어요.
                    </p>
                </NoData>
            </template>
        </el-scrollbar>
        <button
            type="button"
            class="btn-order"
            @click="showOrderSheet"
            :disabled="!basketList.length"
        >
            <span class="txt">ORDER</span>
        </button>
        <div class="total-price-wrap">
            <div class="total-price">
                <span class="key">총 예상 금액</span>
                <strong class="val">
                    <em>{{ totalPrice }}</em>
                    원
                </strong>
            </div>
            <p class="total-desc">
                * VAT 및 운송비, 기타, 운용비는 제외된 금액입니다.<br />
                (실제 세금계산서의 금액은 다를 수 있습니다.)
            </p>
        </div>
        <OrderSheet
            :visible.sync="visible.orderSheet"
            :basketList="basketList"
            :totalPrice="totalPrice"
            :orderComment="orderComment"
            :orderData="orderData"
            @orderSave="orderSave"
        />
    </div>
</template>
<script>
    import {addProductBasket, deleteBasketItem} from '@/utils/basket';
    import OrderSheet from '@/views/pages/product/order-sheet.vue';
    import NoData from '@/components/no-data';

    export default {
    name: 'OderItem',
    data() {
        return {
            test: false,
            visible: {
                orderSheet: false,
            },
        };
    },
    components: {
        OrderSheet,
        NoData,
    },

    computed: {
        basketList() {
            if (!!this.$store.state.basketListData) {
                return this.$store.state.basketListData;
            } else {
                return [];
            }
        },
        totalPrice() {
            if (this.basketList.length) {
                const quantityArr = this.basketList.map(
                    (el) => el.orderQuantity
                );
                const priceArr = this.basketList.map(
                    (el) => el.product.unitPrice
                );
                return quantityArr
                    .map((el, index) => el * priceArr[index])
                    .reduce((acc, cur) => acc + cur);
            } else {
                return 0;
            }
        },
    },
    mounted() {},
    methods: {
        showOrderSheet() {
            this.visible.orderSheet = true;
        },

        // 장바구니 삭제 api
        deleteClick(goodsBasketSeq) {
            deleteBasketItem(goodsBasketSeq);
        },

        // 최소수량
        async changeQuantity(item) {
            if (!this.test) {
                this.test = true;
                await addProductBasket(item.goodsSeq, item.orderQuantity);
                this.test = false;
            }
        },

        // 주문서 발송
        async orderSave() {
            try {
                const { data: response } = await postOrderSave({
                    goodsSeqList: this.orderSeq,
                    orderDescription: this.orderComment,
                    orderQuantityList: this.orderQuan,
                    totalAmount: this.totalPrice,
                });
                if (response.existMsg) {
                    await getExistMsg(response);
                } else {
                    this.visible.orderSheet = false;
                    //todo 장바구니 다건 삭제 추가
                    await this.$router.push('/order/complete');
                }
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped>
.aside-order {
    margin-top: 15px;
    overflow: hidden;
}

.cart-item-wrap {
    box-sizing: border-box;
    height: 360px;
    background: #eee;
    overflow: hidden;
}
.cart-item {
    position: relative;
    box-sizing: border-box;
    display: flex;
    padding: 10px 0;
    align-items: center;
    background: #eee;
}
.cart-item + .cart-item {
    border-top: 1px solid #e5e5e5;
}
.cart-item .thumbnail {
    width: 50px;
    height: 50px;
    margin-right: 10px;
    overflow: hidden;
    background: red;
}
.cart-item .thumbnail img {
    vertical-align: top;
}
.cart-item .del {
    position: absolute;
    top: 4px;
    right: -4px;
    display: block;
    width: 30px;
    height: 30px;
    background: url(../../assets/images/svg/icon-close-small.svg) no-repeat
        center;
}
.cart-item .del span {
    display: block;
    overflow: hidden;
    text-indent: -99999px;
}
.info-box .title {
    display: block;
    line-height: 18px;
    font-size: 12px;
    letter-spacing: -0.05px;
    color: #000;
}
.btn-order {
    position: relative;
    display: flex;
    width: 100%;
    height: 40px;
    border-radius: 0;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    color: #fff;
    background: #000;
}
.btn-order .txt {
    position: relative;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.btn-order:disabled {
    background: #ccc;
    cursor: auto;
}
.total-price-wrap {
    margin-top: 25px;
}
.total-price {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 24px;
}
.total-price .key {
    display: block;
    font-size: 14px;
    color: #555;
    font-weight: bold;
}
.total-price .val {
    display: block;
    font-size: 14px;
    color: #555;
    font-weight: normal;
}
.total-price .val em {
    display: inline-block;
    font-size: 20px;
    color: #000;
    letter-spacing: 0;
    font-weight: bold;
}
.total-desc {
    display: block;
    margin-top: 15px;
    text-align: right;
    font-size: 10px;
    color: #888;
}
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
