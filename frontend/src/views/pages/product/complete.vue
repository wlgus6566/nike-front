<template>
    <div class="completion-box">
        <i class="icon-nike"></i>
        <strong class="title">주문이 완료 되었습니다.</strong>
        <p class="desc">상품별 담당 에이전시 메일로 주문서가 발송되었습니다.</p>
        <p class="sub-desc">
            * 2일 이내로 에이전시에서 주문 확인이 이루어질 예정이며 지연될 경우
            해당 에이전시로 연락바랍니다.
        </p>
        <div class="btn-area">
            <router-link to="/mypage/order" type="button" class="btn-s-white">
                <span>주문내역보기</span>
            </router-link>
            <router-link to="/" class="btn-s-black">
                <span>홈으로 가기</span>
            </router-link>
        </div>
    </div>
</template>
<script>
export default {
    name: 'complete',
};
</script>
<style scoped></style>
