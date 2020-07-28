<template>
    <div>
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">
                    {{ tit }}
                </h2>
                <span class="date">{{ date }}</span>
            </div>
            <div class="detail-cont" v-html="cont"></div>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-black" @click="$router.go(-1)">
                <span>목록으로 가기</span>
            </button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'cc-view',
    data() {
        return {
            ok: true,
            tit:
                '11글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던 서울’ 오픈 포토리캡',
            url: null,
            date: '2020.06.17',
            cont:
                '<div class="img">' +
                '   <img src="http://placehold.it/400x200" alt="임시이미지"></div> ' +
                '   나이키가 서울 강남 가로수길에서 조던 브랜드 전용 공간을 선보인다222. ' +
                '<div class="img">' +
                '   <img src="http://placehold.it/400x200" alt="임시이미지"></div> ' +
                '   나이키가 서울 강남 가로수길에서 조던 브랜드 전용 공간을 선보인다333. ' +
                '<div class="img">' +
                '   <img src="http://placehold.it/400x200" alt="임시이미지"></div> ' +
                '   나이키가 서울 강남 가로수길에서 조던 브랜드 전용 공간을 선보11인다111.',
        };
    },
    methods: {},
};
</script>
<style scoped></style>
