<template>
    <div class="inner">
        <h1 class="gnb-logo">
            <router-link exact to="/">
                <img
                    alt=""
                    class="logo-nike-space"
                    src="@/assets/images/logo-nike-space.svg"
                />
                <img
                    alt=""
                    class="logo-nike"
                    src="@/assets/images/logo-nike.svg"
                />
            </router-link>
        </h1>
        <div class="back">
            <img
                alt=""
                class="logo-nike"
                src="@/assets/images/icon-lnb-back.svg"
            />
        </div>
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
<style scoped></style>
