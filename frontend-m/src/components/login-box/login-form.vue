<template>
    <form action="#" @submit.prevent="$emit('login')">
        <div class="login-input">
            <input
                type="text"
                :value="loginData.username"
                @input="
                    $emit(
                        'updateValue',
                        'username',
                        $event.target.value
                    )
                "
                placeholder="ID(E-MAIL)"
            >
            <input
                type="password"
                :value="loginData.password"
                @input="
                    $emit(
                        'updateValue',
                        'password',
                        $event.target.value
                    )
                "
                placeholder="PW"
            >
        </div>
        <div class="btn-area">
            <button type="submit" class="btn-s-orage">로그인</button>
            <a
                    href="#"
                    class="txt-btn"
                    @click.prevent="$emit('changeLoginBox', 'FindPW')"
            ><span>비밀번호 찾기</span></a
            >
        </div>
    </form>
</template>
<script>
export default {
    name: 'loginForm',
    props: ['loginData'],
    data() {
        return {};
    },
    methods: {},
};
</script>
<style scoped>
    .login-input {
        margin-top: 17px;
    }
    .login-input input {
        width: 100%;
        height: 40px;
        border-color: #2b2b2b;
        background-color: rgba(255, 255, 255, 0.15);
        letter-spacing: 0;
        font-size: 14px;
        color: #fff;
    }
    .login-input input::placeholder,
    .login-input input::-webkit-input-placeholder {
        font-family: 'Bebas Neue', sans-serif !important;
        font-size: 12px !important;
        letter-spacing: 0.05em !important;
        color: #a7a7a7 !important;
    }
    .login-input input + input {
        margin-top: 8px;
    }
    .login-input.pw-type {
        margin-top: 39px;
    }
</style>
