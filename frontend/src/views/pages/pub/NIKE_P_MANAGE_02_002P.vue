<template>
    <div>
        <el-button type="white" size="small" @click="showAccountManagement">
            계정 관리
        </el-button>

        <!--<el-pagination
            layout="prev, pager, next"
            :page-size="10"
            :pager-count="10"
            :total="1000"
            :current-page="1"
            @current-change="1"
        >
        </el-pagination>-->

        <div>
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
            <hr style="margin: 100px 0;" />
        </div>

        <accountManagement :visible.sync="visible.accountManagement" />
    </div>
</template>
<script>
import accountManagement from '@/views/pages/management/account-management';
export default {
    name: 'NIKE_P_MANAGE_02_002P',
    data() {
        return {
            visible: {
                accountManagement: false,
            },
        };
    },
    components: { accountManagement },
    methods: {
        showAccountManagement() {
            this.visible.accountManagement = true;
        },
    },
};
</script>
<style scoped></style>
