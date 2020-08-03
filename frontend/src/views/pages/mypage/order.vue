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
                        :min-date="minDate()"
                        :max-date="new Date()"
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
                        :min-date="minDate()"
                        :max-date="new Date()"
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
            if (
                Number(this.make.beginDt.replace(/-/gi, '')) >
                Number(this.make.endDt.replace(/-/gi, ''))
            ) {
                alert('시작일이 종료일보다 클 수 없습니다.');
                this.range.beginDt = this.range.endDt;
            } else {
                this.make.beginDt = `${year}-${month}-${day}`;
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
            if (
                Number(this.make.beginDt.replace(/-/gi, '')) >
                Number(this.make.endDt.replace(/-/gi, ''))
            ) {
                alert('시작일이 종료일보다 클 수 없습니다.');
                this.range.endDt = this.range.beginDt;
            } else {
                this.make.endDt = `${year}-${month}-${day}`;
            }
        },
    },
    methods: {
        dates() {
            console.log('aa');
            // date = new Date();
            // date.setMonth(date.getMonth() - 1);
            // document.write('1달 전 : ' + date + '<br>');
        },
        minDate() {
            const date = new Date();
            date.setMonth(date.getMonth() - 3);
            return date;
        },
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
            console.log();
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
