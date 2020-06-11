<template>
    <aside class="sticky-container" sticky-container>
        <div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
            <div class="aside-wrap">
                123
                <UserDefault></UserDefault>
                <ul class="tab-list">
                    <li v-bind:class="{ active: tab.active }" v-bind:key="index" v-for="(tab, index) in tabs" v-on:click="tabClick(tabs, index)">
                        <a href="#">
                            <span>{{ tab.tabName }}</span>
                        </a>
                    </li>
                </ul>
                <AsidMyPage v-if="tabs[0].active === true"></AsidMyPage>
                <AsideFile v-if="tabs[1].active === true"></AsideFile>
                <AsideOrder v-if="tabs[2].active === true"></AsideOrder>
                <div class="history-box">
                    <strong class="title">HISTORY</strong>
                    <ul class="tab-list-history">
                        <li v-bind:class="{ active: historyTab.active }" v-bind:key="index" v-for="(historyTab, index) in historyTabs" v-on:click="tabClick(historyTabs, index)">
                            <a href="#">
                                <span>{{ historyTab.tabName }}</span>
                            </a>
                        </li>
                    </ul>
                    <AsideHistory v-if="historyTabs[0].active === true"></AsideHistory>
                </div>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserDefault from './UserDefault.vue';
import AsideFile from './AsideFile.vue';
import AsidMyPage from './AsideMyPage.vue';
import AsideOrder from './AsideOrder';
import AsideHistory from './History.vue';

export default {
    data: function () {
        return {
            tabs: [
                {
                    tabName: 'MY page',
                    active: true,
                },
                {
                    tabName: 'FILE',
                    active: false,
                },
                {
                    tabName: 'ORDER',
                    active: false,
                },
            ],
            historyTabs: [
                {
                    tabName: 'ASSET',
                    active: true,
                },
                {
                    tabName: 'TOOLKIT',
                    active: false,
                },
            ],
        };
    },
    name: 'AsideDefault',
    directives: {
        Sticky,
    },
    components: {
        AsideOrder,
        UserDefault,
        AsideFile,
        AsidMyPage,
        AsideHistory,
    },
    mounted() {},
    methods: {
        tabClick(item, index) {
            item.forEach((element) => (element.active = false));
            if (item[index].active !== true) {
                item[index].active = true;
            }
            console.log(item[index].active);
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
    font-family: 'Bebas Neue', sans-serif;
    font-size: 20px;
    line-height: 24px;
    color: #ccc;
}
.tab-list a span {
    letter-spacing: 0.5px;
}
.tab-list .active a {
    color: #000;
}
.tab-list .active span {
    position: relative;
}
.tab-list .active span:before {
    content: '';
    position: absolute;
    bottom: 2px;
    left: 0;
    display: block;
    width: 100%;
    height: 2px;
    background: #000;
}
.history-box {
    margin-top: 63px;
}
.history-box .title {
    display: block;
    font-family: 'Bebas Neue', sans-serif;
    font-size: 20px;
    font-weight: normal;
    line-height: 24px;
    letter-spacing: 0.5px;
    color: #000;
}
.tab-list-history {
    display: flex;
    margin: 5px 0 0 -10px;
}
.tab-list-history li {
    position: relative;
}
.tab-list-history li + li:before {
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
.tab-list-history a {
    display: block;
    padding: 10px 10px;
    font-size: 14px;
    line-height: 17px;
    color: #ccc;
}
.tab-list-history a span {
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.tab-list-history .active a {
    color: #555;
}
</style>
