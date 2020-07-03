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
                <h2 class="page-title"><span class="ko">메인 비주얼 관리</span></h2>
                <h3 class="form-title mt20">메인 비주얼 등록</h3>
                <hr class="hr-black" />
                <ul class="form-list">
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">TITLE</label>
                        </div>
                        <div class="form-column">
                            <input type="text" />
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">DESCRIPTION</label>
                        </div>
                        <div class="form-column">
                            <span class="textarea">
                                <textarea cols="100" rows="2"></textarea>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">이미지 등록</label>
                        </div>
                        <div class="form-column">
                            <div class="main-visual-upload">
                                <span class="thumb-file file-upload pc-upload">
                                    <input type="file" />
                                    <span class="thumb"> </span>
                                    <span class="txt">
                                        PC 이미지 등록
                                        <span class="sub">
                                            권장 사이즈는 최소 1600*800,<br />
                                            최대 3200*1600입니다.
                                        </span>
                                    </span>
                                </span>
                                <span class="thumb-file mobile-upload">
                                    <input type="file" />
                                    <span class="thumb"> </span>
                                    <span class="txt">
                                        Mobile 이미지 등록
                                        <span class="sub">
                                            권장 사이즈는 최소 1200*1200,<br />
                                            최대 4000*4000입니다.
                                        </span>
                                    </span>
                                </span>
                            </div>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">연결 URL (PC)</label>
                        </div>
                        <div class="form-column">
                            <div class="main-visual-upload">
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="url" checked />
                                        <span></span>
                                    </span>
                                    <span>기본(ASSET > ALL 메뉴로 연결)</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="url" />
                                        <span></span>
                                    </span>
                                    <span>URL 입력</span>
                                </label>
                            </div>
                        </div>
                    </li>
                </ul>
                <hr class="hr-gray" />
                <div class="btn-area">
                    <button type="button" class="btn-s-white"><span>취소</span></button>
                    <button type="button" class="btn-s-black"><span>저장</span></button>
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
    name: 'NIKE_P_MANAGE_01',
};
</script>
<style scoped></style>
