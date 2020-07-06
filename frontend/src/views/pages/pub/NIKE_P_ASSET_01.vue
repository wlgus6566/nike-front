<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"><img alt="" src="@/assets/images/logo-nike.svg" /></a>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <a href="#">TEST</a>
                        </li>
                    </ul>
                    <div class="bg"></div>
                </nav>
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <h2 class="page-title">UPLOAD</h2>
                <div>
                    <h3 class="form-title mt20">캠페인 설정</h3>
                    <hr class="hr-black" />
                    <ul class="form-list-thumb">
                        <li class="form-row thumb-row">
                            <span class="thumb-file file-upload">
                                <input type="file" />
                                <span class="thumb">
                                    <img src="@/assets/images/@test1.jpg" alt="업로드이미지" />
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
                                        <input type="radio" name="campaign" checked />
                                        <span></span>
                                    </span>
                                    <span>노출</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="campaign" />
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
                                        <input type="radio" name="upload" checked />
                                        <span></span>
                                    </span>
                                    <span>SP</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="upload" />
                                        <span></span>
                                    </span>
                                    <span>SU</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="upload" />
                                        <span></span>
                                    </span>
                                    <span>FA</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="upload" />
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
                                    <textarea cols="100" rows="2">
SP20 NSW NIKE DIRECT AM90 2020 NSW NIKE DIRECT AM90 NIKE DIRECT AM90 2020 NSW NIKE DIRECT AM90</textarea
                                    >
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">캠페인 내용</label>
                            </div>
                            <div class="form-column">
                                <span class="textarea">
                                    <textarea cols="100" rows="2">
나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가 업데이트 되었습니다.</textarea
                                    >
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
                                        <input type="radio" name="upload" checked />
                                        <span></span>
                                    </span>
                                    <span>기간선택</span>
                                </label>
                                <label class="check-label">
                                    <span class="radio">
                                        <input type="radio" name="upload" checked />
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
                                    <textarea cols="100" rows="2" style="height: 80px;">
