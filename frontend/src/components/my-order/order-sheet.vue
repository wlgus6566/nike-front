<template>
    <el-dialog
        title=""
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
                    <h3 class="form-title mt0">ORDER</h3>
                    <hr class="hr-black mt10" />
                    <ul class="form-list-colunm">
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">주문자</span>
                            </div>
                            <div class="form-column">
                                <span class="form-val">
                                    {{
                                        orderDetailData.orderProductMapping[0]
                                            .product.agency.agencyName
                                    }}
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">주문일시</span>
                            </div>
                            <div class="form-column">
                                <span class="form-val">
                                    {{ orderDetailData.registrationDt }}
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">총 예상 금액</span>
                            </div>
                            <div class="form-column">
                                <span class="form-val">
                                    <em>{{ orderDetailData.totalAmount }}</em>
                                    원
                                </span>
                            </div>
                        </li>
                    </ul>
                    <hr class="hr-gray" />
                    <p class="form-desc">
                        * VAT 및 운송비,기타,운용비는 제외된 금액입니다. (실제
                        세금계산서의 금액은 다를 수 있습니다.)
                    </p>
                    <el-scrollbar class="sheet-list-wrap" :native="false">
                        <ul class="sheet-list">
                            <li
                                class="sheet-item"
                                v-for="item in orderDetailData.orderProductMapping"
                                :key="item.goodsSeq"
                            >
                                <span class="thumbnail">
                                    <img
                                        :src="
                                            item.product.imageFilePhysicalName
                                        "
                                        :alt="item.product.imageFileName"
                                    />
                                </span>
                                <span class="info-box">
                                    <strong class="title">
                                        {{ item.product.goodsName }}
                                    </strong>
                                    <p class="txt">
                                        {{ item.product.goodsDescription }}
                                    </p>
                                    <span class="desc-txt-box">
                                        <p class="desc">
                                            {{ item.product.agency.agencyName }}
                                        </p>
                                    </span>
                                </span>
                                <span class="quantity-txt">
                                    <!-- 주문수량  -->
                                    <em> {{ item.orderQuantity }}</em>
                                    개
                                </span>
                            </li>
                        </ul>
                    </el-scrollbar>
                    <div>
                        <h3 class="sub-title">COMMENT</h3>
                        <div class="mt10">
                            {{ orderDetailData.orderDescription }}
                        </div>
                    </div>
                    <p class="form-desc-red">
                        * 업무상 필요한 정보 외의 개인 연락처 정보를 기재하지
                        않도록 주의하시기 바랍니다.
                    </p>
                </div>
            </div>
        </el-scrollbar>

        <!--        <div slot="footer" class="dialog-footer">-->
        <!--            <el-button @click="$emit('update:visible', false)" type="info" round>-->
        <!--                확인-->
        <!--            </el-button>-->
        <!--        </div>-->
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {};
    },
    props: ['visible', 'orderDetailData'],
    created() {
        console.log(this.orderDetailData);
    },
    mounted() {},
    methods: {},
};
</script>
<style>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.modal-wrap .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
</style>
