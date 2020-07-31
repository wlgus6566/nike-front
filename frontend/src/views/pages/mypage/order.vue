<template>
    <div>
        <h2 class="page-title">
            <span class="ko">주문내역확인</span>
        </h2>

        {{ range.beginDt }}<br />
        {{ range.endDt }}
        <div class="period-check">
            <strong class="title">기간조회</strong>
            <div class="date-picker-group">
                <div class="date-picker">
                    <v-date-picker color="orange" v-model="range.beginDt" />
                </div>
                <div class="date-picker">
                    <v-date-picker
                        color="orange"
                        :masks="{ title: 'YYYY.MM' }"
                        v-model="range.endDt"
                    />
                </div>
            </div>
        </div>
        <MyOrder :orderList="orderList" v-if="orderList" />
        <MyOrderNodata v-if="orderList && orderList.length === 0" />
    </div>
</template>

<script>
import { getMyOrder } from '@/api/my-order';

export default {
    name: 'my-order',
    components: {
        MyOrder: () => import('@/components/my-order/index'),
        MyOrderNodata: () => import('@/components/my-order/nodata'),
    },
    data() {
        return {
            range: {
                beginDt: null,
                endDt: null,
            },
            orderList: null,
            page: 0,
            itemLength: 20,
            selectedDate: null,
            make: {
                beginDt: null,
                endDt: null,
            },
        };
    },
    activated() {
        this.fetchData();
    },
    mounted() {
        //this.fetchData();
    },
    created() {},
    watch: {
        make: {
            deep: true,
            handler(val) {
                if (val.beginDt && val.endDt) {
                    console.log(val.beginDt);
                    console.log(val.endDt);
                    console.log('실행');
                    this.fetchData();
                }
            },
        },
        // range: {
        //     deep: true,
        //     handler() {
        //         console.log(data);
        //         console.log(data2);
        //         // if (data !== this.range.beginDt) {
        //         //     data = this.range.beginDt;
        //         // } else {
        //         //     data = this.range.endDt;
        //         // }
        //         // console.log(data);
        //     },
        // },

        // dateRefine(date) {
        //     // beginDate = this.range.beginDt;
        //     // endDate = this.range.endDt;
        //     let year = date.getFullYear();
        //     let month = date.getMonth() + 1;
        //     let day = date.getDate();
        //     if (month < 10) {
        //         month = `0${month}`;
        //     }
        //     if (day < 10) {
        //         day = `0${day}`;
        //     }
        //     this.make.endDt = `${year}-${month}-${day}`; //2020-07-08
        //     if (
        //         Number(this.make.beginDt.replace(/-/gi, '')) >
        //         Number(this.make.endDt.replace(/-/gi, ''))
        //     ) {
        //         alert('시작일이 종료일보다 클 수 없습니다.');
        //         date = null;
        //         return false;
        //     } else {
        //         date = `${year}-${month}-${day}`;
        //     }
        // },
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
            //  = this.make.beginDt  // 그냥 들어가도됨
            // this.make.endDt < this.make.beginDt this.make.endDt 알럿이 들어가고 'this.make.endDt' 빈값을 넣어
            // this.make.endDt > this.make.beginDt  // this.make.beginDt 그냥 들어가도됨

            // this.make.endDt null이거나 this.make.endDt 시작일보다 작으면
            if (
                this.make.endDt === null ||
                Number(this.make.beginDt.replace(/-/gi, '')) <
                    Number(this.make.endDt.replace(/-/gi, ''))
            ) {
                this.make.beginDt = `${year}-${month}-${day}`;
            } else {
                alert('시작일이 종료일보다 클 수 없습니다.');
                this.range.endDt = null;
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
            this.make.endDt = `${year}-${month}-${day}`; //2020-07-08
            // this.make.endDt < this.make.beginDt this.make.endDt 알럿이 들어가고 'this.make.endDt' 빈값을 넣어
            // this.make.endDt > this.make.beginDt  // this.make.beginDt 그냥 들어가도됨

            //
            if (
                Number(this.make.beginDt.replace(/-/gi, '')) >
                Number(this.make.endDt.replace(/-/gi, ''))
            ) {
                alert('시작일이 종료일보다 클 수 없습니다.');
                this.range.endDt = null;
            } else {
                this.make.endDt = `${year}-${month}-${day}`;
            }
        },
    },
    methods: {
        dateRefine(date) {
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            if (month < 10) {
                month = `0${month}`;
            }
            if (day < 10) {
                day = `0${day}`;
            }
            return (date = `${year}-${month}-${day}`);

            // this.make.endDt = `${year}-${month}-${day}`; //2020-07-08
            // if (
            //     Number(this.make.beginDt.replace(/-/gi, '')) >
            //     Number(this.make.endDt.replace(/-/gi, ''))
            // ) {
            //     alert('시작일이 종료일보다 클 수 없습니다.');
            //     date = null;
            //     return false;
            // } else {
            //     date = `${year}-${month}-${day}`;
            // }
        },
        async fetchData() {
            this.loadingData = true;
            console.log('타나요');
            try {
                const {
                    data: { data: response },
                } = await getMyOrder({
                    page: this.page,
                    size: this.itemLength,
                    beginDt: this.make.beginDt,
                    endDt: this.make.endDt,
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
