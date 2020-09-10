<template>
    <div class="inner">
        <h1 class="gnb-logo">
            <router-link exact to="/">
                <img
                    alt=""
                    class="logo1"
                    src="@/assets/images/logo-nike2.svg"
                />
                <img alt="" class="logo2" src="@/assets/images/logo-nike.svg" />
            </router-link>
        </h1>
        <nav>
            <navWrap />
        </nav>
    </div>
</template>
<script>
import navWrap from './nav-wrap';

export default {
    name: 'appHeader',
    data() {
        return {};
    },
    components: { navWrap },
};
</script>
<style>
.gnb-logo {
    /*width: 75px;*/
    /*height: 88px;*/
    /*background: url('../../assets/images/logo-nike2.svg') 0 0 no-repeat;*/
}
.gnb-logo .logo2 {
    margin-left: 8px;
    display: none;
}
.gnb-logo a {
    display: block;
}
</style>
