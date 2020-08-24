<template>
    <el-dialog
        title=""
        class="modal-wrap"
        width="600px"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <h3 class="form-title mt0">권한 설정</h3>
        <div class="auth-title">
            <span class="tit1">구분</span>
            <span class="tit2">상세보기</span>
            <span class="tit3">알람 메일 수신 여부</span>
        </div>
        <el-scrollbar wrap-class="test" view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <GroupTreeListTable
                    :groupTreeData="groupTreeData"
                    :groupTreeOpen="groupTreeOpen"
                    :groupTreeAddItem="groupTreeAddItem"
                    :depth="0"
                    :checks="checks"
                />
            </div>
        </el-scrollbar>

        <div slot="footer" class="dialog-footer">
            <button type="button" class="btn-s-black" @click="checksUpdate">
                <span>저장</span>
            </button>
        </div>
    </el-dialog>
</template>

<script>
import GroupTreeListTable from '@/components/group-tree/tree-list-table';
import { getContentsAuthList } from '@/api/contents';
import bus from '@/utils/bus';
export default {
    name: 'ModalAuth',
    data() {
        return {
            groupTreeData: [
                {
                    authDepth: 0,
                    authName: '전체',
                    authSeq: 0,
                    checkBoxYn: 'N',
                    detailAuthYn: 'N',
                    emailReceptionYn: 'N',
                    roleType: null,
                    subAuths: [],
                    viewYn: null,
                },
            ],
            groupTreeOpen: [],
            groupTreeAddItem: null,
            groupDetailData: [],
        };
    },
    mounted() {
        this.getAuthList();

        bus.$on('groupTreeToggle', (authSeq) => {
            if (this.groupTreeOpen.some((el) => el === authSeq)) {
                this.groupTreeOpen = this.groupTreeOpen.filter(
                    (el) => el !== authSeq
                );
            } else {
                this.groupTreeOpen.push(authSeq);
            }
        });
        bus.$on('detailAuthUpdate', (seq) => {
            this.dd(this.groupTreeData, seq, 'detailAuthYn');
        });
        bus.$on('emailReceptionUpdate', (seq) => {
            this.dd(this.groupTreeData, seq, 'emailReceptionYn');
        });
    },
    props: ['visible', 'checks'],
    methods: {
        dd(arr, seq, YN) {
            console.log(YN);
            arr.forEach((el) => {
                if (el.authSeq === seq) {
                    if (el[YN] === 'Y') {
                        el[YN] = 'N';
                    } else {
                        el[YN] = 'Y';
                    }
                }
                if (el.subAuths) {
                    this.dd(el.subAuths, seq, YN);
                }
            });
        },

        async getAuthList() {
            try {
                const { data: response } = await getContentsAuthList(
                    'ASSET',
                    'SP'
                );
                this.groupTreeData[0].subAuths = response;
            } catch (e) {
                console.log(e);
            }
        },
        checksUpdate() {
            this.$emit('checksUpdate', this.groupTreeData);
        },
    },
    components: { GroupTreeListTable },
};
</script>
<style scoped>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.modal-wrap .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
.auth-title {
    margin-top: 10px;
    padding-right: 40px;
    border-top: 2px solid #000;
    border-bottom: 1px solid #000;
    display: flex;
    line-height: 28px;
    color: #000;
    font-size: 12px;
    font-weight: bold;
    justify-content: space-between;
}
.auth-title span {
    text-align: center;
    width: 100px;
}
.auth-title span:first-child {
    width: 260px;
}
::v-deep .test {
    max-height: 500px;
    border-bottom: 1px solid #ccc;
}
</style>
