<template>
    <div>
        <h2 class="page-title">개인정보 처리방침</h2>
        <termsTab :tabName="tabName" />
        <div class="filter-select">
            <el-select v-model="termsHistory">
                <el-option value="2020-01-01">2020.01.01</el-option>
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
        console.log(this.tabName);
    },
    data() {
        return {
            tabName: this.$route.meta.title,
            termsHistory: '2020.01.01',
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
