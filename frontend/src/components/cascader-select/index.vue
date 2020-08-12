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
    props: ['listCascader'],
    watch: {
        'listCascader.value'(value) {
            if (value.length === 0) {
                this.listCascader.value = [null];
            }
            //console.log(value.slice(-1)[0]);
            if (value.length === 1) {
                this.testArr(this.listCascader.options, value);
            }
        },
    },
    mounted() {},
    data() {
        return {
            cloneTxt: '',
        };
    },
    methods: {
        testArr(arr, value) {
            let aa = ''
            if(value.length > 1){}
            arr.forEach((el) => {
                if (el.value === value) {
                    this.cloneTxt = this.cloneTxt + el.label;
                }
            });
        },
        selectWidthSet() {
            const selectDiv = this.$refs.select.$el;
            const input = selectDiv.querySelector('input');
            console.log(input.value);
            input.insertAdjacentHTML(
                'afterend',
                `<div id="select-width">${this.cloneTxt}</div>`
            );
            const widthGuideTxt = selectDiv.querySelector('#select-width');
            input.style.width = `${
                Math.ceil(widthGuideTxt.offsetWidth) + 30
            }px`;
            widthGuideTxt.remove();
        },
    },
};
</script>
<style scoped></style>
