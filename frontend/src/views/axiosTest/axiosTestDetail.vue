<template>
	<div id="list-complete-demo">
		<button @click="getData" type="button">목록</button>|
<!--		<button @click="add" type="button">Add</button>|-->

		<table>
			<tr>
				<th>ID</th><td>{{info.managerId}}</td>
			</tr>
			<tr>
				<th>Name</th><td>{{info.managerName}}</td>
			</tr>
			<tr>
				<th>registrationDt</th><td>{{info.registrationDt}}</td>
			</tr>
		</table>
	</div>
</template>
<script>
	import api from '@/api/asset/assetApi';

	export default {
		data() {
			return {
				key:0,
				info:{
					authSeq:0,
					managerId:'',
					managerName:'',
					password:''
				}
			}
		},
		methods: {
			getData () {
				let vm = this;
				api.detail(vm.key).then(response => {
					console.log('=======param start=====');
					console.log('사용하는 데이터부분');
					console.log(response.data.data);
					vm.info = response.data.data;
				});
			}
		},
		created () {
			const vm = this
			console.log('created');
			console.log(vm.$route.params);
			console.log(vm.$route.params.key);
			vm.key = vm.$route.params.key;
			// vm.$route.params.evntSn
			vm.getData();
		}
	}
</script>
