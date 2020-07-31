<template>
    <div>
        <div class="tbl-list">
            <table>
                <colgroup>
                    <col style="width: 90px;" />
                    <col style="width: auto;" />
                    <col style="width: 110px;" />
                </colgroup>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>등록일</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td v-if="">
                            <span class="label-noti">중요</span>
                        </td>
                        <td v-else>
                            <span class="num">{{}}</span>
                        </td>
                        <td class="agl">
                            <router-link to="/mypage/view">
                                {{}}
                            </router-link>
                        </td>
                        <td>
                            <span class="date">{{}}</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
<script>
export default {
    name: 'noticeList',
    data() {
        return {};
    },
    mounted() {},
    computed: {},
    methods: {},
};
</script>
<style scoped></style>
