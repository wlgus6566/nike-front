<template>
    <ul class="tab-list">
        <li class="active" v-for="(tabMenu, index) in tabMenus" v-bind:key="index" v-on:click="tabClick(index)">
            <a href="#"
                ><span>{{ tabMenu.title }}</span></a
            >
        </li>
    </ul>
</template>
<script>
export default {
    name: 'TabMenu.vue',
    props: ['tabMenus', 'tabActive'],
    created() {},
    methods: {
        tabClick(index) {
            this.$emit('tabClick', index);

            /*item.forEach((element) => (element.active = false));
				if (item[index].active !== true) {
					item[index].active = true;
				}
				console.log(item[index].active);*/
        },
    },
};
</script>
<style scoped>
.tab-list {
    display: flex;
    margin: 44px 0 0 -10px;
}
.tab-list a {
    display: block;
    padding: 0 10px;
}
.tab-list a span {
    display: inline-block;
    line-height: 24px;
    font-size: 20px;
    line-height: 24px;
    color: #ccc;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.5px;
}
.tab-list .active a span {
    position: relative;
    color: #000;
}
.tab-list .active span:before {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    display: block;
    width: 100%;
    border-bottom: 2px solid #000;
}
</style>
