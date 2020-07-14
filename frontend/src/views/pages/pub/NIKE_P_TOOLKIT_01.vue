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
                <h2 class="page-title">TOOLKIT</h2>
                <h3 class="form-title mt20">툴킷 설정</h3>
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
                            <span class="label-title required">툴킷 상태</span>
                        </div>
                        <div class="form-column">
                            <el-radio v-model="radio" label="1">노출</el-radio>
                            <el-radio v-model="radio" label="2">미노출</el-radio>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title required">업로드 위치</span>
                        </div>
                        <div class="form-column">
                            <el-radio v-model="radio2" label="1">VMS</el-radio>
                            <el-radio v-model="radio2" label="2">EKIN</el-radio>
                            <el-radio v-model="radio2" label="3">SOCIAL</el-radio>
                            <el-radio v-model="radio2" label="4">PB</el-radio>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">툴킷 명</label>
                        </div>
                        <div class="form-column">
                            <span class="textarea">
                                <textarea cols="100" rows="2"></textarea>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title required">툴킷 상세</label>
                        </div>
                        <div class="form-column">
                            <span class="textarea">
                                <textarea cols="100" rows="2" style="height: 80px;"></textarea>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title">기간</span>
                        </div>
                        <div class="form-column">
                            <div>
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
                            </div>
                            <!-- todo 추가 스크립트 작업 필요  -->
                            <div class="data-picker">
                                <el-date-picker
                                    v-model="value1"
                                    type="daterange"
                                    range-separator="-"
                                    start-placeholder="Start date"
                                    end-placeholder="End date"
                                >
                                </el-date-picker>
                            </div>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title">메모</label>
                        </div>
                        <div class="form-column">
                            <!-- todo 추가 스크립트 작업 필요  -->
                            <span class="textarea">
                                <textarea cols="100" rows="2" style="height: 80px;"></textarea>
                                <span class="count"><strong>100</strong> / <em>150</em>byte</span>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <label class="label-title">권한 설정</label>
                        </div>
                        <div class="form-column">
                            <button type="button" class="btn-form-gray min100">
                                <span>권한설정</span>
                            </button>
                        </div>
                    </li>
                </ul>
                <hr class="hr-gray" />
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
                                        <span>GUIDE</span>
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
                                    <label class="check-label type2">
                                        <span class="radio">
                                            <input type="radio" name="kind" />
                                            <span></span>
                                        </span>
                                        <span>VR</span>
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
                                                <span class="btn-form-gray"><span>찾기</span></span>
                                                <input type="file" />
                                            </div>
                                            <button type="button" class="btn-form">
                                                <span>삭제</span>
                                            </button>
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
                                            <button type="button" class="btn-form">
                                                <span>삭제</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <button class="btn-del"><span>삭제</span></button>
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
                                            <input type="radio" name="file2" checked />
                                            <span></span>
                                        </span>
                                        <span>ASSET</span>
                                    </label>
                                    <label class="check-label type2">
                                        <span class="radio">
                                            <input type="radio" name="file2" />
                                            <span></span>
                                        </span>
                                        <span>GUIDE</span>
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
                                            <input type="radio" name="kind2" />
                                            <span></span>
                                        </span>
                                        <span>파일</span>
                                    </label>
                                    <label class="check-label type2">
                                        <span class="radio">
                                            <input type="radio" name="kind2" checked />
                                            <span></span>
                                        </span>
                                        <span>동영상(URL)</span>
                                    </label>
                                    <label class="check-label type2">
                                        <span class="radio">
                                            <input type="radio" name="kind2" />
                                            <span></span>
                                        </span>
                                        <span>VR</span>
                                    </label>
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title">파일 타이틀</label>
                                </div>
                                <div class="form-column">
                                    <input
                                        type="text"
                                        value="P20_Nsw_Nike_Gallery_graphic_1_700x1000"
                                    />
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
                <div class="btn-area">
                    <button type="button" class="btn-s-white"><span>취소</span></button>
                    <button type="button" class="btn-s-black"><span>저장</span></button>
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
    name: 'NIKE_P_TOOLKIT_01',
    data() {
        return {
            radio: '1',
            radio2: '1',
            radio3: '1',
            radio4: '1',
            radio5: '1',
            radio6: '2',
            radio7: '1',
        };
    },
};
</script>
<style scoped></style>
