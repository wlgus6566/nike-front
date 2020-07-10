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
                <h2 class="page-title"><span class="ko">최근 본 폴더</span></h2>
                <ul class="sorting-list mt20">
                    <li class="active">
                        <button type="button"><span>ALL</span></button>
                    </li>
                    <li>
                        <button type="button"><span>ASSET</span></button>
                    </li>
                    <li>
                        <button type="button"><span>TOOLKIT</span></button>
                    </li>
                    <li>
                        <button type="button"><span>FOUNDATION</span></button>
                    </li>
                    <li>
                        <button type="button"><span>REPORT</span></button>
                    </li>
                </ul>
                <ul class="asset-item-list-row">
                    <li class="asset-item">
                        <a href="#">
                            <div class="thumbnail">
                                <img src="@/assets/images/img-asset-none@2x.png" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">타이틀입니다</strong>
                                <p class="txt">설명입니다</p>
                                <ul class="location">
                                    <li>ASSET</li>
                                    <li>FA</li>
                                </ul>
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
    name: 'NIKE_P_MY_01_002',
};
</script>
<style scoped></style>
