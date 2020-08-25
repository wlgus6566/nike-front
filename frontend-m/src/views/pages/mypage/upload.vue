<template>
    <div>
        <ul class="sorting-tab">
            <li
                v-for="item in tabList"
                :key="item.value"
                :class="{active:tabValue === item.value}"
            >
                <a href="#" @click="onClickTab(item.value)">{{item.title}}</a>
            </li>
        </ul>
        <ul class="folder-list-row">
            <li
                class="folder-list-item"
                v-for="item in uploadFolderData"
                :key="item.historySeq"
            >
                <router-link :to="setUrl(item)">
                    <div class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">{{item.folderName}}</strong>
                        <p class="date" v-if="item.typeCd === 'REPORT_MANAGE'">
                            {{ $moment(item.updateDt).format('YYYY.MM.DD') }}
                        </p>
                        <p class="date" v-else>
                            {{ $moment(item.campaignBeginDt).format('YYYY.MM.DD') }} ~ {{ $moment(item.campaignEndDt).format('YYYY.MM.DD') }}
                        </p>
                        <ul class="location">
                            <li v-if="item.typeCd !== 'REPORT_MANAGE'">{{item.topMenuCode}}</li>
                            <li v-else>REPORT</li>
                            <li>{{item.menuCode}}</li>
                        </ul>
                    </div>
                    <div class="view-area">
                        <span class="view">{{item.readCount}}</span>
                    </div>
                </router-link>
            </li>
        </ul>

    </div>
</template>
<script>
    import {
        uploadFolderViewList
        , historyFolderViewList
    } from '@/api/mypage';

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
            totalPage: null,
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
            tabValue: 'ALL'
        };
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
                console.log('pageLast', this.pageLast)

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
        // 상세 페이지 연결
        setUrl(item) {
            // console.log('item : ', item);
            if (item.typeCd === 'REPORT_MANAGE') {
                return `/report/${item.folderSeq}`.toLocaleLowerCase();
            } else {
                return `/${item.topMenuCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
            }
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
            console.log('infiniteScroll',
                !this.loadingData,
                this.totalPage > this.page - 1,
                this.uploadFolderData.length >= this.pageSize,
                this.uploadFolderData.length !== 0,
                !this.pageLast)
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.uploadFolderData.length >= this.pageSize &&
                this.uploadFolderData.length !== 0 &&
                !this.pageLast
            ) {
                console.log('uin')
                this.fetchData(true);
            }
        },
    }

};
</script>
<style scoped></style>
