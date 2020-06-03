<template>
	<aside>
		<div class="inner">
			aside
		</div>
	</aside>
</template>
<script>
	import VueStickyDirective from "@renatodeleao/vue-sticky-directive";

	console.log(VueStickyDirective)
	export default {
		name: 'A',
		directives: {
			VueStickyDirective
		},
		data() {
			return {
				"isActive": false,
			}
		},
		mounted() {
			this.navAni();
		},
		methods: {
			navAni: function () {
				this.isActive = !this.isActive;
			}
		}
	}
</script>
<style scoped></style>
