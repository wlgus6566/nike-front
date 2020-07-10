<template>
    <div>
        <el-button type="white" size="small" @click="showAccountManagement">
            계정 관리
        </el-button>

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
    name: 'NIKE_P_INFO_01_001p',
    data() {
        return {
            visible: {
                accountManagement: true,
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
