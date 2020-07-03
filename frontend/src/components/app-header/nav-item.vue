<template>
    <li :class="[{ active: isOpen }, `depth${this.depth}`]">
        <router-link
            @click.native.prevent="toggle(navIdx)"
            :to="item.to"
            :exact="item.exact"
            :event="clickable ? '' : 'click'"
            :class="[{ hasDepth: isFolder }, 'nav-link']"
            v-html="item.title"
        />
        <ul v-show="isOpen" v-if="isFolder">
            <navItem
                v-for="(child, index) in item.children"
                :key="index"
                :navIdx="index"
                :item="child"
                :depth="depth + 1"
            />
        </ul>
    </li>
</template>
<script>
export default {
    name: 'navItem',
    props: ['item', 'depth', 'navIdx', 'activeIndex'],
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
        toggle: function (index) {
            console.log(index);
            if (this.clickable) {
                this.isOpen = !this.isOpen;
            }
        },
        toggleItem(index) {
            this.isOpen = this.isOpen === index ? null : index;
        },
    },
    created() {
        console.log(this.depth, this.navIdx);
    },
};
</script>
<style></style>
