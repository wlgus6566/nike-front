<template>
    <div class="main-fc">
        <div class="main-visual">
            <span class="thumbnail">
                <img :src="mainVisual.mobileImageUrl" alt="" />
            </span>
            <strong class="title" v-text="mainVisual.title"></strong>
            <span class="desc" v-text="mainVisual.contents"> </span>
        </div>
        <h2 class="main-title">RECENT UPDATE</h2>
        <div class="main-update-list">
            <div
                class="update-list-item"
                v-for="item in toolKitContentsList"
                :key="item.contentsSeq"
            >
                <a
                    @click="
                        onClickDetail(
                            item,
                            `/${item.topMenuCode}/${item.menuCode}/${item.contentsSeq}`.toLocaleLowerCase()
                        )
                    "
                >
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <span class="label"> TOOLKIT </span>
                        <span class="desc" v-text="item.folderName">
                            SP20 나이키 다이렉트
                        </span>
                    </span>
                </a>
            </div>
            <div
                class="update-list-item"
                v-for="item in foundationContentsList"
                :key="item.contentsSeq"
            >
                <a
                    @click="
                        onClickDetail(
                            item,
                            `/${item.topMenuCode}/${item.menuCode}/${item.contentsSeq}`.toLocaleLowerCase()
                        )
                    "
                >
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <span class="label"> FOUNDATION </span>
                        <span class="desc" v-text="item.folderName">
                            SP20 나이키 다이렉트</span
                        >
                    </span>
                </a>
            </div>
        </div>
        <h2 class="main-title">NOTICE</h2>
        <ul class="notice-list">
            <li v-for="item in noticeMaxList" :key="item.noticeArticleSeq">
                <router-link
                    :to="'/mypage/notice/detail/' + item.noticeArticleSeq"
                >
                    <span class="label-noti" v-if="item.noticeYn === 'Y'"
                        >중요</span
                    >
                    <span class="title" v-text="item.title"
                        >NIKE 2020 PSKO 일정이 업데이트 되었습니다.</span
                    >
                    <span class="data" v-text="item.updateDt">2020.06.17</span>
                </router-link>
            </li>
        </ul>
        <h2 class="main-title">CALENDAR</h2>
        <div class="fullCalendar-wrap">
            <FullCalendar
                ref="fullCalendar"
                :options="calendarOptions"
                defaultView="month"
                :editable="false"
            />
        </div>
        <h2 class="main-title">REPORT</h2>
        <ul class="main-report-list">
            <li
                class="report-list-item"
                v-for="item in reportMaxList"
                :key="item.reportSeq"
            >
                <a
                    @click.prevent="
                        onClickDetail(item, '/report/detail/' + item.reportSeq)
                    "
                >
                    <span class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <strong class="title" v-text="item.nickname">
                            조던 서울조던
                        </strong>
                        <p class="desc" v-text="item.reportName">
                            코리아 팀 스니커즈 컬렉션 코리아 팀 스니커즈
                            컬렉션코리아 팀 스니커즈 컬렉션코리아 팀 스니커즈
                            컬렉션코리아 팀 스니커즈 컬렉션
                        </p>
                    </span>
                </a>
            </li>
        </ul>
        <h2 class="main-title">NEWS</h2>
        <swiper ref="mySwiper" :options="swiperOptions" class="main-news-list">
            <swiper-slide
                class="news-list-item"
                v-for="item in newsArticleList"
                :key="item.noticeArticleSeq"
            >
                <router-link
                    :to="'/mypage/news/detail/' + item.noticeArticleSeq"
                >
                    <span class="thumbnail">
                        <img :src="item.thumbnailFilePhysicalName" alt="" />
                    </span>
                    <span class="info-box">
                        <strong class="title" v-text="item.title"
                            >JORDAN SEOUL</strong
                        >
                        <!--                        <p class="desc" v-text="item.contents">조던 서울 오픈 포토 리캡</p>-->
                        <span class="date" v-text="item.updateDt"
                            >2020. 06. 17.</span
                        >
                    </span>
                </router-link>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
        </swiper>
        <CalendarModal
            v-if="visible.calendar"
            :visible.sync="visible.calendar"
            :calendarData="calendarData"
        ></CalendarModal>
    </div>
