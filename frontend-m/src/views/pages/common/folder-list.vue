<template>
    <div>

        <div class="sorting-area">
            <button type="button" class="type-list">
                <span>컬럼타입</span>
            </button>
            <!-- todo select 스크립트 작업 필요  -->
            <div class="filter-select">
                <select name="" id="">
                    <option value="최신순">최신순</option>
                </select>
            </div>
            <div class="search-input "> <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." />
                    <button type="button" class="search"><span>검색</span></button>
                </div>
                <button type="button" class="btn-txt"><span>취소</span></button>
            </div>
        </div>
        <ul class="folder-list-row">
            <li class="folder-item">
                <a href="#">
                    <div class="thumbnail">
                        <img src="http://placehold.it/410X600" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">타이틀입니다</strong>
                        <p class="date">2020.00.00 ~ 2020.00.00</p>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
            <li class="folder-item">
                <a href="#">
                    <div class="thumbnail">
                        <img src="http://placehold.it/410X600" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다</strong>
                        <p class="date">2020.00.00 ~ 2020.00.00</p>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
            <li class="folder-item">
                <a href="#">
                    <div class="thumbnail">
                        <img src="http://placehold.it/410X600" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">타이틀입니다</strong>
                        <p class="date">2020.00.00 ~ 2020.00.00</p>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
            <li class="folder-item">
                <a href="#">
                    <div class="thumbnail">
                        <img src="http://placehold.it/410X600" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다</strong>
                        <p class="date">2020.00.00 ~ 2020.00.00</p>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
            <li class="folder-item">
                <a href="#">
                    <div class="thumbnail">
                        <img src="http://placehold.it/410X600" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title">타이틀입니다 타이틀입니다 타이틀입니다 타이틀입니다타이틀입니다</strong>
                        <p class="date">2020.00.00 ~ 2020.00.00</p>
                    </div>
                    <div class="view-area">
                        <span class="view">10,000</span>
                    </div>
                </a>
            </li>
        </ul>

    </div>
</template>
<script>
export default {
    name: 'folder-list',
    data() {
        return {
            uploadFolderData: [],
            page: 0,
            pageSize: 5,
            totalPage: null,
            typeCd: 'ALL',
            pageLast: true,
            loadingData: false,
            orderType: 'LATEST',
            orderSelectList: [
                {
                    value: 'LATEST',
                    title: '최신순',
                },
                {
                    value: 'START_DATE',
                    title: '시작일 순',
                },
            ],
        };
    },
};
</script>
<style scoped></style>
