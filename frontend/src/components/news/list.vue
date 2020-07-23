<template>
    <ul class="news-list">
        <li class="news-list-item" v-for="(item, index) in items" :key="index">
            <a v-bind:href="item.url">
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
                        '111글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: 'https://www.nike.co.kr',
                    esc: '2020.06.17',
                },
                {
                    title:
                        '222글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: 'https://www.nike.co.kr',
                    esc: '2020.06.18',
                },
            ],
        };
    },
    methods: {},
};
</script>
<style scoped></style>
