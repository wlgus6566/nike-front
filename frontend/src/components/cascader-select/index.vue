<template>
    <div class="cascader-select">
        <el-cascader
            ref="select"
            v-model="listCascader.value"
            :options="listCascader.options"
            :props="{ checkStrictly: true }"
            clearable
        />
    </div>
</template>
<script>
export default {
    name: 'cascader-select',
    data() {
        return {
            cloneTxt: '',
        };
    },
    props: ['listCascader'],
    watch: {
        'listCascader.value'(val) {
            // if (val.length === 0) {
            //     this.listCascader.value = [null];
            // }
            this.cloneTxt = '';
            val.forEach((el) => {
                this.testArr(this.listCascader.options, el);
            });
            this.selectWidthSet();
        },
    },
    mounted() {
        const FontFaceObserver = require('fontfaceobserver');
        const NotoSans = new FontFaceObserver('Noto Sans KR', { weight: 700 });
        const Roboto = new FontFaceObserver('Roboto', { weight: 700 });
        Promise.all([NotoSans.load(), Roboto.load()]).then(() => {
            this.selectWidthSet();
            this.listCascader.value = [null];
        });
    },
    methods: {
        testArr(arr, sep) {
            arr.forEach((el) => {
                if (el.value === sep) {
                    this.cloneTxt = this.cloneTxt + ' / ' + el.label;
                }
                if (el.children) {
                    this.testArr(el.children, sep);
                }
            });
        },
        selectWidthSet() {
            const selectDiv = this.$refs.select.$el;
            const input = selectDiv.querySelector('input');
            input.insertAdjacentHTML(
                'afterend',
                `<div id="select-width">${this.cloneTxt}</div>`
            );
            const widthGuideTxt = selectDiv.querySelector('#select-width');
            input.style.width = `${
                Math.ceil(widthGuideTxt.offsetWidth) + 30
            }px`;
            widthGuideTxt.parentNode.removeChild(widthGuideTxt);
        },
    },
};
</script>
<style scoped></style>
