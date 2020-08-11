<template>
    <div>
        <div class="detail-view">
            <div class="title-box">
                <h2 class="title">{{ noticeDetail.title }}</h2>
                <span class="date">{{ noticeDetail.registrationDt }}</span>
            </div>
            <div class="detail-cont">{{ noticeDetail.contents }}</div>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-black" @click="$router.go(-1)">
                <span>목록으로 가기</span>
            </button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'detail-view',
    props: ['noticeDetail'],
    data() {
        return {};
    },
    methods: {},
};
</script>
<style scoped></style>
