<template>
    <div>
        <h2 class="page-title"><span class="ko">개인정보 처리방침</span></h2>
        <termsTab :tabName="tabName" />
        <div class="filter-select">
            <el-select v-model="termsHistory">
                <el-option value="2020-09-21">2020.09.21</el-option>
            </el-select>
        </div>
        <termsService />
    </div>
</template>
<script>
export default {
    name: 'privacy',
    components: {
        termsTab: () => import('@/components/terms/tab'),
        termsService: () => import('@/components/terms/privacy'),
    },
    mounted() {
        //console.log(this.tabName);
    },
    data() {
        return {
            tabName: this.$route.meta.title,
            termsHistory: '2020.09.21',
        };
    },
};
</script>
<style scoped>
.filter-select {
    width: 90px;
}
::v-deep .filter-select .el-input__inner {
    height: 30px;
}
</style>
