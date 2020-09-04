<template>
    <el-dialog
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div class="order-detail-view">
                    <div class="thumbnail">
                        <img
                            :src="productDetailData.imageFilePhysicalName"
                            :alt="productDetailData.imageFileName"
                        />
                    </div>
                    <div class="info-box">
                        <div class="title-box">
                            <strong class="title">
                                {{ productDetailData.goodsName }}
                            </strong>
                            <p class="txt">
                                {{ productDetailData.goodsDescription }}
                            </p>
                            <div class="desc-txt-box">
                                <span class="desc-txt">
                                    <em> {{ productDetailData.unitPrice }}</em>
                                    원
                                </span>
                                <span class="desc-txt">
                                    최소주문수량
                                    <em>{{
                                        productDetailData.minimumOrderQuantity
                                    }}</em>
                                    개
                                </span>
                            </div>
                        </div>
                        <div class="btn-box">
                            <button
                                type="button"
                                class="btn-s"
                                @click="$emit('addWishList', productDetailData)"
                            >
                                <span>위시리스트 담기</span>
                            </button>
                            <button
                                type="button"
                                class="btn-s-black"
                                @click="toggleProductBasket(productDetailData)"
                            >
                                <span>CART 담기</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
import { addProductBasket } from '@/utils/basket';

export default {
    data() {
        return {};
    },
    props: {
        visible: Boolean,
        receipt: Object,
        productDetailData: Object,
    },
    mounted() {},
    computed: {
        basketList() {
            return this.$store.state.basketListData.map((data) => {
                return {
                    goodsSeq: data.goodsSeq,
                    goodsBasketSeq: data.goodsBasketSeq,
                };
            });
        },
    },
    methods: {
        toggleProductBasket(item) {
            const findIndex = this.basketList.findIndex((el) => {
                return el.goodsSeq === item.goodsSeq;
            });
            if (findIndex === -1) {
                addProductBasket(
                    item.goodsSeq,
                    item.minimumOrderQuantity,
                    true
                );
            } else {
                alert('이미 담긴 상품입니다.');
            }
        },
    },
};
</script>
<style scoped>
::v-deep .modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
::v-deep .modal-wrap .el-dialog {
    margin: 0 !important;
    max-width: 600px;
}
::v-deep .modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
</style>
