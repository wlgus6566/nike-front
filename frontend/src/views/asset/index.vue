<template>
	<div id="list-complete-demo">
		<button v-on:click="shuffle" type="button">Shuffle</button>
		<button v-on:click="add" type="button">Add</button>
		<button v-on:click="remove" type="button">Remove</button>
		<transition-group name="list-complete" class="list-complete" tag="ul">
			<li
					v-for="item in items"
					v-bind:key="item"
					class="list-complete-item"
			>
				{{ item }}
			</li>
		</transition-group>
	</div>
</template>
<script>
	import api from '@/api/asset/assetApi';

	export default {
		data() {
			return {
				items: [1, 2, 3, 4, 5, 6, 7, 8, 9],
				nextNum: 10
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
			shuffle: function () {
				let param = {'telNo': 'chejug@gmail.com','password':'pwd', 'name':'aa'};
				api.list(param).then(response => {
					console.log('=======param start=====');
					console.log(response);
					console.log('사용하는 데이터부분');
					console.log(response.data);
				});
				// this.items = _.shuffle(this.items);
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
