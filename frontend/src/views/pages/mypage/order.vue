<template>
    <div>
        <h2 class="page-title">
            <span class="ko">주문내역확인</span>
        </h2>
        <div class="period-check">
            <strong class="title">기간조회</strong>
            <div class="date-picker-group">
                <div class="date-picker">
                    <v-date-picker
                        v-model="range.beginDt"
                        locale="en-us"
                        color="orange"
                        :input-props="{
                            placeholder: 'YYYY.MM.DD',
                        }"
                        :attributes="attrs"
                        :min-date="minDate('from')"
                        :max-date="maxDate('from')"
                    />
                </div>
                <div class="date-picker">
                    <v-date-picker
                        v-model="range.endDt"
                        locale="en-us"
                        color="orange"
                        :input-props="{
                            placeholder: 'YYYY.MM.DD',
                        }"
                        :attributes="attrs"
                        :min-date="minDate('to')"
                        :max-date="maxDate('to')"
                    />
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
            range: {
                beginDt: null,
                endDt: null,
            },
            orderList: null,
            orderDetailData: null,
            page: 0,
            itemLength: 20,
            selectedDate: null,
            make: {
                beginDt: null,
                endDt: null,
            },
            placeholder: 'abc',
            attrs: [
                {
                    key: 'today',
                    highlight: 'gray',
                    dates: new Date(),
                    class: 'vc-today',
                    contentClass: 'vc-today',
                },
            ],
            visible: {
                orderSheet: false,
            },
            today: new Date(),
        };
    },
    activated() {
        this.fetchData();
    },
    created() {
        const minDate = new Date();
        minDate.setMonth(minDate.getMonth() - 3);
        const maxDate = new Date();
        this.range.beginDt = minDate;
        this.range.endDt = maxDate;
    },
    watch: {
        make: {
            deep: true,
            handler(val) {
                if (val.beginDt && val.endDt) {
                    // console.log('실행');
                    this.fetchData();
                }
            },
        },
        'range.beginDt'(val) {
            let year = val.getFullYear();
            let month = val.getMonth() + 1;
            let day = val.getDate();
            if (month < 10) {
                month = `0${month}`;
            }
            if (day < 10) {
                day = `0${day}`;
            }
            this.make.beginDt = `${year}-${month}-${day}`;
            if (this.make.endDt !== null) {
                const begin = Number(this.make.beginDt.replace(/-/gi, ''));
                const end = Number(this.make.endDt.replace(/-/gi, ''));
                if (begin > end) {
                    alert('시작일이 종료일보다 클 수 없습니다.');
                    this.range.endDt = this.today;
                }
            }
        },
        'range.endDt'(val) {
            let year = val.getFullYear();
            let month = val.getMonth() + 1;
            let day = val.getDate();
            if (month < 10) {
                month = `0${month}`;
            }
            if (day < 10) {
                day = `0${day}`;
            }
            this.make.endDt = `${year}-${month}-${day}`;
            if (this.make.beginDt !== null) {
                const begin = Number(this.make.beginDt.replace(/-/gi, ''));
                const end = Number(this.make.endDt.replace(/-/gi, ''));
                if (begin > end) {
                    alert('시작일이 종료일보다 클 수 없습니다.');
                    this.range.endDt = this.today;
                }
            }
        },
    },
    methods: {
        dates() {},
        // showOrderSheet() {
        //     this.visible.orderSheet = true;
        // },
        minDate(tt) {
            if (tt === 'to') {
                return this.range.beginDt;
            }
            if (tt === 'from') {
                return null;
            }
        },
        maxDate(tt) {
            if (tt === 'to') {
                return new Date();
            }
            if (tt === 'from') {
                const date = Math.min(
                    this.range.endDt.getTime(),
                    new Date().getTime()
                );
                return date;
            }
        },
        async fetchData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getMyOrder({
                    page: this.page,
                    size: this.itemLength,
                    beginDt: this.make.beginDt,
                    endDt: this.make.endDt,
                });
                console.log(response);
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
