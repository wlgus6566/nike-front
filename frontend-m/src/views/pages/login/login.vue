<template>
    <div id="wrap" class="login">
        <div class="inner">
            <div class="nike-logo" ref="logo">
                <img
                    src="@/assets/images/svg/nike-space.svg"
                    alt="nike space"
                />
            </div>
            <component
                :is="LoginBox"
                :loginData="loginData"
                :activeState="activeState"
                @login="login"
                @updateValue="updateValue"
                @changeLoginBox="changeLoginBox"
            />
            <p class="login-desc">
                사용자는 NIKE SPACE에 로그인함으로써,<br />
                개인정보 처리방침 및 이용약관에 동의합니다.
            </p>
        </div>
    </div>
</template>
<script>
import LoginForm from '@/components/login-box/login-form';
import CertCode from '@/components/login-box/cert-code';
import FindPW from '@/components/login-box/find-pawd';

export default {
    name: 'login',
    data() {
        return {
            activeState: false,
            LoginBox: 'LoginForm',
            loginData: {
                username: '',
                pawd: '',
                certCode: '',
            },
        };
    },
    components: { LoginForm, CertCode, FindPW },
    watch: {},
    mounted() {
        this.$refs.logo.classList.add('active');
    },
    methods: {
        changeLoginBox(compName, state) {
            this.activeState = state;
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
            if (!this.loginData.pawd) {
                alert('비밀번호를 입력해 주세요.');
                return;
            }
            try {
                const bodyFormData = new FormData();
                bodyFormData.append('username', this.loginData.username);
                bodyFormData.append('pawd', this.loginData.pawd);
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
