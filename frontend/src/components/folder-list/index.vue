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
            <router-link :to="setUrl(item)" @click.native="alertMsg(item)">
                <div class="thumbnail">
                    <span class="exposure" v-if="item.exposureYn === 'N'"
                        ><i></i>작성중</span
                    >
                    <span
                        class="auth"
                        v-if="
                            item.exposureYn === 'Y' && item.detailAuthYn === 'N'
                        "
                        ><i></i>권한 없음</span
                    >
                    <img :src="item.imageFilePhysicalName" alt="" />
                </div>
                <div class="info-box">
                    <strong class="title">{{ item.folderName }}</strong>
                    <p class="txt">{{ item.folderContents }}</p>
                    <p
                        v-if="item.campaignPeriodSectionCode === 'EVERY'"
                        class="date"
                    >
                        365
                    </p>
                    <p v-else class="date">
                        {{ $moment(item.campaignBeginDt).format('YYYY.MM.DD') }}
                        ~
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
    mounted() {},
    methods: {
        alertMsg(item) {
            if (item.detailAuthYn === 'N') {
                alert('접근 권한이 없습니다.');
            }
        },
        classBind(el) {
            const defaultClass = 'folder-item';
            const detailAuth = el.detailAuthYn === 'N' ? ' detail-auth' : '';
            const exposure = el.exposure === 'Y' ? ' exposure' : '';
            return `${defaultClass}${detailAuth}${exposure}`;
        },
        setUrl(item) {
            if (item.detailAuthYn === 'N') {
                return `${this.$route.fullPath}`;
            } else {
                return `/${item.topMenuCode}/${item.menuCode}/${item.contentsSeq}`.toLocaleLowerCase();
            }
        },
    },
};
</script>
