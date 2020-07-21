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
                                <span class="label-title required">상태</span>
                            </div>
                            <div class="form-column">
                                <el-radio v-model="stateRadio" label="노출">노출</el-radio>
                                <el-radio v-model="stateRadio" label="미노출">미노출</el-radio>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title required">업로드 위치</span>
                            </div>
                            <div class="form-column">
                                <el-radio v-model="uploadRadio" label="SP">SP</el-radio>
                                <el-radio v-model="uploadRadio" label="SU">SU</el-radio>
                                <el-radio v-model="uploadRadio" label="FA">FA</el-radio>
                                <el-radio v-model="uploadRadio" label="HO">HO</el-radio>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">캠페인 명</label>
                            </div>
                            <div class="form-column">
                                <el-input type="textarea" :rows="2" v-model="CampaignNameTextarea">
                                </el-input>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <label class="label-title required">캠페인 상세</label>
                            </div>
                            <div class="form-column">
                                <span class="textarea">
                                    <el-input
                                        type="textarea"
                                        :rows="2"
                                        v-model="CampaignDetailTextarea"
                                        class="type2"
                                    >
                                    </el-input>
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">캠페인 기간</span>
                            </div>
                            <div class="form-column">
                                <div>
                                    <el-radio v-model="campaignPeriodRadio" label="기간선택"
                                        >기간선택</el-radio
                                    >
                                    <el-radio v-model="campaignPeriodRadio" label="365"
                                        >365</el-radio
                                    >
                                </div>
                                <!-- todo 추가 스크립트 작업 필요  -->
                                <div class="data-picker">
                                    <el-date-picker
                                        v-model="datapicker"
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
                                <!-- todo count 제어  -->
                                <el-input
                                    type="textarea"
                                    v-model="memoTextarea"
                                    maxlength="150"
                                    show-word-limit
                                    class="type2"
                                >
                                </el-input>
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
                </div>
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
                                    <el-radio
                                        v-model="fileSortationRadio"
                                        label="ASSET"
                                        class="type2"
                                        >ASSET</el-radio
                                    >
                                    <el-radio
                                        v-model="fileSortationRadio"
                                        label="GUIDE"
                                        class="type2"
                                        >GUIDE</el-radio
                                    >
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <span class="label-title required">파일 종류</span>
                                </div>
                                <div class="form-column">
                                    <el-radio v-model="fileTypeRadio" label="파일" class="type2"
                                        >파일</el-radio
                                    >
                                    <el-radio
                                        v-model="fileTypeRadio"
                                        label="동영상(URL)"
                                        class="type2"
                                        >동영상(URL)</el-radio
                                    >
                                    <el-radio v-model="fileTypeRadio" label="VR" class="type2"
                                        >VR</el-radio
                                    >
                                </div>
                            </li>
                            <!-- todo 추가 스크립트 작업 필요  -->
                            <li class="form-row">
                                <div class="form-column">
                                    <span class="label-title">업로드 된 파일</span>
                                </div>
                                <div class="form-column">
                                    <fileUpload />
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
                                    <el-radio
                                        v-model="fileSortationRadio2"
                                        label="ASSET"
                                        class="type2"
                                        >ASSET</el-radio
                                    >
                                    <el-radio
                                        v-model="fileSortationRadio2"
                                        label="GUIDE"
                                        class="type2"
                                        >GUIDE</el-radio
                                    >
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <span class="label-title required">파일 종류</span>
                                </div>
                                <div class="form-column">
                                    <el-radio v-model="fileTypeRadio2" label="파일" class="type2"
                                        >파일</el-radio
                                    >
                                    <el-radio
                                        v-model="fileTypeRadio2"
                                        label="동영상(URL)"
                                        class="type2"
                                        >동영상(URL)</el-radio
                                    >
                                    <el-radio v-model="fileTypeRadio2" label="VR" class="type2"
                                        >VR</el-radio
                                    >
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title">파일 타이틀</label>
                                </div>
                                <div class="form-column">
                                    <el-input
                                        v-model="fileTileInput"
                                        placeholder="파일 타이틀을 입력해 주세요."
                                    />
                                </div>
                            </li>
                            <li class="form-row">
                                <div class="form-column">
                                    <label class="label-title">URL</label>
                                </div>
                                <div class="form-column">
                                    <el-input v-model="urlInput" placeholder="URL을 입력" />
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
	import fileUpload from '@/components/file-settings/file-upload';

	export default {
    name: 'NIKE_P_ASSET_01',
    data() {
        return {
            buttons: [],
            stateRadio: '노출',
            uploadRadio: 'SP',
            campaignPeriodRadio: '기간선택',
            CampaignNameTextarea: '',
            CampaignDetailTextarea: '',
            memoTextarea: '',
            fileSortationRadio: 'ASSET',
            fileTypeRadio: '파일',
            fileSortationRadio2: 'ASSET',
            fileTypeRadio2: '파일',
            fileTileInput: '',
            urlInput: '',
            datapicker: '',
            pickerOptions: {
                shortcuts: [
                    {
                        text: 'Last week',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: 'Last month',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: 'Last 3 months',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                ],
            },
        };
    },
    components: {
        'my-component': {
            template: '<div>파일추가</div>',
        },
        fileUpload,
    },
    methods: {
        add() {
            this.buttons.push('my-component');
        },
    },
};
</script>
<style scoped></style>
