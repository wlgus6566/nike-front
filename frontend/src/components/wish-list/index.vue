<template>
    <ul class="wish-list">
        <li class="wish-list-item" v-for="(item, index) in listData" :key="index">
            <span class="checkbox">
                {{ item.checked }}
                <input
                    type="checkbox"
                    v-model="item.checked"
                    @change="$emit('updateChecked', $event.target.checked, index)"
                />
                <span></span>
            </span>
            <a href="#" class="title-box">
                <span class="thumbnail">
                    <img
                        :src="item.product.imageFilePhysicalName"
                        :alt="item.product.imageFileName"
                    />
                </span>
                <span class="info-box">
                    <strong class="title">{{ item.product.goodsName }}</strong>
                    <span class="txt">{{ item.product.goodsDescription }}</span>
                    <span class="desc-txt-box">
                        <span class="desc-txt">{{ item.product.unitPrice }} 원</span>
                        <span class="desc-txt">{{ item.product.agency.agencyName }}</span>
                    </span>
                </span>
            </a>
            <div class="quantity-box">
                <span class="title">최소주문수량</span>
                <span class="num">
                    <em>{{ item.product.minimumOrderQuantity }}</em
                    >개
                </span>
            </div>
            <div class="btn-box">
                <button type="button" class="btn-s-black-sm">
                    <span>CART</span>
                </button>
                <button
                    type="button"
                    class="delete"
                    v-on:click="$emit('wishDelete', item.wishListSeq)"
                >
                    <span>삭제</span>
                </button>
            </div>
        </li>
    </ul>
</template>

<script>
export default {
    name: 'wish-list-comp',
    props: ['listData'],
    mounted() {
        console.log(this.listData);
    },
    methods: {},
};
</script>

<style scoped></style>
