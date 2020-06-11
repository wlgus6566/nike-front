<template>
    <div class="aside-menu">
        <ul class="menu">
            <li v-for="(mypageMenu, index) in propsdata" v-bind:key="index">
                <a href="#">{{ mypageMenu.title }}</a>
                <ul v-if="mypageMenu.depth">
                    <li></li>
                </ul>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'AsideMyPage.vue',
    props: ['propsdata'],
};
</script>
<style scoped>
.aside-menu {
    margin-top: 19px;
}
.aside-menu .title {
    display: block;
    font-size: 14px;
    line-height: 40px;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.5px;
    font-weight: normal;
}
.aside-menu .menu + .title {
    margin-top: 10px;
}
.aside-menu .menu li a {
    display: block;
    line-height: 30px;
    font-size: 12px;
    color: #000;
}
</style>
