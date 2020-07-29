<template>
    <div>
        <h2 class="page-title">AGENCY CONTACT</h2>
        <ul class="contact-list">
            <li
                class="contact-item"
                v-for="(agencyData, index) in agencyData"
                :key="index"
            >
                <strong class="store-name">
                    {{ agencyData.agencyName }}
                </strong>
                <p class="store-desc">
                    {{ agencyData.agencyDescription }}
                </p>
                <div class="info-box">
                    <a
                        :href="'tel:' + agencyData.telephoneNumber"
                        class="info-txt tel"
                    >
                        {{ agencyData.telephoneNumber }}
                    </a>
                    <a
                        :href="'mailto:' + agencyData.email"
                        class="info-txt mail"
                    >
                        {{ agencyData.email }}
                    </a>
                </div>
                <button
                    type="button"
                    class="modi"
                    v-on:click="
                        $emit('detailAgencyManagement', agencyData.agencySeq)
                    "
                >
                    <span>수정</span>
                </button>
            </li>
        </ul>
        <!-- todo 에이젼시 nodata 요청 -->
        <div class="no-data" v-if="agencyData.length === 0">
            <i class="icon-search"></i>
            <p class="desc">AGENCY 목록이 없습니다.</p>
        </div>
        <div class="btn-tbl-box">
            <div class="right">
                <button
                    type="button"
                    class="btn-form-gray"
                    v-on:click="$emit('showAgencyManagement')"
                >
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'index',
    data() {
        return {};
    },
    props: ['agencyData', 'loadingData'],
    computed: {
        check() {},
    },
    methods: {},
    created() {},
};
</script>
<style scoped></style>
