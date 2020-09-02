<template>
    <form action="#" @submit.prevent="$emit('login')">
        <div class="login-box" ref="loginBox">
            <h1 class="title">
                <span>WELCOME TO</span>
                NIKE SPACE
            </h1>
            <div class="form-box">
                <div class="input-box">
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
                    />
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
                    />
                </div>
            </div>
            <div class="login-btn-wrap">
                <button type="submit" class="btn-s-lg-orage">
                    <span>로그인</span>
                </button>
                <div class="bnt-box">
                    <a
                        href="#"
                        class="txt-btn"
                        @click.prevent="$emit('changeLoginBox', 'FindPW')"
                        ><span>비밀번호 찾기</span></a
                    >
                </div>
            </div>
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
    mounted() {
        this.endLogo();
    },
    methods: {
        endLogo() {
            const box = this.$refs.loginBox;
            document
                .querySelector('.nike-logo')
                .addEventListener('webkitAnimationEnd', function () {
                    box.classList.add('active');
                });
        },
    },
};
</script>
<style scoped>
.login-box {
    z-index: 6;
    opacity: 0;
    position: relative;
    width: 370px;
    height: 390px;
    padding: 50px 45px 45px;
    box-sizing: border-box;
    background: rgba(0, 0, 0, 0.7);
    box-shadow: -20px 20px 8px 0 rgba(0, 0, 0, 0.5);
}
.login-box.active {
    animation: loginAni 1s ease-in-out 0s forwards;
}
@keyframes loginAni {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
.login-box .title {
    display: block;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    color: #fff;
    font-size: 46px;
    font-weight: normal;
    letter-spacing: 0.5px;
    line-height: 46px;
}

.login-box .title span {
    display: block;
    line-height: 36px;
    font-size: 30px;
    letter-spacing: 0.5px;
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
[class^='txt-btn'] {
    line-height: 21px;
}
</style>
