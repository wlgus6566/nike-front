<template>
    <div>
        <h3 class="form-title mt20">등록/수정</h3>
        <hr class="hr-black" />
        <ul class="form-list">
            <EditInner />
        </ul>
        <hr class="hr-gray" />
        <div class="btn-area">
            <button type="button" class="btn-s-white"><span>취소</span></button>
            <button type="button" class="btn-s-black"><span>저장</span></button>
        </div>
    </div>
</template>
<script>
import EditInner from '@/components/news/edit-inner.vue';

export default {
    name: 'Edit',
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
    components: {
        EditInner,
    },
    methods: {},
};
</script>
<style scoped></style>
