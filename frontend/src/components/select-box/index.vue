<template>
    <el-select
        ref="select"
        @change="selectWidthSet"
        v-model="listSortSelect.value"
        placeholder="Select"
    >
        <el-option
            v-for="item in listSortSelect.listSortOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        >
        </el-option>
    </el-select>
</template>
<script>
export default {
    name: 'SelectBox',
    data() {
        return {};
    },
    props: ['listSortSelect'],
    computed: {
        test() {
            const test1 = this.listSortSelect.listSortOptions.find(
                (element) => element.value === this.listSortSelect.value
            );
            return test1.label;
        },
    },
    methods: {
        selectWidthSet() {
            const selectDiv = this.$refs.select.$el;
            const input = selectDiv.querySelector('input');
            input.insertAdjacentHTML('afterend', `<div id="select-width">${this.test}</div>`);
            const testTxt = selectDiv.querySelector('#select-width');
            const test = (input.style.width = `${testTxt.offsetWidth + 45}px`);
            testTxt.remove();
        },
    },
    mounted() {
        this.selectWidthSet();
    },
};
</script>
<style>
.el-input {
}
#select-width {
    opacity: 0;
    position: absolute;
    display: inline;
    top: 0;
    left: 0;
    white-space: nowrap;
}
</style>
