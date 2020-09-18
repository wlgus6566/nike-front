<template>
    <div>
        <ul class="aside-menu" v-if="myMenu">
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
        <a
            href="mailto:NIKESPACE@NIKE.COM?subject=[NIKE SPACE]"
            class="btn-help"
        >
            고객문의
        </a>
    </div>
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
            this.dataBinding();
        },
    },
    mounted() {
        this.dataBinding();
    },
    methods: {
        logout() {
            this.$store.commit('LOGOUT');
            this.$router.push('/login');
        },
        async dataBinding() {
            if (!this.$store.state.gnbMenuListData) return;
            try {
                const menu = await this.$store.state.gnbMenuListData.filter(
                    (item) => {
                        if (
                            item.menuCode === 'MYPAGE' &&
                            item.pcYn === 'Y' &&
                            item.listYn === 'Y'
                        )
                            return item;
                    }
                );
                this.myMenu = menu[0].menus;
            } catch (error) {
                console.log(error);
            }
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
.aside-menu .menu li a,
.btn-help {
    display: block;
    line-height: 30px;
    font-size: 11px;
    color: #555;
}
.aside-menu .menu li .router-link-active {
    font-weight: bold;
    color: #ef6926;
}
</style>
