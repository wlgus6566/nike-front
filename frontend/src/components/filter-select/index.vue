<template>
    <div class="filter-select" :style="{ opacity: complete }">
        <el-select
            clearable
            ref="select"
            v-model="listSortSelect.value"
            placeholder="Select"
            @focus="$emit('selectFocus')"
        >
            <el-option
                v-for="item in listSortSelect.listSortOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
        </el-select>
    </div>
</template>
<script>
export default {
    name: 'SelectBox',
    data() {
        return {
            complete: 0,
        };
    },
    watch: {
        //todo src/components/file-settings/file-item.vue에도 동일한 함수등 사용 수정시 같이
        'listSortSelect.value'() {
            this.selectWidthSet();
        },
    },
    props: ['listSortSelect'],
    computed: {
        cloneTxt() {
            const cloneTxt = this.listSortSelect.listSortOptions.find(
                element => element.value === this.listSortSelect.value
            );
            return cloneTxt.label;
        },
    },
    methods: {
        focus() {},
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
        const FontFaceObserver = require('fontfaceobserver');
        const NotoSans = new FontFaceObserver('Noto Sans KR', { weight: 700 });
        const Roboto = new FontFaceObserver('Roboto', { weight: 700 });
        Promise.all([NotoSans.load(), Roboto.load()]).then(() => {
            this.selectWidthSet();
        });
    },
};
</script>
<style>
.el-input__suffix {
    right: 0;
}
</style>
