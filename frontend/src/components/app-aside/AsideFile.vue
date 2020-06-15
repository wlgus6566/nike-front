<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky="shouldStick">
            <div class="test">
                <div class="aside-wrap">
                    <UserInfo></UserInfo>
                    <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
                </div>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './UserInfo.vue';
import TabComponent from '@/components/tab-components/index.vue';

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
                ],
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
