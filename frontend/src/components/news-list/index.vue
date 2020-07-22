<template>
    <ul class="news-list">
        <li class="news-list-item" v-for="(item, index) in items" :key="index">
            <a href="#">
                <span class="thumbnail">
                    <img :src="item.img" alt="" />
                </span>
                <span class="info-box">
                    <strong class="title">{{ item.title }}</strong>
                    <span class="esc">{{ item.esc }}</span>
                </span>
            </a>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'newsList',
    data() {
        return {
            items: [
                {
                    title:
                        '글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡11111',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: null,
                    esc: '2020.06.17',
                },
                {
                    title:
                        '글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡2222',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: null,
                    esc: '2020.06.18',
                },
            ],
        };
    },
    methods: {},
};
</script>
<style scoped></style>
