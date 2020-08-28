<template>
    <div class="inner">
        <div class="aside-wrap">
            <UserInfo></UserInfo>
            <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
        </div>
    </div>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './user.vue';
import TabComponent from '@/components/tab-comp/index.vue';

export default {
    name: 'AsideFile',
    props: ['shouldStick'],
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
                        title: 'REPORT',
                        component: 'ContentReport',
                    },
                ],
                showIndex: 1,
            },
        };
    },
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
<style scoped></style>
