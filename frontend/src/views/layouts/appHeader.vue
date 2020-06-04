<template>
	<header v-bind:class="{ active: navActive }" sticky-container>
		<div class="inner" sticky-offset="{top:0, bottom:0}" sticky-side="both" sticky-z-index="20" v-sticky>
			<h1>
				<router-link exact to="/">
					<img alt="" src="@/assets/images/logo-nike.svg">
				</router-link>
			</h1>
			<nav>
				<ul>
					<router-link exact tag="li" to="/test">
						<a>TEST</a>
					</router-link>
					<router-link exact tag="li" to="/">
						<a>MAIN</a>
					</router-link>
					<router-link exact tag="li" to="/asset">
						<a>ASSET</a>
					</router-link>
					<router-link exact tag="li" to="/toolkit">
						<a>TOOLKIT</a>
					</router-link>
					<li>
						<router-link exact to="/foundation">FOUNDATION</router-link>
					</li>
					<li>
						<router-link exact to="/order">ORDER</router-link>
					</li>
					<li>
						<router-link exact to="/report">REPORT</router-link>
					</li>
					<li>
						<router-link exact to="/information">INFORMATION</router-link>
					</li>
					<li>
						<router-link exact to="/management">MANAGEMENT</router-link>
					</li>
				</ul>
				<!--<ul>
					<li>
						<router-link to="/test" exact>TEST</router-link>
					</li>
					<li>
						<router-link to="/main" exact>MAIN</router-link>
						<ul>
							<li>
								<a href="#"></a>
							</li>
						</ul>
					</li>
					<li>
						<router-link to="/asset">ASSET</router-link>
						<ul>
							<li>
								<a href="#">ALL</a>
							</li>
							<li>
								<a href="#">SP</a>
							</li>
							<li>
								<a href="#">SU</a>
							</li>
							<li>
								<a href="#">FA</a>
							</li>
							<li>
								<a href="#">HO</a>
							</li>
						</ul>
					</li>
					<li>
						<router-link to="/toolkit" exact>TOOLKIT</router-link>
						<ul>
							<li>
								<a href="#">ALL</a>
							</li>
							<li>
								<a href="#">VMS</a>
							</li>
							<li>
								<a href="#">EKIN</a>
							</li>
							<li>
								<a href="#">SOCIAL</a>
							</li>
							<li>
								<a href="#">RB</a>
							</li>
						</ul>
					</li>
					<li>
						<router-link to="/foundation">FOUNDATION</router-link>
						<ul>
							<li>
								<a href="#">ALL</a>
							</li>
							<li>
								<a href="#">VMS</a>
							</li>
							<li>
								<a href="#">EKIN</a>
							</li>
							<li>
								<a href="#">RB</a>
							</li>
						</ul>
					</li>
					<li>
						<router-link to="/order" exact>ORDER</router-link>
						<ul>
							<li>
								<a href="#">부자재</a>
							</li>
							<li>
								<a href="#">NIKE BY YOU</a>
							</li>
							<li>
								<a href="#">CUSTOM23</a>
							</li>
							<li>
								<a href="#">MNQ</a>
							</li>
						</ul>
					</li>
					<li>
						<router-link to="/report" exact>REPORT</router-link>
					</li>
					<li>
						<router-link to="/information" exact>INFORMATION</router-link>
					</li>
					<li>
						<router-link to="/management" exact>MANAGEMENT</router-link>
					</li>
				</ul>-->
				<div class="bg" v-on:mouseenter="navIn">
					<i></i>
				</div>
			</nav>
		</div>
	</header>
</template>
<script>
	import Sticky from 'vue-sticky-directive'
	import Velocity from 'velocity-animate'

	export default {
		name: 'appHeader',
		directives: {
			Sticky
		},
		props :['navActive'],
		watch : {
			navActive (){
				this.navIn();
			}
		},
		mounted() {
			//this.navAni();
		},
		methods: {
			navIn(){
				console.log(document.querySelector("#wrap"))
				Velocity(this.$el, {opacity: 0.5});
			}
		}
	}
</script>
<style scoped>
	header {}
	header h1 {transform-origin:0 0;}
	header nav > ul {}




	/*header.active {background:red;}*/
	/*header.active h1 {transform:translate(-22px, -30px) scale(0.25)}*/
	/*header.active nav > ul {}*/
	nav ul ul {display:none;}

	/*@keyframes mymove {*/
	/*	from {transform:translateX(0%)}*/
	/*	to {transform:translateX(-100%)}*/
	/*}*/
</style>
