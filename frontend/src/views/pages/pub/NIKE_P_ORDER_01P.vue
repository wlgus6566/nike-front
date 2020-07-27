<template>
    <div>
        <el-button type="white" size="small" @click="showdetailView">
            계정 관리
        </el-button>

        <detailView :visible.sync="visible.detailView" />
    </div>
</template>
<script>
	import detailView from '@/views/pages/product/detail-view';

	export default {
    name: 'NIKE_P_MANAGE_02_002P',
    data() {
        return {
            visible: {
                detailView: true,
            },
        };
    },
    components: { detailView },
    methods: {
        showdetailView() {
            this.visible.detailView = true;
        },
    },
};
</script>
<style scoped></style>
