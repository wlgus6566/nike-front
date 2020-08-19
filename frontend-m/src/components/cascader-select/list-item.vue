<template>
    <ul class="select-options-list">
        <li
            :class="{
                selected: cascaderList.value === val.label,
            }"
            v-for="(val, index) in options"
            :key="index"
        >
            <span @click="selectedFn(val.label)">{{ val.label }}</span>
            <ListItem
                v-if="val.children"
                :cascaderList="cascaderList"
                :options="val.children"
            />
        </li>
    </ul>
</template>
<script>
export default {
    name: 'ListItem',
    props: ['cascaderList', 'options'],
    methods: {
        selectedFn(label) {
            this.cascaderList.value = label;
            // const seqList = this.options.map(el => el.label);
            // const _index = seqList.indexOf(label);
            // if (this.selectedIndex !== _index) {
            //     this.selectedIndex = _index;
            // } else {
            //     this.selectedIndex = null;
            // }
        },
    },
};
</script>
<style scoped>
.select-options-list li {
    padding: 0 20px;
    line-height: 45px;
    font-size: 14px;
    color: #000;
}
.select-options-list li.selected > span {
    color: #fa5400;
    font-weight: bold;
}
.select-options-list li.selected > span:after {
    content: '';
    display: inline-block;
    width: 4px;
    height: 4px;
    margin-left: 8px;
    border-radius: 100%;
    background: #fa5400;
    vertical-align: 3px;
}
</style>
