<template>
    <form action="#" @submit.prevent="login">
        <div class="login-box">
            <strong class="title2"> 비밀번호 찾기 </strong>
            <p class="desc">
                비밀번호 설정 메일 발송을 위한 ID(E-MAIL)을 입력해 주세요.<br />
                입력하신 E-MAIL로 비밀번호 설정을 위한 URL이 전송 됩니다.
            </p>
            <div class="form-box">
                <div class="pw-box">
                    <input
                        ref="mailId"
                        type="text"
                        v-model="userId"
                        placeholder="ID(E-MAIL)"
                    />
                </div>
            </div>
            <div class="login-btn-wrap">
                <button type="button" class="btn-s-lg-orage" @click="findPW">
                    <span>E-MAIL 발송</span>
                </button>
            </div>
            <button
                type="button"
                class="btn-close"
                @click="$emit('changeLoginBox', 'LoginForm')"
            >
                <span>닫기</span>
            </button>
        </div>
    </form>
</template>
<script>
import { sendEmail } from '@/api/login.js';
export default {
    name: 'loginForm',
    data() {
        return {
            userId: 'joosung.yeum@emotion.co.kr',
        };
    },
    methods: {
        async findPW() {
            if (!this.userId) {
                alert('아이디를 입력해 주세요.');
                this.$refs.mailId.focus();
                return;
            }
            try {
                const { data: response } = await sendEmail({
                    userId: this.userId,
                });
                console.log(response);
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.success) {
                    this.$emit('changeLoginBox', 'LoginForm');
                } else {
                    this.$refs.mailId.focus();
                }
            } catch (error) {
                alert(error.response.data.msg);
                this.$refs.mailId.focus();
            }
        },
    },
};
</script>
<style scoped>
.login-box {
    position: relative;
    width: 370px;
    height: 390px;
    margin-top: -100px;
    padding: 50px 45px 45px;
    box-sizing: border-box;
    background: rgba(0, 0, 0, 0.7);
    box-shadow: -20px 20px 8px 0 rgba(0, 0, 0, 0.5);
}
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
