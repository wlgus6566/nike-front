<template>
    <div>
        <h3 class="form-title mt20">등록/수정</h3>
        <hr class="hr-black" />
        <ul class="form-list">
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title">상단 고정 여부</span>
                </div>
                <div class="form-column">
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="show" checked />
                            <span></span>
                        </span>
                        <span>고정</span>
                    </label>
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="show" />
                            <span></span>
                        </span>
                        <span>비고정</span>
                    </label>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">제목</label>
                </div>
                <div class="form-column">
                    <input type="text" />
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">내용</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2" style="height: 300px;"></textarea>
                    </span>
                </div>
            </li>
        </ul>
        <hr class="hr-gray" />
        <div class="btn-area">
            <button type="button" class="btn-s-white"><span>취소</span></button>
            <button type="button" class="btn-s-black"><span>저장</span></button>
        </div>
    </div>
</template>
<script>
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
    methods: {},
};
</script>
<style scoped></style>
