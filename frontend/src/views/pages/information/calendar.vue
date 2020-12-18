<template>
    <div>
        <h2 class="page-title">CALENDAR</h2>
        <div class="fullCalendar-wrap">
            <ul class="schedule-type">
                <li class="campaign">캠페인 런칭</li>
                <li class="upload">자료 업로드일</li>
                <li class="edu">교육</li>
                <li class="official">기타</li>
            </ul>
            <FullCalendar
                ref="fullCalendar"
                :options="calendarOptions"
                defaultView="month"
                :editable="false"
            />
            <p class="desc">
                * 각 해당일은 예정일로 일부 변경, 지연 될 수 있는 점 감안 부탁
                드립니다.
            </p>
        </div>

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
                    v-if="folderAuthCheck('CREATE')"
                >
                    <span>등록</span>
                </a>
            </div>
        </div>
        <h3 class="schedule-title">{{ searchDt }}</h3>
        <template v-if="todayData">
            <ul class="schedule-list" v-if="todayData.length !== 0">
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
                >
                    <div class="content">
                        <h4 class="title">
                            <strong>{{ item.scheduleName }}</strong>
                        </h4>
                        <p class="desc">{{ item.contents }}</p>
                    </div>

                    <div class="info">
                        <el-button
                            type="white"
                            class="btn-edit"
                            @click="onClickToEdit(item)"
                            v-if="folderAuthCheck('CREATE')"
                        >
                            수정하기
                        </el-button>
                        <span class="date">
                            {{ item.beginDt }} - {{ item.endDt }}</span
                        >
                    </div>
                </li>
            </ul>
            <ul class="schedule-list" v-else>
                <li class="schedule-item">
                    <div class="content">
                        <h4 class="title">등록된 일정이 없습니다.</h4>
                    </div>
                </li>
            </ul>
        </template>
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

import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';

import calendarManagement from '@/views/pages/information/calendar-management';
import { authCheck } from '@/utils/authCheck';

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
            todayData: [],
            calendarOptions: {
                height: 'auto',
                plugins: [dayGridPlugin, interactionPlugin, momentPlugin],
                initialView: 'dayGridMonth',
                dateClick: this.handleDateClick,
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
    mixins: [authCheck],
    watch: {
        calenderSectionCodeList() {},
    },
    components: {
        FullCalendar,
        calendarManagement,
    },
    mounted() {
        this.fetchData();
        let calendarApi = this.$refs.fullCalendar.getApi();
        calendarApi.gotoDate(this.searchDt.replace(/\./gi, '-'));
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
                console.error(error);
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
                });
            });
        },
        // 달력에 일자 클릭시
        handleDateClick(arg) {
            if (document.querySelector('.fc-daygrid-day.active')) {
                document
                    .querySelector('.fc-daygrid-day.active')
                    .classList.remove('active');
            }
            arg.dayEl.classList.add('active');
            this.getTodayCalendar(
                this.$moment(arg.dateStr).format('YYYY.MM.DD')
            );
        },
        // 일정 등록 클릭시
        onClickToCreate() {
            this.statusCode = 'CREATE';
            this.calendarDetail = {
                ...this.calendarDialogInitData,
            };
            this.calendarSeq = null;
            this.visible.calendarManagement = true;
        },
        // 일정 수정 클릭시
        onClickToEdit(item) {
            console.log(item);
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
                console.error(error);
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
                console.error(error);
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
                console.error(error);
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
<style scoped>
::v-deep .fullCalendar-wrap .fc .fc-daygrid-body .active .fc-daygrid-day-number,
::v-deep .fc .fc-daygrid-day.fc-active .fc-daygrid-day-number,
::v-deep .fc .fc-daygrid-day.fc-day-today .fc-daygrid-day-number {
    width: 22px;
    height: 22px;
}
::v-deep .fc .fc-daygrid-day-top {
    padding-bottom: 3px;
}
/*TODO 주말 색 변경필오*/
.fc-day-sun.fc-daygrid-day-frame.fc-daygrid-day-top.fc-daygrid-day-number {
    color: #0000ff;
} /* 토요일 */
.fc-day-number.fc-sun.fc-past {
    color: #ff0000;
} /* 일요일 */
</style>
<link href="./fullcalendar-2.9.1/fullcalendar.css" rel="stylesheet" />
