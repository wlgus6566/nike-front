<template>
    <div id="login">
        <section>
            <h1 class="logo">
                <a href="javascript:void(0)">
                    <img src="@/assets/images/logo_login.png" alt="" />
                </a>
            </h1>
            <div class="login-form" v-if="certification === 'success'" v-show="is_show2">
                <form action="" method="">
                    <fieldset>
                        <legend>NIKE ND&P SERVICE</legend>
                        <h2 class="title">NIKE ND&P<br />DIGITAL PLATFORM</h2>
                        <div class="input-group">
                            <label for="id"></label><input type="text" id="id" value="digitalplaform@nike.com" placeholder="ID" />
                            <label for="pw"></label><input type="password" id="pw" value="111111111111" placeholder="PW" maxlength="12" />
                        </div>
                        <button type="button" class="btn-d-black-lg btn-login" @click="changeLogin">
                            <span>LOGIN</span>
                        </button>
                        <a href="javascript:void(0)" class="pw-find" @click="openPop">비밀번호 찾기</a>
                    </fieldset>
                </form>
            </div>
            <div class="login-form" v-else>
                <form class="email-certification" action="" method="">
                    <fieldset>
                        <legend>NIKE ND&P SERVICE</legend>
                        <h2 class="title">
                            이메일 인증
                        </h2>
                        <p class="desc">인증메일이 발송되었습니다.<br />메일함을 확인하여 인증코드를 입력해주세요.</p>
                        <div class="input-group">
                            <label for="word1"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word1"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word2"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word2"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word3"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word3"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word4"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word4"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word5"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word5"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word6"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word6"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word7"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word7"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                            <label for="word8"></label
                            ><input
                                type="text"
                                maxlength="1"
                                id="word8"
                                value=""
                                placeholder=""
                                v-on:keypress="$event.target.nextElementSibling.focus()"
                                v-on:keyup="changeBorderColor()"
                            />
                        </div>
                        <button type="button" class="btn-d-black-lg btn-login">
                            <span>인증하기</span>
                        </button>
                    </fieldset>
                </form>
            </div>
            <!-- 로그인 : 비밀번호 찾기(P) -->
            <div class="login-form" v-show="is_show">
                <form class="login-popup" action="" method="">
                    <fieldset>
                        <legend>NIKE ND&P SERVICE</legend>
                        <h2 class="title">
                            비밀번호 찾기
                        </h2>
                        <p class="desc">
                            비밀번호 설정을 위한 ID(E-MAIL)을 입력해주세요.<br />입력하신 이메일로 비밀번호 설정을 위한 URL이 전송됩니다.
                        </p>
                        <div class="input-group">
                            <label for="id2"><input type="text" id="id2" value="" placeholder="ID" /></label>
                        </div>
                        <button type="button" class="btn-d-black-lg btn-login">
                            <span>이메일 발송</span>
                        </button>
                        <a href="#" class="close-pop">팝업 닫기</a>
                    </fieldset>
                </form>
            </div>
            <!-- //로그인 : 비밀번호 찾기(P) -->
        </section>
    </div>
</template>

<script>
export default {
    name: 'login',
    data: function () {
        return {
            certification: 'success',
            is_show: false,
            is_show2: true,
            //on: '#ffffff',
        };
    },
    methods: {
        changeLogin() {
            if (this.certification === 'success') {
                this.certification = '';
            } else {
                this.certification = 'success';
            }
        },
        changeBorderColor: function () {
            console.log('input에 .on 추가 되면서 border-bottom color 변경 되여야 함');
        },
        openPop: function () {
            this.is_show = !this.is_show;
            this.is_show2 = !this.is_show2;
        },
    },
};
</script>

<style scoped>
/* reset.css > html에 overflow-y:scroll 되어 있어서 로그인 페이지도 스크롤 영향이 생김 */
#login {
    position: fixed;
    width: 100%;
    height: 100%;
    font-family: 'Noto Sans KR', sans-serif;
    background-image: url('../../assets/images/bg_login.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-size: 3000px auto;
}

