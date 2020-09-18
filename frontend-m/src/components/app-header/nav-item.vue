<template>
    <ul class="nav-tab" v-if="tabMenuData">
        <li
            v-for="menu in tabMenuList(tabMenuData)"
            :key="menu.menuSeq"
            :class="tabItemClass(menu.menuPathUrl)"
        >
            <router-link :to="menu.menuPathUrl">
                <span v-html="menuTitle(menu.menuName)"></span>
            </router-link>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'nav-item',
    props: ['tabMenuData'],
    created() {},
    computed: {},
    methods: {
        tabMenuList(tabMenu) {
            const menu = tabMenu.filter(el => {
                return el.listYn === 'Y';
            });
            return menu;
        },
        menuTitle(title) {
            if (title === 'REPORT <span>업로드</span>') {
                return '업로드';
            } else if (title === 'REPORT <span>관리</span>') {
                return '관리';
            } else if (title === 'AGENCY CONTACT') {
                return 'AGENCY';
            } else {
                return title;
            }
        },
        tabItemClass(val) {
            const titleValue = this.$route.path;
            if (val === '/report/upload') {
                const reportArry = titleValue.split('/');
                const reportModify = reportArry.some(el => {
                    return el === 'modify';
                });
                if (reportModify) {
                    return { active: true };
                }
            }
            return {
                active: titleValue === val,
            };
        },
    },
};
</script>
<style scoped></style>
