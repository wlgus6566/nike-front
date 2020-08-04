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
                    <tr v-for="item in noticeData" :key="item.noticeArticleSeq">
                        <td v-if="item.noticeYn === 'Y'">
                            <span class="label-noti">중요</span>
                        </td>
                        <td v-else>
                            <span class="num">
                                숫자가 있어야하는군
                            </span>
                        </td>
                        <td class="agl">
                            <router-link :to="setUrl(item.noticeArticleSeq)">
                                {{ item.title }}
                            </router-link>
                        </td>
                        <td>
                            <span class="date">등록일이 없습니다</span>
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
    props: ['noticeData'],
    mounted() {},
    computed: {},
    methods: {
        setUrl(item) {
            return `/mypage/notice/${item}`.toLocaleLowerCase();
        },
    },
};
</script>
<style scoped></style>
