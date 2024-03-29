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
                console.error(error);
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
                    if (response.data.existMsg) {
                        alert(response.data.msg);
                    }
                    if (response.data.success) {
                        this.visible.agencyManagement = false;
                        await this.getAgencyData();
                    }
                } catch (error) {
                    console.error(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
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
                this.agencySeq = agencySeq.toString();
            } catch (error) {
                console.error(error);
            }
        },
        async delAgencyManagement(agencySeq) {
            this.visible.agencyManagement = true;
            let delAlert = confirm('선택한 AGENCY정보를 삭제하시겠습니까?');
            if (delAlert) {
                try {
                    const response = await delAgencyContact(agencySeq);
                    //console.log(response);
                    if (response.data.existMsg) {
                        alert(response.data.msg);
                    }
                    this.visible.agencyManagement = false;
                    await this.getAgencyData();
                } catch (error) {
                    console.error(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            }
        },
        async modifyAgencyManagement(agencySeq) {
            try {
                const response = await putAgencyContact(agencySeq, {
                    agencyDescription: this.addAgencyData.agencyDescription,
                    agencyName: this.addAgencyData.agencyName,
                    email: this.addAgencyData.email,
                    telephoneNumber: this.addAgencyData.telephoneNumber,
                    agencySeq: agencySeq,
                });

                if (response.data.existMsg) {
                    alert(response.data.msg);
                }
                if (response.data.success) {
                    this.addAgencyData = response;
                    this.visible.agencyManagement = false;
                    await this.getAgencyData();
                }
            } catch (error) {
                console.error(error);
                if (error.data.existMsg) {
                    alert(error.data.msg);
                }
            }
        },
    },
};
</script>
<style scoped>
.list-loading {
    position: relative;
    padding-top: 70%;
}
::v-deep .list-loading .lottie {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
