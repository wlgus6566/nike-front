<template>
	<aside class="sticky-container" sticky-container>
		<div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
			aside
		</div>
	</aside>
</template>
<script>
	import Sticky from 'vue-sticky-directive'

	export default {
		name: 'appAside',
		directives: {
			Sticky
		},
		mounted() {

		},
		methods: {}
	}
</script>
<style scoped></style>
