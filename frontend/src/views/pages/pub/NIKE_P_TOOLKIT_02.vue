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
                <h2 class="page-title">VMS</h2>

                <div class="upload-summury">
                    <strong class="title">파일올리기</strong>
                    <p class="desc">
                        자료는 NIKE.INC.와 NIKE KOREA LLC.의 자산입니다.<br />
                        보안 규정을 준수하시기 바랍니다.
                    </p>
                    <button type="button" class="btn-s-black"><span class="bebas">UPLOAD</span></button>
                </div>
                <div class="sorting-area">
                    <div class="filter-list">
                        <button type="button" class="type-list active"><span>컬럼타입</span></button>
                        <button type="button" class="type-list"><span>로우타입</span></button>
                    </div>
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="filter-select">
                        <select>
                            <option value="최신순">최신순</option>
                            <option value="시작일순">시작일순</option>
                        </select>
                    </div>
                    <div class="search-input">
                        <input type="text" placeholder="검색어를 입력해주세요." />
                        <button type="button"><span>검색</span></button>
                    </div>
                </div>

                <ul class="asset-item-list-row">
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀 입니다 타이으트르르 입니다 두줄표시</strong>
                                <p class="txt">설명입니다 설명입니다 설명</p>
                                <p class="date">2020.00.00 ~ 2020.00.00</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
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
    name: 'NIKE_P_TOOLKIT_02',
};
</script>
<style scoped></style>
