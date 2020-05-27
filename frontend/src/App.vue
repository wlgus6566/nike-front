<template>
	<div id="wrap">
		<header>
			<h1>
				<router-link to="/" exact>MAIN</router-link>
			</h1>
			<nav>
				<ul>
					<li>
						<router-link to="/" exact>MAIN</router-link>
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
								<a href="/asset/all">ALL</a>
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
		</header>
		<section id="container">
			<div class="contents">
				<router-view></router-view>
			</div>
		</section>
		<aside>
			aside
		</aside>
		<footer>
			footer
		</footer>
	</div>
</template>
<script>

</script>

<style>
	@import url("assets/css/fonts.css");
	@import url("assets/css/layout.css");
	@import url("assets/css/reset.css");
</style>
