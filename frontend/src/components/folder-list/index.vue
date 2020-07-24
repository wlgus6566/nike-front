<template>
    <transition-group
        name="folder-list"
        tag="ul"
        v-bind:class="[
            { 'folder-list': listTypes[0].active },
            { 'folder-list-row': listTypes[1].active },
        ]"
    >
        <li :class="classBind(item)" v-for="(item, index) in folderListData" :key="index">
            <router-link :to="setUrl(item)">
                <div class="thumbnail">
                    <img :src="item.imageFilePhysicalName" alt="" />
                </div>
                <div class="info-box">
                    <strong class="title">{{ item.folderName }}</strong>
                    <p class="txt">{{ item.folderContents }}</p>
                    <p class="date">
                        {{ $moment(item.campaignBeginDt).format('YYYY.MM.DD') }} ~
                        {{ $moment(item.campaignEndDt).format('YYYY.MM.DD') }}
                    </p>
                </div>
                <div class="view-area">
                    <span class="view">{{ item.readCount }}</span>
                </div>
            </router-link>
        </li>
    </transition-group>
</template>
<script>
export default {
    name: 'folder-list',
    props: ['listTypes', 'folderListData'],
    mounted() {
        console.log(this.folderListData);
    },
    methods: {
        classBind(el) {
            const defaultClass = 'folder-item';
            const detailAuth = el.detailAuthYn === 'Y' ? ' detail-auth' : '';
            const exposure = el.exposure === 'Y' ? ' exposure' : '';
            return `${defaultClass}${detailAuth}${exposure}`;
        },
        setUrl(item) {
            return `${item.contentsSeq}`.toLocaleLowerCase();
        },
    },
};
</script>
<style scoped>
/* folder-item */
.folder-item {
    position: relative;
    flex: 0 1 auto;
}
.folder-item a {
    z-index: 2;
    position: relative;
    display: block;
    width: 100%;
    box-sizing: border-box;
    text-align: left;
}
.folder-item .thumbnail {
    position: relative;
    padding-top: 100%;
    overflow: hidden;
    background: url('../../assets/images/img-asset-none@2x.png') no-repeat center;
    background-size: 100%;
}
.folder-item .thumbnail img {
    display: none;
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    transform: translate(-50%, -50%);
}
.folder-item .info-box {
    overflow: hidden;
}
.folder-item .info-box .title {
    display: block;
    display: -webkit-box;
    margin-top: 12px;
    max-height: 48px;
    line-height: 24px;
    font-size: 16px;
    color: #000;
    text-overflow: ellipsis;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    word-break: keep-all;
}
.folder-item .info-box .txt {
    margin-top: 8px;
    line-height: 18px;
    font-size: 12px;
    color: #555;
}
.folder-item .info-box .location {
    display: flex;
    margin-top: 10px;
}
.folder-item .info-box .location li {
    position: relative;
    font-size: 10px;
    color: #555;
    line-height: 11px;
}
.folder-item .info-box .location li + li {
    margin-left: 2px;
    padding-left: 14px;
}
.folder-item .info-box .location li + li:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    display: block;
    width: 12px;
    height: 12px;
    margin-top: -6px;
    background: url('../../assets/images/svg/icon-mypage-route.svg') no-repeat center;
}
.folder-item .date {
    margin-top: 8px;
    display: inline-flex;
    align-items: center;
    font-size: 12px;
    height: 14px;
    color: #888;
}
.folder-item .view-area {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    padding-top: 100%;
}
.folder-item .view-area .view {
    position: absolute;
    bottom: 0;
    left: 0;
    display: flex;
    align-items: center;
    width: 100%;
    height: 24px;
    padding: 0 0 0 10px;
    box-sizing: border-box;
    font-size: 10px;
    font-weight: 500;
    letter-spacing: -0.042px;
    color: #fff;
    background: #919191;
}
.folder-item .view-area .view:before {
    content: '';
    display: block;
    width: 20px;
    height: 20px;
    margin-left: 2px;
    background: url('../../assets/images/svg/icon-view-small-w.svg') no-repeat center;
}
[class^='folder-list'] {
    width: 100%;
    display: flex;
    margin-top: 20px;
    flex-wrap: wrap;
}
[class^='folder-list'] > li {
    width: calc((100% - (20px * 3)) / 4);
    margin-left: 20px;
}
[class^='folder-list'] > li:nth-child(4n + 1) {
    margin-left: 0;
}
[class^='folder-list'] > li:nth-child(n + 5) {
    margin-top: 30px;
}
.folder-list-row > li {
    width: 100%;
    margin: 0 !important;
}
.folder-list-row > li + li {
    border-top: 1px solid #eee;
}
.folder-list-row > li a {
    display: flex;
    align-items: center;
    padding: 20px 0;
}
.folder-list-row li .thumbnail {
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 100px;
    padding-top: 100px;
}
.folder-list-row li .info-box {
    flex-grow: 1;
    flex-shrink: 1;
    flex-basis: auto;
    margin-left: 25px;
}
.folder-list-row li .info-box .title {
    margin: 0;
}
.folder-list-row li .info-box .date {
    position: absolute;
    top: 50%;
    right: 0;
    margin: 0;
    transform: translateY(-50%);
}
.folder-list-row li .view-area {
    position: relative;
    top: auto;
    left: auto;
    width: auto;
    padding: 0;
    margin: 0 157px 0 10px;
}
.folder-list-row li .view-area .view {
    position: relative;
    top: auto;
    left: auto;
    width: auto;
    padding: 0 10px;
    color: #000;
    background: transparent;
}
.folder-list-row li .view-area .view:before {
    background-image: url('../../assets/images/svg/icon-view-small.svg');
}
.folder-list-enter-active,
.folder-list-leave-active {
    transition: opacity 0.3s;
}
.folder-list-enter,
.folder-list-leave-to {
    opacity: 0;
    transform: translateY(5px);
}
</style>
