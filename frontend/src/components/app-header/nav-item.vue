<template>
    <li :class="{ active: isOpen }">
        <button type="button" v-if="isFolder && !item.titleKo" @click="toggle" class="nav-link">
            {{ item.title }}
        </button>
        <router-link v-else :to="item.to" :exact="item.exact" :class="[{ hasDepth: isFolder }, 'nav-link']">
            {{ item.title }}
        </router-link>

        <ul v-show="isOpen" v-if="isFolder">
            <navItem v-for="(child, index) in item.children" :key="index" :item="child" />
        </ul>
    </li>
</template>
<script>
export default {
    name: 'navItem',
    props: ['item'],
    data: function () {
        return {
            isOpen: false,
        };
    },
    computed: {
        isFolder: function () {
            return this.item.children && this.item.children.length;
        },
    },
    methods: {
        toggle: function () {
            if (this.isFolder) {
                this.isOpen = !this.isOpen;
            }
        },
    },
};
</script>
<style scoped></style>
