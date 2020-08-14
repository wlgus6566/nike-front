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
    </div>
</template>
<script>
import {
    getAgencyContact
} from '@/api/agency';

export default {
    name: 'agency',
    data() {
        return {
            agencyData: {}
        }
    },
    mounted() {
        this.getAgencyData();
    },
    methods: {
        async getAgencyData() {
            try {
                const {
                    data: {data: response},
                } = await getAgencyContact({});
                this.agencyData = response;
            } catch (error) {
                console.log(error);
            }
        }
    }
};
</script>
<style scoped></style>
