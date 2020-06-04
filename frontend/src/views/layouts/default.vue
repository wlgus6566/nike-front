<template>
	<appHeader/>
	<section id="container">
		<div class="contents">
			<transition
					name="contents-ani"
					mode="out-in">
				<router-view></router-view>
			</transition>
		</div>
		<appAside/>
	</section>
	<footer>
		footer
	</footer>
</template>
<script>
	import appHeader from './appHeader.vue'
	import appAside from './appAside.vue'

	export default {
		name: "Default Layout",
		components: {
			appHeader,
			appAside
		},

	}
</script>
<style scoped></style>

