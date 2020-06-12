<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                <UserInfo></UserInfo>
                <TabMenu v-bind:propsdata="tabMenus"></TabMenu>
                <MypageMenu v-bind:propsdata="tabMenus"></MypageMenu>
                <FileItem></FileItem>
                <HistoryItem></HistoryItem>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './UserInfo.vue';
import MypageMenu from './MypageMenu.vue';
import TabMenu from './TabMenu.vue';
import FileItem from './FileItem.vue';
import HistoryItem from './HistoryItem.vue';

export default {
    data: function () {
        return {
            tabMenus: [
                {
                    title: 'MYPAGE',
                    active: true,
                },
                {
                    title: 'FILE',
                    active: true,
                },
                {
                    title: 'ORDER',
                    active: false,
                },
            ],
        };
    },
    name: 'AsideDefault',
    directives: {
        Sticky,
    },
    components: {
        UserInfo,
        TabMenu,
        MypageMenu,
        FileItem,
        HistoryItem,
    },
    mounted() {},
};
</script>
<style scoped></style>
