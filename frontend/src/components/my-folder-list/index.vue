<template>
    <transition-group
        name="folder-list"
        tag="ul"
        class="mt0"
        v-bind:class="[
            { 'folder-list': listTypes[0].active },
            { 'folder-list-row': listTypes[1].active },
        ]"
    >
        <li
            :class="classBind(item)"
            v-for="(item, index) in folderListData"
            :key="index"
        >
            <router-link :to="setUrl(item)">
                <div class="thumbnail">
                    <img :src="item.imageFilePhysicalName" alt="" />
                </div>
                <div class="info-box">
                    <div v-if="item.folderContents">
                        <strong class="title">{{ item.folderName }}</strong>
                        <p class="txt">
                            {{ item.folderContents }}
                        </p>
                    </div>
                    <div v-else>
                        <strong class="title">
                            {{ item.nickname }}
                        </strong>
                        <p class="txt">
                            {{ item.folderName }}
                        </p>
                    </div>
                    <ul class="location" v-if="item.topMenuCode">
                        <li>{{ item.topMenuCode }}</li>
                        <li>{{ item.menuCode }}</li>
                    </ul>
                    <ul class="location" v-else>
                        <li>REPORT</li>
                        <li>{{ item.menuCode }}</li>
                    </ul>
                    <p
                        class="date"
                        v-if="item.campaignPeriodSectionCode === 'SELECT'"
                    >
                        {{ item.campaignBeginDt }} ~ {{ item.campaignEndDt }}
                    </p>
                    <p
                        class="date"
                        v-else-if="item.campaignPeriodSectionCode === 'EVERY'"
                    >
                        365
                    </p>
                    <p class="date" v-else>
                        {{ item.updateDt }}
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
    name: 'my-folder-list',
    props: ['listTypes', 'folderListData'],
    mounted() {},
    methods: {
        classBind(el) {
            const defaultClass = 'folder-item';
            const detailAuth = el.detailAuthYn === 'Y' ? ' detail-auth' : '';
            const exposure = el.exposure === 'Y' ? ' exposure' : '';
            return `${defaultClass}${detailAuth}${exposure}`;
        },
        setUrl(item) {
            //console.log(item.topMenuCode);
            if (item.topMenuCode) {
                return `/${item.topMenuCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
            } else {
                return `/report/detail/${item.folderSeq}`.toLocaleLowerCase();
            }
        },
    },
};
</script>
