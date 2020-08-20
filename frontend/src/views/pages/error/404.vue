<template>
  <div class="error-box">
    <i class="icon-error"></i>
    <strong class="title">요청하신 페이지를 찾을 수 없습니다.</strong>
    <p class="desc">
      요청하신 페이지는 제거되었거나 이름이 변경되어 사용할 수 없습니다.<br />
      입력하신 페이지의 주소가 정확한지 다시 한번 확인해 주세요.
    </p>
    <div class="btn-area">
      <router-link to="/" class="btn-s-black"><span class="bebas">HOME</span></router-link>
    </div>
  </div>
</template>

<script>
export default {
    title: '404 Error Page',
};
</script>
<style scoped></style>
