<template>
    <div>
        <h2 class="page-title"><span class="ko">권한 그룹관리</span></h2>
        <div class="group-management">
            <GroupTree
                :groupTreeData="groupTreeData"
                :groupTreeActive="groupTreeActive"
                :groupTreeOpen="groupTreeOpen"
                :groupTreeAddItem="groupTreeAddItem"
            />
            <div class="group-detail">
                <div class="group-name">
                    <label for="groupName">그룹명</label>
                    <input type="text" id="groupName" />
                </div>
                <div>
                    <table class="group-table">
                        <colgroup>
                            <col style="width: 175px;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>
                                    <label class="group-check-all">
                                        <input type="checkbox" />
                                        <i />
                                        ASSET
                                    </label>
                                </th>
                                <th>목록</th>
                                <th>등록/수정</th>
                                <th>삭제</th>
                                <th>상세보기</th>
                                <th>다운로드/주문</th>
                                <th>REPORT 노출</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>ALL</th>
                                <td>
                                    <label class="disabled">
                                        <input type="checkbox" disabled />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label class="checked">
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>SP</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>SU</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>FA</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>HO</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="group-table">
                        <colgroup>
                            <col style="width: 175px;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>
                                    <label class="group-check-all">
                                        <input type="checkbox" />
                                        <i />
                                        ASSET
                                    </label>
                                </th>
                                <th>목록</th>
                                <th>등록/수정</th>
                                <th>삭제</th>
                                <th>상세보기</th>
                                <th>다운로드/주문</th>
                                <th>REPORT 노출</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>ALL</th>
                                <td>
                                    <label class="disabled">
                                        <input type="checkbox" disabled />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label class="checked">
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>SP</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>SU</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>FA</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th>HO</th>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <input type="checkbox" />
                                        <i />
                                    </label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import GroupTree from '@/components/group-tree/index';
import bus from '@/utils/bus';
import { getAuthList, getAuthView, delAuth } from '@/api/auth';
export default {
    authName: 'group',
    data() {
        return {
            groupTreeData: [
                {
                    authName: '전체 권한 그룹',
                    authSeq: '0',
                    authDepth: '0',
                    subAuths: [
                        {
                            authName: '그룹1',
                            authSeq: '1',
                            authDepth: '1',
                            subAuths: [
                                {
                                    authName: '그룹1-1',
                                    authSeq: '1-1',
                                    authDepth: '2',
                                },
                                {
                                    authName: '그룹1-2',
                                    authSeq: '1-2',
                                    authDepth: '2',
                                    subAuths: [
                                        {
                                            authName: '그룹1-2-1',
                                            authSeq: '1-2-1',
                                            authDepth: '3',
                                        },
                                        {
                                            authName: '그룹1-2-2',
                                            authSeq: '1-2-2',
                                            authDepth: '3',
                                        },
                                    ],
                                },
                                {
                                    authName: '그룹1-3',
                                    authSeq: '1-3',
                                    authDepth: '2',
                                },
                            ],
                        },
                        {
                            authName: '그룹2',
                            authSeq: '2',
                            authDepth: '1',
                            subAuths: [
                                {
                                    authName: '그룹2-1',
                                    authSeq: '2-1',
                                    authDepth: '2',
                                },
                                {
                                    authName: '그룹2-2',
                                    authSeq: '2-2',
                                    authDepth: '2',
                                },
                                {
                                    authName: '그룹2-3',
                                    authSeq: '2-3',
                                    authDepth: '2',
                                },
                            ],
                        },
                        {
                            authName: '그룹3',
                            authSeq: '3',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹4',
                            authSeq: '4',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹5',
                            authSeq: '5',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹6',
                            authSeq: '6',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹7',
                            authSeq: '7',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹8',
                            authSeq: '8',
                            authDepth: '1',
                        },
                        {
                            authName: '그룹9',
                            authSeq: '9',
                            authDepth: '1',
                        },
                    ],
                },
            ],
            groupTreeActive: {},
            groupTreeOpen: [],
            groupTreeAddItem: null,
            groupDetailData: [],
        };
    },
    methods: {
        async getAuthList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthList();
                this.groupTreeData = response;
                console.log('getAuthList', response);
            } catch (e) {
                console.log(e);
            }
        },
        async authView(seq) {
            console.log('????');
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
                const response = await delAuth(seq);
                this.getAuthList();
                console.log('delAuth', response);
            } catch (e) {
                console.log(e);
            }
        },
    },
    components: { GroupTree },
    created() {
        this.getAuthList();

        bus.$on('selectActive', (item) => {
            this.groupTreeAddItem = null;
            if (this.groupTreeActive.authSeq === item.authSeq) {
                this.groupTreeActive = {};
            } else {
                this.authView(item.authSeq);
                this.groupTreeActive = item;
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
    height: 3000px;
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
