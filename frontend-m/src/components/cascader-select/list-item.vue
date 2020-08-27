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
.select-options-list > li.disabled > span,
.depth-list > li.disabled > .select-label:before {
    opacity: 0.3;
}
.depth-list > li.selected > .select-label:before {
    background-image: url(../../assets/images/svg/icon-dropbox-depth-on.svg);
}
.select-options-list > li {
    margin: 5px 0;
}
.select-list-wrap > .select-options-list > li:first-child .select-label {
    background: inherit;
}
.select-list-wrap > .select-options-list > li > .select-label {
    background: #f7f7f7;
}
.select-options-list > li > .select-label {
    display: block;
    padding: 5px 15px;
    line-height: 20px;
    font-size: 14px;
    color: #000;
    word-break: break-word;
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
.depth-list {
    padding: 0 15px;
}
.depth-list li .select-label {
    position: relative;
    padding-left: 25px;
}
.depth-list li .select-label:before {
    content: '';
    position: absolute;
    top: 2px;
    left: 0;
    display: inline-block;
    width: 20px;
    height: 20px;
    background: url(../../assets/images/svg/icon-dropbox-depth.svg) no-repeat
        center;
    vertical-align: middle;
}
</style>
