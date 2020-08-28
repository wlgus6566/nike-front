<template>
    <div>
        <ul class="upload-list">
            <li class="upload-item thumbnail-file">
                <label class="thumb-file" :class="{ 'file-upload': cropImg }">
                    <input type="file" />
                    <div class="thumb">
                        <!-- <img src="http://placehold.it/320x320" alt="샘플" />-->
                    </div>
                    <p class="txt">
                        썸네일 이미지 등록
                    </p>
                    <!-- 업로드시 나타나는 텍스트 -->
                    <p class="txt-btm">
                        <span>썸네일 이미지 재등록</span>
                    </p>
                </label>
                <div class="upload-file-box">
                    <div class="fine-file">
                        <input type="file" />
                        <!-- 파일 등록 전 -->
                        <!-- <div class="txt">파일등록</div>-->
                        <!-- 파일 등록 후 -->
                        <div class="number">6/10</div>
                    </div>
                    <ul class="upload-file-list">
                        <!-- 이미지 첨부한 타입 -->
                        <li class="upload-file-item">
                            <img
                                src="http://placehold.it/130x130"
                                alt="샘플이미지"
                            />
                            <button type="button" class="file-delete">
                                삭제
                            </button>
                        </li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                        <li class="upload-file-item"></li>
                    </ul>
                </div>
                <p class="desc">* 이미지는 최대 10개까지 등록 가능합니다.</p>
            </li>
            <li class="upload-item">
                <div class="form-column">
                    <label class="label-title required">구분</label>
                </div>
                <div class="form-column">
                    <div class="radio-box">
                        <label class="check-item">
                            <input type="radio" />
                            <span class="txt">SP</span>
                        </label>
                        <label class="check-item">
                            <input type="radio" />
                            <span class="txt">SU</span>
                        </label>
                        <label class="check-item">
                            <input type="radio" />
                            <span class="txt">FA</span>
                        </label>
                        <label class="check-item auto">
                            <input type="radio" />
                            <span class="txt">HO</span>
                        </label>
                    </div>
                </div>
            </li>
            <li class="upload-item">
                <div class="form-column">
                    <label class="label-title required">보고서 명</label>
                </div>
                <div class="form-column">
                    <div class="txtarea">
                        <textarea></textarea>
                    </div>
                </div>
            </li>
        </ul>
        <div class="btn-area">
            <button type="button" class="btn-s-sm">취소</button>
            <button type="button" class="btn-s-black-sm">확인</button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'upload',
};
</script>
<style scoped></style>
