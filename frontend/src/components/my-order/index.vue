<template>
    <ul class="period-list">
        <li
            class="period-list-item"
            v-for="(item, index) in orderList"
            :key="index"
        >
            <button
                type="button"
                class="top"
                @click="$emit('showOrderDetail', item.orderSeq)"
            >
                <strong class="num">
                    주문일 : {{ item.registrationDt }}
                </strong>
                <span class="link-txt">
                    주문상세보기
                </span>
            </button>
            <template v-if="item.orderProductMapping[0]">
              <div class="cont" v-if="item.orderProductMapping[0].product">
                <span class="thumbnail">
                  <img
                      :src="
                            item.orderProductMapping[0].product
                                .imageFilePhysicalName
                        "
                      :alt="item.orderProductMapping[0].product.imageFileName"
                      v-if="item.orderProductMapping[0].product
                                .imageFilePhysicalName"
                  />
                </span>
                <span class="info-box">
                    <strong class="title">
                        {{ item.orderProductMapping[0].product.goodsName }}
                       <!-- <span v-if="item.orderProductMapping.length - 1 !== 0">
                            외 {{ item.orderProductMapping.length - 1 }}개
                        </span>-->
                    </strong>
                </span>
            </div>
            </template>
        </li>
    </ul>
</template>

<script>
export default {
    name: 'order-list',
    props: ['orderList', 'orderDetail'],
    mounted() {},
    computed: {},
    methods: {},
};
</script>

<style lang="scss" scoped></style>
