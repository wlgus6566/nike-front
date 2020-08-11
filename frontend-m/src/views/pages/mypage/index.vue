<template>
    <div class="my-main">
        <div class="user-info">
            <strong class="name">ADMIN</strong>
            <div class="store">store</div>
            <div class="email">email@email.com</div>
            <div class="btn-box">
                <button type="button" class="btn-out">나가기</button>
                <button type="button" class="btn-alarm">알람</button>
            </div>
        </div>
        <hr />
        <ul class="my-menu">
            <li
                v-for="(menu, index) in myMenuData.menus"
                :key="index"
                v-if="menu.mobileYn === 'Y'"
            >
                <strong class="title">{{ menu.menuName }}</strong>
                <ul v-if="menu.menus">
                    <li v-for="depth in menu.menus">
                        <router-link :to="depth.menuPathUrl">{{
                            depth.menuName
                        }}</router-link>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'mypageIndex',
    data() {
        return {
            myMenuData: [],
        };
    },
    created() {},
    mounted() {
        this.myMenuFn();
        //console.log(this.myMenuData);
    },
    watch: {},
    methods: {
        myMenuFn() {
            const menu = this.$store.state.menuData.filter(
                el => el.menuCode === 'MYPAGE'
            );
            this.myMenuData = menu[0];
        },
    },
};
</script>
<style scoped></style>
