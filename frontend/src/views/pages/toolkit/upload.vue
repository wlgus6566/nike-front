<template>
    <div>
        <h2 class="page-title">UPLOAD</h2>
        <CampaignSettings></CampaignSettings>
        <FileSettings></FileSettings>
    </div>
</template>
<script>
import CampaignSettings from '@/components/campaign-settings/index.vue';
import FileSettings from '@/components/file-settings/index.vue';
export default {
    name: 'upload',
    components: {
        CampaignSettings,
        FileSettings,
    },
};
</script>
<style scoped></style>
