<template>
    <div>
        <a href="#" class="main-visual">
            <span class="thumbnail">
                <img :src="mainVisual.mobileImageUrl" alt="">
            </span>
            <strong class="title" v-text="mainVisual.title"></strong>
            <span class="desc" v-text="mainVisual.contents">

            </span>
        </a>
        <h2 class="main-title">RECENT UPDATE</h2>
        <div class="main-update-list">
            <div class="update-list-item" v-for="item in assetContentsList" :key="item.contentsSeq">
                <a href="#">
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            ASSET
                        </span>
                        <span class="desc" v-text="item.folderContents">SP20 나이키 다이렉트</span>
                    </span>
                </a>
            </div>
            <div class="update-list-item" v-for="item in foundationContentsList" :key="item.contentsSeq">
                <a href="#">
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            FOUNDATION
                        </span>
                        <span class="desc" v-text="item.folderContents">SP20 나이키 다이렉트</span>
                    </span>
                </a>
            </div>
            <div class="update-list-item" v-for="item in toolKitContentsList" :key="item.contentsSeq">
                <a href="#">
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            TOOLKIT
                        </span>
                        <span class="desc" v-text="item.folderContents">SP20 나이키 다이렉트</span>
                    </span>
                </a>
            </div>
        </div>
        <h2 class="main-title">NOTICE</h2>
        <ul class="notice-list">
            <li v-for="item in noticeArticleList" :key="item.noticeArticleSeq">
                <a :href="'/mypage/notice/detail/'+item.noticeArticleSeq" >
                    <span class="label-noti" v-if="item.noticeYn === 'Y'">중요</span>
                    <span class="title" v-text="item.title">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</span>
                    <span class="data" v-text="item.updateDt">2020.06.17</span>
                </a>
            </li>
        </ul>
        <h2 class="main-title">CALENDAR</h2>
        <div>
            <FullCalendar ref="fullCalendar" :options="calendarOptions"/>
        </div>
        <h2 class="main-title">REPORT</h2>
        <ul class="main-report-list">
            <li class="report-list-item" v-for="item in reportList" :key="item.reportSeq">
                <a :href="'/report/'+item.reportSeq">
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <strong class="title" v-text="item.nickname">
                            조던 서울조던
                        </strong>
                        <p class="desc" v-text="item.reportName">
                            코리아 팀 스니커즈 컬렉션 코리아 팀 스니커즈 컬렉션코리아 팀
                            스니커즈 컬렉션코리아 팀 스니커즈 컬렉션코리아 팀 스니커즈
                            컬렉션
                        </p>
                    </span>
                </a>
            </li>
        </ul>
        <h2 class="main-title">NEWS</h2>
        <div class="main-news-list">
            <div class="news-list-item" v-for="item in newsArticleList" :key="item.noticeArticleSeq">
                <a :href="'/mypage/news/detail/'+item.noticeArticleSeq">
                    <span class="thumbnail">
                        <img :src="item.thumbnailFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <strong class="title" v-text="item.title">JORDAN SEOUL</strong>
                        <p class="desc" v-text="item.contents">조던 서울 오픈 포토 리캡</p>
                        <span class="date" v-text="item.updateDt">2020. 06. 17.</span>
                    </span>
                </a>
            </div>
        </div>

    </div>
</template>
<script>
import {getMain} from '@/api/main';
import {getCalendarList, getTodayCalendar} from '@/api/calendar/';
import {getCode} from '@/api/code/'

import moment from 'moment';
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

export default {
    name: 'MainPage',
    data() {
        return {
            loading: false,
            assetContentsList : [],
            foundationContentsList : [],
            mainVisual:'',
            newsArticleList : [],
            noticeArticleList:[],
            reportList : [],
            toolKitContentsList : [],
            yyyyMm: moment(new Date()).format('YYYY.MM'),
            searchDt: moment(new Date()).format('YYYY.MM.DD'),
            currentDate: moment(new Date()).format('YYYY.MM.DD'),
            statusCode: null,
            calendarDetail: {},
            calenderSectionCodeList: [],
            calendarData: [],
            todayData: [],
            calendarOptions: {
                plugins: [dayGridPlugin, interactionPlugin],
                initialView: 'dayGridMonth',
                dateClick: this.handleDateClick,
                events: [], //달력에 표시
                customButtons: {
                    prev: {
                        // this overrides the prev button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.prev();
                            console.log(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                            this.getCalendarList(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                        },
                    },
                    next: {
                        // this overrides the next button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.next();
                            console.log(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                            this.getCalendarList(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                        },
                    },
                },

            }
        };
    },
    components: {
        FullCalendar
    },
    mounted(){
        console.log("test");
        this.fetchData();
        this.fetchCalendar();
    },methods: {
        async fetchData(){
            this.loading = true;
            try{
                const {
                    data: {data: response}
                }= await getMain();
                this.assetContentsList = response.assetContentsList;
                this.foundationContentsList = response.foundationContentsList;
                this.mainVisual = response.mainVisual;
                this.newsArticleList = response.newsArticleList;
                this.noticeArticleList = response.noticeArticleList;
                this.reportList = response.reportList;
            } catch (error){
                console.log(error);
            }
        },
        async fetchCalendar() {
            try {
                await this.getCalendarList(this.yyyyMm);
                await this.getTodayCalendar(this.searchDt);
                await this.loadCalendarCode();
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        handleDateClick: function(arg) { // date 클릭시 이벤트 처리
            alert("handleDateClick : " + arg.dateStr);
            this.getTodayCalendar(moment(arg.dateStr).format('YYYY.MM.DD'));
        },
        async getCalendarList(yyyyMm) {
            this.yyyyMm = !!yyyyMm ? yyyyMm : this.yyyyMm;
            const {
                data: { data: response },
            } = await getCalendarList({ yyyyMm: this.yyyyMm });
            this.calendarData = response;
            this.transformData();
        },
        async getTodayCalendar(searchDt) {
            console.log("getTodayCalendar")
            this.searchDt = !!searchDt ? searchDt : this.searchDt;
            const {
                data: { data: response },
            } = await getTodayCalendar({ searchDt: this.searchDt });
            this.todayData = response;
        },
        async loadCalendarCode() {
            const {
                data: { data: response },
            } = await getCode('CALANDAR_TYPE');
            this.calenderSectionCodeList = response;
        },
        transformData: function () {
            this.calendarOptions.events = [];
            this.calendarData.forEach((item) => {
                this.calendarOptions.events.push({
                    ...item,
                    title: item.scheduleName,
                    description: item.contents,
                    start: moment(item.beginDt).format('YYYY-MM-DD'),
                    end: moment(item.endDt).add(1, 'days').format('YYYY-MM-DD'),
                });
            });
        }
    }
};
</script>
<style scoped></style>
