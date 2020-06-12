<template>
    <div class="history-box">
        <strong class="title">HISTORY</strong>
        <ul class="tab-list-history">
            <li>
                <a href="#"><span>ASSET</span></a>
            </li>
            <li>
                <a href="#"><span>TOOLKIT</span></a>
            </li>
            <li>
                <a href="#"><span>FOUNDATION</span></a>
            </li>
        </ul>
        <div class="item-box">
            <ul class="item-list">
                <li class="item">
                    <div class="thumbnail">
                        <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    </div>
                    <div class="info-box">
                        <p class="title">듀브레</p>
                        <span class="date">2020.01.01 - 2020.03.31</span>
                    </div>
                </li>
                <li class="item">
                    <div class="thumbnail">
                        <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    </div>
                    <div class="info-box">
                        <p class="title">듀브레</p>
                        <span class="date">2020.01.01 - 2020.03.31</span>
                    </div>
                </li>
                <li class="item">
                    <div class="thumbnail">
                        <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    </div>
                    <div class="info-box">
                        <p class="title">듀브레</p>
                        <span class="date">2020.01.01 - 2020.03.31</span>
                    </div>
                </li>
                <li class="item">
                    <div class="thumbnail">
                        <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    </div>
                    <div class="info-box">
                        <p class="title">듀브레</p>
                        <span class="date">2020.01.01 - 2020.03.31</span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
export default {
    name: 'HistoryItem',
};
</script>
<style scoped>
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
    font-weight: 300;
}
</style>
