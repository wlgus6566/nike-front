<template>
    <div>
        <h2 class="page-title"><span class="ko">권한 그룹관리</span></h2>
        <button type="button" class="btn-s-black" @click="saveAuth">
            저장
        </button>
        <div class="group-management">
            <GroupTree
                :authName="authName"
                :groupTreeData="groupTreeData"
                :groupTreeActive="groupTreeActive"
                :groupTreeOpen="groupTreeOpen"
                :groupTreeAddItem="groupTreeAddItem"
            />
            <div class="group-detail">
                <div class="group-name">
                    <label for="authName">그룹명</label>
                    <input type="text" id="authName" v-model="authName" />
                </div>
                <div>
                    <GroupTable
                        v-for="(table, index) in groupDetailDataBase"
                        :table="table"
                        :menuRoleSeqArray="menuRoleSeqArray"
                        :key="index"
                        @toggleCheck="toggleCheck"
                    />
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import GroupTree from '@/components/group-tree/index';
import GroupTable from '@/views/pages/management/GroupTable';
import bus from '@/utils/bus';
import { delAuth, getAuthList, getAuthView, postAuth } from '@/api/auth';
import { getMenuManage } from '@/api/menu';

export default {
    name: 'Group',
    data() {
        return {
            authSeq: '',
            authDepth: '',
            upperAuthSeq: '',
            authName: '',
            menuRoleSeqArray: [],
            DefaultMenuRoleSeqArray: [],
            groupTreeData: [
                {
                    authDepth: '',
                    authName: '전체 권한그룹',
                    authSeq: '0',
                    registerSeq: '',
                    registrationDt: '',
                    roleType: '',
                    subAuths: '',
                    updateDt: '',
                    updaterSeq: '',
                    useYn: '',
                },
            ],
            groupTreeActive: {},
            groupTreeOpen: [],
            groupTreeAddItem: null,
            groupDetailData: [],
            groupDetailDataBase: [],
        };
    },
    methods: {
        toggleCheck(seq) {
            if (this.menuRoleSeqArray.some((el) => el === seq)) {
                this.menuRoleSeqArray = this.menuRoleSeqArray.filter(
                    (el) => el !== seq
                );
            } else {
                this.menuRoleSeqArray.push(seq);
            }
        },
        async saveAuth() {
            const { data: response } = await postAuth({
                authDepth: this.authDepth,
                authName: this.authName,
                menuRoleSeqArray: this.menuRoleSeqArray,
                upperAuthSeq: this.upperAuthSeq,
            });
            if (response.existMsg) {
                alert(response.msg);
            }
            console.log(response);
        },
        async getDefaultView() {
            const {
                data: { data: response },
            } = await getMenuManage();
            this.groupDetailDataBase = response;
            this.groupDetailDataBase.forEach((a) => {
                a.subMenus.forEach((b) => {
                    b.skillCodes.forEach((el) => {
                        if (el.menuRoleSeq && b[el.field] === 'Y') {
                            this.menuRoleSeqArray.push(el.menuRoleSeq);
                        }
                    });
                });
            });
            this.DefaultMenuRoleSeqArray = this.menuRoleSeqArray;
        },
        async getAuthList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthList();
                this.groupTreeData[0].subAuths = response;
                console.log('getAuthList', response);
            } catch (e) {
                console.log(e);
            }
        },
        async authView(seq) {
            try {
                const {
                    data: { data: response },
                } = await getAuthView(seq);
                console.log('getAuthView', response);
                this.groupDetailData = response;
            } catch (e) {
                console.log(e);
            }
        },
        async delAuth(seq) {
            try {
                const { data: response } = await delAuth(seq);
                await this.getAuthList();
                if (response.existMsg) {
                    alert(response.msg);
                }
                console.log('delAuth', response);
            } catch (e) {
                console.log(e);
            }
        },
    },
    components: { GroupTable, GroupTree },
    created() {
        this.getAuthList();
        this.getDefaultView();
        bus.$on('authNameUpdate', (value) => {
            this.authName = value;
        });

        bus.$on('selectActive', (item, depth) => {
            this.groupTreeAddItem = null;
            if (this.groupTreeActive.authSeq === item.authSeq) {
                this.groupTreeActive = {};
                this.upperAuthSeq = '';
                this.authDepth = '';
            } else {
                this.authView(item.authSeq);
                this.groupTreeActive = item;
                this.upperAuthSeq = depth;
                this.authDepth = depth - 1;
            }
        });

        bus.$on('groupTreeAdd', () => {
            this.groupTreeAddItem = this.groupTreeActive.authSeq;
            this.groupTreeOpen.push(this.groupTreeActive.authSeq);
            this.groupTreeActive = {};
        });
        bus.$on('groupTreeDel', () => {
            this.delAuth(this.groupTreeActive.authSeq);
        });
        bus.$on('groupTreeToggle', (authSeq) => {
            if (this.groupTreeOpen.some((el) => el === authSeq)) {
                this.groupTreeOpen = this.groupTreeOpen.filter(
                    (el) => el !== authSeq
                );
            } else {
                this.groupTreeOpen.push(authSeq);
            }
        });
        window.onbeforeunload = function () {
            return '진짜?';
        };
    },
    beforeRouteLeave(to, from, next) {
        const answer = window.confirm(
            '이 페이지에서 나가시겠습니까?\n변경사항이 저장되지 않을 수 있습니다.'
        );
        if (answer) {
            next();
        } else {
            next(false);
        }
    },
};
</script>
<style scoped>
.group-management {
    margin-top: 20px;
    display: flex;
}
::v-deep .group-tree-scroll {
    overflow-x: hidden;
    box-sizing: border-box;
    max-height: calc(100vh - 300px);
}
::v-deep .el-scrollbar__bar {
    top: 40px;
    bottom: 40px;
    width: 4px;
    right: 10px;
}

