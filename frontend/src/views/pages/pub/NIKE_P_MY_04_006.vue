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
                <h2 class="page-title">NEWS</h2>
                <h3 class="form-title mt20">등록/수정</h3>
                <hr class="hr-black" />
                <ul class="form-list">
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
    name: 'NIKE_P_MY_04_006',
};
</script>
<style scoped></style>
