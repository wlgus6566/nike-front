<template>
    <div>
        <div class="tbl-list">
            <table>
                <colgroup>
                    <col style="width: 60px;" />
                    <col style="width: 160px;" />
                    <col style="width: 219px;" />
                    <col style="width: 62px;" />
                    <col style="width: 89px;" />
                    <col style="width: 91px;" />
                    <col style="width: 88px;" />
                    <col style="width: 51px;" />
                </colgroup>
                <thead>
                    <tr>
                        <th>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    v-model="checkAll"
                                    @click="allCheckFn()"
                                />
                                <i></i>
                            </span>
                        </th>
                        <th>구분</th>
                        <th>상품명</th>
                        <th>최소<br />주문수량</th>
                        <th>AGENCY</th>
                        <th>최종수정자</th>
                        <th>최종수정일</th>
                        <th>노출</th>
                    </tr>
                </thead>
                <tbody v-if="!loading">
                    <tr v-for="item in productListData" :key="item.goodsSeq">
                        <td>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    :value="item.goodsSeq"
                                    v-model="checkItem"
                                    @click="$emit('checked', item.goodsSeq)"
                                />
                                <i></i>
                            </span>
                        </td>
                        <td>
                            {{ item.category2Name }} >
                            {{ item.category3Name }}
                        </td>
                        <td>
                            <router-link :to="setUrl(item)" class="under-link">
                                <span>
                                    {{ item.goodsName }}
                                </span>
                            </router-link>
                        </td>
                        <td>
                            {{ item.minimumOrderQuantity }}
                        </td>
                        <td>{{ item.agencyName }}</td>
                        <td>{{ item.nickname }}</td>
                        <td>{{ item.updateDt }}</td>
                        <td>{{ item.exposureYn }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr>
                        <td colspan="8">
                            <Loading />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="btn-tbl-box">
            <button type="button" class="btn-form" @click="$emit('checkDel')">
                <span data-v-1756ba49="">삭제</span>
            </button>
            <div class="right">
                <router-link to="/order/registration" class="btn-form-gray">
                    <span data-v-1756ba49="">등록</span>
                </router-link>
            </div>
        </div>
    </div>
</template>
<script>
import Loading from '@/components/loading';

export default {
    name: 'index',
    data() {
        return {};
    },
    components: {
        Loading,
    },
    props: ['productListData', 'checkItem', 'checkAll', 'loading'],
    methods: {
        setUrl(item) {
            return `modify/${item.goodsSeq}`.toLocaleLowerCase();
        },
        allCheckFn(check) {
            this.$emit('allCheckFn', check);
        },
    },
};
</script>
<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: all 1s;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}
</style>
