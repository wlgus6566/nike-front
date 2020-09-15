<template>
    <div id="wrap">
        <header class="page-header">
            <button
                type="button"
                class="btn-back"
                @click.prevent="$router.go(-1)"
            >
                뒤로가기
            </button>
            <div class="inner">
                <h1 class="page-title"><span>약관동의</span></h1>
            </div>
        </header>
        <section class="contents">
            <label class="agree-check">
                <input
                    type="checkbox"
                    v-model="allCheck"
                    ref="allCheck"
                    @change="allAgreeCheck"
                />
                <span class="txt"
                    >서비스 이용약관 안내, 개인정보 처리방침에 대한 안내를 모두
                    읽었으며 아래내용에 모두 동의합니다.</span
                >
            </label>
            <ul class="form-list">
                <li class="form-item">
                    <label class="agree-check">
                        <input
                            type="checkbox"
                            v-model="checkbox"
                            value="check1"
                            ref="check1"
                            @change="agreeCheck"
                        />
                        <span class="txt"
                            >약관을 읽었으며, 이에 동의합니다.</span
                        >
                    </label>
                    <div class="agree-cont">
                        <div class="scroll">
                            <strong>개인정보 처리방침</strong><br /><br />
                            유한회사 나이키코리아는 [사이트 주소 입력](이하
                            “웹사이트”라 함)을(를) 통해 취득한 귀하의 개인정보
                            처리에 대한 책임을 지고 있습니다.<br />
                            <strong>개인정보 처리방침</strong>
                        </div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="agree-check">
                        <input
                            type="checkbox"
                            v-model="checkbox"
                            value="check2"
                            ref="check2"
                            @change="agreeCheck"
                        />
                        <span class="txt"
                            >약관을 읽었으며, 이에 동의합니다.</span
                        >
                    </label>
                    <div class="agree-cont">
                        <div class="scroll">
                            <strong>개인정보 처리방침</strong><br /><br />
                            유한회사 나이키코리아는 [사이트 주소 입력](이하
                            “웹사이트”라 함)을(를) 통해 취득한 귀하의 개인정보
                            처리에 대한 책임을 지고 있습니다.<br />
                            <strong>개인정보 처리방침</strong>
                        </div>
                    </div>
                </li>
            </ul>
        </section>
        <div class="btn-area">
            <button
                type="button"
                class="btn-s-black"
                @click="login"
                :disabled="!allCheck"
            >
                <span>확인</span>
            </button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'agree',
    data() {
        return {
            allCheck: false,
            checkbox: [],
        };
    },
    computed: {
        termsAgreeYn() {
            return this.allCheck ? 'Y' : 'N';
        },
    },
    mounted() {},
    methods: {
        async login() {
            try {
                const bodyFormData = new FormData();
                bodyFormData.append('username', this.$route.params.username);
                bodyFormData.append('password', this.$route.params.password);
                bodyFormData.append('certCode', this.$route.params.certCode);
                bodyFormData.append('termsAgreeYn', this.termsAgreeYn);
                const response = await this.$store.dispatch(
                    'LOGIN',
                    bodyFormData
                );
                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
                if (response.data.code === 'SUCCESS') {
                    await this.$router.push('/');
                }
                console.log(response);
            } catch (error) {
                console.log(error);
                alert(error.response.data.msg);
            }
        },
        agreeCheck() {
            this.allCheck = this.checkbox.length === 2;
        },
        allAgreeCheck() {
            if (this.allCheck) {
                this.checkbox = ['check1', 'check2'];
            } else {
                this.checkbox = [];
            }
        },
    },
};
</script>
<style scoped></style>
