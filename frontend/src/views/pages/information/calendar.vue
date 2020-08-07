<template>
    <div>
        <FullCalendar :options="calendarOptions"/>
        <calendarManagement
            :visible.sync="visible.calendarManagement"
            :statusCode="statusCode"
            :calendarDetail="calendarDetail"
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
    } from '@/api/calendar';

    import moment from 'moment'
    import FullCalendar from '@fullcalendar/vue'
    import dayGridPlugin from '@fullcalendar/daygrid'
    import interactionPlugin from '@fullcalendar/interaction'

    import calendarManagement from '@/views/pages/information/calendar-management'

    export default {
        name: 'calendar',
        data () {
            return {
                searchInfo: {
                    // TODO[lsj]
                    //  달력은 해당 월의 모든데이터를 노출해야 하기 때문에
                    //  페이징 처리 필요 없을 듯 api 문의 필요 2020.08.06 by. sojeong.lee
                    page: 0,
                    itemLength: 20,
                    yyyyMm: moment(new Date()).format('YYYY.MM')
                },
                searchDt: moment(new Date()).format('YYYY.MM.DD'),
                statusCode: null,
                calendarDetail: {},
                visible: {
                    calendarManagement: false,
                },
                loadingData: false,
                calendarData: [],
                todayData: [],
                calendarOptions: {
                    plugins: [ dayGridPlugin, interactionPlugin ],
                    initialView: 'dayGridMonth',
                    dateClick: this.handleDateClick,
                    events: []
                }
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
                    await this.getCalendarList(this.searchInfo);
                    await this.getTodayCalendar(this.searchDt);
                } catch (error) {
                    console.log(error);
                }
                this.loadingData = false;
            },
            // 한달 일정 조회
            async getCalendarList(calendarSearchInfo) {
                const { data: { data: response } }
                    = await getCalendarList(calendarSearchInfo);
                this.calendarData = response;
                this.transformData();
            },
            // 해당 날짜 일정 조회
            async getTodayCalendar(searchDt) {
                const { data: { data: response } }
                    = await getTodayCalendar({searchDt: searchDt});
                this.todayData = response;
                console.log('todayData : ', this.todayData);
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
                this.searchDt = moment(arg.dateStr).format('YYYY.MM.DD');
                this.getTodayCalendar(this.searchDt)
            },
            // 일정 등록 클릭시
            onClickToCreate() {
                this.statusCode = "CREATE"
                this.visible.calendarManagement = true;
            },
            // 일정 수정 클릭시
            onClickToEdit(item) {
                this.statusCode = "EDIT"
                this.calendarDetail = item;
                this.visible.calendarManagement = true;
            }
        }
    }
</script>
