<template>
    <div id="wrap" class="main">
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
                <h2>버튼 기본</h2>
                <button type="button" class="btn-s-black"><span>버튼</span></button>
                <a href="#" class="btn-s-orage"><span>링크</span></a>
                <a href="#" class="btn-s-white"><span>링크</span></a>
                <button type="button" class="btn-s-black" disabled><span>버튼</span></button>
                <a href="#" class="btn-s-orage disabled"><span>링크</span></a>
                <a href="#" class="btn-s-white disabled"><span>링크</span></a>
                <br /><br />

                <h2>영문 특별케이스</h2>
                <button type="button" class="btn-s-black"><span class="bebas">ORDER</span></button>
                <br /><br />

                <h2>버튼 스몰</h2>
                <button type="button" class="btn-s-black-sm"><span>버튼</span></button>
                <a href="#" class="btn-s-black-sm"><span>링크</span></a>
                <a href="#" class="btn-s-gray-sm"><span>링크</span></a>
                <a href="#" class="btn-s-white-sm"><span>링크</span></a>
                <br /><br />

                <h2>버튼 스몰 아이콘</h2>
                <button type="button" class="btn-s-white-sm">
                    <i class="icon-link"></i>
                    <span>LINK</span>
                </button>
                <button type="button" class="btn-s-white-sm">
                    <i class="icon-check"></i>
                    <span>ADDED</span>
                </button>
                <br />
                <br />

                <h2>버튼 라지</h2>
                <button type="button" class="btn-s-black-lg"><span>버튼</span></button>
                <a href="#" class="btn-s-white-lg"><span>링크</span></a>
                <br /><br />

                <h2>상세 버튼</h2>
                <div class="btn-detail">
                    <a href="#" class="btn-list"><span>목록으로 가기</span></a>
                    <div class="btn-box">
                        <button type="button" class="btn-o-white"><span>삭제</span></button>
                        <button type="button" class="btn-o-white"><span>수정</span></button>
                        <button type="button" class="btn-o-gray">
                            <i class="icon-mail"></i>
                            <span>알림메일전송</span>
                        </button>
                    </div>
                </div>
                <br /><br />

                <h2>테이블용</h2>
                <button type="button" class="btn-form"><span>버튼</span></button>
                <a href="#" class="btn-form-gray"><span>링크</span></a>
                <br /><br />

                <h2>텍스트용버튼</h2>
                <button type="button" class="txt-btn-black"><span>이미지 재등록</span></button>
                <a href="#" class="txt-btn"><span>더보기</span></a>
                <button class="btn-file-txt">
                    <i class="icon-add"></i>
                    <span>파일 추가하기</span>
                </button>
                <div class="btn-area-txt">
                    <button type="button" class="txt-btn"><span>추가</span></button>
                    <button type="button" class="txt-btn"><span>식제</span></button>
                </div>
                <br /><br />

                <h2>버튼 업로드</h2>
                <button class="btn-file-full">
                    <i class="icon-add"></i>
                    <span>파일 추가하기</span>
                </button>
                <br /><br />
                <input type="file" multiple />
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
    name: 'button',
};
</script>
<style scoped></style>
