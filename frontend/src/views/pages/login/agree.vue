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
                        </span>
                        <span class="txt"
                            >서비스 이용약관 안내, 개인정보 처리방침에 대한
                            안내를 모두 읽었으며 아래내용에 모두 동의합니다.
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
                            </span>
                            <span class="txt">
                                약관을 읽었으며 이에 동의합니다.
                            </span>
                        </label>
                        <div class="agree-content">
                            <el-scrollbar
                                class="info-scroll"
                                wrap-class="info-scroll-wrap"
                                :native="false"
                            >
                                <div class="info">
                                    <pre><strong>NIKE SPACE 이용약관</strong>

본 이용 약관 (“약관”)의 목적은 유한회사 나이키코리아 (“회사”)가 제공하는 NIKE SPACE 서비스와 관련해 “회사”와 사용자들 (“회사”의 어카운트들 및 직원 포함)(통칭하여 “사용자”)의 권리와 의무, 그리고 책임에 대해 정의하기 위함입니다.

<strong>1. NIKE SPACE 계정 정보의 유출 및 제3자 제공의 금지</strong>
_ NIKE SPACE에 접속하기 위한 계정은 “회사”에 의해 부여되며, 이 계정의 ID와 PW를 타인에게 임의로 유출하거나 “회사”의 영업활동과 무관한 제3자에게 양도, 공여, 판매, 대여, 공유하는 것은 절대 금지됩니다.
_ NIKE SPACE에 접속하기 위한 계정의 관리 책임은 계정을 제공받는 어카운트의 대표에게 귀속되며, 계정 정보의 유출로 인해 발생하는 “회사”의 유/무형의 손실에 대한 책임은 계정의 관리 책임 주체인 어카운트에게 있습니다.

<strong>2. NIKE SPACE 활동 정보 기록 제공의 동의</strong>
_ “사용자”가 계정 정보를 사용해 NIKE SPACE에 접근하여 수행하는 모든 활동과 자료의 취득, 생산, 편집, 삭제 등의 행위에 대한 정보를 기록하고 이를 “회사” 및 NIKE SPACE을 구축하고 관리하는 ㈜이모션글로벌에게 제공하는 것에 동의합니다.

<strong>3. NIKE SPACE 계정의 부정 사용의 금지</strong>
_ NIKE SPACE 접속을 위한 계정을 “회사”의 영업과 무관한 활동에 사용하는 것은 금지됩니다.

<strong>4. NIKE SPACE 상에서 취득한 정보의 오□남용의 금지</strong>
_ NIKE SPACE 상의 모든 정보는 NIKE INC.와 “회사”의 자산입니다.
_ NIKE SPACE 상에서 취득한 정보를 “회사”가 지정하거나 권한을 부여한 용도, 기간, 대상 외로 사용하는 것은 엄격히 금지됩니다.
_ NIKE SPACE 상에서 취득한 정보를 “회사”가 지정한 방식 외로 임의의 편집, 수정, 삭제, 변경을 가하여 사용하는 것은 엄격히 금지됩니다.
_ “사용자”가 NIKE SPACE 상에서 취득한 정보를 “회사”가 지정하거나 권한을 부여한 용도, 기간, 대상, 방식 외로 사용하여 “회사”가 입은 유/무형의 손실에 대한 책임은 계정의 관리 책임 주체인 어카운트에게 있습니다.

<strong>5. NIKE SPACE 상에서 취득한 정보의 유출 및 제3자 제공의 금지</strong>
_ NIKE SPACE 상에서 취득한 정보를 “회사”가 지정하거나 권한을 부여한 “사용자” 외의 제3자에게 양도, 공여, 판매, 대여, 공유 혹은 기타 유사한 행위를 하는 것은 엄격히 금지됩니다.
_ “사용자”가 NIKE SPACE 상에서 취득한 정보를 “회사”가 지정하거나 권한을 부여한 사용자 외의 제3자에게 양도, 공여, 판매, 대여, 공유함으로 인해 발생하는 “회사”의 유/무형의 손실에 대한 책임은 정보를 제3자에게 양도, 공여, 판매, 대여, 공유한 계정 관리의 책임 주체인 어카운트에게 있습니다.

