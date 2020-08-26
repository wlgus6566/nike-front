<template>
    <div>
        <ul class="sorting-tab">
            <li
                    v-for="item in tabList"
                    :key="item.value"
                    :class="{active:typeCd === item.value}"
            >
                <a href="#" @click="onClickTab(item.value)">{{item.title}}</a>
            </li>
        </ul>
        <template v-if="historyFolderData.length">
            <MyFolderList
                    v-if="historyFolderData.length"
                    :folderListData="historyFolderData"
            />
        </template>
        <template v-else>
            <NoData>
                <i class="icon-file"></i>
                <p class="desc">최근 본 폴더가 없습니다.</p>
            </NoData>
        </template>
    </div>
</template>
<script>

    import {
        historyFolderViewList
    } from '@/api/mypage';

    import MyFolderList from '@/components/my-folder-list';
    import NoData from '@/components/no-data';

    export default {
        name: 'latest',
        data() {
            return {
                historyFolderData: [],
                page: 0,
                pageSize: 5,
                totalPage: null,
                typeCd: 'ALL',
                pageLast: true,
                loadingData: false,
                tabList: [
                    {
                        value: 'ALL',
                        title: 'ALL',
                    },
                    {
                        value: 'TOOLKIT',
                        title: 'TOOLKIT',
                    },
                    {
                        value: 'FOUNDATION',
                        title: 'FOUNDATION',
                    },
                    {
                        value: 'REPORT_MANAGE',
                        title: 'REPORT',
                    },
                ],
            };
        },
        components: {
            MyFolderList,
            NoData,
        },
        created() {
            this.fetchData();
            window.addEventListener('scroll', this.handleScroll);
        },
        activated() {
            window.addEventListener('scroll', this.handleScroll);
        },
        deactivated() {
            window.removeEventListener('scroll', this.handleScroll);
        },
        destroyed() {
            window.removeEventListener('scroll', this.handleScroll);
        },
        methods: {
            // 초기 데이타 조회
            async fetchData(infinite) {
                this.loadingData = true;
                try {
                    const {
                        data: { data: response },
                    } = await historyFolderViewList({
                        page: this.page,
                        size: this.pageSize,
                        typeCd: this.typeCd,
                        MobileYn: 'Y'
                    });
                    this.pageLast = response.last;
                    this.totalPage = response.totalPages;

                    if (infinite) {
                        if (this.totalPage > this.page - 1) {
                            this.historyFolderData = this.historyFolderData.concat(
                                response.content
                            );
                        } else if (this.totalPage === this.page - 1) {
                            this.endPage();
                        }
                    } else {
                        this.historyFolderDataList = response;
                        this.historyFolderData = response.content;
                    }
                    this.pageLast = response.last;
                    this.page++;
                    this.loadingData = false;
                } catch (error) {
                    console.log(error);
                }
            },
            // tab 클릭시
            onClickTab(item) {
                this.typeCd = item;
                this.page = 0;
                this.fetchData();
            },
            endPage() {
                alert('마지막 페이지');
            },
            /*
                스크롤 관련 method
             */
            handleScroll() {
                console.log('a')
                if (this.loadingData) return;
                const windowE = document.documentElement;
                console.log('b', windowE.clientHeight + windowE.scrollTop >= windowE.scrollHeight)
                if (
                    windowE.clientHeight + windowE.scrollTop >=
                    windowE.scrollHeight
                ) {
                    this.infiniteScroll();
                }
            },
            infiniteScroll() {
                console.log('ssss');
                if (
                    !this.loadingData &&
                    this.totalPage > this.page - 1 &&
                    this.historyFolderData.length >= this.pageSize &&
                    this.historyFolderData.length !== 0 &&
                    !this.pageLast
                ) {
                    this.fetchData(true);
                }
            },
        }

    };
</script>

<style scoped></style>
