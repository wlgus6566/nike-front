<template>
    <div>
        <h2 class="page-title">
            <span class="ko">주문내역확인</span>
        </h2>
        <div class="period-check">
            <strong class="title">기간조회</strong>
            <div class="date-picker-group">
                <div class="date-picker">
                    <el-date-picker
                        v-model="beginDt"
                        type="date"
                        placeholder="YYYY.MM.DD"
                        format="yyyy.MM.dd"
                        :picker-options="pickerBeginOption"
                    >
                    </el-date-picker>
                </div>
                <div class="date-picker">
                    <el-date-picker
                        v-model="endDt"
                        type="date"
                        placeholder="YYYY.MM.DD"
                        format="yyyy.MM.dd"
                        :picker-options="pickerEndOption"
                    >
                    </el-date-picker>
                </div>
            </div>
        </div>
        <MyOrderNodata v-if="orderList && orderList.length === 0" />
        <MyOrder
            v-if="orderList"
            :orderList="orderList"
            @showOrderDetail="showOrderDetail"
        />
        <OrderSheet
            v-if="orderDetailData"
            :visible.sync="visible.orderSheet"
            :orderDetailData="orderDetailData"
        />
    </div>
</template>

<script>
import { getMyOrder, getMyOrderDetail } from '@/api/my-order';

export default {
    name: 'my-order',
    components: {
        MyOrder: () => import('@/components/my-order/index'),
        MyOrderNodata: () => import('@/components/my-order/nodata'),
        OrderSheet: () => import('@/components/my-order/order-sheet'),
    },
    data() {
        return {
            orderList: null,
            orderDetailData: null,
            page: 0,
            itemLength: 20,
            selectedDate: null,
            placeholder: 'abc',
            visible: {
                orderSheet: false,
            },
            beginDt: new Date(),
            endDt: new Date(),
            today: new Date(),
            pickerBeginOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    const minDt = new Date();
                    minDt.setMonth(minDt.getMonth() - 3);
                    return (
                        time.getTime() > this.endDt.getTime() ||
                        time.getTime() < minDt ||
                        time.getTime() > new Date()
                    );
                },
            },
            pickerEndOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    const minDt = new Date();
                    minDt.setMonth(minDt.getMonth() - 3);
                    return (
                        time.getTime() < this.beginDt.getTime() ||
                        time.getTime() < minDt ||
                        time.getTime() > new Date()
                    );
                },
            },
        };
    },
    activated() {
        this.fetchData();
    },
    created() {},
    mounted() {},
    watch: {
        beginDt() {
            this.fetchData();
        },
        endDt() {
            this.fetchData();
        },
    },
    methods: {
        // showOrderSheet() {
        //     this.visible.orderSheet = true;
        // },
        async fetchData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getMyOrder({
                    page: this.page,
                    size: this.itemLength,
                    beginDt: this.$moment(this.beginDt).format('YYYY-MM-DD'),
                    endDt: this.$moment(this.endDt).format('YYYY-MM-DD'),
                });
                console.log(response);
                console.log(this.beginDt);
                console.log(this.endDt);
                this.orderList = response.content;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        async showOrderDetail(seq) {
            console.log(seq);
            this.visible.orderSheet = true;
            try {
                const {
                    data: { data: response },
                } = await getMyOrderDetail(seq);
                this.orderDetailData = response;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>

<style scoped></style>
