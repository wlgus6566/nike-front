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
                <h2 class="page-title"><span class="ko">공지사항</span></h2>
                <div class="sorting-area">
                    <div class="search-input">
                        <input type="text" placeholder="검색어를 입력해주세요." />
                        <button type="button"><span>검색</span></button>
                    </div>
                </div>
                <div class="tbl-list">
                    <table>
                        <colgroup>
                            <col style="width: 90px;" />
                            <col style="width: auto;" />
                            <col style="width: 110px;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="noti">
                                <td>
                                    <span class="label-noti">중요</span>
                                </td>
                                <td class="agl">
                                    <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                                </td>
                                <td><span class="date">2020.06.17</span></td>
                            </tr>
                            <tr class="noti">
                                <td>
                                    <span class="label-noti">중요</span>
                                </td>
                                <td class="agl">
                                    <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                                </td>
                                <td><span class="date">2020.06.17</span></td>
                            </tr>
                            <tr class="noti">
                                <td>
                                    <span class="label-noti">중요</span>
                                </td>
                                <td class="agl">
                                    <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                                </td>
                                <td><span class="date">2020.06.17</span></td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="num">18</span>
                                </td>
                                <td class="agl">
                                    <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                                </td>
                                <td><span class="date">2020.06.17</span></td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="num">18</span>
                                </td>
                                <td class="agl">
                                    <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                                </td>
                                <td><span class="date">2020.06.17</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="btn-tbl-box">
                    <button type="button" class="btn-form">
                        <span data-v-1756ba49="">삭제</span>
                    </button>
                    <div class="right">
                        <button type="button" class="btn-form-gray">
                            <span data-v-1756ba49="">등록</span>
                        </button>
                    </div>
                </div>

                <div class="pagination">
                    <button type="button" class="prev"><span>이전</span></button>
                    <ul>
                        <li><em>1</em></li>
                        <li>
                            <button type="button"><span>2</span></button>
                        </li>
                        <li>
                            <button type="button"><span>3</span></button>
                        </li>
                        <li>
                            <button type="button"><span>4</span></button>
                        </li>
                        <li>
                            <button type="button"><span>5</span></button>
                        </li>
                        <li>
                            <button type="button"><span>6</span></button>
                        </li>
                        <li>
                            <button type="button"><span>7</span></button>
                        </li>
                        <li>
                            <button type="button"><span>8</span></button>
                        </li>
                        <li>
                            <button type="button"><span>9</span></button>
                        </li>
                        <li>
                            <button type="button"><span>10</span></button>
                        </li>
                    </ul>
                    <button type="button" class="next"><span>다음</span></button>
                </div>
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
    name: 'NIKE_P_MY_04_001',
};
</script>
<style scoped></style>
