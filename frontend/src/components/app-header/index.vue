<template>
    <div class="inner">
        <h1>
            <router-link exact to="/">
                <img alt="" src="@/assets/images/logo-nike.svg" />
            </router-link>
        </h1>
        <nav>
            <ul>
                <router-link exact tag="li" to="/test">
                    <a>TEST</a>
                    <ul></ul>
                </router-link>
                <router-link exact tag="li" to="/">
                    <a>MAIN</a>
                </router-link>
                <router-link exact tag="li" to="/asset">
                    <a>ASSET</a>
                    <ul>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                    </ul>
                </router-link>
                <router-link exact tag="li" to="/toolkit">
                    <a>TOOLKIT</a>
                    <ul>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                        <li>
                            <a href="#">123123</a>
                        </li>
                    </ul>
                </router-link>
                <li>
                    <router-link exact to="/foundation">FOUNDATION</router-link>
                </li>
                <li>
                    <router-link exact to="/order">ORDER</router-link>
                </li>
                <li>
                    <router-link exact to="/report">REPORT</router-link>
                </li>
                <li>
                    <router-link exact to="/information">INFORMATION</router-link>
                </li>
                <li>
                    <router-link exact to="/management">MANAGEMENT</router-link>
                </li>
            </ul>
        </nav>
    </div>
</template>
<script>
//import Sticky from 'vue-sticky-directive';

export default {
    name: 'appHeader',
    directives: {
        // Sticky,
    },
    computed: {
        NavStatus() {
            return this.$route.path === '/';
        },
    },
    mounted() {},
    methods: {},
};
</script>
<style scoped>
header {
}
header h1 {
    transform-origin: 0 0;
}
header nav > ul {
}
nav ul ul {
    display: none;
}
</style>
