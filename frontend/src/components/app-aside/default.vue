<template>
    <div class="inner">
        <div class="aside-wrap">
            <UserInfo></UserInfo>
            <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
        </div>
    </div>
</template>
<script>
import UserInfo from './user.vue';
import TabComponent from '@/components/tab-comp/';

export default {
    name: 'AsideDefault',
    watch: {},
    data: function () {
        return {
            tabMenus: {
                tabClass: 'tab-list',
                tabList: [
                    {
                        title: 'MYPAGE',
                        component: 'ContentMypage',
                    },
                ],
                showIndex: 0,
            },
        };
    },
    components: {
        UserInfo,
        TabComponent,
    },
};
</script>
<style scoped></style>
