<template>
    <ul class="period-list">
        <li class="period-list-item" v-for="item in orderList" :key="item.orderGoodsSeq">
            <a href="#" class="top">
                <strong class="num">주문일 : {{ item.order.registrationDt }}</strong>
                <span class="link-txt">주문상세보기</span>
            </a>
            <div class="cont">
                <span class="thumbnail">
                    <img
                        :src="item.product.imageFileName"
                        :alt="item.product.imageFilePhysicalName"
                    />
                </span>
                <span class="info-box">
                    <strong class="title">{{ item.product.goodsName }}외 4개</strong>
                </span>
            </div>
        </li>
    </ul>
</template>

<script>
export default {
    props: ['orderList'],
    methods: {
        test() {
            console.log('a');
        },
    },
};
</script>

<style lang="scss" scoped></style>
