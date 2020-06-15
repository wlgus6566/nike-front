<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                <UserInfo></UserInfo>
                <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
                <strong class="tab-title">HISTPRY</strong>
                <TabComponent v-bind:tabMenus="historyTab"></TabComponent>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './UserInfo.vue';
import TabComponent from '@/components/tab-components/index.vue';

export default {
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
    name: 'AsideDefault',
    directives: {
        Sticky,
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
