<template>
	<header>
		<div class="inner">
			<h1>
				<router-link to="/" exact>MAIN</router-link>
			</h1>
			<nav>
				<ul>
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
				</ul>
			</nav>
		</div>
	</header>
</template>
<script>
	import sticky from '../../mixins/sticky'

	export default {
		name : 'Aside',
		mixins: [ sticky ]
	}
</script>
<style scoped></style>
