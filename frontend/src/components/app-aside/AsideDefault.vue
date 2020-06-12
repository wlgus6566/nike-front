<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                <UserInfo></UserInfo>
                <TabMenu v-bind:propsdata="tabMenus"></TabMenu>
                <MypageMenu></MypageMenu>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserInfo from './UserInfo.vue';
import MypageMenu from './MypageMenu.vue';
import TabMenu from './TabMenu.vue';

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
                    active: false,
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
    },
    mounted() {},
    methods: {
        tabClick(item, index) {
            item.forEach((element) => (element.active = false));
            if (item[index].active !== true) {
                item[index].active = true;
            }
            console.log(item[index].active);
        },
    },
};
</script>
<style scoped></style>
