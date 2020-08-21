<template>
    <el-dialog
        title="알림함"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false" style="max-height:550px">
            <div class="el-dialog__inner">
                <ul class="notice-list">
                    <li v-for="item in alarmList">
                        <a href="#" @click="delAlarmData(item.alarmSeq)">
                            <span class="title" v-if="item.typeAction === 'NEW'" @click="detailPageUrl(item)">
                                새로 등록된 {{item.typeCd}}({{item.folderName}})이 있습니다.
                            </span>
                            <span class="title" v-else @click="detailPageUrl(item)">{{item.typeCd}}({{item.folderName}})
                                <span v-if="item.typeAction === 'FEEDBACK'"> 에 피드백이 등록되었습니다.</span>
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
            alarmData: [],
            alarmActive: false,
            totalPage: null,
            page: 0,
            itemLength: 20,
        };
    },
    created() {},
    props: ['visible', 'alarmList'],
    mounted() {},
    methods: {
        detailPageUrl: function(data) {
            if (data.typeCd === "REPORT") {
                this.$router.push(`/report/${data.folderSeq}`);
            } else if (data.typeCd === "ASSET") {
                alert("해당 메뉴는 모바일 버전에서 제공되지 않습니다. 자세한 내용은 PC로 접속 시 확인할 수 있습니다.");
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
