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

        <template v-if="orderList">
            <MyOrder
                v-if="orderList.length"
                :orderList="orderList"
                @showOrderDetail="showOrderDetail"
            />
            <template v-else>
                <NoData>
                    <i class="icon-data"></i>
                    <p class="desc">주문 내역이 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
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
        NoData: () => import('@/components/no-data/index'),
        OrderSheet: () => import('@/components/my-order/order-sheet'),
        Loading: () => import('@/components/loading'),
    },
    data() {
        return {
            loadingData: false,
            orderList: null,
            orderDetailData: null,
            page: 0,
            itemLength: 20,
            selectedDate: null,
            totalPage: null,
            placeholder: 'abc',
            visible: {
                orderSheet: false,
            },
            beginDt: new Date().setMonth(new Date().getMonth() - 3),
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
    created() {
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },

    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    watch: {
        beginDt() {
            this.initFetchData();
        },
        endDt() {
            this.initFetchData();
        },
    },
    methods: {
        //클릭시 업로드 한 폴더 리스트 다시 불러오기
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.orderList = null;
            this.fetchData();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.orderList.length >= this.itemLength &&
                this.orderList.length !== 0
            ) {
                this.fetchData(true);
            }
        },
        async fetchData(infinite) {
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
                this.totalPage = response.totalPages;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.orderList = this.orderList.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.orderList = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
        endPage() {
            alert('마지막 페이지 입니다.');
        },
        async showOrderDetail(seq) {
            this.visible.orderSheet = true;
            try {
                const {
                    data: { data: response },
                } = await getMyOrderDetail(seq);
                this.orderDetailData = response;
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>

<style scoped></style>
