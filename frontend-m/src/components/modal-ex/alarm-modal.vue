<template>
    <el-dialog
        title="알림함"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <ul class="notice-list" v-for="item in alarmData">
                    <li>
                        <a href="#">
                            <span class="title">{{item.typeCd}}({{item.folderName}})</span>
                            <span class="data">{{item.registrationDt}}</span>
                        </a>
                    </li>
                </ul>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
import { getAlarm, delAlarm } from '@/api/alarm';

export default {
    data() {
        return {
            orderComment: '',
            alarmList: {},
            alarmData: [],
            alarmActive: false,
            page: 0,
            size: 10
        };
    },
    props: ['visible'],
    mounted() {
        this.getAlarmData();
    },
    methods: {
        async getAlarmData() {
            try {
                const {
                    data: { data: response },
                } = await getAlarm({
                    page: this.page,
                    size: this.size,
                });
                this.alarmList = response;
                this.alarmData = response.content;
                console.log(this.alarmData);
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        }
    },
};
</script>
<style></style>
