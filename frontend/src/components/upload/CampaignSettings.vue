<template>
	<div>
		<h3 class="form-title mt20">캠페인 설정</h3>
		<hr class="hr-black">
		<ul class="form-list-thumb">
			<li class="form-row thumb-row">
				<span class="thumb-file file-upload">
					<input type="file">
					<span class="thumb">
						<img src="@/assets/images/@test1.jpg" alt="업로드이미지">
					</span>
					<span class="txt">썸네일 이미지 재등록</span>
				</span>
			</li>
			<li class="form-row">
				<div class="form-column">
					<span class="label-title required">캠페인 상태</span>
				</div>
				<div class="form-column">
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="campaign" checked>
							<span></span>
						</span>
						<span>노출</span>
					</label>
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="campaign">
							<span></span>
						</span>
						<span>미노출</span>
					</label>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<span class="label-title required">업로드 위치</span>
				</div>
				<div class="form-column">
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload" checked>
							<span></span>
						</span>
						<span>SP</span>
					</label>
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload">
							<span></span>
						</span>
						<span>SU</span>
					</label>
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload">
							<span></span>
						</span>
						<span>FA</span>
					</label>
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload">
							<span></span>
						</span>
						<span>HO</span>
					</label>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title required">캠페인 상태</label>
				</div>
				<div class="form-column">
					<span class="textarea">
						<textarea cols="100" rows="2">SP20 NSW NIKE DIRECT AM90 2020 NSW NIKE DIRECT AM90 NIKE DIRECT AM90 2020 NSW NIKE DIRECT AM90</textarea>
					</span>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title required">캠페인 내용</label>
				</div>
				<div class="form-column">
					<span class="textarea">
						<textarea cols="100" rows="2">나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가 업데이트 되었습니다.</textarea>
					</span>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<span class="label-title">캠페인 기간</span>
				</div>
				<div class="form-column">
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload" checked>
							<span></span>
						</span>
						<span>기간선택</span>
					</label>
					<label class="check-label">
						<span class="radio">
							<input type="radio" name="upload" checked>
							<span></span>
						</span>
						<span>365</span>
					</label>
					<!-- todo 추가 스크립트 작업 필요  -->
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title">메모</label>
				</div>
				<div class="form-column">
					<!-- todo 추가 스크립트 작업 필요  -->
					<span class="textarea">
						<textarea cols="100" rows="2">나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가 업데이트 되었습니다.</textarea>
						<span class="count"><strong>100</strong> / <em>150</em>byte</span>
					</span>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title">권한 설정</label>
				</div>
				<div class="form-column">
					<button type="button" class="btn-form-gray min100"><span>권한설정</span></button>
				</div>
			</li>
		</ul>
		<hr class="hr-gray">
	</div>
</template>
<script>
	export default {
		name: "CampaignSettings"
	}
</script>
<style scoped></style>