<strong>6. NIKE SPACE 상에서의 활동 내역의 유출 및 제3자 제공의 금지</strong>
_ “사용자”가 계정 정보를 사용해 NIKE SPACE에 접근하여 수행하는 모든 활동과 자료의 취득, 생산, 편집, 삭제 등의 행위에 대한 정보를 “회사”가 지정하거나 권한을 부여한 “사용자” 외의 제3자에게 양도, 공여, 판매, 대여, 공유하는 것은 엄격히 금지됩니다.
_ “사용자”가 계정 정보를 사용해 NIKE SPACE에 접근하여 수행하는 모든 활동과 자료의 취득, 생산, 편집, 삭제 등의 행위에 대한 정보를 “회사”가 지정하거나 권한을 부여한 사용자 외의 제3자에게 양도, 공여, 판매, 대여, 공유함으로 인해 발생하는 “회사”의 유/무형의 손실에 대한 책임은 정보를 제3자에게 양도, 공여, 판매, 대여, 공유한 계정 관리의 책임 주체인 어카운트에게 있습니다.

<strong>7. NIKE SPACE 상에서의 활동에 따른 산출물의 유출 및 제3자 제공의 금지</strong>
_ “사용자”가 계정 정보를 사용해 NIKE SPACE에 접근하여 수행하는 모든 활동과 자료의 취득, 생산, 편집, 삭제 등의 행위로 인해 발생하는 산출물을 “회사”가 지정하거나 권한을 부여한 “사용자" 외의 제3자에게 양도, 공여, 판매, 대여, 공유하는 것은 엄격히 금지됩니다.
_ “사용자”가 계정 정보를 사용해 NIKE SPACE에 접근하여 수행하는 모든 활동과 자료의 취득, 생산, 편집, 삭제 등의 행위로 인해 발생하는 산출물을 “회사”가 지정하거나 권한을 부여한 사용자 외의 제3자에게 양도, 공여, 판매, 대여, 공유함으로 인해 발생하는 “회사”의 유/무형의 손실에 대한 귀책은 정보를 제3자에게 양도, 공여, 판매, 대여, 공유한 계정 관리의 책임 주체인 어카운트에게 있습니다.

<strong>8. NIKE SPACE 시스템의 부정 사용의 금지</strong>
_ NIKE SPACE을 운영, 유지, 관리, 보수, 개편하기 위한 유/무형의 전산 체계 제반을 “회사”의 영업활동과 무관한 용도로 사용하는 것은 엄격히 금지됩니다.

<strong>9. NIKE SPACE 관련 설비의 부정 사용의 금지</strong>
_ NIKE SPACE을 운영, 유지, 관리, 보수, 개편하기 위한 유/무형의 전산 설비 제반을 “회사”의 영업활동과 무관한 용도로 사용하는 것은 엄격히 금지됩니다.

<strong>10. 손해배상</strong>
_”사용자”가 본 약관의 규정을 위반하여 “회사” 혹은 “회사”의 계열사 (NIKE, INC. 포함)가 손해를 입은 경우, “회사”는 “사용자”에 대해 손해배상을 청구 할 수 있습니다.

<strong>11. 책임의 면제</strong>
“회사”는 다음과 같은 조건에서 NIKE SPACE 서비스를 제공하지 못 할 경우 “사용자”가 입은 손해에 대해 책임을 지지 않습니다.
1. 자연 재해나 그에 버금가는 불가항력의 경우
2. “회사”에 NIKE SPACE관련 서비스를 제공하기로 서비스 파트너십 계약을 체결한 제3자가 고의적으로 서비스를 중단한 경우
3. “사용자”에게 귀속되는 사유로 인해 서비스 이용에 문제가 발생한 경우
4. 제1호부터 제3호까지에 기술된 조건 이외에, “회사”의 고의/중대한 과실로 인하여 발생되지 않은 경우

<strong>12. 규정의 적용</strong>
_본 약관에 명시되지 아니한 사항들은 관련 법규와 상관행에 따릅니다.

