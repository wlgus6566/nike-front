<template>
	<div id="list-complete-demo">
		<button v-on:click="clickList" type="button">목록조회</button>|
		<button v-on:click="add" type="button">등록 페이지22</button>

		<br>
		데이터 내용 {{ddd || formattedNumber('', '') }}<br>
		<ul>
			<router-link
				v-for="item in content"
				:key="item.managerSeq"
				:to="{path:'/sampleBoard/'+item.managerSeq, params:{ddd:'1'}}"
			>
				<li>{{ item.managerName }}_{{ item.managerSeq }}_{{item.registrationDt}}</li>
			</router-link>

		</ul>
	</div>
</template>
<script>
	import api from '@/api/asset/assetApi';

	export default {
		data() {
			return {
				items: [1, 2, 3, 4, 5, 6, 7, 8, 9],
				nextNum: 10,
				content: [{managerName:'test', managerSeq:1}],
				searchParam:{userName:''},
				ddd: 1591174782
			}
		},
		methods: {
			randomIndex: function () {
				return Math.floor(Math.random() * this.items.length)
			},
			add: function () {
				this.items.splice(this.randomIndex(), 0, this.nextNum++)
			},
			remove: function () {
				this.items.splice(this.randomIndex(), 1)
			},
			clickList: function () {
				// let param = {};
				console.log('=======param start=====');
				let vm = this;
				api.list(vm.searchParam).then(response => {
					console.log(response.data.data);
					vm.content = response.data.data.content
				});
			}
		}
	}
</script>
<style scoped>
	.list-complete{
		display:flex;
		flex-wrap:wrap;
	}
	.list-complete-item{
		flex:1 1 10%;
		background:red;
		transition:all 1s;
		display:block;
		margin:10px;
	}
	.list-complete-enter,
	.list-complete-leave-to{
		opacity:0;
		transform:translateY(30px);
	}
	.list-complete-leave-active{
		position:absolute;
	}
</style>
