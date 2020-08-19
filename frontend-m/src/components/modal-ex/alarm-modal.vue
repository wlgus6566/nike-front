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
                <ul class="notice-list" >
                    <li v-for="item in alarmData">
                        <a href="#" @click="delAlarmData(item.alarmSeq)">
                            <span class="title" @click="detailPageUrl(item)">{{item.typeCd}}({{item.folderName}})
                                <span v-if="item.typeCd === 'REPORT'"> 에 피드백이 등록되었습니다.</span>
                                <span v-else> 이(가) 업데이트 되었습니다.</span>
                            </span>
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
                this.alarmData.forEach((el) => {
                    el.typeCd === "REPORT_MANAGE" ? el.typeCd = "REPORT" : el.typeCd;
                });
                console.log(this.alarmData);
            } catch (error) {
                console.log(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
        detailPageUrl: function(data) {
            if (data.typeCd === "REPORT") {
                this.$router.push(`/report/${data.folderSeq}`);
            } else if (data.typeCd === "ASSET") {
                alert("해당 메뉴는 모바일 버전에서 제공되지 않습니다. 자세한 내용은 PC로 접속 시 확인할 수 있습니다");
            } else {
                //TODO [jjh] 컨텐츠 상세 url
            }
        },
        async delAlarmData(seq) {
            try {
                const {
                    data: { data: response },
                } = await delAlarm(seq);
                await this.alarmData();
                console.log(response)
            } catch (error) {
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        }
    },
};
</script>
<style></style>
