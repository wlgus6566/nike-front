<template>
    <div id="wrap">
        <section class="login">
            <div class="login-box">
                <strong class="title2">
                    비밀번호 찾기
                </strong>
                <p class="desc">
                    비밀번호 설정을 위한 ID(E-MAIL)을 입력해주세요. <br />
                    입력하신 이메일로 비밀번호 설정을 위한 URL이<br />
                    전송됩니다.
                </p>
                <div class="form-box">
                    <div class="pw-box">
                        <input type="text" placeholder="ID(E-MAIL)" />
                    </div>
                    <button type="button" class="btn-s-lg-orage"><span>이메일 발송</span></button>
                </div>
                <button type="button" class="btn-close"><span>닫기</span></button>
            </div>
        </section>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_LOGIN_03',
};
</script>
<style scoped></style>
