<template>
    <ul class="product-list">
        <li
            class="product-list-item"
            v-for="item in userProductListData"
            :key="item.goodsSeq"
        >
            <button
                type="button"
                @click="$emit('showDetailView', item.goodsSeq)"
            >
                <span class="thumbnail">
                    <img
                        :src="item.imageFilePhysicalName"
                        :alt="item.imageFileName"
                    />
                </span>
                <span class="info-box">
                    <strong class="title">{{ item.goodsName }}</strong>
                    <p class="txt">{{ item.goodsDescription }}</p>
                    <span class="desc-txt-box">
                        <p class="desc">{{ item.unitPrice }}</p>
                        <p class="desc">
                            최소수문수량
                            <em>{{ item.minimumOrderQuantity }}</em>
                            개
                        </p>
                        <p class="desc">{{ item.agencyName }}</p>
                    </span>
                </span>
            </button>
            <div class="cart-box">
                <button
                    type="button"
                    class="cart"
                    :class="cartActive(item.goodsSeq)"
                    @click="toggleProductBasket(item)"
                >
                    <span>카드담기</span>
                </button>
            </div>
        </li>
    </ul>
</template>
<script>
import { addProductBasket } from '@/utils/basket';

export default {
    name: 'index',
    data() {
        return {};
    },
    props: ['userProductListData'],
    created() {},
    computed: {
        basketList() {
            if (!!this.$store.state.basketListData) {
                return this.$store.state.basketListData.map((data) => {
                    return {
                        goodsSeq: data.goodsSeq,
                        goodsBasketSeq: data.goodsBasketSeq,
                    };
                });
            } else {
                return null;
            }
        },
    },
    mounted() {},
    methods: {
        cartActive(goodsSeq) {
            if (this.basketList) {
                const findIndex = this.basketList.findIndex((el) => {
                    return el.goodsSeq === goodsSeq;
                });
                if (findIndex !== -1) {
                    return 'active';
                }
            }
        },
        toggleProductBasket(item) {
            console.log(this.basketList);
            if (this.basketList) {
                const findIndex = this.basketList.findIndex((el) => {
                    return el.goodsSeq === item.goodsSeq;
                });
                if (findIndex === -1) {
                    addProductBasket(
                        item.goodsSeq,
                        item.minimumOrderQuantity,
                        false
                    );
                } else {
                    alert('이미 담긴 상품입니다.');
                }
            }
        },
    },
};
</script>
<style scoped>
/* product-list-item*/
.product-list-item {
    position: relative;
}
.product-list-item > button {
    z-index: 2;
    position: relative;
    display: block;
    width: 100%;
    text-align: left;
}
.product-list-item .thumbnail {
    position: relative;
    display: block;
    padding-top: 100%;
    overflow: hidden;
    background: url('../../assets/images/img-asset-none@2x.png') no-repeat
        center;
    background-size: 100%;
}
.product-list-item .thumbnail img {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    transform: translate(-50%, -50%);
}
.product-list-item .info-box {
    overflow: hidden;
    display: block;
    padding: 0 45px 0 5px;
}
.product-list-item .info-box .title {
    display: block;
    max-height: 48px;
    margin-top: 12px;
    font-size: 16px;
    line-height: 24px;
    color: #000;
    font-weight: bold;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-wrap: break-word;
}
.product-list-item .info-box .txt {
    display: block;
    max-height: 36px;
    margin-top: 8px;
    font-size: 12px;
    color: #555;
    line-height: 18px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-wrap: break-word;
}
/* IE 대응 말줄임 */
@media all and (-ms-high-contrast: none), (-ms-high-contrast: active) {
    /* IE10+ CSS */
    .product-list-item .info-box .title {
        position: relative;
        text-align: justify;
        margin-right: -1em;
        padding-right: 1em;
    }
    .product-list-item .info-box .title:before {
        content: '...';
        position: absolute;
        right: 0;
        bottom: 0;
    }
    .product-list-item .info-box .title:after {
        content: '';
        position: absolute;
        right: 0;
        width: 1em;
        height: 1em;
        margin-top: 0.2em;
        background: white;
    }

    .product-list-item .info-box .txt {
        position: relative;
        text-align: justify;
        margin-right: -1em;
        padding-right: 1em;
    }
    .product-list-item .info-box .txt:before {
        content: '...';
        position: absolute;
        right: 0;
        bottom: 0;
    }
    .product-list-item .info-box .txt:after {
        content: '';
        position: absolute;
        right: 0;
        width: 1em;
        height: 1em;
        margin-top: 0.2em;
        background: white;
    }
}

.product-list-item .info-box .desc-txt-box {
    overflow: hidden;
    display: block;
    margin-top: 8px;
}
.product-list-item .info-box .desc-txt-box .desc {
    display: block;
    font-size: 10px;
    line-height: 15px;
    color: #888;
}
.product-list-item .cart-box {
    position: absolute;
    top: 0%;
    left: 0;
    width: 100%;
    padding-top: 100%;
}
.product-list-item .cart-box .cart {
    z-index: 3;
    position: absolute;
    bottom: -36px;
    right: 0;
    width: 24px;
    height: 24px;
    background: url('../../assets/images/svg/icon-order-cart-off.svg') no-repeat
        center;
}
.product-list-item .cart-box .cart.active {
    background-image: url('../../assets/images/svg/icon-order-cart-on.svg');
}
.product-list-item .cart span {
    position: relative;
    display: block;
    overflow: hidden;
    text-indent: -9999px;
}
.product-list {
    display: flex;
    flex-wrap: wrap;
    margin-top: 20px;
}
.product-list li {
    flex-grow: 0;
    flex-basis: calc((100% - 60px) / 4);
    margin-left: 20px;
}
.product-list li:nth-child(4n + 1) {
    margin-left: 0;
}
.product-list li:nth-child(n + 5) {
    margin-top: 30px;
}
</style>
