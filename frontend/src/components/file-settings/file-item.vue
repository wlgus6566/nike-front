<template>
    <li :class="{ 'file-setting': true, error: this.file.progress === true }">
        <transition name="fade">
            <!--<span
                class="progress"
                :style="{ width: `${file.progress}%` }"
            ></span>-->
        </transition>
        <ul class="form-list">
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">파일 구분</span>
                </div>
                <div class="form-column">
                    <div
                        class="filter-select"
                        v-if="$route.path.split('/')[1] === 'asset'"
                    >
                        <el-select
                            v-model="file.fileSectionCode"
                            placeholder="Select"
                            ref="select"
                        >
                            <el-option
                                v-for="item in pageFileSectionCodeName"
                                :key="item"
                                :label="item"
                                :value="item"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <label
                        class="check-label"
                        v-else
                        v-for="item in pageFileSectionCodeName"
                        :key="item"
                    >
                        <span class="radio">
                            <input
                                type="radio"
                                v-model="file.fileSectionCode"
                                :value="item"
                            /><!--:disabled="!!file.url || !!file.title"-->
                            <i></i>
                            <span class="txt">{{ item }}</span>
                        </span>
                    </label>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">파일 종류</span>
                </div>
                <div class="form-column">
                    <label
                        class="check-label"
                        v-for="item in fileKindCodeList"
                        :key="item.value"
                    >
                        <span class="radio">
                            <input
                                type="radio"
                                v-model="file.fileKindCode"
                                :value="item.value"
                            /><!-- :disabled="!!file.url || !!file.title"-->
                            <i></i>
                            <span class="txt">{{ item.label }}</span>
                        </span>
                    </label>
                </div>
            </li>
            <!-- todo 추가 스크립트 작업 필요  -->
            <li class="form-row" v-if="file.fileKindCode === 'FILE'">
                <div class="form-column">
                    <span class="label-title">업로드 된 파일</span>
                </div>
                <div class="form-column">
                    <div
                        class="form-file-wrap"
                        :class="{ 'has-file': file.fileName }"
                    >
                        <span class="txt">{{ file.fileName }}</span>
                        <button
                            type="button"
                            class="btn-form-gray"
                            v-on:click="$emit('fileSelect')"
                        >
                            찾기
                        </button>
                    </div>
                    <!--<UploadFile />-->
                </div>
            </li>
            <template v-else>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">파일 타이틀</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="file.title" />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">URL</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="file.url" />
                    </div>
                </li>
            </template>
        </ul>
        <button
            v-if="emptyCheck || listLength > 1"
            class="btn-del"
            @click.prevent="$emit('fileDelete')"
        >
            <span>삭제</span>
        </button>
    </li>
</template>
<script>
export default {
    name: 'file-item',
    data() {
        return {
            complete: 0,
        };
    },
    props: {
        file: Object,
        errorFile: Array,
        listLength: Number,
        pageFileSectionCodeName: Array,
        menuCode: String,
    },
    watch: {
        'file.fileSectionCode'() {
            //this.file.fileKindCode = 'FILE';
            // asset 일때 select width 조정 (components filter-select 동일)
            if (this.$route.path.split('/')[1] === 'asset') {
                this.selectWidthSet();
            }
        },
    },
    computed: {
        // asset 일때 select width 조정을 위한 data (components filter-select 동일)
        cloneTxt() {
            const cloneTxt = this.pageFileSectionCodeName.find(
                element => element === this.file.fileSectionCode
            );
            return cloneTxt;
        },
        emptyCheck() {
            return this.file.fileName || this.file.title || this.file.url;
            /*return (
                (this.file.fileKindCode === 'FILE' && this.file.fileName) ||
                (this.file.fileKindCode === 'VIDEO' &&
                    (this.file.url || this.file.title)) ||
                (this.file.fileKindCode === 'VR' &&
                    (this.file.url || this.file.title))
            );*/
        },
        fileKindCodeList() {
            return (this.menuCode === 'VMS' &&
                this.file.fileSectionCode === 'VR') ||
                (this.menuCode === 'RB' &&
                    this.file.fileSectionCode === 'GUIDE')
                ? [
                      { label: '파일', value: 'FILE' },
                      { label: '동영상(URL)', value: 'VIDEO' },
                      { label: 'VR', value: 'VR' },
                  ]
                : [
                      { label: '파일', value: 'FILE' },
                      { label: '동영상(URL)', value: 'VIDEO' },
                  ];
        },
    },
    methods: {
        // asset 일때 select width 조정을 위한 함수 (components filter-select 동일)
        selectWidthSet() {
            const selectDiv = this.$refs.select.$el;
            const input = selectDiv.querySelector('input');
            input.insertAdjacentHTML(
                'afterend',
                `<div id="select-width">${this.cloneTxt}</div>`
            );
            const widthGuideTxt = selectDiv.querySelector('#select-width');
            input.style.width = `${Math.ceil(widthGuideTxt.offsetWidth) +
                30}px`;
            widthGuideTxt.parentNode.removeChild(widthGuideTxt);
            this.complete = 1;
        },
    },
    mounted() {
        // asset 일때 select width 조정을 위한 폰트 (components filter-select 동일)
        if (this.$route.path.split('/')[1] === 'asset') {
            const FontFaceObserver = require('fontfaceobserver');
            const NotoSans = new FontFaceObserver('Noto Sans KR', {
                weight: 700,
            });
            const Roboto = new FontFaceObserver('Roboto', { weight: 700 });
            Promise.all([NotoSans.load(), Roboto.load()]).then(() => {
                this.selectWidthSet();
            });
        }
    },
};
</script>
<style scoped>
.form-file-wrap.has-file {
    background: #f7f7f7;
}
.form-file-wrap {
    position: relative;
    padding: 4px;
    display: flex;
    border: 1px solid #ddd;
    width: 100%;
    justify-content: space-between;
}
.form-file-wrap .txt {
    display: block;
    box-sizing: border-box;
    color: #000;
    padding-left: 10px;
    font-size: 14px;
    line-height: 30px;
    flex: 1 1 auto;
    width: calc(100% - 70px);
    white-space: nowrap;
    text-overflow: ellipsis;
    word-wrap: normal;
    overflow: hidden;
}
.file-setting .progress {
    position: absolute;
    top: 0;
    left: 0;
    width: 0;
    height: 10px;
    background: Red;
}

.fade-enter-active,
.fade-leave-active {
    transition: all 1s;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}
.error {
    border-color: #f20000;
}
</style>