나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가 업데이트 되었습니다.</textarea
                                    >
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
                    <hr class="hr-gray" />
                </div>
                <div>
                    <div>
                        <h3 class="form-title">파일 설정</h3>
                        <hr class="hr-black" />
                        <div class="btn-area-file">
                            <button class="btn-file-full" v-on:click="add">
                                <i class="icon-add"></i>
                                <span>파일 추가하기</span>
                            </button>
                        </div>
                        <div class="file-setting">
                            <ul class="form-list">
                                <li class="form-row">
                                    <div class="form-column">
                                        <span class="label-title required">파일 구분</span>
                                    </div>
                                    <div class="form-column">
                                        <label class="check-label type2">
                                            <span class="radio">
                                                <input type="radio" name="file" checked />
                                                <span></span>
                                            </span>
                                            <span>ASSET</span>
                                        </label>
                                        <label class="check-label type2">
                                            <span class="radio">
                                                <input type="radio" name="file" />
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
                                        <label class="check-label type2">
                                            <span class="radio">
                                                <input type="radio" name="kind" checked />
                                                <span></span>
                                            </span>
                                            <span>파일</span>
                                        </label>
                                        <label class="check-label type2">
                                            <span class="radio">
                                                <input type="radio" name="kind" />
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
                                                                <input type="checkbox" />
                                                                <span></span>
                                                            </span>
                                                            <span class="txt"
                                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg<</span
                                                            >
                                                        </label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <span class="checkbox">
                                                                <input type="checkbox" />
                                                                <span></span>
                                                            </span>
                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                                                        </label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <span class="checkbox">
                                                                <input type="checkbox" />
                                                                <span></span>
                                                            </span>
                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                                                        </label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <span class="checkbox">
                                                                <input type="checkbox" />
                                                                <span></span>
                                                            </span>
                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                                                        </label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <span class="checkbox">
                                                                <input type="checkbox" />
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
                                                    <input type="file" />
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
                                            <div class="upload-file-list"></div>
                                            <div class="btn-box">
                                                <div class="fine-file">
                                                    <span class="btn-form-gray"><span>찾기</span></span>
                                                    <input type="file" />
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
                                        <input type="text" value="P20_Nsw_Nike_Gallery_graphic_1_700x1000" />
                                    </div>
                                </li>
                                <li class="form-row">
                                    <div class="form-column">
                                        <label class="label-title">URL</label>
                                    </div>
                                    <div class="form-column">
                                        <input type="text" value="vimeo.com/101912729" />
                                    </div>
                                </li>
                            </ul>
                            <button class="btn-del"><span>삭제</span></button>
                        </div>
                        <component v-for="item in buttons" :is="item"></component>
                        <div class="btn-area-file">
                            <button class="btn-file-full" v-on:click="add">
                                <i class="icon-add"></i>
                                <span>파일 추가하기</span>
                            </button>
                        </div>
                        <div class="btn-area">
                            <button type="button" class="btn-s-white"><span>취소</span></button>
                            <button type="button" class="btn-s-black"><span>저장</span></button>
                        </div>
                    </div>
                </div>
            </div>
            <aside>
                <div class="aside-wrap">
                    aside
                </div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'NIKE_P_ASSET_01',
    data() {
        return {
            buttons: [],
        };
    },
    components: {
        'my-component': {
            template:
                '' +
                ' <div class="file-setting">\n' +
                '                            <ul class="form-list">\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <span class="label-title required">파일 구분</span>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <label class="check-label type2">\n' +
                '                                            <span class="radio">\n' +
                '                                                <input type="radio" name="file" checked />\n' +
                '                                                <span></span>\n' +
                '                                            </span>\n' +
                '                                            <span>ASSET</span>\n' +
                '                                        </label>\n' +
                '                                        <label class="check-label type2">\n' +
                '                                            <span class="radio">\n' +
                '                                                <input type="radio" name="file" />\n' +
                '                                                <span></span>\n' +
                '                                            </span>\n' +
                '                                            <span>ASSET</span>\n' +
                '                                        </label>\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <span class="label-title required">파일 종류</span>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <label class="check-label type2">\n' +
                '                                            <span class="radio">\n' +
                '                                                <input type="radio" name="kind" checked />\n' +
                '                                                <span></span>\n' +
                '                                            </span>\n' +
                '                                            <span>파일</span>\n' +
                '                                        </label>\n' +
                '                                        <label class="check-label type2">\n' +
                '                                            <span class="radio">\n' +
                '                                                <input type="radio" name="kind" />\n' +
                '                                                <span></span>\n' +
                '                                            </span>\n' +
                '                                            <span>동영상(URL)</span>\n' +
                '                                        </label>\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                                <!-- todo 추가 스크립트 작업 필요  -->\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <span class="label-title">업로드 된 파일</span>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <div class="upload-file-box">\n' +
                '                                            <div class="upload-file-list actice">\n' +
                '                                                <ul>\n' +
                '                                                    <li>\n' +
                '                                                        <label>\n' +
                '                                                            <span class="checkbox">\n' +
                '                                                                <input type="checkbox" />\n' +
                '                                                                <span></span>\n' +
                '                                                            </span>\n' +
                '                                                            <span class="txt"\n' +
                '                                                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg<</span\n' +
                '                                                            >\n' +
                '                                                        </label>\n' +
                '                                                    </li>\n' +
                '                                                    <li>\n' +
                '                                                        <label>\n' +
                '                                                            <span class="checkbox">\n' +
                '                                                                <input type="checkbox" />\n' +
                '                                                                <span></span>\n' +
                '                                                            </span>\n' +
                '                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>\n' +
                '                                                        </label>\n' +
                '                                                    </li>\n' +
                '                                                    <li>\n' +
                '                                                        <label>\n' +
                '                                                            <span class="checkbox">\n' +
                '                                                                <input type="checkbox" />\n' +
                '                                                                <span></span>\n' +
                '                                                            </span>\n' +
                '                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>\n' +
                '                                                        </label>\n' +
                '                                                    </li>\n' +
                '                                                    <li>\n' +
                '                                                        <label>\n' +
                '                                                            <span class="checkbox">\n' +
                '                                                                <input type="checkbox" />\n' +
                '                                                                <span></span>\n' +
                '                                                            </span>\n' +
                '                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>\n' +
                '                                                        </label>\n' +
                '                                                    </li>\n' +
                '                                                    <li>\n' +
                '                                                        <label>\n' +
                '                                                            <span class="checkbox">\n' +
                '                                                                <input type="checkbox" />\n' +
                '                                                                <span></span>\n' +
                '                                                            </span>\n' +
                '                                                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>\n' +
                '                                                        </label>\n' +
                '                                                    </li>\n' +
                '                                                </ul>\n' +
                '                                            </div>\n' +
                '                                            <div class="btn-box">\n' +
                '                                                <div class="fine-file">\n' +
                '                                                    <span class="btn-form-gray"><span>찾기</span></span>\n' +
                '                                                    <input type="file" />\n' +
                '                                                </div>\n' +
                '                                                <button type="button" class="btn-form"><span>삭제</span></button>\n' +
                '                                            </div>\n' +
                '                                        </div>\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <span class="label-title">업로드 된 파일</span>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <div class="upload-file-box">\n' +
                '                                            <div class="upload-file-list"></div>\n' +
                '                                            <div class="btn-box">\n' +
                '                                                <div class="fine-file">\n' +
                '                                                    <span class="btn-form-gray"><span>찾기</span></span>\n' +
                '                                                    <input type="file" />\n' +
                '                                                </div>\n' +
                '                                                <button type="button" class="btn-form"><span>삭제</span></button>\n' +
                '                                            </div>\n' +
                '                                        </div>\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <label class="label-title">파일 타이틀</label>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <input type="text" value="P20_Nsw_Nike_Gallery_graphic_1_700x1000" />\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                                <li class="form-row">\n' +
                '                                    <div class="form-column">\n' +
                '                                        <label class="label-title">URL</label>\n' +
                '                                    </div>\n' +
                '                                    <div class="form-column">\n' +
                '                                        <input type="text" value="vimeo.com/101912729" />\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                            </ul>\n' +
                '                            <button class="btn-del"><span>삭제</span></button>\n' +
                '                        </div>',
        },
    },
    methods: {
        add() {
            this.buttons.push('my-component');
        },
    },
};
</script>
<style scoped></style>
