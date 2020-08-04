<template>
    <div>
        <BtnArea />
        <mypageView />
    </div>
</template>

<script>
    import BtnArea from '@/components/asset-view/btn-area.vue';
    import mypageView from '@/components/news/view.vue';

    export default {
    name: 'notice-view',
    data() {
        return {};
    },
    components: {
        mypageView,
        BtnArea,
    },
};
</script>
<style scoped></style>
