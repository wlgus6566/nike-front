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
                    <tr
                        v-for="(item, index) in items.slice().reverse()"
                        :key="index"
                        v-bind:class="[{ noti: item.noti }]"
                    >
                        <td v-if="item.noti">
                            <span class="label-noti">중요</span>
                        </td>
                        <td v-else>
                            <span class="num">{{ item.num }}</span>
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
                    num: '1',
                    noti: false,
                    date: '2020.06.18',
                    agl: '1 NIKE 2020 PSKO 일정이 업데이트 되었습니다.',
                },
                {
                    num: '2',
                    noti: false,
                    date: '2020.06.18',
                    agl: '2 NIKE 2020 PSKO 일정이 업데이트 되었습니다.',
                },
                {
                    num: '3',
                    noti: false,
                    date: '2020.06.18',
                    agl: '3 NIKE 2020 PSKO 일정이 업데이트 되었습니다.',
                },
                {
                    num: null,
                    noti: true,
                    date: '2020.06.18',
                    agl: '4 NIKE 2020 PSKO 일정이 업데이트 되었습니다.',
                },
                {
                    num: null,
                    noti: true,
                    date: '2020.06.18',
                    agl: '5 NIKE 2020 PSKO 일정이 업데이트 되었습니다.',
                },
                {
                    num: null,
                    noti: true,
                    date: '2020.06.18',
                    agl: '6 NIKE 2020 PSKO 일정이 업데이트 되었습니다',
                },
            ],
        };
    },
    filters: {
        reverse: function (array) {
            return array.slice().reverse();
        },
    },
    mounted() {
        console.log(this.items);
    },
    computed: {},
    methods: {},
};
</script>
<style scoped></style>
