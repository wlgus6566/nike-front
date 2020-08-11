<template>
    <ul class="menu-list" v-if="menuData">
        <li
            v-for="menu in menuData"
            :key="menu.menuSeq"
            v-if="menu.mobileYn === 'Y'"
        >
            <button type="button">
                <span>{{ menu.menuName }}</span>
            </button>
            <ul class="depth">
                <li v-for="depth in menu.menus" :key="depth.menuSeq">
                    <router-link :to="menu.menuPathUrl">{{
                        depth.menuName
                    }}</router-link>
                </li>
            </ul>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'menu-list',
    props: ['menuUse', 'menuData'],
};
</script>
<style scoped></style>
