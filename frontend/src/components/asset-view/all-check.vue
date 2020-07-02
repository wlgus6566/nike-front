<template>
	<div class="all-box">
		<!-- todo 전체선택 스크립트 작업 필요  -->
		<label class="check-label">
			<span class="checkbox">
				<input type="checkbox">
				<span></span>
			</span>
			<strong class="txt">전체선택</strong>
		</label>
		<p class="desc"><em>1</em>개의 파일이 선택됨</p>
		<!-- todo select 스크립트 작업 필요  -->
		<div class="filter-select">
			<select>
				<option value="필터">필터</option>
			</select>
		</div>
	</div>
</template>
<script>
	export default {
		name: "allCheck"
	}
</script>
<style scoped></style>
