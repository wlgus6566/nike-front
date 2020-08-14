<template>
    <div>
        <div class="sorting-area">
            <p class="total">전체 <strong>(20)</strong></p>
            <div class="search-input"> <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." />
                    <button type="button" class="search"><span>검색</span></button>
                </div>
                <button type="button" class="btn-txt"><span>취소</span></button>
            </div>
        </div>
        <div class="news-list">
            <div class="news-list-item">
                <a href="#">
							<span class="thumbnail">
								<img src="http://placehold.it/410X410" alt="">
							</span>
                    <span class="info-box">
								<p class="title">글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던 서울’ 오픈 포토리캡 글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던 서울’ 오픈 포토리캡</p>
								<span class="date">2020.06.17</span>
							</span>
                </a>
            </div>
            <div class="news-list-item">
                <a href="#">
							<span class="thumbnail">
								<img src="http://placehold.it/410X410" alt="">
							</span>
                    <span class="info-box">
								<p class="title">글로벌 스포츠 브랜드 나이키, AK 수원 오픈 리캡</p>
								<span class="date">2020.06.17</span>
							</span>
                </a>
            </div>
            <div class="news-list-item">
                <a href="#">
							<span class="thumbnail">
								<img src="http://placehold.it/410X410" alt="">
							</span>
                    <span class="info-box">
								<p class="title">문화적 커뮤니티 공간이 될 조던 서울, 30일 가로수길 오픈</p>
								<span class="date">2020.06.17</span>
							</span>
                </a>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'news-list',
};
</script>
<style scoped></style>