<strong>13. “회사” 연락처 정보</strong>
_NIKE SPACE 사용 등과 관련하여 문의 사항이 있는 경우 아래로 연락 주시기 바랍니다.
담당자: 이은지
이메일: eunji.lee@nike.com
부칙 (2020년 9월 1일)
본 이용약관은 2020년 9월 1일부터 시행됩니다.</pre>
                                </div>
                            </el-scrollbar>
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
                            </span>
                            <span class="txt">
                                약관을 읽었으며 이에 동의합니다.
                            </span>
                        </label>
                        <div class="agree-content">
                            <el-scrollbar
                                class="info-scroll"
                                wrap-class="info-scroll-wrap"
                                :native="false"
                            >
                                <div class="info">
                                    <pre><strong>개인정보 처리방침</strong>

유한회사 나이키코리아는 [사이트 주소 입력](이하 “웹사이트”라 함)을(를) 통해 취득한 귀하의 개인정보 처리에 대한 책임을 지고 있습니다.

<strong>개인정보 처리</strong>

귀하가 이 사이트를 방문하시면 당사는 귀하가 제공하거나 당사가 요청할 수 있는 개인정보를 수집하고 처리합니다. 특히 웹사이트에 대한 로그인 인증 목적으로 귀하의 이메일 및 비밀번호를 수집합니다.

웹사이트 방문 및 활동 시, 쿠키를 통하여 귀하의 장치로부터 특정 데이터를 수집합니다. 쿠키를 통하여 수집하는 데이터에는 (i) 방문한 웹사이트, (ii) 방문 일시 및 기간, (iii) 특정 장치 식별 장치 및 장치 종류 (데스크 톱, 태블릿 혹은 스마트 폰), (iv) 다운로드 받은 내역이 포함됩니다.

당사 사이트에는 일반적으로 두개의 범주의 쿠키가 사용됩니다.

•	귀하의 방문 일시에 관한 정보와 방문 기간 그리고 방문한 페이지에 관한 정보를 수집하기 위한 분석 쿠키. 이러한 정보는 웹사이트의 이용에 대한 이해를 증진하고 웹사이트를 개선하기 위하여 사용됩니다.
•	웹사이트 방문에 사용한 장치의 종류에 대한 정보를 수집하기 위해 사용되는 기능적 쿠키. 이러한 정보는 귀하에게 웹사이트의 기본 기능을 제공하는데 필수적입니다.

당사는 또한 휴면 계정을 확인하고 다운로드 받은 내역의 추세에 대한 누적된 보고서를 생성하기 위해 쿠키를 사용합니다.

귀하는 쿠키가 전송될 때마다 컴퓨터가 경고를 보내도록 선택하거나 모든 쿠키를 끄도록 선택할 수 있습니다. 귀하가 사용하고 있는 브라우저 및 장치에서 브라우저 설정을 통해 이 작업을 수행할 수 있습니다. 각 브라우저는 다를 수 있으므로 브라우저 도움말 메뉴로 쿠키를 수정하는 올바른 방법을 알아보십시오. 쿠키를 끄시면 웹사이트를 더 유용하게 해주는 기능을 사용할 수 없거나 웹사이트의 기능들이 제대로 작동하지 않을 수도 있습니다.

<storng>데이터 공유</storng>

당사가 웹사이트를 통해 취득한 개인 정보는 당사를 대신하여 제3자들이 서비스나 기타 업무를 수행할 수 있도록 제3자와 공유할 수 있습니다 (예: 당사 플랫폼 운영 대리인). 이러한 제3자들은 당사를 대신하여, 이 방침에 언급된 목적의 지시 사항에 따라서만 개인정보를 처리합니다
</pre>
                                    <div class="tbl-list">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <th>수탁인</th>
                                                    <th>위탁 업무</th>
                                                    <th>제공되는 개인정보</th>
                                                </tr>
                                                <tr>
                                                    <td>EMOTION GLOBAL INC.</td>
                                                    <td>
                                                        일반적인 플랫폼 운영
                                                    </td>
                                                    <td>이메일 주소</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <pre>
                                    당사는 정부의 요청, 법원 명령이나 법령에 의해 요구되거나 또는 제3자의 권리, 재산 및 안전을 보호하기 위해 귀하의 개인정보를 공개할 수 있습니다. 당사는 또한 사이트의 불법 사용 방지 또는 이용 약관의 위반을 방지하거나 제3자의 청구로부터 당사를 방어하기 위해 정보를 공유할 수 있습니다.

