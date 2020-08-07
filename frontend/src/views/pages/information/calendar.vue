<template>
    <div>
        <div>
            <span>
                <el-button type="white" size="small" @click="onClickToCreate">
                    등록
                </el-button>
            </span>
        </div>

        <FullCalendar :options="calendarOptions"/>
        <calendarManagement
            :visible.sync="visible.calendarManagement"
            :statusCode="statusCode"
            :calendarDetail="calendarDetail"
            :calenderSectionCodeList="calenderSectionCodeList"

            @createCalendar="createCalendar"
            @modifyCalendar="modifyCalendar"
            @delCalendar="delCalendar"
            @closeDialog="closeDialog"
        />

        <ul class="item-list">
            <span>{{this.searchDt}}</span>
            <li class="item"
                v-for="item in todayData"
                :key="item.calendarSeq"
            >
                <div class="info-box">
                    <p class="title">{{item.scheduleName}}</p>
                    <span class="date"> {{item.contents}} / {{item.beginDt}}</span> ~ <span>{{item.endDt}}</span>
                    <span>
                        <el-button type="white" size="small" @click="onClickToEdit(item)">
                            수정
                        </el-button>
                    </span>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    import {
        getCalendarList  // CALENDAR 목록 조회
        , postCalendar  // CALENDAR 등록
        , getTodayCalendar  // CALENDAR 오늘 조회
        , getDetailCalendar  // CALENDAR 상세조회
        , delCalendar  // CALENDAR 삭제
        , putCalendar // CALENDAR 수정
    } from '@/api/calendar'

    import {
        getCode
    } from '@/api/code'

    import moment from 'moment'
    import FullCalendar from '@fullcalendar/vue'
    import dayGridPlugin from '@fullcalendar/daygrid'
    import interactionPlugin from '@fullcalendar/interaction'

    import calendarManagement from '@/views/pages/information/calendar-management'

    export default {
        name: 'calendar',
        data () {
            return {
                yyyyMm: moment(new Date()).format('YYYY.MM'),
                searchDt: moment(new Date()).format('YYYY.MM.DD'),
                statusCode: null,
                calendarDetail: {},
                calenderSectionCodeList: [],
                calendarData: [],
                todayData: [],
                calendarOptions: {
                    plugins: [ dayGridPlugin, interactionPlugin ],
                    initialView: 'dayGridMonth',
                    dateClick: this.handleDateClick,
                    events: [],
                    eventClick: this.handleEventClick,
                    headerToolbar: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'dayGridMonth'
                    }
                },
                visible: {
                    calendarManagement: false,
                },
                calendarDialogInitData: {
                    calendarSeq: null,
                    calendarSectionCode: 'EDUCATION',
                    scheduleName: null,
                    beginDt: null,
                    endDt: null,
                    contents: null
                },
                loadingData: false
            }
        },
        components: {
            FullCalendar
            , calendarManagement
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
                } catch (error) {
                    console.log(error);
                }
                this.loadingData = false;
                await this.loadCalendarCode();
            },
            // 한달 일정 조회
            async getCalendarList(yyyyMm) {
                const { data: { data: response } }
                    = await getCalendarList({ yyyyMm: yyyyMm });
                this.calendarData = response;
                this.transformData();
            },
            // 해당 날짜 일정 조회
            async getTodayCalendar(searchDt) {
                const { data: { data: response } }
                    = await getTodayCalendar({searchDt: searchDt});
                this.todayData = response;
            },
            // 달력에 맞게 변수명 변경
            transformData() {
                this.calendarOptions.events = [];
                this.calendarData.forEach(item => {
                    this.calendarOptions.events.push({
                        ...item,
                        title: item.scheduleName,
                        description: item.contents,
                        start: moment(item.beginDt).format('YYYY-MM-DD'),
                        end: moment(item.endDt).add(1, 'days').format('YYYY-MM-DD')
                    });
                });
            },
            // 달력에 일자 클릭시
            handleDateClick(arg) {
                this.getTodayCalendar(moment(arg.dateStr).format('YYYY.MM.DD'));
            },
            handleEventClick(clickInfo) {
                console.log('handleEventClick', clickInfo);
            },
            // 일정 등록 클릭시
            onClickToCreate() {
                this.statusCode = "CREATE"
                this.calendarDetail = this.calendarDialogInitData;
                this.visible.calendarManagement = true;
            },
            // 일정 수정 클릭시
            onClickToEdit(item) {
                this.statusCode = "EDIT"
                this.calendarDetail = item;
                this.visible.calendarManagement = true;
            },
            // 캘린더 코드 목록 조회
            async loadCalendarCode() {
                const { data: { data: response } }
                    = await getCode("CALANDAR_TYPE");
                this.calenderSectionCodeList = response;
            },

            // calendar-management 관련 메소드
            async createCalendar(data) {
                try {
                    console.log('create', data)
                    const { data: response } = await postCalendar(data);
                    if (response.existMsg) {
                        alert(response.msg);
                    }
                    if (response.success) {
                        await this.processAfterSuccess(data.beginDt);
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
                        await this.processAfterSuccess(data.beginDt);
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
                        this.closeDialog();
                        // await this.processAfterSuccess();
                    }
                } catch (error) {
                    alert(error.response.data.msg);
                }
            },
            async processAfterSuccess(beginDt) {
                FullCalendar.gotoDate(moment(beginDt).format('YYYY.MM.DD'));
                await this.getTodayCalendar(moment(beginDt).format('YYYY.MM.DD'));
                await this.getCalendarList(moment(beginDt).format('YYYY.MM'));
                // TODO 수정/등록 한 달로 가게 끔.
                this.closeDialog();
            },
            // 다이얼로드 닫기
            closeDialog() {
                this.visible.calendarManagement = false;
            }
        }
    }
</script>
