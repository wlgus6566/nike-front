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
                <h2 class="page-title"><span class="ko">상품등록</span></h2>
                <h3 class="form-title mt20">상품등록</h3>
                <hr class="hr-black" />
                <ul class="form-list-thumb">
                    <li class="form-row thumb-row">
                        <span class="thumb-file">
                            <input type="file" />
                            <span class="thumb"> </span>
                            <span class="txt">썸네일 이미지 재등록</span>
                        </span>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title required">상태</span>
                        </div>
                        <div class="form-column">
                            <label class="check-label">
                                <span class="radio">
                                    <input
                                        type="radio"
                                        name="campaign"
                                        checked
                                    />
                                    <i></i>
                                    <span class="txt">노출</span>
                                </span>
                            </label>
                            <label class="check-label">
                                <span class="radio">
                                    <input type="radio" name="campaign" />
                                    <i></i>
                                    <span class="txt">미노출</span>
                                </span>
                            </label>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <lavel class="label-title required">구분</lavel>
                        </div>
                        <div class="form-column">
                            <div class="column-box">
                                <div class="column">
                                    <select>
                                        <option>대구분</option>
                                    </select>
                                </div>
                                <div class="column">
                                    <select>
                                        <option>소구분</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">에이전시</label>
                        </div>
                        <div class="form-column">
                            <select style="width: 100%;">
                                <option>에이전시 선택</option>
                            </select>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">상품 명</label>
                        </div>
                        <div class="form-column">
                            <span class="textarea">
                                <textarea cols="100" rows="2"></textarea>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">추가설명</label>
                        </div>
                        <div class="form-column">
                            <input type="text" />
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required"
                                >단가 입력</label
                            >
                        </div>
                        <div class="form-column">
                            <span class="input-txt">
                                <input type="text" />
                                <span class="txt">원</span>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required"
                                >최소 주문 수량</label
                            >
                        </div>
                        <div class="form-column">
                            <span class="input-txt">
                                <input type="text" />
                                <span class="txt">개</span>
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
    name: 'NIKE_P_ORDER_01',
};
</script>
<style scoped></style>
