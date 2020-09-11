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
        <el-scrollbar wrap-class="wrap" view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <GroupTreeListTable
                    :groupTreeData="groupTreeData"
                    :groupTreeOpen="groupTreeOpen"
                    :groupTreeAddItem="groupTreeAddItem"
                    :depth="0"
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
                    checkBoxYn: 'Y',
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
    watch: {},
    activated() {
        this.groupTreeOpen = [];
        this.groupTreeAddItem = null;
        this.groupDetailData = [];
    },
    mounted() {
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
            this.groupTreeDataCheckUpdate(
                this.groupTreeData,
                seq,
                'detailAuthYn'
            );
        });
        bus.$on('emailReceptionUpdate', (seq) => {
            this.groupTreeDataCheckUpdate(
                this.groupTreeData,
                seq,
                'emailReceptionYn'
            );
        });
    },
    props: ['visible', 'checks', 'menuCode'],
    methods: {
        copyObj(obj) {
            let copy = {};
            if (Array.isArray(obj)) {
                copy = obj.slice().map((v) => {
                    return this.copyObj(v);
                });
            } else if (typeof obj === 'object' && obj !== null) {
                for (let attr in obj) {
                    if (obj.hasOwnProperty(attr)) {
                        copy[attr] = this.copyObj(obj[attr]);
                    }
                }
            } else {
                copy = obj;
            }
            return copy;
        },
        dataInit() {
            this.groupTreeData = [
                {
                    authDepth: 0,
                    authName: '전체',
                    authSeq: 0,
                    checkBoxYn: 'Y',
                    detailAuthYn: 'N',
                    emailReceptionYn: 'N',
                    roleType: null,
                    subAuths: this.copyObj(this.checks),
                    viewYn: null,
                },
            ];
        },

        childYN(arr, value, YN) {
            arr.forEach((el) => {
                el[YN] = value;
                if (el.subAuths) {
                    this.childYN(el.subAuths, value, YN);
                }
            });
        },

        groupTreeDataCheckUpdate(arr, seq, YN) {
            arr.forEach((el) => {
                if (el.authSeq === seq) {
                    if (el[YN] === 'Y') {
                        el[YN] = 'N';
                        if (YN === 'detailAuthYn') {
                            el['emailReceptionYn'] = 'N';
                            if (el.subAuths) {
                                this.childYN(
                                    el.subAuths,
                                    'N',
                                    'emailReceptionYn'
                                );
                            }
                        }
                        if (el.subAuths) {
                            this.childYN(el.subAuths, 'N', YN);
                        }
                    } else {
                        el[YN] = 'Y';
                        if (YN === 'emailReceptionYn') {
                            el['detailAuthYn'] = 'Y';
                            if (el.subAuths) {
                                this.childYN(el.subAuths, 'Y', 'detailAuthYn');
                            }
                        }
                        if (el.subAuths) {
                            this.childYN(el.subAuths, 'Y', YN);
                        }
                    }
                } else if (el.subAuths) {
                    this.groupTreeDataCheckUpdate(el.subAuths, seq, YN);
                }
            });
        },

        checksUpdate() {
            this.$emit('checksUpdate', this.groupTreeData[0].subAuths);
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
::v-deep .wrap {
    max-height: 500px;
    border-bottom: 1px solid #ccc;
}
</style>
