<template>
    <div style="min-width: 1200px; padding: 20px;">
        <h1 style="font-size: 20px; margin-bottom: 20px;">페이지 리스트</h1>
        <div class="thead">
            <ul>
                <li class="num">no</li>
                <li class="title">구분</li>
                <li class="depth1">1Depth</li>
                <li class="depth2">2Depth</li>
                <li class="depth3">3Depth</li>
                <li class="view-name">화면명</li>
                <li class="name">담당자</li>
                <li class="link">파일명</li>
                <li class="com">완료일</li>
                <li class="modi">수정일</li>
                <li class="comment">비고</li>
            </ul>
        </div>
        <ul class="tbody">
            <li v-for="(item, index) in pageLists" :key="index">
                <span class="num"></span>
                <span class="title">{{ item.title }}</span>
                <span class="depth1"></span>
                <span class="depth2"></span>
                <span class="depth3"></span>
                <span class="view-name"></span>
                <span class="name">지은유</span>
                <span class="link">
                    <router-link :to="item.fileName.link">{{ item.fileName.title }}</router-link>
                </span>
                <span class="com"></span>
                <span class="modi"></span>
                <span class="comment">캘린더제외</span>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'PageList',
    data() {
        return {
            pageLists: [
                {
                    title: 'HOME',
                    depth: {
                        title: '',
                    },
                    name: '지은유',
                    fileName: {
                        title: 'mainPage.vue',
                        link: 'main',
                    },
                    comDate: '2020',
                    motiDate: '2020',
                    comment: '없다',
                },
                {
                    title: 'GNB',
                    depth: {
                        title: 'ASSET',
                    },
                    name: '지은유',
                    fileName: {
                        title: 'NIKE_P_ASSET_01.vue',
                        link: 'NIKE_P_ASSET_01',
                    },
                    comDate: '2020',
                    motiDate: '2020',
                    comment: '없다',
                },
            ],
        };
    },
};
</script>
<style scoped>
.thead ul {
    width: 100%;
    display: flex;
}
.thead ul li {
    flex-shrink: 1;
    flex-grow: 1;
    height: 30px;
    padding: 5px 10px;
    box-sizing: border-box;
    border: 1px solid #ddd;
    vertical-align: middle;
    text-align: center;
    background: #f7f7f7;
}

.tbody li {
    width: 100%;
    display: flex;
}
.tbody li span {
    flex-shrink: 1;
    flex-grow: 1;
    height: 30px;
    padding: 5px 10px;
    box-sizing: border-box;
    border: 1px solid #ddd;
    vertical-align: middle;
    text-align: left;
}
.tbody .link {
    color: #00a1e0;
}

.tbody li {
    counter-increment: num;
}
.tbody .num {
    text-align: center;
}
.tbody .num:before {
    content: counter(num);
}

.num {
    flex-basis: 3%;
}
.title {
    flex-basis: 9%;
}
[class^='depth'] {
    flex-basis: 9%;
}
.view-name {
    flex-basis: 9%;
}
.name {
    flex-basis: 9%;
}
.link {
    flex-basis: 9%;
}
.com {
    flex-basis: 9%;
}
.modi {
    flex-basis: 9%;
}
.comment {
    flex-basis: 14%;
}
</style>
