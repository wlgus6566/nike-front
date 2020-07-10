<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"><img alt="" src="@/assets/images/logo-nike.svg" /></a>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <a href="#">TEST</a>
                        </li>
                    </ul>
                    <div class="bg"></div>
                </nav>
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <h2 class="page-title"><span class="ko">위시리스트</span></h2>
                <div class="all-box">
                    <!-- todo 전체선택 스크립트 작업 필요  -->
                    <label class="check-label">
                        <span class="checkbox">
                            <input type="checkbox" />
                            <span></span>
                        </span>
                        <strong class="txt">전체선택</strong>
                    </label>
                    <p class="desc"><em>1</em>개의 파일이 선택됨</p>
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="btn-box">
                        <button type="button" class="btn-s-lightgray-sm">
                            <i class="icon-cart"></i><span>선택 CART 담기</span>
                        </button>
                        <button type="button" class="btn-s-lightgray-sm">
                            <i class="icon-del"></i><span>선택삭제</span>
                        </button>
                    </div>
                </div>
                <ul class="wish-item-list">
                    <li class="wish-item">
                        <span class="checkbox">
                            <input type="checkbox" />
                            <span></span>
                        </span>
                        <a href="#" class="title-box">
                            <span class="thumbnail">
                                <img src="http://placehold.it/400x200" alt="임시ㅣ미지" />
                            </span>
                            <span class="info-box">
                                <strong class="title"
                                    >뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설</strong
                                >
                                <span class="txt">일이삼사</span>
                                <span class="desc-txt-box">
                                    <span class="desc-txt">15,000원</span>
                                    <span class="desc-txt">에이전시명</span>
                                </span>
                            </span>
                        </a>
                        <div class="quantity-box">
                            <span class="title">최소주문수량</span>
                            <span class="num"><em>1,000</em>개</span>
                        </div>
                        <div class="btn-box">
                            <button type="button" class="btn-s-black-sm">
                                <span>CART</span>
                            </button>
                            <button type="button" class="delete">
                                <span>삭제</span>
                            </button>
                        </div>
                    </li>
                    <li class="wish-item">
                        <span class="checkbox">
                            <input type="checkbox" />
                            <span></span>
                        </span>
                        <a href="#" class="title-box">
                            <span class="thumbnail">
                                <img src="http://placehold.it/400x200" alt="임시ㅣ미지" />
                            </span>
                            <span class="info-box">
                                <strong class="title"
                                    >뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설</strong
                                >
                                <span class="txt">일이삼사</span>
                                <span class="desc-txt-box">
                                    <span class="desc-txt">15,000원</span>
                                    <span class="desc-txt">에이전시명</span>
                                </span>
                            </span>
                        </a>
                        <div class="quantity-box">
                            <span class="title">최소주문수량</span>
                            <span class="num"><em>1,000</em>개</span>
                        </div>
                        <div class="btn-box">
                            <button type="button" class="btn-s-black-sm">
                                <span>CART</span>
                            </button>
                            <button type="button" class="delete">
                                <span>삭제</span>
                            </button>
                        </div>
                    </li>
                    <li class="wish-item">
                        <span class="checkbox">
                            <input type="checkbox" />
                            <span></span>
                        </span>
                        <a href="#" class="title-box">
                            <span class="thumbnail">
                                <img src="http://placehold.it/400x200" alt="임시ㅣ미지" />
                            </span>
                            <span class="info-box">
                                <strong class="title"
                                    >뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴 유니버설 뉴
                                    유니버설 뉴 유니버설</strong
                                >
                                <span class="txt">일이삼사</span>
                                <span class="desc-txt-box">
                                    <span class="desc-txt">15,000원</span>
                                    <span class="desc-txt">에이전시명</span>
                                </span>
                            </span>
                        </a>
                        <div class="quantity-box">
                            <span class="title">최소주문수량</span>
                            <span class="num"><em>1,000</em>개</span>
                        </div>
                        <div class="btn-box">
                            <button type="button" class="btn-s-black-sm">
                                <span>CART</span>
                            </button>
                            <button type="button" class="delete">
                                <span>삭제</span>
                            </button>
                        </div>
                    </li>
                </ul>
            </div>
            <aside>
                <div class="aside-wrap">
                    aside
                </div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_MY_02_002',
};
</script>
<style scoped></style>
