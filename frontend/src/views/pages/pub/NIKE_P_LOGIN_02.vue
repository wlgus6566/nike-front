<template>
    <div id="wrap">
        <section class="login">
            <div class="login-box">
                <strong class="title2">
                    E-MAIL 인증
                </strong>
                <p class="desc">
                    해당 계정의 E-MAIL로 인증코드가 발송되었습니다.<br />
                    E-MAIL 확인 후 인증코드를 입력해 주세요.<br />
                    ※인증코드는 대소문자를 구분합니다.
                </p>
                <div class="form-box">
                    <div class="certified-box">
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                        <input type="txt" max="1" />
                    </div>
                </div>
                <div class="login-btn-wrap">
                    <button type="button" class="btn-s-lg-orage"><span>인증하기</span></button>
                </div>
            </div>
            <p class="f-desc">
                사용자는 NIKE D&P Plarform에 로그인함으로써,<br />
                개인정보 취급방침 및 이용약관에 동의합니다.
            </p>
        </section>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_LOGIN_02',
};
</script>
<style scoped></style>
