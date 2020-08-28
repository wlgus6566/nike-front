<template>
    <div class="aside-file">
        <div>
            <el-scrollbar
                class="file-list-scroll"
                wrap-class="file-list-wrap"
                :native="false"
            >
                <div @mouseenter="basketEnter" @mouseleave="basketLeave">
                    <ul class="file-list" v-if="contBasketList.length">
                        <li
                            v-for="item in contBasketList"
                            :key="item.contentsBasketSeq"
                        >
                            <img :src="item.filePhysicalName" alt="" />
                            <button
                                type="button"
                                class="btn-del"
                                @click="delContBasket(item.contentsBasketSeq)"
                            >
                                <span>삭제</span>
                            </button>
                            <Loading
                                :width="100"
                                :height="100"
                                v-if="isLoading(item.contentsBasketSeq)"
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
            <button
                v-if="!downloadFiles"
                type="button"
                class="btn-download"
                @click="fileDownload"
                :disabled="contBasketList.length === 0"
            >
                <span class="txt">DOWNLOAD</span>
            </button>
            <span v-else class="btn-download active">
                <span class="txt">DOWNLOADING({{ this.loaded }}%)…</span>
                <span class="gage" :style="{ width: `${this.loaded}%` }"></span>
            </span>
        </div>

        {{ contBasketList.length }}

        <strong class="tab-title">HISTORY</strong>
        <TabComponent v-bind:tabMenus="historyTab"></TabComponent>
    </div>
</template>
<script>
import TabComponent from '@/components/tab-comp';
import NoData from '@/components/no-data';
import Loading from '@/components/loading';
import {
    addContentsBasket,
    delContentsBasket,
    contentFileDownload,
} from '@/api/contents';
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
                        component: 'ContentToolKit',
                    },
                    {
                        title: 'FOUNDATION',
                        component: 'ContentFoundation',
                    },
                ],
                showIndex: 0,
            },
            deleteLoading: [],
            downloadFiles: null,
            loaded: 0,
        };
    },
    /* watch: {
        downloadFiles: {
            deep: true,
            handler(current) {
                const total = current.reduce((a, b) => {
                    console.log(b);
                    return a + b.total;
                }, 0);
                const loaded = current.reduce((a, b) => {
                    console.log(b);

                    return a + b.loaded;
                }, 0);
                console.log(loaded);
                console.log(total);
                this.loaded = Math.round((loaded * 100) / total);
            },
        },
    },*/
    computed: {
        contBasketList: {
            get() {
                return this.$store.state.contBasketList;
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
        progress() {
            const total = this.downloadFiles.reduce((a, b) => {
                return a + b.total;
            }, 0);
            const loaded = this.downloadFiles.reduce((a, b) => {
                return a + b.loaded;
            }, 0);
            this.loaded = Math.round((loaded * 100) / total);
        },

        async fileDownload() {
            this.downloadFiles = [];
            await Promise.all(
                this.contBasketList.map(async (el, i) => {
                    try {
                        const config = {
                            responseType: 'blob',
                            timeout: 0,
                            onDownloadProgress: (progressEvent) => {
                                this.downloadFiles[i] = {
                                    total: progressEvent.total,
                                    loaded: progressEvent.loaded,
                                };
                                this.progress();
                            },
                        };
                        const response = await contentFileDownload(
                            el.contentsFileSeq,
                            config
                        );
                        await this.delContBasket(el.contentsBasketSeq);
                        const url = window.URL.createObjectURL(
                            new Blob([response.data])
                        );
                        const link = document.createElement('a');
                        link.href = url;
                        link.id = 'id_' + i;
                        link.setAttribute('download', el.fileName);
                        document.body.appendChild(link);
                        link.click();
                        window.URL.revokeObjectURL(url);
                    } catch (error) {
                        console.error(error);
                    }
                })
            );
            this.downloadFiles = null;
        },
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
                } = await this.$store.dispatch('getContBasket');
                console.log(response);
                this.contBasketList = response;
            } catch (error) {
                console.error(error);
            }
        },
        async addContBasket(seqArr) {
            try {
                await addContentsBasket(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    seqArr
                );
                await this.$store.dispatch('getContBasket');
            } catch (error) {
                console.error(error);
            }
        },
        async delContBasket(seq) {
            this.deleteLoading.push(seq);
            try {
                await delContentsBasket(seq);
                await this.$store.dispatch('getContBasket');
                this.deleteLoading = [];
            } catch (error) {
                console.error(error);
            }
        },
    },
    components: {
        NoData,
        Loading,
        TabComponent,
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
    background: #000;
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
    z-index: 1;
    position: relative;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.btn-download.active {
    background-color: #888 !important;
}
.btn-download:disabled {
    background-color: #ccc !important;
}
.btn-download:disabled .gage {
    display: none;
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
