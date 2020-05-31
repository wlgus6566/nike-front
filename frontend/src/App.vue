<template>
	<div id="wrap">
		<layoutHeader/>
		<section id="container">
			<div class="contents">
				<router-view></router-view>
			</div>
		</section>
		<layoutAside/>
		<footer>
			footer
		</footer>
	</div>
</template>

<script>
	//import aSwiper from './components/swiper'
	import layoutHeader from '@/views/layout/layout-header'
	import layoutAside from '@/views/layout/layout-aside'

	export default {
		name: 'App',
		components: {
			layoutHeader,
			layoutAside
		}
	}
</script>

<style>
	@import url("assets/css/fonts.css");
	@import url("assets/css/layout.css");
	@import url("assets/css/reset.css");
</style>
