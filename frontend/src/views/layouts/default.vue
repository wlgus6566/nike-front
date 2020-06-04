<template>
	<div id="wrap">
		<appHeader/>
		<section id="container">
			<div class="contents">
				<transition name="contents-ani" mode="out-in">
					<router-view></router-view>
				</transition>
			</div>
			<appAside/>
		</section>
		<footer>
			footer
		</footer>
	</div>
</template>
<script>
	import appHeader from './appHeader.vue'
	import appAside from './appAside.vue'

	export default {
		name: "defaultLayout",
		components: {
			appHeader,
			appAside
		},

	}
</script>
<style scoped></style>

