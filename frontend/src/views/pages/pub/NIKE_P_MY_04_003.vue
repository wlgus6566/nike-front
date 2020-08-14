<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"
                        ><img alt="" src="@/assets/images/logo-nike.svg"
                    /></a>
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
                <h3 class="form-title mt20">등록/수정</h3>
                <hr class="hr-black" />
                <ul class="form-list">
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title">상단 고정 여부</span>
                        </div>
                        <div class="form-column">
                            <label class="check-label">
                                <span class="radio">
                                    <input type="radio" name="show" checked />
                                    <i></i>
                                    <span class="txt">고정</span>
                                </span>
                            </label>
                            <label class="check-label">
                                <span class="radio">
                                    <input type="radio" name="show" />
                                    <i></i>
                                    <span class="txt">비고정</span>
                                </span>
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
                                <textarea
                                    cols="100"
                                    rows="2"
                                    style="height: 300px;"
                                ></textarea>
                            </span>
                        </div>
                    </li>
                </ul>
                <hr class="hr-gray" />
                <div class="btn-area">
                    <button type="button" class="btn-s-white">
                        <span>취소</span>
                    </button>
                    <button type="button" class="btn-s-black">
                        <span>저장</span>
                    </button>
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
    name: 'NIKE_P_MY_04_003',
};
</script>
<style scoped></style>
