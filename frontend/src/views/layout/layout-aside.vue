<template>
	<aside>
		<div class="inner">
			aside
		</div>
	</aside>
</template>
<script>
	import sticky from '@/mixins/sticky'

	export default {
		name : 'Aside',
		mixins: [ sticky ],
		mounted() {
			sticky.init( this,
				{"test":"test"}
			);
		}
	}
</script>
<style scoped></style>
