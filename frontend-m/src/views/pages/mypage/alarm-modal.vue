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
            <ul class="alarm-list" ref="alarmWrap">
                <li v-for="(item, index) in alarmList" :key="index">
                    <a href="#" @click="delAlarmData(item, index)">
                        <span class="title" v-if="item.typeAction === 'NEW'">
                            새로 등록된 {{ item.typeCd }}({{
                                item.folderName
                            }})이 있습니다.
                        </span>
                        <span class="title" v-else
                            >{{ item.typeCd }}({{ item.folderName }})
                            <span v-if="item.typeAction === 'FEEDBACK'">
                                에 피드백이 등록되었습니다.</span
                            >
                            <span v-else> 이(가) 업데이트 되었습니다.</span>
                        </span>
                        <span class="data">{{ item.registrationDt }}</span>
                    </a>
                </li>
            </ul>
        </div>
    </el-dialog>
</template>

<script>
import { delAlarm } from '@/api/alarm';
import Loading from '@/components/loading/index';

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
        Loading,
    },
    props: ['visible', 'alarmList'],
    mounted() {
        /*console.log(modalContents.clientHeight + modalContents.scrollTop);
		console.log(modalContents.scrollHeight);*/
    },
    methods: {
        detailPageUrl(item) {
            let typeCdLow = item.typeCd.toLowerCase().trim();

            if (typeCdLow === 'report') {
                this.$router.push(`/report/detail/${item.folderSeq}`);
            } else if (typeCdLow === 'asset') {
                alert(
                    '해당 메뉴는 모바일 버전에서 제공되지 않습니다. 자세한 내용은 PC로 접속 시 확인할 수 있습니다.'
                );
            } else {
                let menuCodeLow = item.menuCode.toLowerCase().trim();
                this.$router.push(
                    '/' + typeCdLow + '/' + menuCodeLow + '/' + item.folderSeq
                );
            }
        },
        async delAlarmData(item, key) {
            try {
                const {
                    data: { data: response },
                } = await delAlarm(item.alarmSeq);
                if (item.typeCd === 'ASSET') {
                    this.alarmList.splice(key, 1);
                }
                this.detailPageUrl(item);
            } catch (error) {
                console.log(error);
                alert(error);
            }
        },
    },
};
</script>
<style scoped>
.modal-contents {
    margin: 0 -20px;
    max-height: 340px;
    overflow: auto;
}
.alarm-list {
}
.alarm-list li {
    position: relative;
}
.alarm-list li + li {
    border-top: 1px solid #eee;
}

.alarm-list li a {
    display: block;
    padding: 15px 20px 15px 29px;
}
.alarm-list li:first-child a {
    padding-top: 5px;
}
.alarm-list li .title {
    positoin: relative;
    display: block;
    font-size: 14px;
    line-height: 20px;
    color: #333;
}
.alarm-list li .title:before {
    content: '';
    position: absolute;
    top: 22px;
    left: 20px;
    display: block;
    width: 4px;
    height: 4px;
    border-radius: 100%;
    background: #fa5400;
}
.alarm-list li:first-child .title:before {
    top: 12px;
}
.alarm-list li .data {
    display: block;
    margin-top: 5px;
    font-size: 12px;
    line-height: 18px;
    color: #888;
}
</style>
