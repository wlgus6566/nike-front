<template>
    <div class="aside-menu">
        <strong class="title">MANAGEMENT</strong>
        <ul class="menu">
            <li>
                <a href="#">파트너 계정관리</a>
            </li>
            <li>
                <a href="#">에이전시 계정관리</a>
            </li>
        </ul>
        <strong class="title">UPLOAD</strong>
        <ul class="menu">
            <li>
                <a href="#">내가 업로드한 파일</a>
            </li>
            <li>
                <a href="#">최근 업로드 파일</a>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'asideMenu.vue',
}
</script>
<style scoped>
.aside-menu {
    margin-top: 43px;
}
.aside-menu .title {
    display: block;
    font-size: 14px;
    font-weight: bold;
    line-height: 40px;
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
