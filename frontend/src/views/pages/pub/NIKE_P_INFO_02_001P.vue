<template>
    <div>
        <el-button type="white" size="small" @click="showCalendarManagement">
            계정 관리
        </el-button>

        <calendarManagement :visible.sync="visible.calendarManagement" />
    </div>
</template>
<script>
    import calendarManagement from '@/views/pages/information/calendar-management';

    export default {
    name: 'NIKE_P_MANAGE_02_002P',
    data() {
        return {
            visible: {
                calendarManagement: true,
            },
        };
    },
    components: { calendarManagement },
    methods: {
        showCalendarManagement() {
            this.visible.calendarManagement = true;
        },
    },
};
</script>
<style scoped></style>
