<template>
    <div>
        <el-button type="white" size="small" @click="showorderSheet">
            계정 관리
        </el-button>

        <orderSheet :visible.sync="visible.orderSheet" />
    </div>
</template>
<script>
	import orderSheet from '@/views/pages/product/order-sheet';

	export default {
    name: 'NIKE_P_MANAGE_02_002P',
    data() {
        return {
            visible: {
                orderSheet: true,
            },
        };
    },
    components: { orderSheet },
    methods: {
        showorderSheet() {
            this.visible.orderSheet = true;
        },
    },
};
</script>
<style scoped></style>
