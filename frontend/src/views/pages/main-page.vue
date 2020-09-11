<template>
    <div v-if="mainData">
        <div class="main-banner">
            <div class="thumbnail">
                <img
                    :src="mainData.mainVisual.pcImageUrl"
                    :alt="mainData.mainVisual.imageFileName"
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
                    :to="setUrl(assetItem)"
                    @click.native="alertMsg(assetItem)"
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
                    :to="setUrl(toolKitItem)"
                    @click.native="alertMsg(toolKitItem)"
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
                    :to="setUrl(foundationItem)"
                    @click.native="alertMsg(foundationItem)"
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
                <div class="main-fc">
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
                v-for="reportItem in mainData.reportList"
                :key="reportItem.reportSeq"
            >
                <router-link
                    :to="setUrl(reportItem)"
                    @click.native="alertMsg(reportItem)"
                >
                    <span class="thumbnail">
                        <img
                            :src="reportItem.imageFilePhysicalName"
                            :alt="reportItem.reportName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">
                            {{ reportItem.nickname }}
                        </strong>
                        <p class="desc">
                            {{ reportItem.reportName }}
                        </p>
                        <span class="date">
                            {{ reportItem.updateDt }}
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

import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';

export default {
    name: 'MainPage',
    data() {
        return {
            mainData: null,
            todayData: [],
            yyyyMm: this.$moment(new Date()).format('YYYY.MM'),
            calendarOptions: {
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                initialView: 'dayGridMonth',
                // 일자 클릭시
                // dateClick: this.handleDateClick,
                dateClick: this.handleDateClick,
                moreLinkClick: this.calClickEvent,
                height: 358,
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
                titleFormat: 'yyyy.MM',
                customButtons: {
                    prev: {
                        // this overrides the prev button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.prev();
                            this.getCalendarEachList(
                                this.$moment(calendarApi.getDate()).format(
                                    'YYYY.MM'
                                )
                            );
                        },
                    },
                    next: {
                        // this overrides the next button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.next();
                            this.getCalendarEachList(
                                this.$moment(calendarApi.getDate()).format(
                                    'YYYY.MM'
                                )
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
    activated() {
        this.main();
    },
    methods: {
        alertMsg(item) {
            if (item.detailAuthYn === 'N') {
                alert('접근 권한이 없습니다.');
            }
        },
        setUrl(item) {
            if (item.detailAuthYn === 'N') {
                return `${this.$route.fullPath}`;
            } else {
                if (item.reportSeq) {
                    return `/report/detail/${item.reportSeq}`.toLocaleLowerCase();
                } else {
                    return `/${item.topMenuCode}/${item.menuCode}/${item.contentsSeq}`.toLocaleLowerCase();
                }
            }
        },
        handleScroll() {
            const body = document.querySelector('.fc-daygrid-body');
            const cal = this.$refs.fullCalendar.$el;

            if (body.childNodes[1]) {
                body.classList.remove('pop-open');
                cal.querySelectorAll('td').forEach((el) => {
                    el.classList.remove('fc-active');
                });
                //body.removeChild(body.childNodes[1]);
                window.removeEventListener('scroll', this.handleScroll);
                window.removeEventListener('resize', this.handleScroll);
            }
        },
        calClickEvent(e) {
            //console.log('e / ', e);
            const body = document.querySelector('.fc-daygrid-body');
            //const tdWidth = e.jsEvent.target.closest('td').offsetWidth / 2; IE 지원 안함
            const date = this.$moment(e.date).format('YYYY-MM-DD');
            const cal = this.$refs.fullCalendar.$el;
            const td = cal.querySelector(`td[data-date="${date}"]`);
            body.classList.add('pop-open');
            cal.querySelectorAll('td').forEach((el) => {
                el.classList.remove('fc-active');
            });
            td.classList.add('fc-active');
            window.addEventListener('scroll', this.handleScroll);
            window.addEventListener('resize', this.handleScroll);
            setTimeout(() => {
                const modal = document.querySelector('.fc-more-popover');
                const close = modal.querySelector('.fc-popover-close');
                const body = modal.querySelector('.fc-popover-body');
                const a = document.createElement('a');
                const txt = document.createTextNode('자세히 보기');
                // modal.style.marginLeft = `${tdWidth}px`;
                a.href =
                    '/information/calendar?yyyyMm=' +
                    this.yyyyMm +
                    '&searchDt=' +
                    this.$moment(e.date).format('YYYY.MM.DD');
                a.classList.add('fc-more');
                a.appendChild(txt);
                body.appendChild(a);
                close.addEventListener('click', () => {
                    td.classList.remove('fc-active');
                });
            }, 0);
        },

        async main() {
            try {
                const {
                    data: { data: response },
                } = await getMain();
                this.mainData = response;
                //console.log(response);
            } catch (error) {
                console.error(error);
            }
        },
        // 달력 초기 목록 호출
        async loadCalendar() {
            this.loadingData = true;
            try {
                await this.getCalendarEachList(this.yyyyMm);
                this.loadingData = false;
            } catch (error) {
                console.error(error);
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
                let className;
                let tarnsformContents;
                if (item.calendarSectionCode === 'EDUCATION') {
                    className = 'edu';
                    tarnsformContents = '[교육] ' + item.scheduleName;
                } else if (item.calendarSectionCode === 'CAMPAIGN') {
                    className = 'campaign';
                    tarnsformContents = '[캠페인] ' + item.scheduleName;
                } else {
                    className = 'official';
                    tarnsformContents = '[기타 교육일정] ' + item.scheduleName;
                }
                this.calendarOptions.events.push({
                    ...item,
                    title: tarnsformContents,
                    description: item.contents,
                    start: item.beginDt.replace(/\./gi, '-'),
                    end: item.viewEndDt.replace(/\./gi, '-'),
                    className: className,
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
    width: 100%;
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
    text-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
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
    width: 100%;
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
    margin-top: 45px;
    /*transform: translateX(-50%);*/
    /*margin-left: -2px;*/
}
::v-deep .fc .fc-more-popover:before {
    /*position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: -5px;
    z-index: 1;
    content: '';
    display: inline-block;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-bottom: 5px solid #ccc;
    border-top: 0;*/
}
/*::v-deep .fc-active {*/
/*    background-color: #fa5400;*/
/*}*/
::v-deep .fc .fc-daygrid-day-number {
    font-weight: 400;
    font-size: 12px;
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
    height: 40px;
    font: 0/0 a;
    /*text-indent: -99999px;*/
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
