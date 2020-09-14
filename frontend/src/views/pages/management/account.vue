<template>
    <div>
        <h2 class="page-title">
            <span class="ko">계정관리</span>
        </h2>
        <div class="sorting-area">
            <CascaderSelect :listCascader="authority" />
            <div
                class="date-picker-wrap"
                :class="{ active: dataPickerShowData.visible }"
            >
                <span class="btn-box">
                    <button
                        type="button"
                        class="btn-txt"
                        @click="dataPickerShow"
                        ref="datePickerBtn"
                    >
                        <span>기간조회</span>
                    </button>
                    <i class="icon-close" @click="dataPickerReset"></i>
                    <i class="icon-look"></i>
                </span>
                <div class="date-picker-group-box" ref="datePicker">
                    <strong class="title">최종로그인</strong>
                    <div class="date-picker-group">
                        <div class="date-picker">
                            <el-date-picker
                                v-model="beginDt"
                                type="date"
                                placeholder="YYYY.MM.DD"
                                format="yyyy.MM.dd"
                                :picker-options="pickerBeginOption"
                            >
                            </el-date-picker>
                        </div>
                        <div class="date-picker">
                            <el-date-picker
                                v-model="endDt"
                                type="date"
                                placeholder="YYYY.MM.DD"
                                format="yyyy.MM.dd"
                                :picker-options="pickerEndOption"
                            >
                            </el-date-picker>
                        </div>
                    </div>
                </div>
            </div>
            <FilterSelect :listSortSelect="listSortSelect" />
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="userData">
            <div class="tbl-list" v-if="userData.length">
                <table>
                    <colgroup>
                        <col
                            style="width: 60px;"
                            v-if="folderAuthCheck('DELETE')"
                        />
                        <col style="width: auto;" />
                        <col style="width: auto;" />
                        <col style="width: 150px;" />
                        <col style="width: 100px;" />
                        <col style="width: 160px;" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th v-if="folderAuthCheck('DELETE')">
                                <span class="checkbox">
                                    <input
                                        type="checkbox"
                                        v-model="checkAll"
                                        @click="allCheckFn()"
                                    />
                                    <i></i>
                                </span>
                            </th>
                            <th>계정명</th>
                            <th>ID (E-MAIL)</th>
                            <th>권한그룹</th>
                            <th>상태</th>
                            <th>최종로그인</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item in userData" :key="item.userSeq">
                            <td v-if="folderAuthCheck('DELETE')">
                                <span class="checkbox">
                                    <input
                                        type="checkbox"
                                        :value="item.userSeq"
                                        v-model="checkItem"
                                        @click="checked(item.userSeq)"
                                    />
                                    <i></i>
                                </span>
                            </td>
                            <td>
                                <button
                                    v-if="folderAuthCheck('CREATE')"
                                    class="under-link"
                                    @click="userDetailView(item.userSeq)"
                                >
                                    <span>{{ item.nickname }}</span>
                                </button>
                                <span v-else>{{ item.nickname }}</span>
                            </td>
                            <td>{{ item.userId }}</td>
                            <td>{{ item.authName }}</td>
                            <td>
                                <span>{{ item.userStatusCodeName }}</span>
                            </td>
                            <td>{{ item.loginDt }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <template v-else>
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-file"></i>
                    <p class="desc">계정 관리 목록이 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />

        <div class="btn-tbl-box">
            <button
                type="button"
                class="btn-form"
                @click="userArrayDelete"
                v-if="userData.length && folderAuthCheck('DELETE')"
            >
                <span>삭제</span>
            </button>
            <div class="right">
                <button
                    type="button"
                    class="btn-form-gray"
                    @click="openPop"
                    v-if="folderAuthCheck('CREATE')"
                >
                    <span>등록</span>
                </button>
            </div>
        </div>

        <Pagination
            v-if="userData.length"
            :itemLength="itemLength"
            :pageCount="pageCount"
            :totalItem="totalItem"
            @handleCurrentChange="handleCurrentChange"
        />
        <AccountManagement
            :visible.sync="visible.AccountManagement"
            :addUserData="this.addUserData"
            :addAuthority="this.addAuthority"
            @userIdCheck="userIdCheck"
            @addAuthData="addAuthData"
            @modifyAuthData="modifyAuthData"
            @userDelete="userDelete"
        />
    </div>
</template>
<script>
import {
    getUser,
    postUser,
    putUser,
    getDuplicate,
    getUserDetail,
    deleteArrayUser,
    deleteUser,
} from '@/api/user';
import { getAuthCacheList } from '@/api/auth';
import { getCategoryList } from '@/utils/code';
import CascaderSelect from '@/components/cascader-select';
import FilterSelect from '@/components/filter-select';
import SearchInput from '@/components/search-input';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import Pagination from '@/components/pagination';
import AccountManagement from '@/views/pages/management/account-management.vue';
import bus from '@/utils/bus';
import { authCheck } from '@/utils/authCheck';
export default {
    name: 'account',
    data() {
        return {
            visible: {
                AccountManagement: false,
            },
            idCheck: false,
            loading: false,
            itemLength: 20,
            pageCount: 11,
            totalItem: 0,
            page: null,
            searchKeyword: '',
            sort: null,
            userDataList: '',
            userData: '',
            checkAll: false,
            checkItem: [],
            listSortSelect: {
                listSortOptions: [
                    {
                        value: '',
                        label: '계정 상태',
                    },
                ],
                value: '',
            },
            authority: {
                value: [null],
                options: [
                    {
                        value: null,
                        label: '전체 권한그룹',
                    },
                ],
            },
            placeholder: 'abc',
            beginDt: null,
            endDt: null,
            today: new Date(),
            pickerBeginOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.endDt !== null) {
                        return time.getTime() > this.endDt.getTime();
                    }
                },
            },
            pickerEndOption: {
                firstDayOfWeek: 7,
                cellClassName: (date) => {
                    if (new Date(date).getDay() === 0) {
                        return 'el-holiday';
                    }
                },
                disabledDate: (time) => {
                    if (this.beginDt !== null) {
                        return time.getTime() < this.beginDt.getTime();
                    }
                },
            },
            userSeqArray: [],
            dataPickerShowData: {
                visible: false,
            },
            addAuthority: {
                value: [null],
                options: [
                    {
                        value: null,
                        label: '권한그룹을 선택해 주세요.',
                    },
                ],
            },
            addUserData: {
                authSeq: null,
                nickname: null,
                userId: null,
                authSeqArray: null,
            },
            loadingData: false,
        };
    },
    mixins: [authCheck],
    components: {
        AccountManagement,
        CascaderSelect,
        FilterSelect,
        SearchInput,
        Loading,
        NoData,
        Pagination,
    },
    created() {
        this.getUserList();
        this.authCacheList();
        getCategoryList('USER_STATUS', this.listSortSelect.listSortOptions);
    },
    activated() {
        //initializationData
    },
    destroy() {
        this.compDestroy();
    },
    computed: {},
    watch: {
        'listSortSelect.value'() {
            this.getUserList();
        },
        'authority.value'(val) {
            if (val.length === 0) {
                this.authority.value = [null];
            }
            this.getUserList();
        },
        beginDt() {
            this.getUserList();
        },
        endDt() {
            this.getUserList();
        },
    },
    methods: {
        /*//검색후 페이지 이동 리셋
        initializationData() {
            this.searchKeyword = '';
            this.searchSubmit(this.searchKeyword);
        },*/
        compMount() {
            document
                .querySelector('html')
                .addEventListener('click', this.htmlClick);
            document
                .querySelector('.el-select')
                .addEventListener('click', this.elSelectClick);
        },
        compDestroy() {
            document
                .querySelector('html')
                .removeEventListener('click', this.htmlClick);
            document
                .querySelector('.el-select')
                .removeEventListener('click', this.elSelectClick);
        },
        elSelectClick() {
            this.dataPickerShowData.visible = false;
        },
        htmlClick(e) {
            const target = e.target;
            if (
                target.closest('.date-picker-group-box') !==
                    this.$refs.datePicker &&
                !target.closest('.btn-box')
            ) {
                this.dataPickerShowData.visible = false;
            }
        },
        //데이터 피커 리켓
        dataPickerReset() {
            this.beginDt = null;
            this.endDt = null;

            this.dataPickerShowData.visible = false;
            this.getUserList();
        },
        //등록 팝업 오픈
        openPop() {
            this.addUserData = {
                authSeq: null,
                nickname: null,
                userId: null,
                authSeqArray: null,
            };
            this.addAuthority.value = [null];
            this.visible.AccountManagement = true;
        },
        //dataPicker show
        dataPickerShow() {
            this.dataPickerShowData.visible = !this.dataPickerShowData.visible;
            this.compMount();
        },
        dates() {},
        // checkbox
        checked(seq, del) {
            const indexOfChecked = this.checkItem.findIndex((el) => el === seq);
            if (!del && indexOfChecked === -1) {
                this.checkItem.push(seq);
            } else {
                this.checkItem = this.checkItem.filter((el) => {
                    return el !== seq;
                });
            }
            this.checkAll = this.checkItem.length === this.userData.length;
        },
        // 전체 checkbox
        allCheckFn() {
            this.checkAll = !this.checkAll;
            if (this.checkAll) {
                this.userData.forEach((el) => {
                    const indexOfChecked = this.checkItem.findIndex(
                        (elChecked) => elChecked === el.userSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkItem.push(el.userSeq);
                    }
                });
            } else {
                this.checkItem = [];
            }
        },
        // 상품 검색 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getUserList();
        },
        // Pagination fn
        handleCurrentChange(val) {
            this.page = val;
            this.getUserList();
        },

        // USER 목록 조회
        async getUserList() {
            this.loadingData = true;
            let beginDt = null;
            let endDt = null;
            if (this.beginDt !== null) {
                beginDt = this.$moment(this.beginDt).format('YYYY-MM-DD');
            }
            if (this.endDt !== null) {
                endDt = this.$moment(this.endDt).format('YYYY-MM-DD');
            }
            try {
                const {
                    data: { data: response },
                } = await getUser({
                    size: this.itemLength,
                    page: this.page,
                    keyword: this.searchKeyword,
                    status: this.listSortSelect.value,
                    sort: this.sort,
                    authSeq: this.authority.value.slice(-1)[0],
                    beginDt: beginDt,
                    endDt: endDt,
                });
                this.userData = response.content;
                this.totalItem = response.totalElements;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },

        recursionFn(data, item, minIndx) {
            let _minIndx = minIndx;
            if (minIndx === undefined) {
                _minIndx = 0;
            }
            data.forEach((el, index) => {
                item.push({
                    value: el.authSeq,
                    label: el.authName,
                });
                if (el.subAuths) {
                    item[index + _minIndx].children = [];
                    this.recursionFn(
                        el.subAuths,
                        item[index + _minIndx].children
                    );
                }
            });
        },

        //권한 조회
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthCacheList();
                this.userDataList = response;
                this.recursionFn(response, this.authority.options, 1);
                this.recursionFn(response, this.addAuthority.options, 1);
            } catch (error) {
                console.error(error);
            }
        },

        // 유저 다건 삭제
        async userArrayDelete() {
            // console.log(this.checkItem.length !== 0);
            if (this.checkItem.length === 0 || this.checkItem.length === null) {
                alert('하나 이상의 계정을 선택해 주세요');
            } else {
                let deleteAlert = confirm(
                    '선택한 계정을 삭제하시겠습니까? 계정 삭제 시 해당 계정으로 로그인이 불가능하며, 플랫폼 사용 이력이 모두 삭제됩니다.'
                );
                if (deleteAlert) {
                    // console.log(this.checkItem);
                    try {
                        const response = await deleteArrayUser({
                            userSeqArray: this.checkItem,
                        });
                        if (response.data.existMsg) {
                            alert(response.data.msg);
                        }
                        if (response.data.success) {
                            this.getUserList();
                        }
                    } catch (error) {
                        console.error(error);
                    }
                }
            }
        },
        // 유저 단건 삭제
        async userDelete(seq) {
            let deleteAlert = confirm(
                '선택한 계정을 삭제하시겠습니까? 계정 삭제 시 해당 계정으로 로그인이 불가능하며, 플랫폼 사용 이력이 모두 삭제됩니다.'
            );
            if (deleteAlert) {
                if (this.loading) return;
                this.loading = true;
                try {
                    const response = await deleteUser(seq);
                    if (response.data.existMsg) {
                        alert(response.data.msg);
                    }
                    if (response.data.success) {
                        this.visible.AccountManagement = false;
                        await this.getUserList();
                        this.loading = false;
                    }
                } catch (error) {
                    console.error(error);
                }
            }
        },
        // 유저 상세 조회
        async userDetailView(seq) {
            try {
                const {
                    data: { data: response },
                } = await getUserDetail(seq);
                this.addAuthority.value = response.authSeqArray;
                this.addUserData = response;
                this.visible.AccountManagement = true;
                //console.log(response);
            } catch (error) {
                console.error(error);
            }
        },

        // 유저 추가
        async addAuthData(Seq, userData) {
            if (this.idCheck === false) {
                alert('아이디 중복 체크를 해주세요');
            } else {
                let addAlert = confirm('계정을 등록하시겠습니까?');
                if (addAlert) {
                    if (this.loading) return;
                    this.loading = true;
                    try {
                        const response = await postUser({
                            authSeq: Seq.slice(-1)[0],
                            nickname: userData.nickname,
                            userId: userData.userId,
                        });
                        if (response.data.existMsg) {
                            alert(response.data.msg);
                        }
                        // console.log(response);
                        if (response.data.success) {
                            this.visible.AccountManagement = false;
                            await this.getUserList();
                            this.loading = false;
                            this.idCheck = false;
                            this.addUserData = {
                                authSeq: null,
                                nickname: null,
                                userId: null,
                            };
                            this.addAuthority.value = [null];
                        }
                    } catch (error) {
                        console.error(error);
                    }
                }
            }
        },

        // 유저 수정
        async modifyAuthData(userSeq, Seq, userData) {
            let addAlert = confirm('계정을 수정하시겠습니까?');
            if (addAlert) {
                if (this.loading) return;
                this.loading = true;
                try {
                    const response = await putUser(userSeq, {
                        authSeq: Seq.slice(-1)[0],
                        nickname: userData.nickname,
                    });
                    if (response.data.existMsg) {
                        alert(response.data.msg);
                    }
                    if (response.data.success) {
                        this.visible.AccountManagement = false;
                        await this.getUserList();
                        this.loading = false;
                        this.idCheck = false;
                        this.addUserData = {
                            authSeq: null,
                            nickname: null,
                            userId: null,
                        };
                        this.addAuthority.value = [null];
                    }
                } catch (error) {
                    console.error(error);
                }
            }
        },
        // 유저 id 체크
        async userIdCheck(id) {
            this.idCheck = true;

            try {
                const response = await getDuplicate({ userId: id });
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>
<style scoped></style>
