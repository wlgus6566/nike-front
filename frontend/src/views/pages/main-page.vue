<template>
    <div>
        <div class="main-banner">
            <div class="thumbnail">
                <!--img src="http://placehold.it/820X410" alt="이미지없음" />-->
                <img
                    :src="mainData.mainVisual.pcImageUrl"
                    :alt="mainData.mainVisual.imageFileName"
                    v-if="mainData.mainVisual.pcImageUrl"
                />
            </div>
            <div class="info-box">
                <strong class="title">
                    {{ mainData.mainVisual.title }}
                </strong>
                <p class="desc">
                    {{ mainData.mainVisual.contents }}
                </p>

                <a :href="`${mainData.mainVisual.linkUrl}`" class="btn-s-black">
                    <span>VIEW</span>
                </a>
            </div>
        </div>
        <h2 class="main-title">RECENT UPDATE</h2>
        <ul class="main-update-list">
            <li
                v-for="assetItem in mainData.assetContentsList"
                :key="assetItem.contentsSeq"
            >
                <router-link
                    :to="`/asset/${assetItem.menuCode.toLocaleLowerCase()}/${
                        assetItem.contentsSeq
                    }`"
                >
                    <span class="thumbnail">
                        <img
                            :src="assetItem.imageFilePhysicalName"
                            :alt="assetItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ assetItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ assetItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
            <li
                v-for="toolKitItem in mainData.toolKitContentsList"
                :key="toolKitItem.contentsSeq"
            >
                <router-link
                    :to="`/toolKit/${toolKitItem.menuCode.toLocaleLowerCase()}/${
                        toolKitItem.contentsSeq
                    }`"
                >
                    <span class="thumbnail">
                        <img
                            :src="toolKitItem.imageFilePhysicalName"
                            :alt="toolKitItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ toolKitItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ toolKitItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
            <li
                v-for="foundationItem in mainData.foundationContentsList"
                :key="foundationItem.contentsSeq"
            >
                <router-link
                    :to="`'/foundation/${foundationItem.menuCode.toLocaleLowerCase()}/${
                        foundationItem.contentsSeq
                    }'`"
                >
                    <span class="thumbnail">
                        <img
                            :src="foundationItem.imageFilePhysicalName"
                            :alt="foundationItem.folderName"
                        />
                    </span>
                    <span class="info-box">
                        <span class="label">
                            {{ foundationItem.topMenuCode }}
                        </span>
                        <span class="desc">
                            {{ foundationItem.folderName }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>

        <div class="board-box">
            <div class="inner">
                <h2 class="main-title">NOTICE</h2>
                <ul class="main-notice-list">
                    <li
                        :class="{ noti: noticeItem.noticeYn === 'Y' }"
                        v-for="noticeItem in mainData.noticeArticleList"
                        :key="noticeItem.noticeArticleSeq"
                    >
                        <router-link
                            :to="`/mypage/notice/detail/${noticeItem.noticeArticleSeq}`"
                        >
                            <span class="title">
                                {{ noticeItem.title }}
                            </span>
                            <span class="date">
                                {{ noticeItem.updateDt }}
                            </span>
                        </router-link>
                    </li>
                </ul>
            </div>
            <div class="inner">
                <h2 class="main-title">CALENDAR</h2>
                <div>
                    <!-- todo// 캘릭터 작업 -->
                    CALENDAR
                </div>
            </div>
        </div>

        <h2 class="main-title">REPORT</h2>
        <ul class="main-report-list">
            <li
                v-for="repoertItem in mainData.reportList"
                :key="repoertItem.reportSeq"
            >
                <router-link :to="`/report/${repoertItem.readCount}`">
                    <span class="thumbnail">
                        <img
                            :src="repoertItem.imageFilePhysicalName"
                            :alt="repoertItem.reportName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">
                            리포트 계정명 필요
                        </strong>
                        <p class="desc">
                            {{ repoertItem.reportName }}
                        </p>
                        <span class="date">
                            {{ repoertItem.updateDt }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>
        <h2 class="main-title">NEWS</h2>
        <ul class="main-news-list">
            <li
                v-for="newItem in mainData.newsArticleList"
                :key="newItem.noticeArticleSeq"
            >
                <router-link
                    :to="`/mypage/news/detail/${newItem.noticeArticleSeq}`"
                >
                    <span class="thumbnail">
                        <img
                            :src="newItem.thumbnailFilePhysicalName"
                            :alt="newItem.thumbnailFileName"
                        />
                    </span>
                    <span class="info-box">
                        <strong class="title">
                            {{ newItem.title }}
                        </strong>
                        <p class="desc">
                            {{ newItem.contents }}
                        </p>
                        <span class="date">
                            {{ newItem.updateDt }}
                        </span>
                    </span>
                </router-link>
            </li>
        </ul>
    </div>
</template>
<script>
import { getMain } from '@/api/main';

export default {
    name: 'MainPage',
    data() {
        return {
            mainData: [],
        };
    },
    created() {
        this.main();
    },
    methods: {
        async main() {
            try {
                const {
                    data: { data: response },
                } = await getMain();
                this.mainData = response;
            } catch (error) {
                console.log(error);
                alert(error.response.data.msg);
            }
        },
    },
};
</script>
<style scoped>
/* main */
.main-banner {
    display: block;
}
.main-banner .thumbnail img {
    width: 100%;
    vertical-align: top;
}
.main-banner .info-box {
    position: relative;
    margin-top: 30px;
    padding-right: 228px;
}
.main-banner .info-box .title {
    display: block;
    font-size: 40px;
    line-height: 60px;
    letter-spacing: -2px;

    word-break: keep-all;
}

.main-banner .info-box .desc {
    display: block;
    margin-top: 4px;
    font-size: 14px;
    color: #555;
    line-height: 20px;
}
.main-banner .info-box [class^='btn-s'] {
    position: absolute;
    top: 0;
    right: 0;
}
.main-title {
    display: block;
    margin-top: 60px;
    line-height: 36px;
    font-size: 30px;
    font-family: 'Bebas Neue', sans-serif;
    font-weight: normal;
    letter-spacing: 0.5px;
}
.main-update-list {
    overflow: hidden;
    margin-top: 20px;
}
.main-update-list li:nth-child(1) {
    width: calc(50% - 10px);
}
.main-update-list li:nth-child(1),
.main-update-list li:nth-child(6) {
    margin-left: 0;
}
.main-update-list li:nth-child(-n + 3) {
    margin-top: 0;
}
.main-update-list li {
    float: left;
    width: calc((100% - 60px) / 4);
    margin: 20px 0 0 20px;
}
.main-update-list li a {
    display: block;
    position: relative;
}
.main-update-list li .thumbnail {
    position: relative;
    display: block;
    padding-top: 100%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-update-list li img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
.main-update-list li a .info-box {
    position: absolute;
    top: 0;
    left: 0;
}
.main-update-list li a .info-box .desc {
    display: none;
}
.main-update-list li .info-box .label {
    display: block;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    padding: 10px 12px;
    font-size: 15px;
    line-height: 17px;
    letter-spacing: 0.5px;
    color: #fff;
}
.main-update-list li a:hover .info-box {
    top: 50%;
    left: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    height: 100%;
    transform: translate(-50%, -50%);
}
.main-update-list li a:hover .info-box:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.7);
}
.main-update-list li a:hover .info-box .label {
    z-index: 2;
    position: relative;
    padding: 0 12px;
    text-align: center;
    font-size: 24px;
    line-height: 29px;
    letter-spacing: 0.6px;
}
.main-update-list li a:hover .info-box .desc {
    z-index: 2;
    position: relative;
    display: block;
    margin-top: 10px;
    padding: 0 30px;
    text-align: center;
    font-size: 14px;
    line-height: 20px;
    letter-spacing: 0;
    color: #fff;
    word-break: keep-all;
}
.board-box {
    overflow: hidden;
}
.board-box > .inner {
    float: left;
    width: calc((100% - 20px) / 2);
}
.board-box > .inner + .inner {
    margin-left: 20px;
}
.main-notice-list {
    margin-top: 20px;
    border: 1px solid #eee;
    border-radius: 2px;
}
.main-notice-list li + li {
    border-top: 1px solid #eee;
}
.main-notice-list li.noti .title {
    font-weight: bold;
    color: #333;
}
.main-notice-list li a {
    position: relative;
    display: block;
    overflow: hidden;
    padding: 0 110px 0 20px;
}
.main-notice-list li a .title {
    display: block;
    height: 50px;
    line-height: 50px;
    font-size: 12px;
    color: #555;
    text-overflow: ellipsis;
    word-wrap: normal;
    overflow: hidden;
    white-space: nowrap;
}
.main-notice-list li a .date {
    position: absolute;
    top: 50%;
    right: 20px;
    display: block;
    line-height: 50px;
    font-size: 10px;
    color: #555;
    transform: translateY(-50%);
}

.main-report-list {
    display: flex;
    margin-top: 20px;
}

.main-report-list li {
    width: calc((100% - 40px) / 3);
}
.main-report-list li + li {
    margin-left: 20px;
}
.main-report-list li a {
    position: relative;
    display: block;
}
.main-report-list .thumbnail {
    position: relative;
    display: block;
    width: 100%;
    padding-top: 100%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-report-list .thumbnail img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.main-report-list .info-box {
    position: absolute;
    bottom: 0;
    left: 0;
    display: block;
    width: 100%;
    box-sizing: border-box;
    padding: 10px 20px;
    background-color: rgba(0, 0, 0, 0.6);
}
.main-report-list .info-box .title {
    display: block;
    font-size: 12px;
    line-height: 18px;
    font-weight: bold;
    color: #fff;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-report-list .info-box .desc {
    display: block;
    margin-top: 1px;
    padding-right: 70px;
    font-size: 10px;
    line-height: 15px;
    color: #fff;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-report-list .info-box .date {
    position: absolute;
    bottom: 10px;
    right: 20px;
    display: block;
    font-size: 10px;
    line-height: 11px;
    color: #fff;
}

.main-news-list {
    display: flex;
    margin-top: 20px;
    flex-wrap: wrap;
}

.main-news-list li {
    flex: 0 0 calc(50% - 10px);
    width: calc(50% - 10px);
}
.main-news-list li + li {
    margin-left: 20px;
}
.main-news-list li a {
    position: relative;
    display: block;
}
.main-news-list .thumbnail {
    position: relative;
    display: block;
    width: 100%;
    padding-top: 47.5%;
    overflow: hidden;
    background: #f7f7f7;
}
.main-news-list .thumbnail img {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.main-news-list .info-box {
    display: block;
    box-sizing: border-box;
    padding: 20px 5px;
}
.main-news-list .info-box .title {
    display: block;
    font-size: 16px;
    line-height: 24px;
    font-weight: bold;
    color: #000;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-news-list .info-box .desc {
    display: block;
    margin-top: 8px;
    font-size: 12px;
    line-height: 18px;
    color: #555;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
}

.main-news-list .info-box .date {
    display: block;
    margin-top: 15px;
    font-size: 12px;
    line-height: 14px;
    color: #888;
}
</style>
