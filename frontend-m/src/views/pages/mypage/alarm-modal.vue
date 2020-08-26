<template>
    <el-dialog
        ref="alarm"
        title="알림함"
        class="modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
      <div class="modal-contents">
        <ul class="alarm-list">
          <li v-for="(item ,index) in alarmList" :key="index">
            <a href="#" @click="delAlarmData(item)">
              <span class="title" v-if="item.typeAction === 'NEW'">
                새로 등록된 {{item.typeCd}}({{item.folderName}})이 있습니다.
              </span>
              <span class="title" v-else>{{item.typeCd}}({{item.folderName}})
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
import { delAlarm } from '@/api/alarm';
import Loading from "@/components/loading/index";

export default {
    name: 'Alarm',
    data() {
        return {
            alarmData: [],
            alarmActive: false,
        };
    },
    created() {},
    components: {
      Loading
    },
    props: ['visible', 'alarmList'],
    mounted() {},
    methods: {
        detailPageUrl(item) {
            if (item.typeCd === "REPORT") {
                this.$router.push(`/report/${item.folderSeq}`);
            } else if (item.typeCd === "ASSET") {
                this.$emit("assetClick");
            } else {
              this.$router.push(`/${item.typeCd}/${item.menuCode}/${item.folderSeq}`);
            }
        },
        async delAlarmData(item) {
          console.log("delAlarmData");
            try {
                const {
                    data: { data: response },
                } = await delAlarm(item.alarmSeq);
                this.detailPageUrl(item);
                console.log(response)
            } catch (error) {
              console.log(error);
              alert(error);
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
