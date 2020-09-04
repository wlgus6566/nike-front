<template>
    <div>
        <ul class="sorting-tab">
            <li
                v-for="item in tabList"
                :key="item.value"
                :class="{ active: typeCd === item.value }"
            >
                <a href="#" @click="onClickTab(item.value)">{{ item.title }}</a>
            </li>
        </ul>
        <template v-if="uploadFolderData">
            <MyFolderList
                v-if="uploadFolderData.length"
                :folderListData="uploadFolderData"
            />
        </template>
        <template v-else>
            <NoData>
                <i class="icon-upload"></i>
                <p class="desc">업로드한 폴더가 없습니다.</p>
            </NoData>
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
import { uploadFolderViewList } from '@/api/mypage';

import MyFolderList from '@/components/my-folder-list';
import NoData from '@/components/no-data';

export default {
    name: 'upload',
    data() {
        return {
            uploadFolderDataList: null,
            uploadFolderData: null,
            page: 0,
            pageSize: 5,
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
    components: {
        MyFolderList,
        NoData,
        Loading: () => import('@/components/loading/'),
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
            this.uploadFolderData = null;
            this.fetchData();
        },
        async fetchData(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await uploadFolderViewList({
                    page: this.page,
                    size: this.pageSize,
                    typeCd: this.typeCd,
                    MobileYn: 'Y',
                });
                this.uploadFolderData = response.content;
                this.isLastPage = response.last;
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
                !this.isLastPage
            ) {
                this.fetchData(true);
            }
        },
    },
};
</script>
<style scoped></style>
