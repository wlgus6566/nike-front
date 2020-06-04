<template>
	<div id="wrap">
		<appHeader v-bind:propsdata="navActive"/>
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
		<appAside/>
		<footer>
			footer
		</footer>
	</div>
</template>

<script>
	import appHeader from '@/views/layout/appHeader.vue'
	import appAside from '@/views/layout/appAside.vue'

	export default {
		name: 'App',
		data(){
			return {
				navActive : true
			}
		},
		components: {
			appHeader,
			appAside
		},
		methods : {
			beforeEnter(){
				//console.log(this.$route.path)
			},
			enter: function () {
				// console.log(el)
				// console.log("enter")
				// Velocity(el, { opacity: 1, fontSize: '12.4em' }, { duration: 1000 });
				// Velocity(el, { fontSize: '0.1em' }, { duration: 1000 }, { complete: done });
				// Velocity(el, { fontSize: '1em' }, { complete: done })
			},
			afterEnter(){
				//console.log(this.$route.path)
			},
			enterCancelled(){
				//console.log("enterCancelled")
			},
			beforeLeave(){
				this.navActive = this.$route.path === "/";
			},
			leave(){
				//console.log("leave")
			},
			afterLeave(){
				//console.log("afterLeave")
			},
			leaveCancelled(){
				//console.log("leaveCancelled")
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
