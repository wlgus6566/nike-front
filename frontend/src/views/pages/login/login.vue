<template>
    <section class="login">
        <div class="login-inner">
            <div class="nike-logo" ref="logo">
                <img
                    src="@/assets/images/svg/nike-space.svg"
                    alt="nike space"
                />
            </div>
            <component
                :is="LoginBox"
                :loginData="loginData"
                @login="login"
                @updateValue="updateValue"
                @changeLoginBox="changeLoginBox"
            />
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
    data() {
        return {
            LoginBox: 'LoginForm',
            loginData: {
                username: '',
                password: '',
                certCode: '',
            },
        };
    },
    watch: {
        LoginBox() {
            console.log(1);
            const login = document.querySelector('.login-box');
            login.classList.add('active');
        },
    },
    mounted() {
        this.$refs.logo.classList.add('active');
    },
    components: { LoginForm, CertCode, FindPW },
    methods: {
        changeLoginBox(compName) {
            this.LoginBox = compName;
        },
        updateValue(target, value) {
            this.loginData[target] = value;
        },
        async login() {
            if (!this.loginData.username) {
                alert('E-MAIL을 입력해 주세요.');
                return;
            }
            if (!this.loginData.password) {
                alert('비밀번호를 입력해 주세요.');
                return;
            }
            try {
                const bodyFormData = new FormData();
                bodyFormData.append('username', this.loginData.username);
                bodyFormData.append('password', this.loginData.password);
                bodyFormData.append('certCode', this.loginData.certCode);
                const response = await this.$store.dispatch(
                    'LOGIN',
                    bodyFormData
                );
                console.log(response);
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
                if (response.data.code === 'SEND_EMAIL') {
                    console.log('SEND_EMAIL');
                } else if (response.data.code === 'SEND_EMAIL_CERT_CODE') {
                    this.changeLoginBox('certCode');
                } else if (response.data.code === 'TERMS_AGREEMENT') {
                    await this.$router.push({
                        name: 'agree',
                        params: this.loginData,
                    });
                } else if (response.data.code === 'SUCCESS') {
                    await this.$router.push('/');
                }
            } catch (error) {
                console.error(error.response.data);
                if (error.response.data.existMsg) {
                    alert(error.response.data.msg);
                }
            }
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
.nike-logo {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 370px;
    height: 390px;
    transform: translate(-50%, -50%);
    vertical-align: top;
}
.nike-logo.active {
    animation: logoAni 1s ease-in-out forwards;
}
.nike-logo.hidden {
    opacity: 0;
}

@keyframes logoAni {
    from {
        opacity: 1;
    }
    to {
        opacity: 0;
    }
}
.nike-logo img {
    width: 100%;
    vertical-align: top;
}
.login-inner {
    position: absolute;
    top: 50%;
    left: 50%;
    display: flex;
    padding: 30px;
    flex-direction: column;
    align-items: center;
    transform: translate(-50%, -50%);
}
/*.login-inner:before {
    content: '';
    display: block;
    width: 500px;
    height: 500px;
    background: url('../../../assets/images/svg/img-login-swoosh.svg') no-repeat
        center center / contain;
}*/
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
