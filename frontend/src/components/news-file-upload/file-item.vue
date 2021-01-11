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
            fileKindCodeList: [
                { label: '파일', value: 'FILE' },
                { label: '동영상(URL)', value: 'VIDEO' },
            ],
        };
    },
    props: {
        file: Object,
        errorFile: Array,
        listLength: Number,
        menuCode: String,
    },
    watch: {},
    computed: {
        emptyCheck() {
            return this.file.fileName || this.file.title || this.file.url;
        },
    },
    methods: {},
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
::v-deep .filter-select .el-input--suffix .el-input__inner {
    font-size: 14px;
    font-weight: 400;
}
</style>
