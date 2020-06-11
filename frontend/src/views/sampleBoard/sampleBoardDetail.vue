<template>
	<div id="list-complete-demo">
		<button @click="goBack" type="button">목록</button>|
		<button v-if="mode ==='VIEW'" @click="changeMode" type="button">수정|</button>
		<button v-if="mode ==='EDIT'" @click="save" type="button">저장|</button>
		<button @click="remove" type="button">삭제|</button>

		<table v-if="mode ==='VIEW'">
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

		<table v-if="mode==='EDIT'">
			<tr>
				<th>ID</th>
				<td>
					{{info.managerId}}
				</td>
			</tr>
			<tr>
				<th>Name</th>
				<td>
					<input v-model="info.managerName">
				</td>
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
				mode:'VIEW', // VIEW,EDIT
				info:{
					key:0,
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
					console.log('사용하는 데이터부분');
					console.log(response.data.data);
					vm.info = response.data.data;
				});
			},
			changeMode(){
				let vm =this;
				if('VIEW'===vm.mode){
					vm.mode='EDIT';
				}else{
					vm.mode='VIEW';
				}
			},
			goBack(){
				let vm =this;
				vm.$router.back();
			},
			remove(){
				let vm =this;
				if(confirm('삭제하시겠습니까?')){
					api.delete(vm.key).then(response => {
						console.log('삭제완료');
						console.log(response);
						vm.$router.push({ path:'/sampleBoard/list'})
					});
				}
			},
			save(){
				let vm =this;
				console.log(vm.info);
				//오류 확인....
				vm.info.authSeq = 1;
				if(vm.info.key ===0 ){
					if(confirm('저장하시겠습니까?')){
						api.post(vm.info).then(response => {
							console.log('등록');
							console.log(response);
							vm.info = response.data.data;
						});
					}
				}else{
					if(confirm('수정하시겠습니까?')){
						api.update(vm.key, vm.info).then(response => {
							console.log('수정');
							console.log(response);
							vm.info = response.data.data;
							alert('수정완료');
							vm.mode='VIEW';
						});
					}
				}
			}
		},
		created () {
			const vm = this
			vm.key = vm.$route.params.key;

			if(vm.key !== 0){
				vm.getData();
			}else{
				vm.mode='EDIT';
			}
		}
	}
</script>