.logo {
    min-width: 1208px;
    max-width: 1208px;
    position: relative;
    top: -230px;
}

.logo a {
    display: block;
    text-align: center;
}

section {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

section .text {
    margin-top: 23px;
    font-size: 14px;
    text-align: center;
    color: #888;
    letter-spacing: -0.5px;
}

section .text strong {
    color: #000;
}

.login-form {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 370px;
    height: 390px;
    margin: 160px auto 0;
    padding: 50px 45px 75px;
    box-sizing: border-box;
    background-color: rgba(0, 0, 0, 0.7);
    box-shadow: -20px 20px 8px 0 rgba(0, 0, 0, 0.5);
    text-align: left;
}

.login-form form {
    position: relative;
}

.login-form .title {
    margin-bottom: 26px;
    font-weight: 400;
    font-size: 40px;
    font-family: 'Bebas Neue', sans-serif;
    line-height: 1.1;
    letter-spacing: -0.5px;
    color: #fff;
}

.login-form .email-certification .title,
.login-form .login-popup .title {
    margin-bottom: 0;
    font-weight: 700;
    font-size: 30px;
    font-family: 'Noto Sans KR', sans-serif;
    line-height: normal;
    letter-spacing: -2px;
}

.login-form .desc {
    margin: 9px 0 40px;
    font-weight: 400;
    font-size: 14px;
    letter-spacing: -0.5px;
    opacity: 0.5;
    color: #fff;
}

.login-form .login-popup .desc {
    margin-bottom: 30px;
}
.login-form .title span {
    font-weight: 200;
    font-size: 24px;
    letter-spacing: -0.5px;
}

.input-group {
    width: 100%;
}

.input-group input {
    display: block;
    width: 100%;
    height: 40px;
    padding: 10px 15px;
    border: 0;
    font-size: 15px;
    background-color: rgba(255, 255, 255, 0.1);
    color: #fff;
    letter-spacing: 0;
}

.input-group label ~ label {
    display: block;
    margin-top: 8px;
}

.email-certification .input-group {
    display: flex;
    justify-content: center;
    margin-bottom: 24px;
}

.email-certification .input-group input {
    display: block;
    width: 20px;
    height: 40px;
    padding: 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.3);
    font-size: 24px;
    font-family: 'Roboto', sans-serif;
    background-color: transparent;
    color: #fff;
    text-align: center;
}
.email-certification .input-group input.on {
    border-bottom: 1px solid rgba(255, 255, 255, 1);
}
.email-certification .input-group input ~ input {
    margin-left: 10px;
    margin-top: 0;
}

.input-group input::-webkit-input-placeholder {
    font-size: 16px;
    font-family: 'Bebas Neue', sans-serif;
    color: #fff;
    opacity: 0.5;
}

.input-group input:-ms-input-placeholder {
    font-size: 16px;
    font-family: 'Bebas Neue', sans-serif;
    color: #888;
    opacity: 0.5;
}

.login-form .btn-login {
    width: 100%;
    height: 45px;
    margin-top: 20px;
    border-radius: 0;
    font-weight: normal;
    font-size: 20px;
    font-family: 'Bebas Neue', sans-serif;
    background-color: #fa5400;
    line-height: 45px;
    letter-spacing: 0.5px;
    color: #fff;
    border: 0;
}

.login-form .email-certification .btn-login {
    font-size: 16px;
    font-family: inherit;
    letter-spacing: -0.57px;
}

.login-form .login-popup .btn-login {
    margin-top: 35px;
    font-size: 16px;
    font-family: inherit;
    letter-spacing: -0.57px;
}

.close-pop {
    position: absolute;
    top: -20px;
    right: -20px;
    width: 30px;
    height: 30px;
    font-size: 0;
    background: url('../../assets/images/icon-close-mideum@2x.png') no-repeat center center;
    background-size: 30px auto;
}
.pw-find {
    display: block;
    position: absolute;
    left: 0;
    bottom: -30px;
    margin-top: 12px;
    padding-bottom: 1px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.5);
    font-size: 12px;
    color: rgba(255, 255, 255, 0.5);
}
</style>
