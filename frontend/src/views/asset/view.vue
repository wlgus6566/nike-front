<template>
	<div>
		<BtnArea></BtnArea>
		<folder></folder>
		<SortingList></SortingList>
		<allCheck></allCheck>
		<fileItem v-bind:fileItems="fileItems"></fileItem>
	</div>
</template>
<script>
	import BtnArea from '../../components/asset-view/BtnArea.vue'
	import folder from '../../components/asset-view/folder.vue'
	import SortingList from '../../components/asset-view/SortingList.vue'
	import allCheck from '../../components/asset-view/allCheck.vue'
	import fileItem from '../../components/asset-view/fileItem.vue'
	export default {
		name: "view",
		data(){
			return {
				fileItems : [
					{
						title : "타이틀",
						img:"@/assets/images/img-asset-none@2x.png",
						added :{
							exist:true,
							state:false,
						},
						link :false,
						acd : false,
						down : 0,
					},
					{
						title : "타이틀2",
						img:"@/assets/images/img-asset-none@2x.png",
						added :{
							exist:true,
							state:true,
						},
						link :false,
						acd : false,
						down : 0,
					},
					{
						title : "타이틀3",
						img:"@/assets/images/img-asset-none@2x.png",
						added :{
							exist:false,
							state:false,
						},
						link :true,
						acd : false,
						down : 0,
					},
				]
			}
		},
		components : {
			BtnArea,
			folder,
			SortingList,
			allCheck,
			fileItem
		}
	}
</script>
<style scoped></style>
