<template>
    <div class="history-box">
        <div class="item-box">
            <template v-if="historyFolderData">
                <ul class="item-list" v-if="historyFolderData.length">
                    <li
                        class="item"
                        v-for="(item, index) in historyFolderData"
                        :key="index"
                    >
                        <router-link :to="setUrl(item)">
                            <div class="thumbnail">
                                <img :src="item.imageFilePhysicalName" alt="" />
                            </div>
                            <div class="info-box">
                                <p class="title">{{ item.folderName }}</p>
                                <span class="date">
                                    {{ item.campaignBeginDt }}
                                    ~
                                    {{ item.campaignEndDt }}
                                </span>
                            </div>
                        </router-link>
                    </li>
                </ul>
                <div class="no-data" v-else>
                    <span class="txt">최근 본 폴더가 없습니다</span>
                </div>
            </template>
        </div>
    </div>
</template>
<script>
import { historyFolderViewList } from '@/api/mypage';

export default {
    name: 'ContentAsset',
    data() {
        return {
            loadingData: false,
            historyFolderData: [],
            size: 3,
            page: 0,
            sectionCode: 'ASSET',
        };
    },
    created() {
        this.historyViewDataList();
    },
    methods: {
        //최근 본 폴더 리스트
        async historyViewDataList() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await historyFolderViewList({
                    page: this.page,
                    size: this.size,
                    typeCd: this.sectionCode,
                });
                this.historyFolderData = response.content;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
        setUrl(item) {
            return `/${item.topMenuCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
        },
    },
};
</script>
