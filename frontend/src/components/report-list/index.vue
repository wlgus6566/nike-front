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
            v-for="(item, index) in reportListData"
            :key="index"
        >
            <router-link :to="setUrl(item)">
                <div class="thumbnail">
                    <img
                        :src="item.imageFilePhysicalName"
                        :alt="item.imageFileName"
                    />
                </div>
                <div class="info-box">
                    <strong class="title">{{ item.nickname }}</strong>
                    <p class="txt">{{ item.reportName }}</p>
                    <p class="date">
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
    name: 'report-list',
    props: ['listTypes', 'reportListData'],
    mounted() {},
    methods: {
        classBind(el) {
            const defaultClass = 'folder-item';
            const detailAuth = el.detailAuthYn === 'Y' ? ' detail-auth' : '';
            const exposure = el.exposure === 'Y' ? ' exposure' : '';
            return `${defaultClass}${detailAuth}${exposure}`;
        },
        setUrl(item) {
            return `/report/${item.reportSectionCode}/${item.reportSeq}`.toLocaleLowerCase();
        },
    },
};
</script>
