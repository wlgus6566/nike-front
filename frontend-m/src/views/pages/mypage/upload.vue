<template>
    <div>
        <ul class="sorting-tab">
            <li
                v-for="item in tabList"
                :key="item.value"
                :class="{active:tabValue === item.value}"
            >
                <a href="javascript:onClickTab(item.value);">{{item.title}}</a>
            </li>
        </ul>
        <ul class="folder-list-row">
            <li class="folder-list-item" v-for="item in uploadFolderList" :key="item.folderSeq">
                <a href="javascript:onClickDetail(item);">
                    <div class="thumbnail">
                        <img :="item.imageFilePhysicalName" alt="" />
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
                            <li>{{item.topMenuCode}}</li>
                            <li>{{item.menuCode}}</li>
                        </ul>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
<!--            <li class="folder-list-item">-->
<!--                <a href="#">-->
<!--                    <div class="thumbnail">-->
<!--                        <img src="http://placehold.it/410X600" alt="" />-->
<!--                    </div>-->
<!--                    <div class="info-box">-->
<!--                        <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다</strong>-->
<!--                        <p class="date">2020.00.00</p>-->
<!--                        <ul class="location">-->
<!--                            <li>ASSET</li>-->
<!--                            <li>FA</li>-->
<!--                        </ul>-->
<!--                    </div>-->
<!--                    <div class="view-area">-->
<!--                        <span class="view">10,000</span>-->
<!--                    </div>-->
<!--                </a>-->
<!--            </li>-->
<!--            <li class="folder-list-item">-->
<!--                <a href="#">-->
<!--                    <div class="thumbnail">-->
<!--                        <img src="http://placehold.it/410X600" alt="" />-->
<!--                    </div>-->
<!--                    <div class="info-box">-->
<!--                        <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다</strong>-->
<!--                        <p class="date">2020.00.00</p>-->
<!--                        <ul class="location">-->
<!--                            <li>ASSET</li>-->
<!--                            <li>FA</li>-->
<!--                        </ul>-->
<!--                    </div>-->
<!--                    <div class="view-area">-->
<!--                        <span class="view">10,000</span>-->
<!--                    </div>-->
<!--                </a>-->
<!--            </li>-->
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
            uploadFolderList: [],
            searchData: {
                page: 0,
                itemLength: 20,
                typeCd: 'ALL'
            },
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
    mounted() {
        this.fetchData();
    },
    methods: {
        // 초기 데이타 조회
        async fetchData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await uploadFolderViewList({
                    page: this.page,
                    size: this.itemLength,
                    typeCd: this.tabValue,
                });
                this.uploadFolderList = response;
            } catch (error) {
                console.log(error);
            }
        },
        // tab 클릭시
        onClickTab(item) {
            this.tabValue = item;
            this.page = 0;
            this.fetchData();
        },
        // 콘텐츠 클릭시
        onClickDetail(item) {

        }
    }

};
</script>
<style scoped></style>
