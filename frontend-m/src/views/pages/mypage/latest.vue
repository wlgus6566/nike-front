<template>
    <div>
        <ul class="sorting-tab">
            <li
                v-for="item in tabList"
                :key="item.value"
                :class="{ active: typeCd === item.value }"
            >
                <a href="#" @click.prevent="onClickTab(item.value)">{{
                    item.title
                }}</a>
            </li>
        </ul>
        <template v-if="historyFolderData">
            <MyFolderList
                v-if="historyFolderData.length"
                :folderListData="historyFolderData"
            />
            <template v-else>
                <NoData>
                    <i class="icon-folder"></i>
                    <p class="desc">최근 본 폴더가 없습니다.</p>
                </NoData>
            </template>
        </template>

        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
    </div>
</template>
<script>
import { historyFolderViewList } from '@/api/mypage';

import MyFolderList from '@/components/my-folder-list';
import NoData from '@/components/no-data';

export default {
    name: 'latest',
    components: {
        MyFolderList,
        NoData,
        Loading: () => import('@/components/loading/'),
    },
    data() {
        return {
            historyFolderDataList: null,
            historyFolderData: null,
            page: 0,
            pageSize: 20,
            totalPage: null,
            typeCd: 'ALL',
            isLastPage: true,
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
    created() {
        this.initFetchData();
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
        async initFetchData() {
            this.totalElements = 0;
            this.page = 0;
            this.historyFolderData = null;
            this.fetchData();
        },
        async fetchData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await historyFolderViewList({
                    page: this.page,
                    size: this.pageSize,
                    typeCd: this.typeCd,
                    MobileYn: 'Y',
                });
                this.isLastPage = response.last;
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
                this.isLastPage = response.last;
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        // tab 클릭시
        onClickTab(item) {
            this.typeCd = item;
            this.initFetchData();
        },
        endPage() {
            alert('마지막 페이지');
        },
        /*
            스크롤 관련 method
         */
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.offsetHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.historyFolderData.length >= this.pageSize &&
                this.historyFolderData.length !== 0 &&
                !this.isLastPage
            ) {
                this.fetchData(true);
            }
        },
    },
};
</script>

<style scoped>
::v-deep .no-data-wrap {
    min-height: calc(100vh - 345px);
}
</style>
