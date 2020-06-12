<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                <UserInfo></UserInfo>
                <TabMenu v-bind:tabMenus="tabMenus" v-bind:tabActive="activeTabCont" v-on:tabClick="tabClick"></TabMenu>
                <component v-bind:is="activeTabCont"></component>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './UserInfo.vue';
import MYPAGE from './MypageMenu.vue';
import TabMenu from './TabMenu.vue';
import FILE from './OderItem.vue';

export default {
    data: function () {
        return {
            activeTabCont: 0,
            tabMenus: [
                {
                    title: 'MYPAGE',
                },
                {
                    title: 'FILE',
                },
            ],
        };
    },
    name: 'AsideDefault',
    directives: {
        Sticky,
    },
    components: {
        MYPAGE,
        FILE,
        UserInfo,
        TabMenu,
    },
    methods: {
        tabClick(index) {
            console.log(index);
            this.activeTabCont = this.tabMenus[index];
        },
    },
};
</script>
<style scoped></style>
