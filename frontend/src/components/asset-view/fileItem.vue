<template>
	<div>
		<ul class="file-item-list">
			<li class="file-item"  v-for="(fileItem ,index) in fileItems" :key="index">
				<div class="list">
					<label>
						<span class="checkbox">
							<input type="checkbox" v-model="fileItem.added.state" v-on:change="checkbox(index , fileItem.added.state)">
							<span></span>
						</span>
						<span class="thumbnail">
							<img  src="@/assets/images/img-asset-none@2x.png" alt="">
						</span>
						<span class="info-box">
							<strong class="title">{{fileItem.title}}</strong>
						</span>
					</label>
					<div class="btn-box">
						<div v-if="fileItem.added.exist">
							<button type="button" class="btn-s-sm-black" v-if="fileItem.added.state == false"><span>ADD</span></button>
							<button type="button" class="btn-s-sm-white" v-if="fileItem.added.state == true"><i class="icon-check"></i><span>ADDED</span></button>
						</div>
						<button type="button" class="btn-s-sm-white" v-if="fileItem.link"><i class="icon-link"></i><span>LINK</span></button>
						<button type="button" class="btn-more active"><span>더보기</span></button>
					</div>
				</div>
				<div class="detail active">
					<div class="thumbnail">
						<img  src="@/assets/images/img-asset-none@2x.png" alt="">
					</div>
					<div class="down-info">
						<span class="key">다운로드 횟수</span>
						<span class="val"><strong>100,300</strong> 회</span>
					</div>
				</div>
			</li>
		</ul>
	</div>
</template>
<script>
	export default {
		name: "fileItem",
		props:["fileItems"],
		methods:{
			checkbox(index , checked){
				console.log(checked)
				if(checked !== true){
					checked = true
				}else{
					checked = false
				}
				this.fileItems[index].added.state = checked
			},
		}
	}
</script>
<style scoped></style>
