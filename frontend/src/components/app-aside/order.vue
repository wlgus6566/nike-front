<template>
    <div class="inner">
        <div class="aside-wrap">
            <UserInfo></UserInfo>
            <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
            <strong class="tab-title">HISTPRY</strong>
            <TabComponent v-bind:tabMenus="historyTab"></TabComponent>
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
                        title: 'MYPAGE',
                        component: 'ContentMypage',
                    },
                    {
                        title: 'CART',
                        component: 'ContentOrder',
                    },
                ],
            },
            historyTab: {
                tabClass: 'tab-list-sm',
                tabList: [
                    {
                        title: 'ASSET',
                        component: 'ContentAsset',
                    },
                    {
                        title: 'TOOLKIT',
                        component: 'ContentTooKlit',
                    },
                    {
                        title: 'FOUNDATION',
                        component: 'ContentFoundation',
                    },
                ],
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
<style scoped>
.tab-title {
    display: block;
    margin-top: 40px;
    font-family: 'Bebas Neue', sans-serif;
    font-size: 20px;
    font-weight: normal;
    line-height: 24px;
    letter-spacing: 0.5px;
    color: #000;
}
</style>
