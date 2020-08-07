<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <div class="sorting-area">
            <el-cascader
                v-model="value"
                :options="options"
                :props="{ expandTrigger: 'hover' }"
                @change="handleChange"
            ></el-cascader>
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
                            <a href="#" class="under-link">
                                <span>{{ item.nickname }}</span>
                            </a>
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
            <button type="button" class="btn-form">
                <span>삭제</span>
            </button>
            <div class="right">
                <button type="button" class="btn-form-gray">
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
    </div>
</template>
<script>
    import {getUser} from '@/api/user';
    import SearchInput from '@/components/search-input';
    import Pagination from '@/components/pagination';

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
            status: null,
            authSeq: null,
            beginDt: null,
            endDt: null,
            userDataList: '',
            userData: '',
            checkAll: false,
            checkItem: [],
            authority: {
                value: [],
                options: [{
                    value: 'guide',
                    label: 'Guide',
                    children: [{
                        value: 'disciplines',
                        label: 'Disciplines',
                        children: [
                            {
                                value: 'consistency',
                                label: 'Consistency'
                            },
                            {
                                value: 'feedback',
                                label: 'Feedback'
                            },
                            {
                                value: 'efficiency',
                                label: 'Efficiency'
                            },
                            {
                                value: 'controllability',
                                label: 'Controllability'
                            }
                        ],
                    },
                 ]
                };
            },
        }
    },
    components: {
        SearchInput,
        Pagination,
    },
    created() {
        this.getUserList();
    },
    methods: {
        handleChange(value) {
            console.log(value);
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
            console.log(21);
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
            try {
                const {
                    data: { data: response },
                } = await getUser({
                    size: this.itemLength,
                    page: this.page,
                    keyword: this.searchKeyword,
                    status: this.status,
                    sort: this.sort,
                    authSeq: this.authSeq,
                    beginDt: this.beginDt,
                    endDt: this.endDt,
                });
                //this.userDataList = response;
                this.userData = response.content;
                //this.loading = false;
                //this.totalItem = this.userDataList.totalElements;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
