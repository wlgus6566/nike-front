<template>
    <div>
        <h2 class="page-title">UPLOAD</h2>
        <FileSettings></FileSettings>
    </div>
</template>
<script>
import FileSettings from '@/components/file-settings/index.vue';
export default {
    name: 'upload',
    components: {
        FileSettings,
    },
};
</script>
<style scoped></style>
