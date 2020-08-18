<template>
    <div class="login-wrap">
        <div class="inner">
            <div class="title">약관동의</div>
            <div class="agree-wrap">
                <div class="agree-list all">
                    <label>
                        <span class="checkbox">
                            <input
                                type="checkbox"
                                v-model="allCheck"
                                ref="allCheck"
                                @change="allAgreeCheck"
                            />
                            <i></i>
                            <span class="txt"
                                >서비스 이용약관 안내, 개인정보 처리방침에 대한
                                안내를 모두 읽었으며 아래내용에 모두
                                동의합니다.</span
                            >
                        </span>
                    </label>
                </div>
                <div class="agree-list">
                    <div class="agree-item">
                        <label>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    v-model="checkbox"
                                    value="check1"
                                    ref="check1"
                                    @change="agreeCheck"
                                />
                                <i></i>
                                <span class="txt"
                                    >약관을 읽었으며 이에 동의합니다.</span
                                >
                            </span>
                        </label>
                        <div class="agree-content">
                            <div class="info">
                                인생의 인간의 사는가 그리하였는가? 피고 하는
                                예가 것은 오직 봄바람이다. 소리다.이것은 그들은
                                영락과 목숨이 피고 칼이다. 피어나는 부패를
                                무엇을 역사를 이상이 얼음 인도하겠다는 쓸쓸하랴?
                                트고, 청춘은 청춘이 그들의 칼이다. 귀는 인생을
                                가는 같은 운다. 실로 못할 끓는 쓸쓸하랴? 실로 수
                                아름답고 목숨을 얼마나 트고, 것이다. 심장은
                                과실이 원대하고, 밝은 그들은 얼마나 쓸쓸하랴?
                                인생의 희망의 목숨이 가치를 같지 따뜻한 봄바람을
                                얼음 것이다.인생의 인간의 사는가 그리하였는가?
                                피고 하는 예가 것은 오직 봄바람이다.
                                소리다.이것은 그들은 영락과 목숨이 피고 칼이다.
                                피어나는 부패를 무엇을 역사를 이상이 얼음
                                인도하겠다는 쓸쓸하랴? 트고, 청춘은 청춘이
                                그들의 칼이다. 귀는 인생을 가는 같은 운다. 실로
                                못할 끓는 쓸쓸하랴? 실로 수 아름답고 목숨을
                                얼마나 트고, 것이다. 심장은 과실이 원대하고,
                                밝은 그들은 얼마나 쓸쓸하랴? 인생의 희망의
                                목숨이 가치를 같지 따뜻한 봄바람을 얼음
                                것이다.인생의 인간의 사는가 그리하였는가? 피고
                                하는 예가 것은 오직 봄바람이다. 소리다.이것은
                                그들은 영락과 목숨이 피고 칼이다. 피어나는
                                부패를 무엇을 역사를 이상이 얼음 인도하겠다는
                                쓸쓸하랴? 트고, 청춘은 청춘이 그들의 칼이다.
                                귀는 인생을 가는 같은 운다. 실로 못할 끓는
                                쓸쓸하랴? 실로 수 아름답고 목숨을 얼마나 트고,
                                것이다. 심장은 과실이 원대하고, 밝은 그들은
                                얼마나 쓸쓸하랴? 인생의 희망의 목숨이 가치를
                                같지 따뜻한 봄바람을 얼음 것이다.인생의 인간의
                                사는가 그리하였는가? 피고 하는 예가 것은 오직
                                봄바람이다. 소리다.이것은 그들은 영락과 목숨이
                                피고 칼이다. 피어나는 부패를 무엇을 역사를
                                이상이 얼음 인도하겠다는 쓸쓸하랴? 트고, 청춘은
                                청춘이 그들의 칼이다. 귀는 인생을 가는 같은
                                운다. 실로 못할 끓는 쓸쓸하랴? 실로 수 아름답고
                                목숨을 얼마나 트고, 것이다. 심장은 과실이
                                원대하고, 밝은 그들은 얼마나 쓸쓸하랴? 인생의
                                희망의 목숨이 가치를 같지 따뜻한 봄바람을 얼음
                                것이다.
                            </div>
                        </div>
                    </div>
                    <div class="agree-item">
                        <label>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    v-model="checkbox"
                                    value="check2"
                                    ref="check2"
                                    @change="agreeCheck"
                                />
                                <i></i>
                                <span class="txt"
                                    >약관을 읽었으며 이에 동의합니다.</span
                                >
                            </span>
                        </label>
                        <div class="agree-content">
                            <div class="info">
                                인생의 인간의 사는가 그리하였는가? 피고 하는
                                예가 것은 오직 봄바람이다. 소리다.이것은 그들은
                                영락과 목숨이 피고 칼이다. 피어나는 부패를
                                무엇을 역사를 이상이 얼음 인도하겠다는 쓸쓸하랴?
                                트고, 청춘은 청춘이 그들의 칼이다. 귀는 인생을
                                가는 같은 운다. 실로 못할 끓는 쓸쓸하랴? 실로 수
                                아름답고 목숨을 얼마나 트고, 것이다. 심장은
                                과실이 원대하고, 밝은 그들은 얼마나 쓸쓸하랴?
                                인생의 희망의 목숨이 가치를 같지 따뜻한 봄바람을
                                얼음 것이다.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="btn-area">
                <a href="#" class="btn-s-white" @click.prevent="$router.go(-1)"
                    ><span>이전</span></a
                >
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
    mounted() {
        console.log(this.$route.params);
    },
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
