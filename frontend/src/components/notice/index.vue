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
                        v-for="(item, index) in noticeData"
                        :key="index"
                        :class="{ noti: item.noticeYn === 'Y' }"
                    >
                        <td v-if="item.noticeYn === 'Y'">
                            <span class="label-noti">중요</span>
                        </td>
                        <td v-else>
                            <span class="num">{{ item.number }}</span>
                        </td>
                        <td class="agl">
                            <router-link :to="setUrl(item.noticeArticleSeq)">
                                {{ item.title }}
                            </router-link>
                        </td>
                        <td>
                            <span class="date">{{ item.updateDt }}</span>
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
            return `/mypage/notice/detail/${item}`.toLocaleLowerCase();
        },
    },
};
</script>
<style scoped></style>
