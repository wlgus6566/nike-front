<template>
    <div id="wrap">
        <section class="login">
            <div class="login-box">
                <strong class="title2">
                    비밀번호 찾기
                </strong>
                <p class="desc">
                    비밀번호 설정 메일 발송을 위한 ID(E-MAIL)을 입력해 주세요.<br />
                    입력하신 E-MAIL로 비밀번호 설정을 위한 URL이 전송 됩니다.
                </p>
                <div class="form-box">
                    <div class="pw-box">
                        <input type="text" placeholder="ID(E-MAIL)" />
                    </div>
                </div>
                <div class="login-btn-wrap">
                    <button type="button" class="btn-s-lg-orage"><span>E-MAIL 발송</span></button>
                </div>
                <button type="button" class="btn-close"><span>닫기</span></button>
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
    name: 'NIKE_P_LOGIN_03',
};
</script>
<style scoped></style>