당사는 마케팅 목적으로 외부 혹은 에이전트에게 귀하의 개인정보를 제공하지 않으며 본 방침에 명시되지 않은 외부 당사들에게 귀하의 개인정보를 판매, 거래, 공유 혹은 이전하지 않습니다.

<strong>귀하의 개인정보 권리</strong>

귀하는 (i) 귀하의 개인 데이터에 대한 접근, (ii) 귀하의 개인 데이터의 전자 사본 (휴대성), (iii) 불완전하거나 부정확한 귀하의 개인정보 수정, 또는 (iv) 귀하의 개인정보 삭제, 또는 (v) 해당 법률에 따른 특정한 경우에 귀하의 개인 계정의 폐쇄를 요청할 권리가 있습니다. 이러한 권리는 절대적이지 않습니다. 당사가 귀하의 개인 데이터 처리에 대한 귀하의 동의를 받은 경우, 귀하는 언제든지 동의를 철회할 권리가 있습니다.

만약 귀하가 귀하의 권리를 행사하시고 싶으시면  <a href="mailto:privacy@nike.com?subject=[NIKE SPACE]"
                >privacy@nike.com</a
            >으로 메일을 보내 주십시오.

<strong>기밀 유지와 보호</strong>

이 웹사이트는 귀하의 개인정보에 대한 인가되지 않은 접근, 사용 및 공개로부터 귀하의 개인정보를 보호하기 위하여 일련의 보안 기술 및 절차를 사용하고 있습니다. 웹사이트가 수집하는 개인정보는 일반인이 접근할 수 없는 안전한 환경에 저장됩니다.

<strong>다른 웹사이트에 대한 링크</strong>

이 웹사이트는 귀하의 편의와 정보를 위해 다른 웹사이트에 대한 링크를 제공할 수 있습니다. 이러한 웹사이트는 당사와 무관하게 운영되며 그들 자신의 개인정보 보호정책을 가지고 있을 수 있으므로 그 내용을 검토해 보시기 바랍니다. 링크된 웹사이트를 당사가 소유하거나 통제하지 않는 경우, 당사는 그 웹사이트의 콘텐츠, 웹사이트의 사용 또는 웹사이트의 개인정보 보호 관행에 대한 책임을 지지 않습니다.

<strong>당사 개인정보 처리방침 변경</strong>

당사가 개인정보 처리방침을 변경하기로 결정하면, 당사는 변경된 방침을 웹사이트에 게시할 것입니다. 당사 개인정보 처리방침의 변경 여부를 정기적으로 확인해 볼 것을 권장합니다. 당사가 귀하의 개인 데이터를 처리하는 방식을 중대하게 변경하는 경우, 당사는 귀하에게 사전에 통지하거나, 법적으로 요구되는 경우 그러한 변경을 실행하기 전에 귀하의 동의를 요청하거나, 그러한 처리에 대한 또 다른 법적 근거가 있음을 확인해 드릴 것입니다.

<strong>질문 및 피드백</strong>

피드백을 주시거나 질문 또는 우려 사항이 있으시면  <a href="mailto:privacy@nike.com?subject=[NIKE SPACE]"
                >privacy@nike.com</a
            > 주소로 이메일을 보내시거나 (ii)서울특별시 강남구 테헤란로 152, GFC 빌딩 법무팀으로 우편을 보내 주십시오.

개인정보 처리방침 버전 v. 1.1
개인정보 처리방침 (변경) 공지 일자:
(변경된) 개인정보 처리방침 발효일:
                                </pre>
                                </div>
                            </el-scrollbar>
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
                console.error(error);
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
