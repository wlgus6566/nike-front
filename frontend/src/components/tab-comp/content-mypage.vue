<template>
    <div class="aside-menu">
        <strong class="title">HISTORY</strong>
        <ul class="menu">
            <li>
                <router-link to="/mypage/upload">내가 업로드한 폴더</router-link>
            </li>
            <li>
                <router-link to="/mypage/latest">최근 본 폴더</router-link>
            </li>
        </ul>
        <strong class="title">ORDER</strong>
        <ul class="menu">
            <li>
                <router-link to="/mypage/order">주문내역확인</router-link>
            </li>
            <li>
                <router-link to="/mypage/wish-list">위시리스트</router-link>
            </li>
        </ul>
        <strong class="title">MY INFO</strong>
        <ul class="menu">
            <li>
                <router-link to="/mypage/info">회원정보 조회</router-link>
            </li>
            <li>
                <router-link to="/mypage/password">비밀번호 변경</router-link>
            </li>
            <li>
                <router-link to="/mypage/logout">로그아웃</router-link>
            </li>
        </ul>
        <strong class="title">CUSTOMER CENTER</strong>
        <ul class="menu">
            <li>
                <router-link to="/mypage/notice">공지사항</router-link>
            </li>
            <li>
                <router-link to="/mypage/news">NEWS</router-link>
            </li>
            <li>
                <router-link to="/mypage/faq">자주 묻는 질문</router-link>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'MypageMenu.vue',
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
