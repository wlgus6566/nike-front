<template>
    <transition-group tag="ul" class="wish-list" name="list" mode="out-in">
        <li
            class="wish-list-item"
            v-for="item in listData"
            :key="item.goodsSeq"
        >
            <span class="checkbox">
                <input
                    type="checkbox"
                    :value="item.goodsSeq"
                    v-model="checkWishItem"
                    @click="$emit('checkedWish', item.goodsSeq)"
                />
                <i></i>
            </span>
            <a
                href="#"
                class="title-box"
                @click.prevent="$emit('showDetailView', item.goodsSeq)"
            >
                <span class="thumbnail">
                    <img
                        :src="item.product.imageFilePhysicalName"
                        :alt="item.product.imageFileName"
                    />
                </span>

                <span class="info-box">
                    <strong class="title">{{ item.product.goodsName }}</strong>
                    <span class="txt">{{ item.product.goodsDescription }}</span>
                    <div class="desc-txt-box">
                        <span class="desc-txt">
                            {{
                                item.product.unitPrice | formattedNumber('', '')
                            }}
                            원</span
                        >

                        <span class="desc-txt">
                            {{ item.product.agency.agencyName }}
                        </span>
                    </div>
                </span>
            </a>
            <div class="quantity-box">
                <span class="title">최소주문수량</span>
                <span class="num">
                    <em>{{
                        item.product.minimumOrderQuantity
                            | formattedNumber('', '')
                    }}</em
                    >개
                </span>
            </div>
            <div class="btn-box">
                <button
                    type="button"
                    class="btn-s-black-sm"
                    @click="$emit('addBasket', item)"
                >
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
            <Loading v-if="isLoading(item.goodsSeq)" />
        </li>
    </transition-group>
</template>

<script>
export default {
    name: 'wish-list-comp',
    props: ['listData', 'checkWishItem', 'deleteLoading'],
    components: {
        Loading: () => import('@/components/loading'),
    },
    methods: {
        isLoading(seq) {
            const indexFind = this.deleteLoading.findIndex((el) => {
                return el === seq;
            });
            return indexFind !== -1;
        },
    },
};
</script>

<style scoped>
.wish-list {
    position: relative;
}
.wish-list-item {
    position: relative;
    overflow: hidden;
    transition: all 300ms;
}
.list-enter-active,
.list-leave-active {
    transition: all 300ms;
}
.list-enter, .list-leave-to /* .list-leave-active below version 2.1.8 */ {
    opacity: 0;
}
.list-leave-active {
    position: absolute;
    width: 100%;
}
.loading {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    z-index: 10;
    background-color: rgba(255, 255, 255, 0.5);
}
</style>
