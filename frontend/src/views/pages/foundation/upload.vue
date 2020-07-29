<template>
    <div>
        <h2 class="page-title">UPLOAD</h2>
        <form action="">
            <FileSettings></FileSettings>
            <div class="btn-area">
                <button type="button" class="btn-s-white"><span>취소</span></button>
                <button type="button" class="btn-s-black"><span>저장</span></button>
            </div>
        </form>
    </div>
</template>
<script>
//import CampaignSettings from '@/components/campaign-settings/index.vue';
import FileSettings from '@/components/file-settings/index.vue';
export default {
    name: 'upload',
    components: {
        //CampaignSettings,
        FileSettings,
    },
};
</script>
<style scoped></style>
