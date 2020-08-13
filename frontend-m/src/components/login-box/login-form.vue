<template>
    <form action="#" @submit.prevent="$emit('login')">
        <strong class="nike-title">
            <span>NIKE D & P</span>
            <span>NIKE SPACE</span>
        </strong>
        <div class="login-input">
            <input
                type="text"
                :value="loginData.username"
                @input="
                    $emit(
                        'updateValue',
                        'username',
                        $event.target.value
                    )
                "
                placeholder="ID(E-MAIL)"
            >
            <input
                type="password"
                :value="loginData.password"
                @input="
                    $emit(
                        'updateValue',
                        'password',
                        $event.target.value
                    )
                "
                placeholder="PW"
            >
        </div>
        <div class="btn-area">
            <button type="submit" class="btn-s-orage">로그인</button>
            <a
                href="#"
                class="under-link"
                @click.prevent="$emit('changeLoginBox', 'FindPW')"
            ><span>비밀번호 찾기</span></a>
        </div>
    </form>
</template>
<script>
export default {
    name: 'loginForm',
    props: ['loginData'],
    data() {
        return {};
    },
    methods: {},
};
</script>
