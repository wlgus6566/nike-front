<template>
    <li :class="[{ active: isOpen }, `depth${this.depth}`]">
        <!--<button type="button" v-if="isFolder && !item.titleKo" @click="toggle" class="nav-link" v-html="item.title" />-->
        <router-link
            @click.native.prevent="toggle"
            :to="item.to"
            :exact="item.exact"
            :event="clickable ? '' : 'click'"
            :class="[{ hasDepth: isFolder }, 'nav-link']"
            v-html="item.title"
        />
        <ul v-show="isOpen" v-if="isFolder">
            <navItem v-for="(child, index) in item.children" :key="index" :item="child" :depth="depth + 1" />
        </ul>
    </li>
</template>
<script>
export default {
    name: 'navItem',
    props: ['item', 'depth'],
    data: function () {
        return {
            isOpen: false,
        };
    },
    computed: {
        clickable: function () {
            return this.isFolder && this.depth !== 1;
        },
        isFolder: function () {
            return this.item.children && this.item.children.length;
        },
    },
    methods: {
        toggle: function () {
            if (this.clickable) {
                this.isOpen = !this.isOpen;
            }
        },
    },
};
</script>
<style></style>
