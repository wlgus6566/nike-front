<template>
    <aside class="sticky-container" sticky-container>
        <div
            class="inner"
            on-stick="onStick"
            sticky-offset="{top: 10, bottom: 30}"
            sticky-side="both"
            sticky-z-index="20"
            v-sticky
        >
            <div class="aside-wrap">
                <UserDefault></UserDefault>
                <ul class="tab-list">
                    <li>
                        <a href="#"><span>MY PAGE</span></a>
                    </li>
                    <li class="active">
                        <a href="#"><span>FILE</span></a>
                    </li>
                </ul>
                <AsideFile></AsideFile>
                <AsideOrder></AsideOrder>
                <AsidMyPage></AsidMyPage>
                <div class="history-box">
                    <strong class="title">HISTORY</strong>
                    <ul class="tab-list-history">
                        <li class="active">
                            <a href="#"><span>ASSET</span></a>
                        </li>
                        <li>
                            <a href="#"><span>TOOLKIT</span></a>
                        </li>
                    </ul>
                    <AsideHistory></AsideHistory>
                </div>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserDefault from '../UserInfo/UserDefault.vue';
import AsideFile from './AsideFile.vue';
import AsidMyPage from './AsidMyPage.vue';
import AsideOrder from './AsideOrder';
import AsideHistory from './AsidMyPage.vue';

export default {
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
    methods: {},
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
    line-height: 24px;
    font-size: 20px;
    color: #ccc;
    font-family: 'Bebas Neue', sans-serif;
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
    position: absolute;
    bottom: 2px;
    left: 0;
    content: '';
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
    font-size: 20px;
    line-height: 24px;
    color: #000;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.5px;
    font-weight: normal;
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
    line-height: 17px;
    font-size: 14px;
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
