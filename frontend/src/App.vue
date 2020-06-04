<template>
	<div id="app">
		<component :is="layout" />
	</div>
</template>

<script>
	import * as layouts from './views/layouts';

	export default {
		name: 'App',
		data(){
			return {
				navActive : true
			}
		},
		computed: {
			layout() {
				return `${this.$route.meta.layout || 'default'}-layout`
			}
		},
		components: {
			...layouts,
		},
		mounted(){
			console.log(this.layout)
		},
		methods : {

		}
	}
</script>

<style>
	@import url("assets/css/fonts.css");
	@import url("assets/css/layout.css");
	@import url("assets/css/reset.css");

	.contents-ani-enter-active {
		transition: all .3s ease-in-out;
	}
	.contents-ani-leave-active {
		transition: all .3s ease-in-out .3ms;

	}
	.contents-ani-enter,
	.contents-ani-leave-to {
		transform: translateY(10px);
		opacity: 0;
	}


</style>
