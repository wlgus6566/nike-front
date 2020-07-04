<template>
    <li :class="[{ active: activeIndex === navIdx }, `depth${this.depth}`]">
        <router-link
            @click.native.prevent="toggle(navIdx)"
            :to="item.to"
            :exact="item.exact"
            :event="clickable ? '' : 'click'"
            :class="[{ hasDepth: isFolder }, 'nav-link']"
            v-html="item.title"
        />
        <ul v-show="activeIndex === navIdx" v-if="isFolder">
            <navItem
                @update="update"
                v-for="(child, index) in item.children"
                :key="index"
                :navIdx="index"
                :item="child"
                :depth="depth + 1"
                :activeIndex="myIndex"
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
            myIndex: null,
        };
    },
    computed: {
        clickable: function () {
            return this.isFolder && this.depth === 2;
        },
        isFolder: function () {
            return this.item.children && this.item.children.length;
        },
    },
    methods: {
        toggle: function (index) {
            if (this.clickable) {
                this.$emit('update', index);
            }
        },
        update(index) {
            this.myIndex = this.myIndex === index ? null : index;
        },
    },
    created() {
        //console.log(this.depth, this.navIdx);
    },
};
</script>
<style></style>
