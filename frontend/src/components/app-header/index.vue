<template>
    <div class="inner">
        <h1>
            <router-link exact to="/">
                <img alt="" src="@/assets/images/logo-nike.svg" />
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
<style scoped></style>
