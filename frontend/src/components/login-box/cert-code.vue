<template>
    <form action="#" @submit.prevent="$emit('login')">
        <div class="login-box" ref="loginBox">
            <strong class="title2"> E-MAIL 인증 </strong>
            <p class="desc">
                해당 계정의 E-MAIL로 인증코드가 발송되었습니다.<br />
                E-MAIL 확인 후 인증코드를 입력해 주세요.<br />
                ※인증코드는 대소문자를 구분합니다.
            </p>
            <div class="form-box">
                <div class="certified-box">
                    <input type="txt" v-model="loginData.certCode" max="10" />
                </div>
            </div>
            <div class="login-btn-wrap">
                <button type="submit" class="btn-s-lg-orage">
                    <span>인증하기</span>
                </button>
            </div>
        </div>
    </form>
</template>
<script>
export default {
    name: 'certCode',
    data() {
        return {};
    },
    props: ['loginData', 'activeState'],
    mounted() {},
};
</script>
<style scoped>
.login-box .title {
    display: block;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    color: #fff;
    font-weight: normal;
}
.login-box .title em {
    display: block;
    font-size: 30px;
    letter-spacing: 0.38px;
    line-height: 36px;
}
.login-box .title span {
    display: block;
    vertical-align: top;
    font-size: 46px;
    line-height: 55px;
    letter-spacing: 0.58px;
}
.login-box .title2 {
    display: block;
    font-size: 30px;
    line-height: 44px;
    letter-spacing: -2px;
    color: #fff;
    font-weight: bold;
}
.login-box .desc {
    display: block;
    margin-top: 5px;
    font-size: 14px;
    color: #fff;
    line-height: 20px;
    opacity: 0.5;
}
.login-box .txt-btn {
    font-weight: normal;
}
.form-box {
    margin-top: 20px;
}
.form-box input {
    width: 100%;
    background: rgba(255, 255, 255, 0.15);
    border: none;
    font-size: 16px;
    font-weight: normal;
    color: #fff;
    letter-spacing: 0;
}
.form-box input::placeholder,
.form-box input::-webkit-input-placeholder {
    font-family: 'Bebas Neue', sans-serif !important;
    font-size: 16px !important;
    letter-spacing: 0.5px !important;
    color: #a7a7a7 !important;
}
.form-box input + input {
    margin-top: 8px;
}
.login-btn-wrap {
    margin-top: 20px;
}
.login-btn-wrap.half [class^='btn-s'] {
    flex-basis: calc((100% - 10px) / 2);
}
.login-btn-wrap.half [class^='btn-s'] + [class^='btn-s'] {
    margin-left: 10px;
}
.login-btn-wrap.half .btn-s {
    border-color: #fff;
}
.login-btn-wrap .btn-s-lg-orage {
    width: 100%;
}
.login-box .bnt-box {
    margin-top: 12px;
}
</style>
