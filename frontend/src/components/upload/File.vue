<template>
	<div class="file-setting">
		<ul class="form-list">
			<li class="form-row">
				<div class="form-column">
					<span class="label-title required">파일 구분</span>
				</div>
				<div class="form-column">
					<label class="check-label minw120">
						<span class="radio">
							<input type="radio" name="file" checked>
							<span></span>
						</span>
						<span>ASSET</span>
					</label>
					<label class="check-label minw120">
						<span class="radio">
							<input type="radio" name="file">
							<span></span>
						</span>
						<span>ASSET</span>
					</label>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<span class="label-title required">파일 종류</span>
				</div>
				<div class="form-column">
					<label class="check-label minw120">
						<span class="radio">
							<input type="radio" name="kind" checked>
							<span></span>
						</span>
						<span>파일</span>
					</label>
					<label class="check-label minw120">
						<span class="radio">
							<input type="radio" name="kind">
							<span></span>
						</span>
						<span>동영상(URL)</span>
					</label>
				</div>
			</li>
			<!-- todo 추가 스크립트 작업 필요  -->
			<li class="form-row">
				<div class="form-column">
					<span class="label-title">업로드 된 파일</span>
				</div>
				<div class="form-column">
					<div class="upload-file-box">
						<div class="upload-file-list actice">
							<ul>
								<li>
									<label>
										<span class="checkbox">
											<input type="checkbox">
											<span></span>
										</span>
										<span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg<</span>
									</label>
								</li>
								<li>
									<label>
										<span class="checkbox">
											<input type="checkbox">
											<span></span>
										</span>
										<span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
									</label>
								</li>
								<li>
									<label>
										<span class="checkbox">
											<input type="checkbox">
											<span></span>
										</span>
										<span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
									</label>
								</li>
								<li>
									<label>
										<span class="checkbox">
											<input type="checkbox">
											<span></span>
										</span>
										<span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
									</label>
								</li>
								<li>
									<label>
										<span class="checkbox">
											<input type="checkbox">
											<span></span>
										</span>
										<span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
									</label>
								</li>
							</ul>
						</div>
						<div class="btn-box">
							<div class="fine-file">
								<span class="btn-form-gray"><span>찾기</span></span>
								<input type="file">
							</div>
							<button type="button" class="btn-form"><span>삭제</span></button>
						</div>
					</div>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<span class="label-title">업로드 된 파일</span>
				</div>
				<div class="form-column">
					<div class="upload-file-box">
						<div class="upload-file-list">
						</div>
						<div class="btn-box">
							<div class="fine-file">
								<span class="btn-form-gray"><span>찾기</span></span>
								<input type="file">
							</div>
							<button type="button" class="btn-form"><span>삭제</span></button>
						</div>
					</div>
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title">파일 타이틀</label>
				</div>
				<div class="form-column">
					<input type="text" value="P20_Nsw_Nike_Gallery_graphic_1_700x1000">
				</div>
			</li>
			<li class="form-row">
				<div class="form-column">
					<label class="label-title">URL</label>
				</div>
				<div class="form-column">
					<input type="text" value="vimeo.com/101912729">
				</div>
			</li>
		</ul>
		<button class="btn-del"><span>삭제</span></button>
	</div>
</template>
<script>
	export default {
		name: "File.vue"
	}
</script>
<style scoped></style>
