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
                <tbody v-for="(item, index) in items" :key="index">
                    <tr class="noti" v-if="">
                        <td>
                            <span class="label-noti">중요</span>
                        </td>
                        <td class="agl">
                            <a href="#">{{ item.agl }}</a>
                        </td>
                        <td>
                            <span class="date">{{ item.date }}</span>
                        </td>
                    </tr>
                    <tr v-else>
                        <td>
                            <span class="num">{{ index }}</span>
                        </td>
                        <td class="agl">
                            <a href="#">{{ item.agl }}</a>
                        </td>
                        <td>
                            <span class="date">{{ item.date }}</span>
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
        return {
            items: [
                {
                    date: '2020.06.18',
                    agl: 'NIKE 2020 PSKO 일정이 업데이트 되었습니다111.',
                },
                {
                    date: '2020.06.18',
                    agl: 'NIKE 2020 PSKO 일정이 업데이트 되었습니다222.',
                },
                {
                    date: '2020.06.18',
                    agl: 'NIKE 2020 PSKO 일정이 업데이트 되었습니다333.',
                },
            ],
        };
    },
    computed: {},
    methods: {},
};
</script>
<style scoped></style>
