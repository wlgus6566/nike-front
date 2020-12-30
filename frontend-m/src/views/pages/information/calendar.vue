<template>
    <div>
        <div class="fullCalendar-wrap">
            <FullCalendar
                ref="fullCalendar"
                :options="calendarOptions"
                defaultView="month"
                :editable="false"
            />
            <ul class="schedule-type">
                <li class="campaign">캠페인 런칭</li>
                <li class="upload">자료 업로드일</li>
                <li class="edu">교육</li>
                <li class="official">기타</li>
            </ul>
            <h3 class="schedule-title">{{ searchDt }}</h3>
            <ul class="schedule-list">
                <li class="schedule-item no-data" v-if="todayData.length == 0">
                    <p>등록된 일정이 없습니다.</p>
                </li>
                <li
                    class="schedule-item"
                    :class="{
                        edu: item.calendarSectionCode === 'EDUCATION',
                        campaign: item.calendarSectionCode === 'CAMPAIGN',
                        upload: item.calendarSectionCode === 'UPLOAD_DATE',
                        official: item.calendarSectionCode === 'ETC',
                    }"
                    v-for="item in todayData"
                    :key="item.calendarSeq"
                    v-else
                >
                    <div class="content">
                        <h4 class="title">{{ item.scheduleName }}</h4>
                        <p class="desc">{{ item.contents }}</p>
                    </div>
                    <div class="info">
                        <span class="date">
                            {{ item.beginDt }} - {{ item.endDt }}</span
                        >
                    </div>
                </li>
            </ul>
            <p class="desc">
                * 각 해당일은 예정일로 일부 변경, 지연 될 수 있는 점 감안 부탁
                드립니다.
            </p>
        </div>
        <CalendarModal
            v-if="visible.calendar"
            :visible.sync="visible.calendar"
            :calendarData="calendarDetailData"
        ></CalendarModal>
    </div>
</template>

<script>
import {
    getCalendarList, // CALENDAR 목록 조회
    getTodayCalendar, // CALENDAR 오늘 조회
} from '@/api/calendar';

import { getCode } from '@/api/code';

import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';
import CalendarModal from '@/views/pages/information/calendar-detail.vue';

export default {
    name: 'calendar',
    data() {
        return {
            visible: {
                calendar: false,
            },
            calendarDialogInitData: {
                calendarSectionCode: 'EDUCATION',
                scheduleName: null,
                beginDt: null,
                endDt: null,
                contents: null,
            },
            calendarSeq: null,
            loadingData: false,
            yyyyMm: !!this.$route.query.yyyyMm
                ? this.$route.query.yyyyMm
                : this.$moment(new Date()).format('YYYY.MM'),
            searchDt: !!this.$route.query.searchDt
                ? this.$route.query.searchDt
                : this.$moment(new Date()).format('YYYY.MM.DD'),
            currentDate: this.$moment(new Date()).format('YYYY.MM.DD'),
            statusCode: null,
            calendarDetail: {},
            calenderSectionCodeList: [],
            calendarData: [],
            calendarDetailData: null,
            todayData: [],
            calendarOptions: {
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                initialView: 'dayGridMonth',
                //dateClick: this.handleDateClick,
                eventClick: this.eventClickEvent,
                height: 'auto',
                events: [],
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
                            this.getCalendarList(
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
                            this.getCalendarList(
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
    watch: {
        calenderSectionCodeList() {},
    },
    components: {
        FullCalendar,
        CalendarModal,
    },
    mounted() {
        this.fetchData();
        let calendarApi = this.$refs.fullCalendar.getApi();
        calendarApi.gotoDate(this.searchDt.replace(/\./gi, '-'));
    },
    methods: {
        eventClickEvent(e) {
            console.log(e.event);
            this.calendarDetailData = e.event;
            this.visible.calendar = true;
        },
        async fetchData() {
            this.loadingData = true;
            try {
                await this.getCalendarList(this.yyyyMm);
                await this.getTodayCalendar(this.searchDt);
                this.loadingData = false;
                await this.loadCalendarCode();
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        // 한달 일정 조회
        async getCalendarList(yyyyMm) {
            this.yyyyMm = !!yyyyMm ? yyyyMm : this.yyyyMm;
            const {
                data: { data: response },
            } = await getCalendarList({ yyyyMm: this.yyyyMm });
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
        // 달력에 일자 클릭시
        /*handleDateClick(arg) {
            this.getTodayCalendar(
                this.$moment(arg.dateStr).format('YYYY.MM.DD')
            );
            const date = this.$moment(arg.date).format('YYYY-MM-DD');
            const cal = this.$refs.fullCalendar.$el;
            const td = cal.querySelector(`td[data-date="${date}"]`);
            cal.querySelectorAll('td').forEach(el => {
                el.classList.remove('fc-active');
            });
            td.classList.add('fc-active');
        },*/
        // 캘린더 코드 목록 조회
        async loadCalendarCode() {
            const {
                data: { data: response },
            } = await getCode('CALANDAR_TYPE');
            this.calenderSectionCodeList = response;
        },
    },
};
</script>
<style scoped></style>