</template>
<script>
import { getMain } from '@/api/main';
import { getCalendarEachList, getTodayCalendar } from '@/api/calendar/';
import { directive, Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';

import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';
import CalendarModal from '@/views/pages/information/calendar-detail.vue';
export default {
    name: 'MainPage',
    data() {
        return {
            calendarData: null,
            visible: {
                calendar: false,
            },
            swiperOptions: {
                pagination: {
                    el: '.swiper-pagination',
                    type: 'fraction',
                },
                // Some Swiper option/callback...
            },
            noticeLength: 5,
            reportLength: 2,
            num: 0,
            loading: false,
            assetContentsList: [],
            foundationContentsList: [],
            mainVisual: '',
            newsArticleList: [],
            noticeArticleList: [],
            reportList: [],
            toolKitContentsList: [],
            todayData: [],
            yyyyMm: this.$moment(new Date()).format('YYYY.MM'),
            calendarOptions: {
                height: 'auto',
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                initialView: 'dayGridMonth',
                // 일자 클릭시
                // dateClick: this.handleDateClick,
                //moreLinkClick: this.calClickEvent,
                eventClick: this.eventClickEvent,
                //eventMouseEnter:this.mouserOverEvent,
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
    created() {
        this.calLendarFetchData();
    },
    mounted() {
        this.fetchData();
        this.swiperFn();
    },
    computed: {
        swiper() {
            return this.$refs.mySwiper.$swiper;
        },
        noticeMaxList() {
            const start = this.num * this.noticeLength,
                end = start + this.noticeLength;
            return this.noticeArticleList.slice(start, end);
        },
        reportMaxList() {
            const start = this.num * this.reportLength,
                end = start + this.reportLength;
            return this.reportList.slice(start, end);
        },
    },
    components: { FullCalendar, Swiper, SwiperSlide, CalendarModal },
    directives: {
        swiper: directive,
    },
    methods: {
        eventClickEvent(e) {
            let end = new Date(e.event.end);
            end.setDate(end.getDate() - 1);
            this.calendarData = e.event;
            const beginyear = e.event.startStr.substr(0, 4);
            const beginmonth = e.event.startStr.substr(5, 2);
            const beginday = e.event.startStr.substr(8, 2);

            const endnyear = end.getFullYear();
            let endnmonth = end.getMonth() + 1;
            endnmonth = endnmonth >= 10 ? endnmonth : '0' + endnmonth;
            let endnday = end.getDate();
            endnday = endnday >= 10 ? endnday : '0' + endnday;

            this.calendarData.beginDt =
                beginyear + '.' + beginmonth + '.' + beginday;
            this.calendarData.endDt =
                endnyear + '.' + endnmonth + '.' + endnday;
            this.visible.calendar = true;
        },
        onClickDetail(item, url) {
            if (item.detailAuthYn === 'N') {
                alert('접근 권한이 없습니다.');
            } else {
                this.$router.push(url);
            }
        },
        swiperFn() {
            this.swiper.slideTo(0, 1000, false);
        },
        async fetchData() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getMain({
                    mobile: 'Y',
                });
                this.assetContentsList = response.assetContentsList;
                this.foundationContentsList = response.foundationContentsList;
                this.mainVisual = response.mainVisual;
                this.newsArticleList = response.newsArticleList;
                this.noticeArticleList = response.noticeArticleList;
                this.toolKitContentsList = response.toolKitContentsList;
                this.reportList = response.reportList;
            } catch (error) {
                console.log(error);
            }
        },
        /* handleScroll() {
            const body = document.querySelector('.fc-daygrid-body');
            const cal = this.$refs.fullCalendar.$el;
            console.log(cal);
            if (body.childNodes[1]) {
                body.classList.remove('pop-open');
                cal.querySelectorAll('td').forEach(el => {
                    el.classList.remove('fc-active');
                });
                body.removeChild(body.childNodes[1]);
                window.removeEventListener('scroll', this.handleScroll);
                window.removeEventListener('resize', this.handleScroll);
            }
        },*/
        /*  calClickEvent(e) {
        const body = document.querySelector('.fc-daygrid-body');
        const tdWidth = e.jsEvent.target.closest('td').offsetWidth / 2;
        const date = this.$moment(e.date).format('YYYY-MM-DD');
        const cal = this.$refs.fullCalendar.$el;
        const td = cal.querySelector(`td[data-date="${date}"]`);
        body.classList.add('pop-open');
        cal.querySelectorAll('td').forEach(el => {
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
            //modal.style.marginLeft = `${tdWidth}px`;
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
    },*/
        // 달력 초기 목록 호출
        async calLendarFetchData() {
            this.loadingData = true;
            try {
                await this.getCalendarEachList(this.yyyyMm);
                //await this.getTodayCalendar(this.searchDt);
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
        // 해당 날짜 일정 조회
        async getTodayCalendar(searchDt) {
            this.searchDt = !!searchDt ? searchDt : this.searchDt;
            const {
                data: { data: response },
            } = await getTodayCalendar({ searchDt: this.searchDt });
            this.todayData = response;
        },
        // 달력에 맞게 변수명 변경
        transformData() {
            this.calendarOptions.events = [];
            this.calendarData.forEach(item => {
                let className;
                if (item.calendarSectionCode === 'EDUCATION') {
                    className = 'edu';
                } else if (item.calendarSectionCode === 'CAMPAIGN') {
                    className = 'campaign';
                } else if (item.calendarSectionCode === 'UPLOAD_DATE') {
                    className = 'upload';
                } else {
                    className = 'official';
                }

                this.calendarOptions.events.push({
                    ...item,
                    title: item.scheduleName,
                    description: item.contents,
                    start: item.beginDt.replace(/\./gi, '-'),
                    end: item.viewEndDt.replace(/\./gi, '-'),
                    className: className,
                    id: item.calendarSeq,
                    constraint: item.contents,
                });
            });
        },
        /*    distinctAndAddEvent() {
        let distinctEventList = [];
        this.calendarOptions.events.forEach(item => {
            let check = false;
            distinctEventList.forEach(ele => {
                if (item.start === ele.start) {
                    check = true;
                }
            });
            if (!check) {
                distinctEventList.push(item);
            }
        });
        distinctEventList.forEach(item => {
            this.calendarOptions.events.unshift(item);
        });
    },*/
        /*
    calendar-management 관련 메소드
 */
        /*  async createCalendar(data) {
            try {
                const { data: response } = await postCalendar(data);
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.success) {
                    this.processAfterSuccess();
                }
            } catch (error) {
                console.error(error);
            }
        },*/
    },
};
</script>
<style scoped>
/*
::v-deep .fc .fc-more-popover {
    margin-top: 41px;
    !*transform: translateX(-50%);*!
}
::v-deep .fc {
    margin: 15px -20px 0;
    padding: 25px 10px 0;
    border-top: 1px solid #eee;
    border-bottom: 1px solid #eee;
}
::v-deep .fc .fc-toolbar.fc-header-toolbar {
    margin: 0 0 18px !important;
}
::v-deep .fc .fc-header-toolbar .fc-toolbar-title {
    margin: 0 22px;
}
::v-deep .fc .fc-col-header-cell-cushion {
    padding: 9px 4px !important;
}
::v-deep .fc .fc-scrollgrid-liquid {
    border: none;
}
::v-deep .fc .fc-scroller-liquid-absolute {
    overflow: visible !important;
}
::v-deep .fc .fc-view-harness {
    height: 316px;
}
::v-deep .fc .fc-view-harness {
}
::v-deep .fc-theme-standard td,
::v-deep .fc-theme-standard th {
    border: none;
}
::v-deep .fc-theme-standard .fc-daygrid-day {
    padding-top: 10px;
}
::v-deep .fc .fc-h-event {
    line-height: 10px;
}
::v-deep .fc-daygrid-day-top {
    display: flex;
    min-height: 40px;
    justify-content: center;
    align-items: center;
}
::v-deep .fc-daygrid-day-bottom {
    margin: 0;
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
}
::v-deep .fc .fc-daygrid-day.fc-active .fc-daygrid-day-number,
::v-deep .fc .fc-daygrid-day.fc-day-today .fc-daygrid-day-number {
    margin: 0;
}
::v-deep .fc-daygrid-more-link {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 5;
    display: block;
    width: 100%;
    height: 40px;
    !*font: 0/0 a;*!
    text-indent: -99999px;
}
::v-deep .fc-daygrid-more-link:before {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    content: '';
    display: block;
    width: 4px;
    height: 4px;
    border-radius: 50%;
    background: #fa5400;
}
!*fc-daygrid-event fc-daygrid-block-event fc-h-event fc-event fc-event-start fc-event-end fc-event-past*!
::v-deep .fc .fc-daygrid-day .fc-daygrid-event-harness .fc-daygrid-event {
    !*display: none;*!
}
::v-deep .fc .fc-daygrid-day .fc-daygrid-event-harness {
    visibility: hidden;
}*/
</style>
