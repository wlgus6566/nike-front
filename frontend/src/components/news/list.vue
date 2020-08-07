<template>
    <div>
        <ul class="news-list">
            <li
                class="news-list-item"
                v-for="(item, index) in newsData"
                :key="index"
            >
                <router-link :to="setUrl(item.noticeArticleSeq)">
                    <span class="thumbnail">
                        <img
                            :src="item.thumbnailFilePhysicalName"
                            :alt="item.thumbnailFileName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">{{ item.title }}</strong>
                        <span class="date">등록일 필요</span>
                    </span>
                </router-link>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'newsList',
    data() {
        return {};
    },
    props: ['newsData'],
    methods: {
        setUrl(item) {
            return `/mypage/news/detail/${item}`.toLocaleLowerCase();
        },
    },
};
</script>
<style scoped></style>
