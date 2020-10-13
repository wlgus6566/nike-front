<template>
    <section ref="wrap" class="login">
        <div class="login-inner">
            <div class="login-box">
                <strong class="title2">
                    휴면 해제 안내
                </strong>
                <p class="desc">
                    NIKE D&P Platform을 1년 동안 이용하지 않아,<br />
                    해당 계정이 휴면 상태로 전환되었습니다.<br />
                    휴면 상태에서 3개월이 경과하면 계정이 삭제되며,<br />
                    복구되지 않습니다.<br />
                    휴면 전환된 계정을 해제하시려면,<br />
                    아래 휴면해제 버튼을 통해 이메일 인증 완료 시<br />
                    사이트 이용이 가능합니다.
                </p>
                <div class="login-btn-wrap half">
                    <button type="button" class="btn-s">
                        <span>취소</span>
                    </button>
                    <button type="button" class="btn-s-orage">
                        <span>휴면해제</span>
                    </button>
                </div>
                <button type="button" class="btn-close">
                    <span>닫기</span>
                </button>
            </div>
            <p class="f-desc">
                사용자는 NIKE D&P Plarform에 로그인함으로써,<br />
                개인정보 취급방침 및 이용약관에 동의합니다.
            </p>
        </div>
    </section>
</template>
<script>
export default {
    name: 'dormantMember',
    data() {
        return {};
    },
    props: [],
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
    margin-top: 9px;
    font-size: 14px;
    color: #fff;
    line-height: 20px;
    opacity: 0.5;
}
.login-box .txt-btn {
    font-weight: normal;
}
/*.form-box {
		margin-top: 20px;
	}*/
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
.pw-box input {
    margin-top: 28px;
}
.login-btn-wrap {
    margin-top: 30px;
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
