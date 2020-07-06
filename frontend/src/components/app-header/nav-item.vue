<template>
    <li :class="[{ active: activeIndex === navIdx }, `depth${this.depth}`]">
        <router-link
            @click.native.prevent="toggle()"
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
    watch: {
        $route(newRoute) {
            this.init(newRoute.matched);
        },
    },
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
        init(routeArray) {
            const linkPath = this.$el.querySelector(':scope > .nav-link').getAttribute('href');
            for (let item in routeArray) {
                const path = routeArray[item].path;
                if (path === linkPath) {
                    this.$emit('update', this.navIdx, true);
                }
            }
        },
        toggle() {
            if (this.clickable) {
                this.$emit('update', this.navIdx);
            }
        },
        update(index, initState) {
            this.myIndex = !!initState ? index : this.myIndex === index ? null : index;
        },
    },
    mounted() {
        this.init(this.$route.matched);
    },
};
</script>
<style></style>
