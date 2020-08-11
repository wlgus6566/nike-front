<template>
    <ul class="menu-list">
        <li
            v-for="(menu, index) in menuData"
            :key="index"
            v-if="menu.mobileYn === 'Y'"
        >
            <a href="#">
                <span>{{ menu.menuName }}</span>
            </a>
            <ul class="depth">
                <li v-for="(depth, index) in menu.menus" :key="index">
                    <a href="#">{{ depth.menuName }}</a>
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
