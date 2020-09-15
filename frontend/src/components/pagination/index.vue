<template>
    <el-pagination
        :page-size="itemLength"
        :pager-count="pageCount"
        layout="prev, pager, next"
        :total="totalItem"
        @current-change="handleCurrentChange"
        :current-page.sync="page"
    >
    </el-pagination>
</template>
<script>
export default {
    name: 'pagination',
    data() {
        return {
            page: 1,
        };
    },
    watch: {},
    props: ['itemLength', 'pageCount', 'totalItem', 'currentPage'],
    methods: {
        handleCurrentChange(val) {
            this.page = val;
            this.$emit('handleCurrentChange', val - 1);
        },
    },
};
</script>
<style scoped></style>
