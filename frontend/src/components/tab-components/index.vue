<template>
    <div>
        <ul v-bind:class="tabMenus.tabClass">
            <li v-bind:class="{ active: tabMenu.component === tabContent }" v-for="(tabMenu, index) in tabMenus.tabList" v-bind:key="index" v-on:click="tabClick(index)">
                <a href="#"
                    ><span>{{ tabMenu.title }}</span></a
                >
            </li>
        </ul>
        <component v-bind:is="tabContent"></component>
    </div>
</template>
<script>
export default {
    data() {
        return {
            tabContent: this.tabMenus.tabList[0].component,
        };
    },
    name: 'TabComponent.vue',
    props: ['tabMenus'],
    mounted() {
        // console.log(this.tabMenus);
    },
    components: {
        ContentMypage: () => import('@/components/tab-components/ContentMypage.vue'),
        ContentOrder: () => import('@/components/tab-components/ContentOrder.vue'),
        ContentFile: () => import('@/components/tab-components/ContentFile.vue'),
        ContentAsset: () => import('@/components/tab-components/ContentAsset.vue'),
        ContentTooKlit: () => import('@/components/tab-components/ContentTooKlit.vue'),
        ContentFoundation: () => import('@/components/tab-components/ContentFoundation.vue'),
    },
    methods: {
        tabClick(index) {
            this.tabContent = this.tabMenus.tabList[index].component;
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
.tab-list-sm {
    display: flex;
    margin: 5px 0 0 -10px;
}
.tab-list-sm li {
    position: relative;
}
.tab-list-sm li + li:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    display: block;
    width: 1px;
    height: 10px;
    margin-top: -5px;
    background: #ddd;
}
.tab-list-sm a {
    display: block;
    padding: 5px 10px;
    font-size: 14px;
    line-height: 17px;
    color: #ccc;
}
.tab-list-sm a span {
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.tab-list-sm .active a {
    color: #555;
}
</style>
