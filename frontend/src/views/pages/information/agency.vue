<template>
    <div>
        <AgencyContact
            v-bind:agencyData="agencyData"
            v-bind:loadingData="loadingData"
            v-on:showAgencyManagement="showAgencyManagement"
            v-on:detailAgencyManagement="detailAgencyManagement"
        />
        <agencyManagement
            :visible.sync="visible.agencyManagement"
            :addAgencyData="addAgencyData"
            :agencySeq="agencySeq"
            @addAgencyManagement="addAgencyManagement"
            @delAgencyManagement="delAgencyManagement"
            @modifyAgencyManagement="modifyAgencyManagement"
            @cancelFn="cancelFn"
        />
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
    </div>
</template>
<script>
import {
    delAgencyContact,
    getAgencyContact,
    getDetailAgencyContact,
    postAgencyContact,
    putAgencyContact,
} from '@/api/agency';
import agencyManagement from '@/views/pages/information/agency-management';
import Loading from '@/components/loading';

export default {
    name: 'agency',
    data() {
        return {
            agencyData: null,
            addAgencyData: {
                agencyDescription: '',
                agencyName: '',
                email: '',
                telephoneNumber: '',
            },
            visible: {
                agencyManagement: false,
            },
            agencySeq: '',
            loadingData: false,
        };
    },
    components: {
        AgencyContact: () => import('@/components/agency-contact/index'),
        agencyManagement,
        Loading,
    },
    mounted() {
        this.getAgencyData();
    },
    computed: {},
    methods: {
        cancelFn() {
            this.visible.agencyManagement = false;
        },
        showAgencyManagement() {
            this.addAgencyData = {
                agencyDescription: '',
                agencyName: '',
                email: '',
                telephoneNumber: '',
            };
            this.agencySeq = '';
            this.visible.agencyManagement = true;
        },
        async getAgencyData() {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getAgencyContact({});
                this.agencyData = response;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        async addAgencyManagement() {
            const addData = {
                agencyDescription: this.addAgencyData.agencyDescription,
                agencyName: this.addAgencyData.agencyName,
                email: this.addAgencyData.email,
                telephoneNumber: this.addAgencyData.telephoneNumber,
            };
            let addAlert = confirm('AGENCY를 등록하시겠습니까?');
            if (addAlert) {
                try {
                    const response = await postAgencyContact(addData);

                    if (response.data.success) {
                        this.visible.agencyManagement = false;
                        await this.getAgencyData();
                    } else {
                        alert(response.data.msg);
                    }
                } catch (error) {
                    console.log(error.response);
                }
            }
        },
        async detailAgencyManagement(agencySeq) {
            this.visible.agencyManagement = true;
            try {
                const {
                    data: { data: response },
                } = await getDetailAgencyContact(agencySeq, {});
                this.addAgencyData = response;
                this.agencySeq = agencySeq;
            } catch (error) {
                console.log(error);
            }
        },
        async delAgencyManagement(agencySeq) {
            this.visible.agencyManagement = true;
            let delAlert = confirm('선택한 AGENCY정보를 삭제하시겠습니까?');
            if (delAlert) {
                try {
                    const {
                        data: { data: response },
                    } = await delAgencyContact(agencySeq);
                    this.visible.agencyManagement = false;
                    await this.getAgencyData();
                } catch (error) {
                    console.log(error);
                }
            }
        },
        async modifyAgencyManagement(agencySeq) {
            try {
                const {
                    data: { data: response },
                } = await putAgencyContact(agencySeq, {
                    agencyDescription: this.addAgencyData.agencyDescription,
                    agencyName: this.addAgencyData.agencyName,
                    email: this.addAgencyData.email,
                    telephoneNumber: this.addAgencyData.telephoneNumber,
                    agencySeq: agencySeq,
                });
                this.addAgencyData = response;
                this.visible.agencyManagement = false;
                await this.getAgencyData();
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
