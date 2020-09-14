<template>
    <div>
        <ul class="desc-list mt20">
            <li class="desc-item">
                아이디와 비밀번호는 중복 불가합니다.
            </li>
            <li class="desc-item">
                비밀번호에는 간단한 패턴이나 공통 사전 단어<br />
                (예: NIKE(나이키), JORDAN(조던) 등)를 포함할 수 없습니다.
            </li>
            <li class="desc-item">
                비밀번호는 동일한 글자, 문자, 숫자 등을 2글자이상 반복할 수<br />
                없습니다. (예: AA1234! / A12345@@ 등)
            </li>
            <li class="desc-item">
                비밀번호 재사용 : 비밀번호 변경 시 현재 사용 중인 비밀번호의 재
                사용은 불가능하며, 기존과는 다른 비밀번호로 변경하셔야 합니다.
            </li>
        </ul>
        <form action="#" @submit.prevent="passwordChange">
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
                            id="change-pw1"
                            class="input-box"
                            maxlength="16"
                            v-model="password"
                        />
                    </span>
                </li>
                <li class="form-item">
                    <span class="form-column">
                        <label class="label-title required">새 비밀번호</label>
                    </span>
                    <span class="form-column">
                        <input
                            type="password"
                            id="change-pw2"
                            class="input-box"
                            maxlength="16"
                            placeholder="(8~16자/대소문자/숫자/특수문자 포함)"
                            v-model="newpassword"
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
                            id="change-pw3"
                            class="input-box"
                            maxlength="16"
                            placeholder=""
                            v-model="confirmpassword"
                        />
                    </span>
                </li>
            </ul>
            <div id="sticky">
                <div class="btn-wrap">
                    <button type="submit" class="btn-s-black-lg">저장</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
import { changepassword } from '@/api/my-page';

export default {
    data() {
        return {
            password: '',
            newpassword: '',
            confirmpassword: '',
        };
    },
    computed: {
        stickyH() {
            return document.querySelector('.btn-wrap').offsetHeight;
        },
    },
    mounted() {
        this.password = '';
        this.newpassword = '';
        this.confirmpassword = '';
        this.stickyCss();
        this.handleScroll();
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    created() {
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        stickyCss() {
            const stickyWrap = document.querySelector('#sticky');
            const contents = document.querySelector('.contents');
            const contentsH = contents.offsetHeight;
            stickyWrap.style.height = this.stickyH + 'px';
            contents.style.position = 'relative';
            contents.style.height = contentsH + 60 + 'px';
        },
        handleScroll() {
            const windowE = document.documentElement;
            const btnWrap = document.querySelector('.btn-wrap');
            const contents = document.querySelector('.contents');
            const footerH = document.querySelector('footer').offsetHeight;
            const navH = document.querySelector('.nav-area').offsetHeight;
            const stickyWrap = document.querySelector('#sticky');
            if (
                windowE.offsetHeight + windowE.scrollTop - navH - footerH >=
                stickyWrap.offsetTop
            ) {
                btnWrap.style.position = 'relative';
                btnWrap.style.bottom = '0';
            } else {
                btnWrap.style.position = '';
                btnWrap.style.bottom = '';
                contents.style.paddingBottom = '';
            }
        },
        async passwordChange() {
            try {
                const res = await changepassword({
                    confirmpassword: this.confirmpassword,
                    newpassword: this.newpassword,
                    password: this.password,
                });

                console.log(res);

                this.msg = res.data.msg;
                if (res.data.success) {
                    alert(this.msg);
                    this.$router.push('/mypage/info');
                } else {
                    alert(this.msg);
                }
                return;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped>
#sticky {
    position: absolute;
    left: 20px;
    bottom: 0;
    width: 100%;
    margin-left: -20px;
    margin-right: -20px;
}
</style>
