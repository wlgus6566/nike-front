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
                <h2 class="page-title"><span class="ko">주문내역확인</span></h2>
                <div class="period-check">
                    <strong class="title">기간조회</strong>
                    <div class="data-picker">
                        <el-date-picker
                            v-model="value1"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="Start date"
                            end-placeholder="End date"
                        >
                        </el-date-picker>
                    </div>
                </div>
                <ul class="period-item-list">
                    <li class="period-item">
                        <a href="#" class="top">
                            <strong class="num">주문일 : 2020.01.20 12:00</strong>
                            <span class="link-txt">주문상세보기</span>
                        </a>
                        <div class="cont">
                            <span class="thumbnail">
                                <img src="http://placehold.it/400x200" alt="임시ㅣ미지" />
                            </span>
                            <span class="info-box">
                                <strong class="title"
                                    >뉴 유니버설 H/L 177X177뉴 유니버설 H/L 177X177 뉴 유니버설 H/L
                                    177X177뉴 유니버설 H/L 177X177외 4개</strong
                                >
                            </span>
                        </div>
                    </li>
                    <li class="period-item">
                        <a href="#" class="top">
                            <strong class="num">주문일 : 2020.01.20 12:00</strong>
                            <span class="link-txt">주문상세보기</span>
                        </a>
                        <div class="cont">
                            <span class="thumbnail">
                                <img src="http://placehold.it/400x200" alt="임시ㅣ미지" />
                            </span>
                            <span class="info-box">
                                <strong class="title"
                                    >뉴 유니버설 H/L 177X177뉴 유니버설 H/L 177X177 뉴 유니버설 H/L
                                    177X177뉴 유니버설 H/L 177X177외 4개</strong
                                >
                            </span>
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
    name: 'NIKE_P_MY_02_001',
};
</script>
<style scoped></style>
