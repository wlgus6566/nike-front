<template>
    <form action="#" @submit.prevent="findPW">
        <div class="login-form" ref="loginBox">
            <strong class="title-ko">
                비밀번호 설정
            </strong>
            <p class="title-desc">
                비밀번호 설정을 위한 ID(E-MAIL) 을 입력해주세요.<br />
                입력하신 E-MAIL로 비밀번호 설정을 위한 URL이<br />
                전송됩니다.
            </p>

            <div class="login-input pw-type">
                <input
                    ref="mailId"
                    type="text"
                    v-model="userId"
                    placeholder="ID(E-MAIL)"
                />
            </div>
            <div class="btn-area">
                <button type="submit" class="btn-s-orage">E-MAIL 발송</button>
            </div>

            <button type="button" class="btn-close" @click="closePassword">
                <span>닫기</span>
            </button>
        </div>
    </form>
</template>
<script>
import { sendEmail } from '@/api/login.js';

export default {
    name: 'FindPW',
    data() {
        return {
            userId: '',
        };
    },
    props: ['activeState'],
    mounted() {
        this.$refs.loginBox.classList.add('active');
    },
    methods: {
        closePassword() {
            this.$emit('changeLoginBox', 'LoginForm', true);
        },
        async findPW() {
            try {
                const { data: response } = await sendEmail({
                    userId: this.userId,
                    platform: 'MOBILE',
                });
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.success) {
                    this.$emit('changeLoginBox', 'LoginForm', true);
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
