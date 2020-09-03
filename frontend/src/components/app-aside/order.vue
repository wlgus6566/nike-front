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
import TabComponent from '@/components/tab-comp/index.vue';

export default {
    name: 'AsideOrder',
    data: function () {
        return {
            tabMenus: {
                tabClass: 'tab-list',
                tabList: [
                    {
                        title: 'MY PAGE',
                        component: 'ContentMypage',
                    },
                    {
                        title: 'CART',
                        component: 'ContentOrder',
                    },
                ],
                showIndex: '1',
            },
        };
    },
    components: {
        UserInfo,
        TabComponent,
    },
    methods: {},
};
</script>
<style scoped></style>
