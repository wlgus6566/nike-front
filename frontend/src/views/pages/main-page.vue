<template>
    <div>
        <div class="main-banner">
            <div class="thumbnail">
                <!--img src="http://placehold.it/820X410" alt="이미지없음" />-->
                <img
                    :src="mainData.mainVisual.pcImageUrl"
                    :alt="mainData.mainVisual.imageFileName"
                    v-if="mainData.mainVisual.pcImageUrl"
                />
            </div>
            <div class="info-box">
                <strong class="title">
                    {{ mainData.mainVisual.title }}
                </strong>
                <p class="desc">
                    {{ mainData.mainVisual.contents }}
                </p>

                <a :href="`${mainData.mainVisual.linkUrl}`" class="btn-s-black">
                    <span class="bebas">VIEW</span>
                </a>
            </div>
        </div>
        <h2 class="main-title">RECENT UPDATE</h2>
        <ul class="main-update-list">
            <li
                v-for="assetItem in mainData.assetContentsList"
                :key="assetItem.contentsSeq"
            >
                <router-link
                    :to="`/asset/${assetItem.menuCode.toLocaleLowerCase()}/${
                        assetItem.contentsSeq
                    }`"
                >
                    <span class="thumbnail">
                        <img
                            :src="assetItem.imageFilePhysicalName"
                            :alt="assetItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ assetItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ assetItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
            <li
                v-for="toolKitItem in mainData.toolKitContentsList"
                :key="toolKitItem.contentsSeq"
            >
                <router-link
                    :to="`/toolKit/${toolKitItem.menuCode.toLocaleLowerCase()}/${
                        toolKitItem.contentsSeq
                    }`"
                >
                    <span class="thumbnail">
                        <img
                            :src="toolKitItem.imageFilePhysicalName"
                            :alt="toolKitItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ toolKitItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ toolKitItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
            <li
                v-for="foundationItem in mainData.foundationContentsList"
                :key="foundationItem.contentsSeq"
            >
                <router-link
                    :to="`'/foundation/${foundationItem.menuCode.toLocaleLowerCase()}/${
                        foundationItem.contentsSeq
                    }'`"
                >
                    <span class="thumbnail">
                        <img
                            :src="foundationItem.imageFilePhysicalName"
                            :alt="foundationItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ foundationItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ foundationItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>

        <div class="board-box">
            <div class="inner">
                <h2 class="main-title">NOTICE</h2>
                <ul class="main-notice-list">
                    <li
                        :class="{ noti: noticeItem.noticeYn === 'Y' }"
                        v-for="noticeItem in mainData.noticeArticleList"
                        :key="noticeItem.noticeArticleSeq"
                    >
                        <router-link
                            :to="`/mypage/notice/detail/${noticeItem.noticeArticleSeq}`"
                        >
                            <span class="title">
                                {{ noticeItem.title }}
                            </span>
                            <span class="date">
                                {{ noticeItem.updateDt }}
                            </span>
                        </router-link>
                    </li>
                </ul>
            </div>
            <div class="inner">
                <h2 class="main-title">CALENDAR</h2>
                <div>
                    <FullCalendar
                        ref="fullCalendar"
                        :options="calendarOptions"
                        defaultView="month"
                        :editable="false"
                    />
                </div>
            </div>
        </div>

        <h2 class="main-title">REPORT</h2>
        <ul class="main-report-list">
            <li
                v-for="repoertItem in mainData.reportList"
                :key="repoertItem.reportSeq"
            >
                <router-link :to="`/report/${repoertItem.readCount}`">
                    <span class="thumbnail">
                        <img
                            :src="repoertItem.imageFilePhysicalName"
                            :alt="repoertItem.reportName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">
                            리포트 계정명 필요
                        </strong>
                        <p class="desc">
                            {{ repoertItem.reportName }}
                        </p>
                        <span class="date">
                            {{ repoertItem.updateDt }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>
        <h2 class="main-title">NEWS</h2>
        <ul class="main-news-list">
            <li
                v-for="newItem in mainData.newsArticleList"
                :key="newItem.noticeArticleSeq"
            >
                <router-link
                    :to="`/mypage/news/detail/${newItem.noticeArticleSeq}`"
                >
                    <span class="thumbnail">
                        <img
                            :src="newItem.thumbnailFilePhysicalName"
                            :alt="newItem.thumbnailFileName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">
                            {{ newItem.title }}
                        </strong>
                        <!--                        <p class="desc" v-html="newItem.contents" />-->
                        <span class="date">
                            {{ newItem.updateDt }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>
    </div>
</template>
<script>
import { getMain } from '@/api/main';
import {
    getCalendarEachList, // CALENDAR 목록 조회
    getTodayCalendar, // CALENDAR 오늘 조회
} from '@/api/calendar';

import moment from 'moment';
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';

export default {
    name: 'MainPage',
    data() {
        return {
            mainData: [],
            todayData: [],
            yyyyMm: moment(new Date()).format('YYYY.MM'),
            calendarOptions: {
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                eventRender: function (info) {
                    var tooltip = new Tooltip(info.el, {
                        title: info.event.extendedProps.description,
                        placement: 'top',
                        trigger: 'hover',
                        container: 'body',
                    });
                },
                initialView: 'dayGridMonth',
                // 일자 클릭시
                // dateClick: this.handleDateClick,
                dateClick: this.handleDateClick,
                moreLinkClick: this.test,
                height: 500,
                events: [],
                dayMaxEventRows: true,
                timeGrid: {
                    dayMaxEventRows: 1,
                },
                headerToolbar: {
                    left: 'prev',
                    center: 'title',
                    right: 'next',
                },
                titleFormat: 'yyyy.M',
                customButtons: {
                    prev: {
                        // this overrides the prev button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.prev();
                            this.getCalendarEachList(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                        },
                    },
                    next: {
                        // this overrides the next button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.next();
                            this.getCalendarEachList(
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                        },
                    },
                },
            },
        };
    },
    components: {
        FullCalendar,
    },
    created() {
        this.main();
        this.loadCalendar();
    },
    methods: {
        test(e) {
            console.log(e);
            const date = this.$moment(e.date).format('YYYY-MM-DD');
            const cal = this.$refs.fullCalendar.$el;
            const td = cal.querySelector(`td[data-date="${date}"]`);
            td.classList.add('test');

            setTimeout(() => {
                const modal = document.querySelector('.fc-more-popover');
                const close = modal.querySelector('.fc-popover-close');
                const body = modal.querySelector('.fc-popover-body');
                body.append('<a>자세히 보기?</a>');
                close.addEventListener('click', () => {
                    td.classList.remove('test');
                });
                console.log();
            }, 0);
        },

        async main() {
            try {
                const {
                    data: { data: response },
                } = await getMain();
                this.mainData = response;
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        // 달력 초기 목록 호출
        async loadCalendar() {
            this.loadingData = true;
            try {
                await this.getCalendarEachList(this.yyyyMm);
                this.loadingData = false;
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        // 한달 일정 조회
        async getCalendarEachList(yyyyMm) {
            this.yyyyMm = !!yyyyMm ? yyyyMm : this.yyyyMm;
            const {
                data: { data: response },
            } = await getCalendarEachList({ yyyyMm: this.yyyyMm });
            this.calendarData = response;
            this.transformData();
        },
        // 달력에 맞게 변수명 변경
        transformData() {
            this.calendarOptions.events = [];
            this.calendarData.forEach((item) => {
                let color;
                if (item.calendarSectionCode === 'EDUCATION') {
                    color = '#be1767';
                } else if (item.calendarSectionCode === 'CAMPAIGN') {
                    color = '#007b68';
                } else {
                    color = '#2c0fb4';
                }
                this.calendarOptions.events.push({
                    ...item,
                    title: item.scheduleName,
                    description: item.contents,
                    start: moment(item.beginDt).format('YYYY-MM-DD'),
                    end: moment(item.endDt).add(1, 'days').format('YYYY-MM-DD'),
                    color: color,
                    checkDuple: false,
                });
            });
            this.distinctAndAddEvent();
        },
        distinctAndAddEvent() {
            let distinctEventList = [];
            this.calendarOptions.events.forEach((item) => {
                let check = false;
                distinctEventList.forEach((ele) => {
                    if (item.start === ele.start) {
                        check = true;
                    }
                });
                if (!check) {
                    distinctEventList.push(item);
                }
            });
            distinctEventList.forEach((item) => {
                this.calendarOptions.events.unshift(item);
            });
        },
        // 달력에 일자 클릭시
        // handleDateClick(arg) {
        //     console.log('click : ', arg);
        //     // this.getTodayCalendar(moment(arg.dateStr).format('YYYY.MM.DD'));
        // },
        async getTodayCalendar(searchDt) {
            this.searchDt = !!searchDt ? searchDt : this.searchDt;
            const {
                data: { data: response },
            } = await getTodayCalendar({ searchDt: this.searchDt });
            this.todayData = response;
        },
    },
};
</script>
<style scoped>
/* main */
.main-banner {
    display: block;
}
.main-banner .thumbnail img {
    width: 100%;
    vertical-align: top;
}
.main-banner .info-box {
    position: relative;
    margin-top: 30px;
    padding-right: 228px;
}
.main-banner .info-box .title {
    display: block;
    font-size: 40px;
    line-height: 60px;
    letter-spacing: -2px;

    word-break: keep-all;
}

.main-banner .info-box .desc {
    display: block;
    margin-top: 4px;
    font-size: 14px;
    color: #555;
    line-height: 20px;
}
.main-banner .info-box [class^='btn-s'] {
    position: absolute;
    top: 0;
    right: 0;
}
.main-title {
    display: block;
    margin-top: 60px;
    line-height: 36px;
    font-size: 30px;
    font-family: 'Bebas Neue', sans-serif;
    font-weight: normal;
    letter-spacing: 0.5px;
}
.main-update-list {
    overflow: hidden;
    margin-top: 20px;
}
.main-update-list li:nth-child(1) {
    width: calc(50% - 10px);
}
.main-update-list li:nth-child(1),
.main-update-list li:nth-child(6) {
    margin-left: 0;
}
.main-update-list li:nth-child(-n + 3) {
    margin-top: 0;
}
.main-update-list li {
    float: left;
    width: calc((100% - 60px) / 4);
    margin: 20px 0 0 20px;
}
.main-update-list li a {
    display: block;
    position: relative;
}
.main-update-list li .thumbnail {
    position: relative;
    display: block;
    padding-top: 100%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-update-list li img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
.main-update-list li a .info-box {
    position: absolute;
    top: 0;
    left: 0;
}
.main-update-list li a .info-box .desc {
    display: none;
}
.main-update-list li .info-box .label {
    display: block;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    padding: 10px 12px;
    font-size: 15px;
    line-height: 17px;
    letter-spacing: 0.5px;
    color: #fff;
}
.main-update-list li a:hover .info-box {
    top: 50%;
    left: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    height: 100%;
    transform: translate(-50%, -50%);
}
.main-update-list li a:hover .info-box:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.7);
}
.main-update-list li a:hover .info-box .label {
    z-index: 2;
    position: relative;
    padding: 0 12px;
    text-align: center;
    font-size: 24px;
    line-height: 29px;
    letter-spacing: 0.6px;
}
.main-update-list li a:hover .info-box .desc {
    z-index: 2;
    position: relative;
    display: block;
    margin-top: 10px;
    padding: 0 30px;
    text-align: center;
    font-size: 14px;
    line-height: 20px;
    letter-spacing: 0;
    color: #fff;
    word-break: keep-all;
}
.board-box {
    overflow: hidden;
}
.board-box > .inner {
    float: left;
    width: calc((100% - 20px) / 2);
}
.board-box > .inner + .inner {
    margin-left: 20px;
}
.main-notice-list {
    margin-top: 20px;
    border: 1px solid #eee;
    border-radius: 2px;
}
.main-notice-list li + li {
    border-top: 1px solid #eee;
}
.main-notice-list li.noti .title {
    font-weight: bold;
    color: #333;
}
.main-notice-list li a {
    position: relative;
    display: block;
    overflow: hidden;
    padding: 0 110px 0 20px;
}
.main-notice-list li a .title {
    display: block;
    height: 50px;
    line-height: 50px;
    font-size: 12px;
    color: #555;
    text-overflow: ellipsis;
    word-wrap: normal;
    overflow: hidden;
    white-space: nowrap;
}
.main-notice-list li a .date {
    position: absolute;
    top: 50%;
    right: 20px;
    display: block;
    line-height: 50px;
    font-size: 10px;
    color: #555;
    transform: translateY(-50%);
}

.main-report-list {
    display: flex;
    margin-top: 20px;
}

.main-report-list li {
    width: calc((100% - 40px) / 3);
}
.main-report-list li + li {
    margin-left: 20px;
}
.main-report-list li a {
    position: relative;
    display: block;
}
.main-report-list .thumbnail {
    position: relative;
    display: block;
    width: 100%;
    padding-top: 100%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-report-list .thumbnail img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.main-report-list .info-box {
    position: absolute;
    bottom: 0;
    left: 0;
    display: block;
    width: 100%;
    box-sizing: border-box;
    padding: 10px 20px;
    background-color: rgba(0, 0, 0, 0.6);
}
.main-report-list .info-box .title {
    display: block;
    font-size: 12px;
    line-height: 18px;
    font-weight: bold;
    color: #fff;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-report-list .info-box .desc {
    display: block;
    margin-top: 1px;
    padding-right: 70px;
    font-size: 10px;
    line-height: 15px;
    color: #fff;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-report-list .info-box .date {
    position: absolute;
    bottom: 10px;
    right: 20px;
    display: block;
    font-size: 10px;
    line-height: 11px;
    color: #fff;
}

.main-news-list {
    display: flex;
    margin-top: 20px;
    flex-wrap: wrap;
}

.main-news-list li {
    flex: 0 0 calc(50% - 10px);
    width: calc(50% - 10px);
}
.main-news-list li + li {
    margin-left: 20px;
}
.main-news-list li a {
    position: relative;
    display: block;
}
.main-news-list .thumbnail {
    position: relative;
    display: block;
    width: 100%;
    padding-top: 47.5%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-news-list .thumbnail img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.main-news-list .info-box {
    display: block;
    box-sizing: border-box;
    padding: 20px 5px;
}
.main-news-list .info-box .title {
    display: block;
    font-size: 16px;
    line-height: 24px;
    font-weight: bold;
    color: #000;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-news-list .info-box .desc {
    display: block;
    margin-top: 8px;
    font-size: 12px;
    line-height: 18px;
    color: #555;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-news-list .info-box .date {
    display: block;
    margin-top: 15px;
    font-size: 12px;
    line-height: 14px;
    color: #888;
}
::v-deep .fc .fc-more-popover {
    margin-top: 20px;
}
::v-deep .test {
    background: red;
}

::v-deep .fc-daygrid-day-bottom {
    width: 100%;
}
::v-deep .fc-daygrid-more-link {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    text-indent: -99999px;
}
::v-deep .fc-daygrid-more-link:before {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    content: '';
    display: block;
    width: 3px;
    height: 3px;
    border-radius: 100%;
    background: #fa5400;
}
::v-deep .fc-popover-body .fc-daygrid-event-harness:first-child {
    display: none;
}
</style>
