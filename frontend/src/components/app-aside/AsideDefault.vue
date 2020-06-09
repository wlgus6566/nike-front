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
                    <div class="tab-contents">
                        <ul class="item-list">
                            <li class="item">
                                <div class="thumbnail">
                                    <img
                                        src="../../assets/images/img-asset-none@2x.png"
                                        alt=""
                                    />
                                </div>
                                <div class="info-box">
                                    <p class="title">듀브레</p>
                                    <span class="date"
                                        >2020.01.01 - 2020.03.31</span
                                    >
                                </div>
                            </li>
                            <li class="item">
                                <div class="thumbnail">
                                    <img
                                        src="../../assets/images/img-asset-none@2x.png"
                                        alt=""
                                    />
                                </div>
                                <div class="info-box">
                                    <p class="title">듀브레</p>
                                    <span class="date"
                                        >2020.01.01 - 2020.03.31</span
                                    >
                                </div>
                            </li>
                            <li class="item">
                                <div class="thumbnail">
                                    <img
                                        src="../../assets/images/img-asset-none@2x.png"
                                        alt=""
                                    />
                                </div>
                                <div class="info-box">
                                    <p class="title">듀브레</p>
                                    <span class="date"
                                        >2020.01.01 - 2020.03.31</span
                                    >
                                </div>
                            </li>
                            <li class="item">
                                <div class="thumbnail">
                                    <img
                                        src="../../assets/images/img-asset-none@2x.png"
                                        alt=""
                                    />
                                </div>
                                <div class="info-box">
                                    <p class="title">듀브레</p>
                                    <span class="date"
                                        >2020.01.01 - 2020.03.31</span
                                    >
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import UserDefault from '../UserInfo/UserDefault.vue';
import AsideFile from './AsideFile.vue';
import AsideOrder from './AsideOrder';

export default {
    name: 'AsideDefault',
    directives: {
        Sticky,
    },
    components: {
        AsideOrder,
        UserDefault,
        AsideFile,
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
    font-weight: bold;
}
.tab-list .active a {
    color: #000;
}
.tab-list .active span {
    border-bottom: 2px solid #000;
}

.history-box {
    margin-top: 63px;
}
.history-box .title {
    display: block;
    font-size: 20px;
    line-height: 24px;
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
    line-height: 17px;
    font-size: 14px;
    color: #ccc;
    font-weight: bold;
}
.tab-list-history .active a {
    color: #555;
}
.item-list .item {
    display: flex;
    align-items: center;
    padding: 5px 0;
}
.item-list .item .thumbnail {
    display: block;
    width: 50px;
    height: 50px;
    margin-right: 10px;
}
.item-list .item .thumbnail img {
    vertical-align: top;
}
.item-list .item .info-box .title {
    display: block;
    font-size: 12px;
    line-height: 18px;
    letter-spacing: -0.5px;
    color: #000;
}
.item-list .item .info-box .date {
    display: block;
    margin-top: 3px;
    font-size: 10px;
    line-height: 11px;
    color: #888;
}
</style>
