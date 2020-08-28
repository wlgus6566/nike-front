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
                <li class="edu">교육</li>
                <li class="campaign">캠페인</li>
                <li class="official">기타 공개일정</li>
            </ul>
            <h3 class="schedule-title">{{ searchDt }}</h3>
            <ul class="schedule-list">
                <li
                    class="schedule-item"
                    :class="{ 'edu':item.calendarSectionCode === 'EDUCATION'
                        , 'campaign':item.calendarSectionCode === 'CAMPAIGN'
                        , 'official':item.calendarSectionCode === 'ETC'
                    }"
                    v-for="item in todayData"
                    :key="item.calendarSeq"
                >
                    <div class="content">
                        <h4 class="title">{{ item.scheduleName }}</h4>
                        <p class="desc">{{ item.contents }}</p>
                    </div>
                    <div class="info">
                        <span class="date">
                        {{ item.beginDt }} ~ {{ item.endDt }}</span
                        >
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import {
    getCalendarList, // CALENDAR 목록 조회
    getTodayCalendar, // CALENDAR 오늘 조회
} from '@/api/calendar';

import { getCode } from '@/api/code';

import moment from 'moment';
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';

export default {
    name: 'calendar',
    data() {
        return {
            calendarDialogInitData: {
                calendarSectionCode: 'EDUCATION',
                scheduleName: null,
                beginDt: null,
                endDt: null,
                contents: null,
            },
            calendarSeq: null,
            loadingData: false,
            yyyyMm: moment(new Date()).format('YYYY.MM'),
            searchDt: moment(new Date()).format('YYYY.MM.DD'),
            currentDate: moment(new Date()).format('YYYY.MM.DD'),
            statusCode: null,
            calendarDetail: {},
            calenderSectionCodeList: [],
            calendarData: [],
            todayData: [],
            calendarOptions: {
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                initialView: 'dayGridMonth',
                dateClick: this.handleDateClick,
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
                                moment(calendarApi.getDate()).format('YYYY.MM')
                            );
                        },
                    },
                    next: {
                        // this overrides the next button
                        click: () => {
                            let calendarApi = this.$refs.fullCalendar.getApi();
                            calendarApi.next();
                            this.getCalendarList(
                                moment(calendarApi.getDate()).format('YYYY.MM')
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
    },
    mounted() {
        this.fetchData();
    },
    methods: {
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
            this.calendarData.forEach((item) => {
                let className;
                if (item.calendarSectionCode === 'EDUCATION') {
                    className = 'edu';
                } else if (item.calendarSectionCode === 'CAMPAIGN') {
                    className = 'campaign';
                } else {
                    className = 'official';
                }
                this.calendarOptions.events.push({
                    ...item,
                    title: item.scheduleName,
                    description: item.contents,
                    start: item.beginDt.replace(/\./gi, "-"),
                    end: moment(item.endDt).add(1, 'days')._i.replace(/\./gi, "-"),
                    className: className,
                });
            });
        },
        // 달력에 일자 클릭시
        handleDateClick(arg) {
            this.getTodayCalendar(moment(arg.dateStr).format('YYYY.MM.DD'));
        },
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
