<template>
    <section class="login">
        <div class="login-inner">
            <component :is="LoginBox" @changeLoginBox="changeLoginBox" />
            <p class="f-desc">
                사용자는 NIKE SPACE에 로그인함으로써,<br />
                개인정보 처리방침 및 이용약관에 동의합니다.
            </p>
        </div>
    </section>
</template>
<script>
import LoginForm from '@/components/login-box/login-form';
import CertCode from '@/components/login-box/cert-code';
import FindPW from '@/components/login-box/find-password';
export default {
    name: 'login',
    data: function () {
        return {
            LoginBox: 'LoginForm',
        };
    },
    components: { LoginForm, CertCode, FindPW },
    methods: {
        changeLoginBox(compName) {
            this.LoginBox = compName;
        },
    },
};
</script>

<style scoped>
.login {
    position: relative;
    display: flex;
    width: 100%;
    min-height: 940px;
    height: 100%;
    align-items: center;
    flex-direction: column;
    background: url('../../../assets/images/img-login-main-bg@2x.png') no-repeat
        50% 50% / 3000px 2000px #000;
}
.login-inner {
    position: absolute;
    top: 50%;
    left: 50%;
    display: flex;
    padding: 30px 30px 50px 30px;
    flex-direction: column;
    align-items: center;
    transform: translate(-50%, -50%);
}
.login-inner:before {
    content: '';
    display: block;
    width: 500px;
    height: 500px;
    background: url('../../../assets/images/svg/img-login-swoosh.svg') no-repeat
        center center / contain;
}
.login .f-desc {
    display: block;
    margin-top: 30px;
    text-align: center;
    color: #fff;
    opacity: 0.4;
    font-size: 12px;
    line-height: 18px;
    letter-spacing: -0.55px;
}
</style>
