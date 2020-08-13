<template>
    <transition-group
        name="folder-list"
        tag="ul"
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
                    <strong class="title">{{ item.folderName }}</strong>
                    <p class="txt" v-if="item.folderContents">
                        {{ item.folderContents }}
                    </p>
                    <p class="txt" v-else>보고서명</p>
                    <ul class="location" v-if="item.topMenuCode">
                        <li>{{ item.topMenuCode }}</li>
                        <li>{{ item.menuCode }}</li>
                    </ul>
                    <p class="date" v-if="item.topMenuCode">
                        {{ $moment(item.campaignBeginDt).format('YYYY.MM.DD') }}
                        ~
                        {{ $moment(item.campaignEndDt).format('YYYY.MM.DD') }}
                    </p>
                    <p class="date" v-else>
                        {{ $moment(item.updateDt).format('YYYY.MM.DD') }}
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
            if (item.topMenuCode) {
                return `/${item.topMenuCode}/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
            } else {
                return `/report/${item.menuCode}/${item.folderSeq}`.toLocaleLowerCase();
            }
        },
    },
};
</script>
