<template>
    <div>
        <h2 class="page-title">AGENCY CONTACT</h2>
        <template v-if="agencyData">
            <ul class="contact-list" v-if="agencyData.length">
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
                            $emit(
                                'detailAgencyManagement',
                                agencyData.agencySeq
                            )
                        "
                    >
                        <span>수정</span>
                    </button>
                </li>
            </ul>
            <!-- todo 에이젼시 nodata 요청 -->
            <NoData v-else>
                <i class="icon-search"></i>
                <p class="desc">AGENCY 목록이 없습니다.</p>
            </NoData>
        </template>
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
    import NoData from '@/components/no-data';

    export default {
    name: 'index',
    data() {
        return {};
    },
    components: {
        NoData,
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
