<template>
    <div>
        <ul class="upload-list">
            <li class="upload-item thumbnail-file">
                프론트엔드
            </li>
            <li class="upload-item">
                <div class="form-column">
                    <label class="label-title required">구분</label>
                </div>
                <div class="form-column">
                    <div class="radio-box">
                        <label class="check-item">
                            <input type="radio">
                            <span class="txt">SP</span>
                        </label>
                        <label class="check-item">
                            <input type="radio">
                            <span class="txt">SU</span>
                        </label>
                        <label class="check-item">
                            <input type="radio">
                            <span class="txt">FA</span>
                        </label>
                        <label class="check-item auto">
                            <input type="radio">
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
