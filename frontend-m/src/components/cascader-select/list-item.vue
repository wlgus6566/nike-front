<template>
    <ul class="select-options-list">
        <li
            :class="{
                selected: cascaderList.value === val.value,
                disabled: val.disabled === true,
            }"
            v-for="(val, index) in options"
            :key="index"
        >
            <span class="select-label" @click="changeInput(val)">
                <span>{{ val.label }}</span>
            </span>
            <ListItem
                class="depth-list"
                v-if="val.children"
                :cascaderList="cascaderList"
                :options="val.children"
            />
        </li>
    </ul>
</template>
<script>
import bus from '@/utils/bus';
export default {
    name: 'ListItem',
    data() {
        return {};
    },
    props: ['cascaderList', 'options'],
    methods: {
        changeInput(val) {
            bus.$emit('changeInput', val);
        },
    },
};
</script>
<style scoped>
.select-options-list > li.disabled > span,
.select-options-list > li.disabled > .select-label:before {
    opacity: 0.3;
}
.depth-list > li.selected > .select-label:before {
    background-image: url(../../assets/images/svg/icon-dropbox-depth-on.svg);
}
.select-options-list > li {
    min-height: 45px;
    box-sizing: border-box;
}
.select-list-wrap > .select-options-list > li:first-child .select-label {
    background: inherit;
    font-weight: 600;
}
.select-list-wrap > .select-options-list > li > .select-label {
    background: #f7f7f7;
    font-size: 14px;
    font-weight: 600;
}
.select-options-list > li > .select-label {
    display: block;
    padding: 10px 15px;
    line-height: 20px;
    font-size: 14px;
    color: #000;
    word-break: break-word;
}
.select-options-list .select-label span {
    letter-spacing: 0;
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
.select-list-wrap > .select-options-list > li > .depth-list {
    padding: 0 10px 10px;
}
.depth-list {
}
.depth-list .depth-list {
    padding: 0 20px;
}
.depth-list li .select-label {
    position: relative;
    padding: 13px 13px 12px 25px;
}
.depth-list li .select-label:before {
    content: '';
    position: absolute;
    top: 11px;
    left: 0;
    display: inline-block;
    width: 20px;
    height: 20px;
    background: url(../../assets/images/svg/icon-dropbox-depth.svg) no-repeat
        center;
    vertical-align: middle;
}
</style>
