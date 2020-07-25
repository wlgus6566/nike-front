<template>
    <div>
        <ul class="form-list" v-show="$route.meta.title == '공지사항'">
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
        <ul class="form-list" v-show="$route.meta.title == 'NEWS'">
            >
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">썸네일 이미지</span>
                </div>
                <div class="form-column">
                    <span class="thumb-file new-upload">
                        <input type="file" />
                        <span class="thumb"> </span>
                        <span class="txt">
                            NEWS 이미지 등록
                            <span class="sub">
                                권장 사이즈는 최소 660*330,<br />
                                최대 3000*1500입니다.
                            </span>
                        </span>
                    </span>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">제목</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2" style="height: 60px;"></textarea>
                    </span>
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
    </div>
</template>
<script>
export default {
    name: 'Edit',
    data() {
        return {
            notice: true,
            news: false,
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
