<template>
    <el-dialog
        title="알림함"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
      <div class="modal-contents">
        <ul class="alarm-list">
          <li v-for="(item ,index) in alarmList" :key="index">
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
<style>
.modal-contents{
  margin:0 -20px;
}
.alarm-list li{
  position:relative;
}
.alarm-list li + li{
  border-top:1px solid #eee;
}
.alarm-list li:before{
  content:"";
  position:absolute;
  top:24px;
  left:20px;
  display:block;
  width:4px;
  height:4px;
  border-radius:100%;
  background:#fa5400;
}
.alarm-list li a{
  display:block;
  padding:15px 20px 15px 29px;
}
.alarm-list li:first-child a{
  padding-top:5px;
}
.alarm-list li .title{
  display:block;
  font-size:14px;
  line-height:20px;
  color:#333;
}
.alarm-list li .data{
  display:block;
  margin-top:5px;
  font-size:12px;
  line-height:18px;
  color:#888;
}
</style>