.group-detail {
    flex: 1 1 auto;
    margin-left: 30px;
}
.group-name {
    display: flex;
    align-items: center;
    border: solid #ddd;
    padding: 15px 0;
    border-width: 1px 0;
}
.group-name label {
    flex: 0 0 auto;
    width: 100px;
    font-weight: 700;
}
.group-name input {
    flex: 1 1 auto;
}
.group-table {
    table-layout: fixed;
    margin-top: 20px;
    border-collapse: collapse;
    width: 100%;
    border-bottom: 1px solid #ccc;
}
.group-table thead th {
    font-size: 10px;
    padding: 5px 0;
    line-height: 30px;
    font-weight: 700;
    color: #555;
}
.group-table thead th:first-child {
    text-align: left;
    padding: 5px 6px;
}
.group-table thead th label {
    font-size: 14px;
    font-family: 'Roboto', 'Noto Sans KR', 'Arial', 'Apple SD Gothic Neo',
        sans-serif;
    font-weight: 700;
    color: #000;
}
.group-table tbody tr:first-child th,
.group-table tbody tr:first-child td {
    border-top: 1px solid #555;
}

.group-table tbody th {
    text-align: left;
    padding: 0 20px;
    line-height: 50px;
    font-size: 12px;
    letter-spacing: 0;
    font-family: 'Roboto', 'Noto Sans KR', 'Arial', 'Apple SD Gothic Neo',
        sans-serif;
    font-weight: 400;
    color: #000;
    border-top: 1px solid #ddd;
    background: #f7f7f7;
}
.group-table tbody td {
    text-align: center;
    line-height: 50px;
    border-top: 1px solid #ddd;
    border-left: 1px solid #ddd;
    background: #fff;
}
.group-table tbody td label {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 50px;
    width: 100%;
}
.group-table tbody td label input {
    position: absolute;
    top: 0;
    left: -9999px;
    opacity: 0;
}
.group-table tbody td label i {
    display: inline-block;
    height: 20px;
    width: 20px;
    background: url('../../../assets/images/svg/icon-btn-check-management-grey.svg')
        no-repeat 50% / contain;
}
.group-table tbody td label.checked i {
    background-image: url('../../../assets/images/svg/icon-btn-check-management-orange.svg');
}
.group-table tbody td label.disabled {
    background: #f7f7f7;
}
.group-check-all {
    line-height: 20px;
}
.group-check-all input {
    opacity: 0;
    position: absolute;
    top: 0;
    left: -9999px;
}
.group-check-all i {
    display: inline-block;
    vertical-align: middle;
    height: 20px;
    width: 20px;
    background: url('../../../assets/images/svg/icon-btn-check-management-circle-grey.svg')
        no-repeat 50% / contain;
}
.group-check-all.checked i {
    background-image: url('../../../assets/images/svg/icon-btn-check-management-circle-orange.svg');
}
</style>
