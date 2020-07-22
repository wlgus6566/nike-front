<template>
    <div>
        <h2 class="page-title">
            <span class="ko">위시리스트</span>
        </h2>
        <div class="all-box">
            <!-- todo 전체선택 스크립트 작업 필요  -->
            <label class="check-label">
                <span class="checkbox">
                    <input type="checkbox" />
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>
            <p class="desc">
                <em>1</em>개의 파일이 선택됨
            </p>
            <!-- todo select 스크립트 작업 필요  -->
            <div class="btn-box">
                <button type="button" class="btn-s-lightgray-sm">
                    <i class="icon-cart"></i>
                    <span>선택 CART 담기</span>
                </button>
                <button type="button" class="btn-s-lightgray-sm">
                    <i class="icon-del"></i>
                    <span>선택삭제</span>
                </button>
            </div>
        </div>
        <!-- wish list -->
        <router-view />
    </div>
</template>

<script>
//import wish-list from '@/components/wish-list/index.vue';

export default {
    name: 'wish-list',
    components: {
        //WishList: () => import('@/components/wish-list/index'),
        // wish-list,
    },
    data() {
        return {};
    },
};
</script>

<style></style>
