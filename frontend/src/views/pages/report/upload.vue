<template>
    <div>
        <h2 class="page-title">
            {{ this.$route.meta.title }}
        </h2>
        <h3 class="form-title">폴더 설정</h3>
        <hr class="hr-black" />
        <ul class="form-list-thumb">
            <li class="form-row thumb-row">
                <span class="thumb-file">
                    <input type="file" />
                    <span class="thumb"> </span>
                    <span class="txt">썸네일 이미지 재등록</span>
                </span>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">구분</span>
                </div>
                <div class="form-column">
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="sortation" checked />
                            <span></span>
                        </span>
                        <span>SP</span>
                    </label>
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="sortation" />
                            <span></span>
                        </span>
                        <span>SU</span>
                    </label>
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="sortation" />
                            <span></span>
                        </span>
                        <span>FA</span>
                    </label>
                    <label class="check-label">
                        <span class="radio">
                            <input type="radio" name="sortation" />
                            <span></span>
                        </span>
                        <span>HO</span>
                    </label>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">보고서 명</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2"></textarea>
                    </span>
                </div>
            </li>
        </ul>
        <hr class="hr-gray" />
        <h3 class="form-title">파일 설정</h3>
        <hr class="hr-black" />
        <div class="file-setting">
            <ul class="form-list">
                <!-- todo 추가 스크립트 작업 필요  -->
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title">파일 찾기</span>
                    </div>
                    <div class="form-column">
                        <div class="upload-file-box">
                            <div class="upload-file-list actice">
                                <ul>
                                    <li>
                                        <label>
                                            <span class="checkbox">
                                                <input type="checkbox" />
                                                <span></span>
                                            </span>
                                            <span class="txt"
                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg</span
                                            >
                                        </label>
                                    </li>
                                    <li>
                                        <label>
                                            <span class="checkbox">
                                                <input type="checkbox" />
                                                <span></span>
                                            </span>
                                            <span class="txt"
                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                            >
                                        </label>
                                    </li>
                                    <li>
                                        <label>
                                            <span class="checkbox">
                                                <input type="checkbox" />
                                                <span></span>
                                            </span>
                                            <span class="txt"
                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                            >
                                        </label>
                                    </li>
                                    <li>
                                        <label>
                                            <span class="checkbox">
                                                <input type="checkbox" />
                                                <span></span>
                                            </span>
                                            <span class="txt"
                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                            >
                                        </label>
                                    </li>
                                    <li>
                                        <label>
                                            <span class="checkbox">
                                                <input type="checkbox" />
                                                <span></span>
                                            </span>
                                            <span class="txt"
                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                            >
                                        </label>
                                    </li>
                                </ul>
                            </div>
                            <div class="btn-box">
                                <div class="fine-file">
                                    <span class="btn-form-gray"
                                        ><span>찾기</span></span
                                    >
                                    <input type="file" />
                                </div>
                                <button type="button" class="btn-form">
                                    <span>삭제</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-black"><span>저장</span></button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'upload',
};
</script>
<style scoped></style>
