<template>
    <li :class="[`depth${this.depth}`]">
        <router-link
            @click.native.prevent="toggle()"
            :to="item.menuPathUrl"
            :exact="item.menuPathUrl === '/'"
            :event="clickable ? '' : 'click'"
            :class="[
                { active: activeIndex === navIdx },
                { hasDepth: clickable },
                'nav-link',
            ]"
            v-html="item.menuName"
        />
        <transition @enter="enter" @leave="leave" :css="false">
            <ul v-show="activeIndex === navIdx" v-if="isFolder">
                <navItem
                    @update="update"
                    v-for="(child, index) in item.menus"
                    :key="index"
                    :navIdx="index"
                    :item="child"
                    :depth="depth + 1"
                    :activeIndex="myActiveIndex"
                />
            </ul>
        </transition>
    </li>
    <!-- <li :class="[`depth${this.depth}`]">
        <router-link
            @click.native.prevent="toggle()"
            :to="item.to"
            :exact="item.exact"
            :event="clickable ? '' : 'click'"
            :class="[{ active: activeIndex === navIdx }, { hasDepth: clickable }, 'nav-link']"
            v-html="item.title"
        />
        <transition @enter="enter" @leave="leave" :css="false">
            <ul v-show="activeIndex === navIdx" v-if="isFolder">
                <navItem
                    @update="update"
                    v-for="(child, index) in item.children"
                    :key="index"
                    :navIdx="index"
                    :item="child"
                    :depth="depth + 1"
                    :activeIndex="myActiveIndex"
                />
            </ul>
        </transition>
    </li>-->
</template>
<script>
import { gsap, Cubic } from 'gsap/all';

export default {
    name: 'navItem',
    props: ['item', 'depth', 'navIdx', 'activeIndex'],
    watch: {
        $route(newRoute) {
            //this.init(newRoute.matched);
        },
    },
    data: function () {
        return {
            myActiveIndex: null,
        };
    },
    computed: {
        clickable: function () {
            return this.isFolder && this.depth === 2;
        },
        isFolder: function () {
            return this.item.menus && this.item.menus.length;
        },
    },
    methods: {
        enter(el, done) {
            gsap.set(el, {
                paddingBottom: '20',
                height: 'auto',
            });
            gsap.from(el, 0.3, {
                paddingBottom: 0,
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
        leave(el, done) {
            gsap.to(el, 0.3, {
                paddingBottom: 0,
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
        init(routeArray) {
            const linkPath = this.$el
                .querySelector(':scope > .nav-link')
                .getAttribute('href');
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
            this.myActiveIndex = !!initState
                ? index
                : this.myActiveIndex === index
                ? null
                : index;
        },
    },
    mounted() {
        this.init(this.$route.matched);
    },
};
</script>
<style></style>
