<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
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
                            <v-date-picker
                                v-model="range.beginDt"
                                locale="en-us"
                                color="orange"
                                :input-props="{
                                    placeholder: 'YYYY.MM.DD',
                                }"
                                :attributes="attrs"
                                :min-date="minDate()"
                                :max-date="new Date()"
                            />
                        </div>
                        <div class="date-picker">
                            <v-date-picker
                                v-model="range.endDt"
                                locale="en-us"
                                color="orange"
                                :input-props="{
                                    placeholder: 'YYYY.MM.DD',
                                }"
                                :attributes="attrs"
                                :min-date="minDate()"
                                :max-date="new Date()"
                            />
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
                        <col style="width: 60px;" />
                        <col style="width: auto;" />
                        <col style="width: auto;" />
                        <col style="width: 150px;" />
                        <col style="width: 100px;" />
                        <col style="width: 160px;" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>
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
                            <td>
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
                                    class="under-link"
                                    @click="userDetailView(item.userSeq)"
                                >
                                    <span>{{ item.nickname }}</span>
                                </button>
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
        <Loading :loadingStyle="loadingStyle" v-if="loadingData" />

        <div class="btn-tbl-box" v-if="searchKeyword === ''">
            <button
                type="button"
                class="btn-form"
                @click="userArrayDelete"
                v-if="userData.length"
            >
                <span>삭제</span>
            </button>
            <div class="right">
                <button type="button" class="btn-form-gray" @click="openPop">
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
            beginDt: null,
            endDt: null,
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
            range: {
                beginDt: null,
                endDt: null,
            },
            make: {
                beginDt: null,
                endDt: null,
            },
            placeholder: 'abc',
            attrs: [
                {
                    key: 'today',
                    highlight: 'gray',
                    dates: new Date(),
                    class: 'vc-today',
                    contentClass: 'vc-today',
                },
            ],
            today: new Date(),
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
            },
            loadingData: false,
            loadingStyle: {
                width: this.width ? `${this.width}px` : '100%',
                height: this.height ? `${this.height}px` : '100%',
                overflow: 'hidden',
                margin: '0 auto',
            },
        };
    },
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
    },
    mounted() {
        getCategoryList('USER_STATUS', this.listSortSelect.listSortOptions);
        document.querySelector('html').addEventListener('click', (e) => {
            const target = e.target;
            console.log(e.target);
            if (
                target.closest('.date-picker-group-box') !==
                    this.$refs.datePicker &&
                !target.closest('.btn-box')
            ) {
                console.log(3);
                this.dataPickerShowData.visible = false;
            }
        });
        document.querySelector('.el-select').addEventListener('click', () => {
            this.dataPickerShowData.visible = false;
        });
    },
    computed: {},
    watch: {
        'listSortSelect.value'() {
            this.getUserList();
        },
        'authority.value'() {
            this.getUserList();
        },
        make: {
            deep: true,
            handler(val) {
                if (val.beginDt && val.endDt) {
                    this.dataPickerShowData.visible = false;
                    this.getUserList();
                }
            },
        },
        'range.beginDt'(val) {
            if (val !== null) {
                let year = val.getFullYear();
                let month = val.getMonth() + 1;
                let day = val.getDate();
                if (month < 10) {
                    month = `0${month}`;
                }
                if (day < 10) {
                    day = `0${day}`;
                }
                this.make.beginDt = `${year}-${month}-${day}`;
                if (this.make.endDt !== null) {
                    const begin = Number(this.make.beginDt.replace(/-/gi, ''));
                    const end = Number(this.make.endDt.replace(/-/gi, ''));
                    if (begin > end) {
                        alert('시작일이 종료일보다 클 수 없습니다.');
                        this.range.endDt = this.today;
                    }
                }
            }
        },
        'range.endDt'(val) {
            if (val !== null) {
                let year = val.getFullYear();
                let month = val.getMonth() + 1;
                let day = val.getDate();
                if (month < 10) {
                    month = `0${month}`;
                }
                if (day < 10) {
                    day = `0${day}`;
                }
                this.make.endDt = `${year}-${month}-${day}`;
                if (this.make.beginDt !== null) {
                    const begin = Number(this.make.beginDt.replace(/-/gi, ''));
                    const end = Number(this.make.endDt.replace(/-/gi, ''));
                    if (begin > end) {
                        alert('시작일이 종료일보다 클 수 없습니다.');
                        this.range.endDt = this.today;
                    }
                }
            }
        },
    },
    methods: {
        //데이터 피커 리켓
        dataPickerReset() {
            this.range = {
                beginDt: null,
                endDt: null,
            };
            this.make = {
                beginDt: null,
                endDt: null,
            };
            this.dataPickerShowData.visible = false;
            this.getUserList();
        },
        //등록 팝업 오픈
        openPop() {
            this.addUserData = {
                authSeq: null,
                nickname: null,
                userId: null,
            };
            this.addAuthority.value = [null];
            this.visible.AccountManagement = true;
        },
        //dataPicker show
        dataPickerShow() {
            this.dataPickerShowData.visible = !this.dataPickerShowData.visible;
        },
        dates() {},
        minDate() {
            const date = new Date();
            date.setMonth(date.getMonth() - 3);
            return date;
        },
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
                    beginDt: this.make.beginDt,
                    endDt: this.make.endDt,
                });
                this.userData = response.content;
                this.totalItem = response.totalElements;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
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
                console.log(error);
            }
        },

        // 유저 다건 삭제
        async userArrayDelete() {
            console.log(this.checkItem.length !== 0);
            if (this.checkItem.length === 0 || this.checkItem.length === null) {
                alert('하나 이상의 계정을 선택해 주세요');
            } else {
                let deleteAlert = confirm(
                    '선택한 계정을 삭제하시겠습니까? 계정 삭제 시 해당 계정으로 로그인이 불가능하며, 플랫폼 사용 이력이 모두 삭제됩니다.'
                );
                if (deleteAlert) {
                    console.log(this.checkItem);
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
                        console.log(error);
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
                    console.log(error);
                }
            }
        },
        // 유저 상세 조회
        async userDetailView(seq) {
            try {
                const {
                    data: { data: response },
                } = await getUserDetail(seq);
                this.addUserData = response;
                this.visible.AccountManagement = true;
                console.log(response);
            } catch (error) {
                console.log(error);
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
                        console.log(response);
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
                        console.log(error);
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
                    console.log(error);
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
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
