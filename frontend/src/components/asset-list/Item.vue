<template>
	<ul v-bind:class="[ {'asset-item-list': listTypes[0].active }, {'asset-item-list-row': listTypes[1].active } ]">
		<li class="asset-item">
			<a href="#">
				<div class="thumbnail">
					<img  src="@/assets/images/img-asset-none@2x.png" alt="">
				</div>
				<div class="info-box">
					<strong class="title">타이틀입니다</strong>
					<p class="txt">설명입니다</p>
					<p class="date">2020.00.00 ~ 2020.00.00</p>
				</div>
				<div class="view-area">
					<span class="view">10,000</span>
				</div>
			</a>
		</li>
	</ul>
</template>
<script>
	export default {
		name: "Item.vue",
		props:["listTypes"],
	}
</script>
<style scoped></style>
