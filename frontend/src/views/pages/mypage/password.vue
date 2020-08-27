<template>
    <div class="login-wrap">
        <div class="title">비밀번호 변경</div>
        <div class="bullet-list">
            <p class="bullet-item">아이디와 비밀번호는 중복 불가합니다</p>
            <p class="bullet-item">
                비밀번호에는 공통 사전 단어(예: NIKE(나이키), JORDAN(조던) 등)를
                포함할 수 없습니다.
            </p>
            <p class="bullet-item">
                비밀번호는 동일한 글자, 문자, 숫자 등을 2글자이상 반복할 수
                없습니다. (예: AA1234! / A12345@@ 등)
            </p>
            <p class="bullet-item">
                비밀번호 재사용: 비밀번호 변경 시 현재 사용 중인 비밀번호의
                재사용은 불가능하며, 기존과는 다른 비밀번호로 변경하셔야 합니다.
            </p>
        </div>
        <div class="table-form">
            <form action="#" @submit.prevent="passwordChange">
                <div class="table">
                    <div class="table-row">
                        <label for="change-pw1" class="table-th">
                            <span class="required">기존 비밀번호</span>
                        </label>
                        <div class="table-td">
                            <input
                                type="password"
                                id="change-pw1"
                                class="input-box"
                                maxlength="16"
                                v-model="password"
                            />
                        </div>
                    </div>
                    <div class="table-row">
                        <label for="change-pw2" class="table-th">
                            <span class="required">새 비밀번호</span>
                        </label>
                        <div class="table-td">
                            <input
                                type="password"
                                id="change-pw2"
                                class="input-box"
                                maxlength="16"
                                placeholder="(8~16자/대소문자/숫자/특수문자 포함)"
                                v-model="newPassword"
                            />
                        </div>
                    </div>
                    <div class="table-row">
                        <label for="change-pw3" class="table-th">
                            <span class="required">새 비밀번호(확인)</span>
                        </label>
                        <div class="table-td">
                            <input
                                type="password"
                                id="change-pw3"
                                class="input-box"
                                maxlength="16"
                                placeholder=""
                                v-model="confirmPassword"
                            />
                        </div>
                    </div>
                </div>
                <div class="btn-area">
                    <button type="button" class="btn-s" @click="$router.go(-1)">
                        <span>취소</span>
                    </button>
                    <button
                        type="submit"
                        class="btn-s-black"
                        :disabled="
                            password.length < 1 ||
                            newPassword.length < 1 ||
                            confirmPassword.length < 1
                        "
                    >
                        <span>저장</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import { changePassword } from '@/api/mypage';

export default {
    data() {
        return {
            password: '',
            newPassword: '',
            confirmPassword: '',
        };
    },
    mounted() {
        this.password = '';
        this.newPassword = '';
        this.confirmPassword = '';
    },
    methods: {
        async passwordChange() {
            try {
                const res = await changePassword({
                    certCode:
                        'y1v0LCq93KX05pR%2FWw3zF65hK%2FCOqYTZDdIXzM0BsC97m%2Fg1QcY1sCZAEvuTFgmcVg3a8J6xDFalUNjUfmmtu5sWZuI%3D',
                    confirmPassword: this.confirmPassword,
                    newPassword: this.newPassword,
                    password: this.password,
                });
                this.msg = res.data.msg;
                console.log(res);
                if (res.data.success) {
                    alert('변경되었습니다.');
                    this.$router.push('/mypage/info');
                } else {
                    alert(res.data.msg);
                }
                return;
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>

<style scoped>
.login-wrap {
    padding-top: 0;
}
</style>
