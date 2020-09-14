<template>
    <div id="wrap">
        <section id="container">
            <div class="login-wrap">
                <div class="inner">
                    <div class="title">비밀번호 변경</div>
                    <div class="bullet-list">
                        <p class="bullet-item">
                            아이디와 비밀번호는 중복 불가합니다.
                        </p>
                        <p class="bullet-item">
                            비밀번호에는 공통 사전 단어(예: NIKE(나이키),
                            JORDAN(조던) 등)를 포함할 수 없습니다.
                        </p>
                        <p class="bullet-item">
                            비밀번호는 동일한 글자, 문자, 숫자 등을 2글자이상
                            반복할 수 없습니다. (예: AA1234! / A12345@@ 등)
                        </p>
                        <p class="bullet-item">
                            비밀번호 재사용: 비밀번호 변경 시 현재 사용 중인
                            비밀번호의 재사용은 불가능하며, 기존과는 다른
                            비밀번호로 변경하셔야 합니다.
                        </p>
                    </div>
                    <div class="table-form">
                        <form action="" class="">
                            <fieldset>
                                <legend>비밀번호 변경</legend>
                                <div class="table-col">
                                    <label for="change-pw1"
                                        ><span class="tit">기존 비밀번호</span
                                        ><input
                                            type="password"
                                            id="change-pw1"
                                            class="pw"
                                            maxlength="16"
                                            placeholder=""
                                    /></label>
                                    <label for="change-pw2"
                                        ><span class="tit"
                                            ><span class="required"
                                                >새 비밀번호</span
                                            ></span
                                        ><input
                                            type="password"
                                            id="change-pw2"
                                            class="pw"
                                            maxlength="16"
                                            placeholder="(8~16자/대소문자/숫자/특수문자 포함)"
                                    /></label>
                                    <label for="change-pw3"
                                        ><span class="tit"
                                            ><span class="required"
                                                >새 비밀번호(확인)</span
                                            ></span
                                        ><input
                                            type="password"
                                            id="change-pw3"
                                            class="pw"
                                            maxlength="16"
                                            placeholder=""
                                    /></label>
                                </div>
                                <div class="btn-area">
                                    <button type="button" class="btn-s-black">
                                        <span>저장</span>
                                    </button>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_LOGIN_06',
};
</script>
<style scoped></style>
