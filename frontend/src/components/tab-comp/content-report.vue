<template>
    <div class="aside-file">
        <div>
            <el-scrollbar
                class="file-list-scroll"
                wrap-class="file-list-wrap"
                :native="false"
            >
                <div @mouseenter="basketEnter" @mouseleave="basketLeave">
                    <ul class="file-list" v-if="reportBasketList.length">
                        <li
                            v-for="item in reportBasketList"
                            :key="item.reportBasketSeq"
                        >
                            <img :src="item.filePhysicalName" alt="" />
                            <button
                                type="button"
                                class="btn-del"
                                @click="delReportBasket(item.reportBasketSeq)"
                            >
                                <span>삭제</span>
                            </button>
                            <Loading
                                :style="{ height: '100px', width: '100px' }"
                                v-if="isLoading(item.reportBasketSeq)"
                            />
                        </li>
                    </ul>
                    <NoData v-else>
                        <i class="icon-file"></i>
                        <p class="txt">더욱 빠르게 파일 받기</p>
                        <p class="desc">
                            이곳에 끌어다 놓으면 파일을 바로<br />
                            다운받을 수 있어요.
                        </p>
                    </NoData>
                </div>
            </el-scrollbar>
            <button type="button" class="btn-download">
                <span class="gage" style="width: 50%;"></span>
                <span class="txt" style="display: none;">DOWNLOAD</span>
                <span class="txt">DOWNLOAD...</span>
            </button>
        </div>
    </div>
</template>
<script>
import NoData from '@/components/no-data';
import Loading from '@/components/loading';
import { postReportBasket, deleteReportBasket } from '@/api/report';
import bus from '@/utils/bus';

export default {
    name: 'FileItem',
    data() {
        return {
            historyTab: {
                tabClass: 'tab-list-sm',
                tabList: [
                    {
                        title: 'ASSET',
                        component: 'ContentAsset',
                    },
                    {
                        title: 'TOOLKIT',
                        component: 'ContentTooKit',
                    },
                    {
                        title: 'FOUNDATION',
                        component: 'ContentFoundation',
                    },
                ],
            },
            deleteLoading: [],
        };
    },
    computed: {
        reportBasketList: {
            get() {
                return this.$store.state.reportBasketList;
            },
            set(value) {
                this.$store.commit('SET_CONT_BASKET', value);
            },
        },
    },
    mounted() {
        this.getContBasket();
    },
    created() {
        bus.$on('addContBasket', (seq) => {
            this.addContBasket(seq);
        });
    },
    methods: {
        basketEnter() {
            this.$store.commit('SET_FILE_MOUSEENTER', true);
        },
        basketLeave() {
            this.$store.commit('SET_FILE_MOUSEENTER', false);
        },
        isLoading(seq) {
            return this.deleteLoading.some((el) => {
                return el === seq;
            });
        },
        async getContBasket() {
            try {
                const {
                    data: { data: response },
                } = await this.$store.dispatch('getReportListBasket');
                this.reportBasketList = response;
            } catch (e) {
                console.log(e);
            }
        },
        async addContBasket(seq) {
            console.log(seq);
            try {
                await postReportBasket();
                await this.$store.dispatch('getReportListBasket');
            } catch (e) {
                console.log(e);
            }
        },
        async delReportBasket(seq) {
            console.log(seq);
            this.deleteLoading.push(seq);
            try {
                await deleteReportBasket(seq);
                await this.$store.dispatch('getReportListBasket');
                await this.getContBasket();
                this.deleteLoading = [];
            } catch (e) {
                console.log(e);
            }
        },
    },
    components: {
        NoData,
        Loading,
    },
};
</script>
<style scoped>
.tab-title {
    display: block;
    margin-top: 40px;
    font-family: 'Bebas Neue', sans-serif;
    font-size: 20px;
    font-weight: normal;
    line-height: 24px;
    letter-spacing: 0.5px;
    color: #000;
}
.file-list-scroll {
    margin-top: 15px;
    height: 360px;
}
::v-deep .file-list-wrap {
    box-sizing: border-box;
    background: #eee;
}
::v-deep .file-list-wrap .no-data {
    background: #eee;
    height: 360px;
}
.file-list {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    box-sizing: border-box;
    padding: 18px 0 18px 18px;
    overflow: auto;
    background: #eee;
}
.file-list li {
    position: relative;
    flex: 0 0 100px;
    height: 100px;
}
.file-list li .loading {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
}
.file-list li:nth-child(2n) {
    margin-left: 4px;
}
.file-list li:nth-child(n + 3) {
    margin-top: 4px;
}
.file-list li.no-data {
    flex: 1 0 100px;
}
.file-list li .flag-box {
    position: absolute;
    top: 0;
    left: 0;
}
.file-list li .btn-del {
    position: absolute;
    top: 0;
    right: 0;
    display: block;
    width: 20px;
    height: 20px;
    border-radius: 0;
    background: url(../../assets/images/svg/icon-close-white-small.svg)
        no-repeat center;
}
.file-list li .btn-del span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.file-list li img {
    width: 100%;
    height: 100%;
    background: red;
    display: block;
    vertical-align: top;
}
.btn-download {
    position: relative;
    display: flex;
    width: 100%;
    height: 40px;
    border-radius: 0;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    color: #fff;
    background: #ccc;
}
.btn-download .gage {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    background: #fa5400;
}
.btn-download .txt {
    position: relative;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.btn-download:disabled .gage {
    width: 0;
}
.flag-box .flag {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 24px;
    height: 24px;
    background: #000;
    font-size: 10px;
    color: #fff;
    font-weight: bold;
}
.no-data {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}
.no-data .icon-file {
    display: block;
    width: 50px;
    height: 50px;
    background: url(../../assets/images/svg/illust-file-empty.svg);
}
.no-data .txt {
    margin-top: 15px;
    font-size: 11px;
    font-weight: 500;
    color: #000;
    line-height: 17px;
    text-align: center;
}
.no-data .txt + .desc {
    margin-top: 2px;
}
.no-data .desc {
    margin-top: 7px;
    font-size: 10px;
    line-height: 15px;
    color: #999;
    font-weight: 300;
    text-align: center;
}
</style>
