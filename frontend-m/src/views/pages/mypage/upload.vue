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
        <template v-if="uploadFolderData">
            <MyFolderList
                v-if="uploadFolderData.length"
                :folderListData="uploadFolderData"
            />
            <template v-else>
                <NoData>
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
            </template>
        </template>
    </div>
</template>
<script>
import {
    uploadFolderViewList
    , historyFolderViewList
} from '@/api/mypage';

import MyFolderList from '@/components/my-folder-list';
import NoData from '@/components/no-data';

export default {
    name: 'upload',
    data() {
        return {
            uploadFolderData: [],
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
                } = await uploadFolderViewList({
                    page: this.page,
                    size: this.pageSize,
                    typeCd: this.typeCd,
                    MobileYn: 'Y'
                });
                this.uploadFolderData = response.content;
                this.pageLast = response.last;
                this.totalPage = response.totalPages;

                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.uploadFolderData = this.uploadFolderData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.uploadFolderDataList = response;
                    this.uploadFolderData = response.content;
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
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.uploadFolderData.length >= this.pageSize &&
                this.uploadFolderData.length !== 0 &&
                !this.pageLast
            ) {
                this.fetchData(true);
            }
        },
    }

};
</script>
<style scoped></style>
