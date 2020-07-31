<template>
    <div>
        <h2 class="page-title">
            <span class="ko">주문내역확인</span>
        </h2>
        <div class="period-check">
            <strong class="title">기간조회</strong>
            <div class="data-picker-group">
                <div class="data-picker">
                    <vc-date-picker color="orange" />
                </div>
                <div class="data-picker">
                    <vc-date-picker color="orange" />
                </div>
            </div>
        </div>
        <MyOrder :orderList="orderList" />
    </div>
</template>

<script>
import { getMyOrder } from '@/api/my-order';

export default {
    name: 'my-order',
    components: {
        MyOrder: () => import('@/components/my-order/index'),
    },
    data() {
        return {
            range: {
                start: null, // Jan 16th, 2018
                end: null, // Jan 19th, 2018
            },
            attributes: [
                {
                    key: 'today',
                    highlight: true,
                    dates: new Date(),
                },
            ],
            orderList: null,
            page: 0,
            itemLength: 20,
            componentPrefix: 'vc',
            selectedDate: null,
        };
    },
    activated() {
        this.fetchData();
    },
    watch: {
        range() {
            this.fetchData();
        },
    },
    computed: {
        // layout() {
        //     return this.$screens({
        //         // Default layout for mobile
        //         default: {
        //             columns: 1,
        //             rows: 1,
        //             isExpanded: true,
        //         }
        //     });
        // },
    },
    methods: {
        async fetchData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getMyOrder({
                    page: this.page,
                    size: this.itemLength,
                    beginDt: this.range.start,
                    endDt: this.range.end,
                });
                this.orderList = response.content;
                this.loadingData = false;
                return;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>

<style scoped></style>
