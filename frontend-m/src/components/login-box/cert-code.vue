<template>
    <form action="#" @submit.prevent="$emit('login')">
        <strong class="title-ko">
            E-MAIL 인증
        </strong>
        <p class="title-desc">
            해당 계정의 E-MAIL로 인증코드가 발송되었습니다. <br>
            E-MAIL 확인 후 인증코드를 입력해 주세요.<br>
            ※인증코드는 대소문자를 구분합니다.
        </p>
        <div class="certified-input">
            <input type="text" v-model="loginData.certCode" maxlength="10" style="width: 200px"/>
        </div>
        <div class="btn-area">
            <button type="submit" class="btn-s-orage">인증하기</button>
        </div>
    </form>
</template>
<script>
export default {
    name: 'certCode',
    data() {
        return {};
    },
    props: ['loginData'],
};
</script>
