<template>
    <div class="history-box">
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
    margin-top: 15px;
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
