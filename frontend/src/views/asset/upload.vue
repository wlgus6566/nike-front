<template>
	<div>
		<h2 class="page-title">UPLOAD</h2>
		<CampaignSettings></CampaignSettings>
		<FileSettings></FileSettings>
	</div>
</template>
<script>
	import CampaignSettings from '../../components/upload/CampaignSettings.vue'
	import FileSettings from '../../components/upload/FileSettings.vue'
	export default {
		name: "upload.vue",
		components : {
			CampaignSettings,
			FileSettings
		}
	}
</script>
<style scoped></style>
