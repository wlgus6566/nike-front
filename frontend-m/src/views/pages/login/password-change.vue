<template>
    <div id="wrap" class="sticky-wrap">
        <header class="page-header">
            <div class="inner">
                <h1 class="page-title"><span>비밀번호 변경</span></h1>
            </div>
        </header>
        <form action="" @submit.prevent="passwordChange">
            <section class="contents">
                <ul class="desc-list">
                    <li class="desc-item">
                        아이디와 비밀번호는 중복 불가합니다.
                    </li>
                    <li class="desc-item">
                        비밀번호에는 간단한 패턴이나 공통 사전 단어<br />
                        (예: NIKE(나이키), JORDAN(조던) 등)를 포함할 수
                        없습니다.
                    </li>
                    <li class="desc-item">
                        비밀번호는 동일한 글자, 문자, 숫자 등을 2글자이상 반복할
                        수<br />
                        없습니다. (예: AA1234! / A12345@@ 등)
                    </li>
                    <li class="desc-item">
                        비밀번호 재사용 : 비밀번호 변경 시 현재 사용 중인
                        비밀번호의 재 사용은 불가능하며, 기존과는 다른
                        비밀번호로 변경하셔야 합니다.
                    </li>
                </ul>
                <ul class="form-list">
                    <li class="form-item">
                        <span class="form-column">
                            <label class="label-title required"
                                >기존 비밀번호</label
                            >
                        </span>
                        <span class="form-column">
                            <input
                                type="password"
                                id="change-pw0"
                                class="input-box"
                                maxlength="16"
                                placeholder=""
                                v-model="encryption"
                            />
                        </span>
                    </li>
                    <li class="form-item">
                        <span class="form-column">
                            <label class="label-title required"
                                >새 비밀번호</label
                            >
                        </span>
                        <span class="form-column">
                            <input
                                type="password"
                                id="change-pw1"
                                class="input-box"
                                maxlength="16"
                                placeholder="(8~16자/대소문자/숫자/특수문자 포함)"
                                v-model="newEncryption"
                            />
                        </span>
                    </li>
                    <li class="form-item">
                        <span class="form-column">
                            <label class="label-title required"
                                >새 비밀번호(확인)</label
                            >
                        </span>
                        <span class="form-column">
                            <input
                                type="password"
                                id="change-pw2"
                                class="input-box"
                                maxlength="16"
                                placeholder=""
                                v-model="confirmEncryption"
                            />
                        </span>
                    </li>
                </ul>
            </section>
            <div class="btn-wrap">
                <button type="submit" class="btn-s-black-lg">저장</button>
            </div>
        </form>
    </div>
</template>
<script>
import { changepassword } from '@/api/login';

export default {
    name: 'passwordChange',
    data() {
        return {
            encryption: '',
            newEncryption: '',
            confirmEncryption: '',
        };
    },
    methods: {
        async passwordChange() {
            try {
                const { data: response } = await changepassword({
                    certCode: this.$route.params.certCode,
                    encryption: this.encryption,
                    newEncryption: this.newEncryption,
                    confirmEncryption: this.confirmEncryption,
                });
                if (response.existMsg) {
                    alert(response.msg);
                }
                console.log(response);
                if (response.success) {
                    await this.$router.push('/login');
                }
            } catch (error) {
                console.log('error', error);
            }
        },
    },
};
</script>
<style scoped>
.btn-wrap {
    bottom: 0;
}
</style>
