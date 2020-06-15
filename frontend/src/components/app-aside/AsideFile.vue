<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                <UserInfo></UserInfo>
                <TabComponent v-bind:tabMenus="tabMenus"></TabComponent>
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
                        title: 'FILE',
                        component: 'ContentFile',
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
<style scoped></style>
