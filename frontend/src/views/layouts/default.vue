<template>
	<div id="wrap">
		<appHeader/>
		<section id="container">
			<div class="contents">
				<transition name="contents-ani" mode="out-in">
					<router-view></router-view>
				</transition>
			</div>
			<transition mode="out-in" name="aside-ani">
				<component :is="aside"/>
			</transition>
		</section>
		<footer>
			footer
		</footer>
	</div>
</template>
<script>
	import appHeader from '../../components/app-header';
	import * as appAside from '../../components/app-aside/index.js';

	export default {
		name: "defaultLayout",
		data(){
			return {
				//aside : 'default-aside'
			}
		},
		computed: {
			aside() {
				return `${this.$route.meta.aside || 'default'}-aside`
			}
		},
		components: {
			appHeader,
			...appAside
		},

	}
</script>
<style scoped>
	.contents-ani-enter-active {
		transition:all .3s ease-in-out;
	}
	.contents-ani-leave-active {
		transition:all .3s ease-in-out .3ms;

	}
	.contents-ani-enter,
	.contents-ani-leave-to {
		opacity:0;
		transform:translateY(10px);
	}

	.aside-ani-enter-active {
		transition:all .3s ease-in-out;
	}
	.aside-ani-leave-active {
		transition:all .3s ease-in-out .3ms;

	}
	.aside-ani-enter,
	.aside-ani-leave-to {
		opacity:0;
		transform:translateY(10px);
	}
</style>

