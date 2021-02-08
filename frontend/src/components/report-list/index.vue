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
            <router-link :to="setUrl(item)" @click.native="alertMsg(item)">
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
                        {{ item.updateDt }}
                        <!--  {{ $moment(item.updateDt).format('YYYY.MM.DD') }}-->
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
import { authCheck } from '@/utils/authCheck';

export default {
    name: 'report-list',
    props: ['listTypes', 'reportListData'],
    mixins: [authCheck],
    methods: {
        alertMsg() {
            if (!this.folderAuthCheck('VIEW')) {
                alert('접근 권한이 없습니다.');
            }
        },
        classBind(el) {
            const defaultClass = 'folder-item';
            const detailAuth = el.detailAuthYn === 'Y' ? ' detail-auth' : '';
            const exposure = el.exposure === 'Y' ? ' exposure' : '';
            return `${defaultClass}${detailAuth}${exposure}`;
        },
        setUrl(item) {
            if (this.folderAuthCheck('VIEW')) {
                return `/report/detail/${item.reportSeq}`.toLocaleLowerCase();
            } else {
                return `${this.$route.fullPath}`;
            }
        },
    },
};
</script>
