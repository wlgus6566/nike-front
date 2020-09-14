<template>
    <form action="#" @submit.prevent="$emit('login')">
        <div class="login-form" ref="loginBox">
            <strong class="nike-title">
                <span>WELCOME TO</span>
                <span>NIKE SPACE</span>
            </strong>
            <div class="login-input">
                <input
                    type="text"
                    :value="loginData.username"
                    @input="
                        $emit('updateValue', 'username', $event.target.value)
                    "
                    placeholder="ID(E-MAIL)"
                />
                <input
                    type="password"
                    :value="loginData.pawd"
                    @input="$emit('updateValue', 'pawd', $event.target.value)"
                    placeholder="PW"
                />
            </div>
            <div class="btn-area">
                <button type="submit" class="btn-s-orage">로그인</button>
                <a
                    href="#"
                    class="under-link"
                    @click.prevent="$emit('changeLoginBox', 'FindPW', false)"
                    ><span>비밀번호 설정</span></a
                >
            </div>
        </div>
    </form>
</template>
<script>
import pawd from '@/views/pages/mypage/pawd';

export default {
    name: 'loginForm',
    props: ['loginData', 'activeState'],
    data() {
        return {};
    },
    mounted() {
        if (this.activeState) {
            this.$refs.loginBox.classList.add('active');
        }
        this.endLogo();
    },
    methods: {
        endLogo() {
            const logo = document.querySelector('.nike-logo');
            const box = this.$refs.loginBox;
            logo.addEventListener('webkitAnimationEnd', function() {
                box.classList.add('active');
                logo.classList.add('hidden');
                logo.classList.remove('active');
            });
        },
    },
};
</script>
<style scoped>
::v-deep .login-input input::placeholder {
    font-size: 14px !important;
    color: rgba(255, 255, 255, 0.5) !important;
}
::v-deep .login-input input::-webkit-input-placeholder {
    font-size: 14px !important;
    color: rgba(255, 255, 255, 0.5) !important;
}
::v-deep .under-link {
    margin-top: 15px;
    font-weight: 100;
    line-height: 13px;
    text-decoration: none;
    border-bottom: 1px solid #fff;
}
</style>
