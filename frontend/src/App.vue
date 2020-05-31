<template>
	<div id="wrap">
		<layoutHeader/>
		<section id="container">
			<div class="contents">
				<transition
						name="contents-ani"
						mode="out-in"
						v-on:before-enter="beforeEnter"
						v-on:enter="enter"
						v-on:after-enter="afterEnter"
						v-on:enter-cancelled="enterCancelled"
						v-on:before-leave="beforeLeave"
						v-on:leave="leave"
						v-on:after-leave="afterLeave"
						v-on:leave-cancelled="leaveCancelled"
						v-bind:css="true"
				>
					<router-view></router-view>
				</transition>
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
		},
		methods : {
			beforeEnter(){
				console.log("beforeEnter")
			},
			enter: function (el) {
				console.log(el)
				// console.log("enter")
				// Velocity(el, { opacity: 1, fontSize: '12.4em' }, { duration: 1000 });
				// Velocity(el, { fontSize: '0.1em' }, { duration: 1000 }, { complete: done });
				// Velocity(el, { fontSize: '1em' }, { complete: done })
			},
			afterEnter(){
				console.log("afterEnter")
			},
			enterCancelled(){
				console.log("enterCancelled")
			},
			beforeLeave(){
				console.log("beforeLeave")
			},
			leave(){
				console.log("leave")
			},
			afterLeave(){
				console.log("afterLeave")
			},
			leaveCancelled(){
				console.log("leaveCancelled")
			}
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
