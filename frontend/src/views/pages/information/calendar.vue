<template>
    <div>
        <h2 class="page-title">
            {{ this.$route.meta.title }}
        </h2>
        <FullCalendar
            ref="fullCalendar"
            :options="calendarOptions"
            defaultView="month"
            :editable="false"
        />

        <calendarManagement
            :visible.sync="visible.calendarManagement"
            :statusCode="statusCode"
            :calendarDetail="calendarDetail"
            :calendarSeq="calendarSeq"
            :calenderSectionCodeList="calenderSectionCodeList"
            @createCalendar="createCalendar"
            @modifyCalendar="modifyCalendar"
            @delCalendar="delCalendar"
            @closeDialog="closeDialog"
        />
        <div class="btn-tbl-box">
            <div class="right">
                <a
                    href="#"
                    size="small"
                    @click.prevent="onClickToCreate"
                    class="btn-form-gray"
                >
                    <span>등록</span>
                </a>
            </div>
        </div>

        <h3 class="schedule-title">{{ searchDt }}</h3>
        <ul class="schedule-list">
            <li
                class="schedule-item"
                v-for="item in todayData"
                :key="item.calendarSeq"
            >
                <div class="content">
                    <h4 class="title">{{ item.scheduleName }}</h4>
                    <p class="desc">{{ item.contents }}</p>
                </div>

                <div class="info">
                    <el-button
                        type="white"
                        class="btn-edit"
                        @click="onClickToEdit(item)"
                    >
                        수정하기
                    </el-button>
                    <span class="date">
                        {{ item.beginDt }} ~ {{ item.endDt }}</span
                    >
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import {
    getCalendarList, // CALENDAR 목록 조회
    postCalendar, // CALENDAR 등록
    getTodayCalendar, // CALENDAR 오늘 조회
    getDetailCalendar, // CALENDAR 상세조회
    delCalendar, // CALENDAR 삭제
    putCalendar, // CALENDAR 수정
} from '@/api/calendar';

import { getCode } from '@/api/code';

import moment from 'moment';
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

import calendarManagement from '@/views/pages/information/calendar-management';

export default {
    name: 'calendar',
    data() {
        return {
            visible: {
                calendarManagement: false,
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
                events: [],
                header: {
                    left: 'prev',
                    center: 'title',
                    right: 'next',
                },
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
            },
        };
    },
    components: {
        FullCalendar,
        calendarManagement,
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        // 초기 데이타 조회
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
            console.log(yyyyMm);
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
                this.calendarOptions.events.push({
                    ...item,
                    title: item.scheduleName,
                    description: item.contents,
                    start: moment(item.beginDt).format('YYYY-MM-DD'),
                    end: moment(item.endDt).add(1, 'days').format('YYYY-MM-DD'),
                });
            });
        },
        // 달력에 일자 클릭시
        handleDateClick(arg) {
            this.getTodayCalendar(moment(arg.dateStr).format('YYYY.MM.DD'));
        },
        // 일정 등록 클릭시
        onClickToCreate() {
            this.statusCode = 'CREATE';
            this.calendarDetail = {
                ...this.calendarDialogInitData,
            };
            this.visible.calendarManagement = true;
        },
        // 일정 수정 클릭시
        onClickToEdit(item) {
            this.statusCode = 'EDIT';
            this.calendarDetail = item;
            this.calendarSeq = item.calendarSeq;
            this.visible.calendarManagement = true;
        },
        // 캘린더 코드 목록 조회
        async loadCalendarCode() {
            const {
                data: { data: response },
            } = await getCode('CALANDAR_TYPE');
            this.calenderSectionCodeList = response;
        },

        /*
                calendar-management 관련 메소드
             */
        async createCalendar(data) {
            try {
                const { data: response } = await postCalendar(data);
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.success) {
                    this.processAfterSuccess();
                }
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        async modifyCalendar(calendarSeq, data) {
            try {
                const { data: response } = await putCalendar(calendarSeq, data);
                if (response.existMsg) {
                    alert(response.msg);
                }

                if (response.success) {
                    this.processAfterSuccess();
                }
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        async delCalendar(calendarSeq) {
            try {
                const { data: response } = await delCalendar(calendarSeq);
                if (response.existMsg) {
                    alert(response.msg);
                }

                if (response.success) {
                    this.processAfterSuccess();
                }
            } catch (error) {
                alert(error.response.data.msg);
            }
        },
        async processAfterSuccess() {
            await this.getCalendarList();
            await this.getTodayCalendar();
            this.closeDialog();
        },
        // 다이얼로드 닫기
        closeDialog() {
            this.visible.calendarManagement = false;
        },
    },
};
</script>
