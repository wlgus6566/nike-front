<template>
    <ul class="aside-menu">
        <li v-for="(menu, index) in myMenu" :key="index">
            <strong class="title" v-html="menu.menuName" />
            <ul class="menu" v-if="menu.menus">
                <li v-for="(depth, index) in menu.menus" :key="index">
                    <router-link
                        :to="depth.menuPathUrl"
                        v-html="depth.menuName"
                    />
                </li>
            </ul>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'MypageMenu',
    data() {
        return {
            myMenu: null,
        };
    },
    watch: {
        '$store.state.gnbMenuListData'() {
            if (this.$store.state.gnbMenuListData) {
                const menu = this.$store.state.gnbMenuListData.filter(
                    (item) => {
                        if (item.menuCode === 'MYPAGE' && item.pcYn === 'Y')
                            return item;
                    }
                );
                this.myMenu = menu[0].menus;
            } else {
                return null;
            }
        },
    },
    methods: {
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
    },
};
</script>

<style scoped>
.aside-menu {
    margin-top: 19px;
}
.aside-menu .title {
    display: block;
    font-size: 14px;
    line-height: 40px;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.5px;
    font-weight: normal;
}
.aside-menu li + li .title {
    margin-top: 10px;
}
.aside-menu .menu li a {
    display: block;
    line-height: 30px;
    font-size: 12px;
    color: #000;
}
.aside-menu .menu li .router-link-active {
    font-weight: bold;
    color: #ef6926;
}
</style>
