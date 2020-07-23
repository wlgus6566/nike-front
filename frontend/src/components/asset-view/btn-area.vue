<template>
    <div class="btn-detail">
        <a href="#" class="btn-list"><span>목록으로 가기</span></a>
        <div class="btn-box">
            <button type="button" class="btn-o-white"><span>삭제</span></button>
            <button type="button" class="btn-o-white"><span>수정</span></button>
            <slot></slot>
        </div>
    </div>
</template>
<script>
export default {
    name: 'BtnArea',
};
</script>
<style scoped></style>
