<template>
    <ul class="menu-list" v-if="menuData">
        <li v-for="menu in menuData" :key="menu.menuSeq">
            <button type="button">
                <span>{{ menu.menuName }}</span>
            </button>
            <ul class="depth">
                <li v-for="depth in menu.menus" :key="depth.menuSeq">
                    <a :href="depth.menuPathUrl">{{ depth.menuName }}</a>
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
