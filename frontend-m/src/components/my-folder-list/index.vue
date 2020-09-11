<template>
    <transition-group name="folder-list" tag="ul" class="folder-list-row">
        <li
            class="folder-list-item"
            v-for="(item, index) in folderListData"
            :key="index"
        >
            <router-link :to="setUrl(item)">
                <div class="thumbnail">
                    <img :src="item.imageFilePhysicalName" alt="" />
                </div>
                <div class="info-box" v-if="item.typeCd === 'REPORT_MANAGE'">
                    <strong class="title">{{ item.nickname }}</strong>
                    <p class="date">{{ item.folderName }}</p>
                    <ul class="location">
                        <li>REPORT</li>
                        <li>{{ item.menuCode }}</li>
                    </ul>
                </div>
                <div class="info-box" v-else>
                    <strong class="title">{{ item.folderName }}</strong>
                    <p class="date" v-if="item.typeCd === 'REPORT_MANAGE'">
                        {{ $moment(item.updateDt).format('YYYY.MM.DD') }}
                    </p>
                    <p class="date" v-else>
                        {{ $moment(item.campaignBeginDt).format('YYYY.MM.DD') }}
                        ~ {{ $moment(item.campaignEndDt).format('YYYY.MM.DD') }}
                    </p>
                    <ul class="location">
                        <li>
                            {{ item.topMenuCode }}
                        </li>
                        <li>{{ item.menuCode }}</li>
                    </ul>
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
    name: 'my-folder-list',
    props: ['folderListData', 'mypageYn'],
    mounted() {},
    methods: {
        setUrl(item) {
            if (item.topMenuCode) {
                return `/${item.topMenuCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
            } else {
                return (
                    `/report/detail/${item.folderSeq}`.toLocaleLowerCase() +
                    '?mypageYn=' +
                    this.mypageYn
                );
            }
        },
    },
};
</script>
