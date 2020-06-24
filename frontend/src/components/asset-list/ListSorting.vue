<template>
	<div class="sorting-area">
		<div class="filter-list">
			<button type="button" class="type-list" v-bind:class="{active : listType.active === true}" v-for="(listType , index) in listTypes" :key="index" v-on:click="update(index)"><span>{{listType.title}}</span></button>
		</div>
		<!-- todo select 스크립트 작업 필요  -->
		<div class="filter-select">
			<select>
				<option value="최신순">최신순</option>
				<option value="시작일순">시작일순</option>
			</select>
		</div>
		<div class="search-input">
			<input type="text" placeholder="검색어를 입력해주세요.">
			<button type="button"><span>검색</span></button>
		</div>
	</div>
</template>
<script>
	export default {
		name: "ListSorting",
		data(){
			return {
			}
		},
		props:["listTypes"],
		methods :{
			update(index){
				if(	this.listTypes[index].active == false){
					this.listTypes.forEach(element => element.active = false);
					this.listTypes[index].active = true
				}
				// 자식 컴포넌트에서 부모 컴포넌트로 보내는 것이 $emit()
				//this.$emit("child",this.user)
			},
		}
	}
</script>
<style scoped></style>
