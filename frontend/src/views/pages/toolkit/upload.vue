<template>
    <div>
        <h2 class="page-title">UPLOAD</h2>
        <CampaignSettings></CampaignSettings>
        <FileSettings></FileSettings>
    </div>
</template>
<script>
import CampaignSettings from '../../../components/upload/campaign-settings.vue';
import FileSettings from '../../../components/upload/file-settings.vue';
export default {
    name: 'upload',
    components: {
        CampaignSettings,
        FileSettings,
    },
};
</script>
<style scoped></style>
