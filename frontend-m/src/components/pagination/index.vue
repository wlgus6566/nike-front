<template>
    <el-pagination
        :page-size="itemLength"
        :pager-count="pageCount"
        layout="prev, pager, next"
        :total="totalItem"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
</template>
<script>
export default {
    name: 'pagination',
    data() {
        return {};
    },
    props: ['itemLength', 'pageCount', 'totalItem'],
    methods: {
        handleCurrentChange(val) {
            console.log(val);
            this.$emit('handleCurrentChange', val - 1);
        },
    },
};
</script>
<style scoped></style>
