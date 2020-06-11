<template>
	<div id="list-complete-demo">
		<button v-on:click="clickList" type="button">목록조회</button>

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
		<div>

			<paginate
				:page-count="pageInfo.totalCount"
				:page-range="3"
				:margin-pages="3"
				:click-handler="clickCallback"
				:prev-text="'이전'"
				:next-text="'다음'"
				:container-class="'pagination'"
				:page-class="'page-item'"
			>

			</paginate>

		</div>
	</div>
</template>
<script>
	import api from '@/api/asset/assetApi';

	import Paginate from 'vuejs-paginate';

	export default {
		data() {
			return {
				items: [1, 2, 3, 4, 5, 6, 7, 8, 9],
				nextNum: 10,
				content: null,
				searchParam:{userName:'', page:0},
				ddd: 1591174782,
				page:10,
				pageInfo: {
					page: 1,
					perPageNum: 20,
					totalCount: 0
				}
			}
		},
		components:{
			Paginate
		},
		methods: {
			getList: function() {
				console.log('=======param start=====');
				console.log(this.searchParam);
				api.list(this.searchParam).then(response => {
					console.log(response.data.data);
					let info = response.data.data;
					this.content = info.content;
					this.pageInfo.totalCount = info.totalPages;
					console.log('==============='+info.totalPages);
				});
			},
			clickCallback: function(pageNum){
				this.searchParam.page = pageNum-1;
				console.log('pageNum');
				console.log(pageNum);
				this.getList();
			},
			clickList: function () {
				this.getList();
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
