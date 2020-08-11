<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <div class="sorting-area">
            <div class="select-cascader">
                <el-cascader
                    v-model="authority.value"
                    :options="authority.options"
                    :props="{ checkStrictly: true }"
                    @change="handleChange"
                />
            </div>
            <div
                class="date-picker-wrap"
                :class="{ active: dataPickerShowData.visible }"
            >
                <button
                    type="button"
                    class="btn-date-picker"
                    @click="dataPickerShow"
                >
                    <span>기간조회</span>
                </button>
                <div class="inner">
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
        <div class="tbl-list">
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
                                <span></span>
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
                                <span></span>
                            </span>
                        </td>
                        <td>
                            <button class="under-link">
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

        <div class="btn-tbl-box">
            <button type="button" class="btn-form" @click="authDelete">
                <span>삭제</span>
            </button>
            <div class="right">
                <button
                    type="button"
                    class="btn-form-gray"
                    @click="showDetailView"
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
            :visible.sync="visible.detailView"
            :addUserData="addUserData"
            :addAuthority="addAuthority"
            @userIdCheck="userIdCheck"
            @addAuthData="addAuthData"
        />
    </div>
</template>
<script>
import { getUser, postUser, getDuplicate, deleteUser } from '@/api/user';
import { getAuthCacheList } from '@/api/auth';
import FilterSelect from '@/components/filter-select';
import SearchInput from '@/components/search-input';
import Pagination from '@/components/pagination';
import AccountManagement from '@/views/pages/management/account-management.vue';

export default {
    name: 'account',
    data() {
        return {
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
                        value: null,
                        label: '계정 상태',
                    },
                    {
                        value: '휴면',
                        label: '휴면',
                    },
                    {
                        value: '정상',
                        label: '정상',
                    },
                ],
                value: null,
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
            visible: {
                detailView: false,
            },
            dataPickerShowData: {
                visible: false,
            },
            addAuthority: {
                value: [null],
                options: [
                    {
                        value: null,
                        label: '권한그룹을 선택해 주세요.\n',
                    },
                ],
            },
            addUserData: {
                authSeq: null,
                nickname: null,
                userId: null,
            },
        };
    },
    components: {
        FilterSelect,
        SearchInput,
        Pagination,
        AccountManagement,
    },
    created() {
        this.getUserList();
        this.authCacheList();
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
        },
        'range.endDt'(val) {
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
        },
    },
    methods: {
        //dataPicker show
        dataPickerShow() {
            if (this.dataPickerShowData.visible) {
                this.dataPickerShowData.visible = false;
            } else {
                this.dataPickerShowData.visible = true;
            }
        },
        // 상세 팝업
        showDetailView() {
            this.visible.detailView = true;
        },
        dates() {},
        minDate() {
            const date = new Date();
            date.setMonth(date.getMonth() - 3);
            return date;
        },
        handleChange(value) {
            console.log(value);
        },
        // checkbox
        checked(seq, del) {
            console.log(seq);
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
            //this.loadingData = true;
            console.log(this.listSortSelect.value);
            //console.log(this.authority.value.slice(-1)[0]);

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
                //this.userDataList = response;
                this.userData = response.content;
                //this.loading = false;
                //this.totalItem = this.userDataList.totalElements;
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

        // 권한 조회
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

        // 유저 삭제
        async authDelete() {
            console.log(this.checkItem);
            try {
                const {
                    data: { data: response },
                } = await deleteUser({
                    userSeqArray: [4],
                });
                console.log(response);
            } catch (error) {
                console.log(error);
            }
        },

        // 유저 추가
        async addAuthData(Seq, userData) {
            console.log(Seq.slice(-1)[0]);
            console.log(userData);
            try {
                const response = await postUser({
                    authSeq: Seq.slice(-1)[0],
                    nickname: userData.nickname,
                    userId: userData.userId,
                });
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.log(error);
            }
        },

        // 유저 id 체크
        async userIdCheck(id) {
            console.log(id);
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
