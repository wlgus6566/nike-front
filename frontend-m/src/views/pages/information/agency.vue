<template>
    <div>
        <div class="inner bg" v-for="item in agencyData">
            <div class="info-agency-list">
                <strong class="title">{{ item.agencyName }}</strong>
                <p class="text">{{ item.agencyDescription }}</p>
                <div class="info-box">
                    <a :href="'tel:${item.telephoneNumber}'" class="info-txt tel">{{ item.telephoneNumber }}</a>
                    <a :href="'mailto:${item.email}'" class="info-txt mail">{{ item.email }}</a>
                </div>
            </div>
        </div>
        <Loading v-if="loadingData" />
    </div>
</template>
<script>
import {
    getAgencyContact
} from '@/api/agency';
import Loading from '@/components/loading';

export default {
    name: 'agency',
    data() {
        return {
            agencyData: {},
            loadingData: false
        }
    },
    components: {
        Loading
    },
    mounted() {
        this.getAgencyData();
    },
    methods: {
        async getAgencyData() {
            this.loadingData = true;
            try {
                const {
                    data: {data: response},
                } = await getAgencyContact({});
                this.agencyData = response;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        }
    }
};
</script>
<style scoped></style>
