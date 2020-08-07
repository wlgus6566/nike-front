<template>
    <ul class="nav-tab" v-if="$route.meta.tab">
        <li
            :class="tabItemClass(item.value)"
            v-for="(item, index) in $route.meta.tab"
            :key="index"
        >
            <router-link :to="item.value">
                <span>{{ item.title }}</span>
            </router-link>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'nav-item',
    props: ['menuItem'],
    created() {
        console.log(this.menuItem);
    },
    computed: {
        menu() {
            // if (this.menuItem.length > 1) {
            //     console.log(1);
            // } else {
            //     console.log(2);
            // }
        },
    },
    methods: {
        tabItemClass(val) {
            const titleValue = this.$route.path.split('/').slice(-1)[0];
            return {
                active: titleValue === val,
            };
        },
    },
};
</script>
<style scoped></style>
