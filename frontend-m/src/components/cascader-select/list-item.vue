<template>
    <ul class="select-options-list">
        <li
            :class="{
                selected: cascaderList.value === val.label,
            }"
            v-for="(val, index) in options"
            :key="index"
        >
            <label class="select-label">
                <input
                    type="radio"
                    v-model="cascaderList.value"
                    :name="cascaderList.name"
                    :value="val.value"
                    @change="changeInput(val.label )"
                >
                <span>{{ val.label }}</span>
            </label>
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
    data(){
        return{
        }
    },
    props: ['cascaderList', 'options'],
    methods: {
      changeInput(val){
        bus.$emit('changeInput', val);
      }
    },
};
</script>
<style scoped>
.select-list-wrap > .select-options-list > li > .select-label{
    background:#f7f7f7;
}
.select-options-list > li > .select-label {
    display:block;
    padding:0 20px;
    line-height:45px;
    font-size:14px;
    color:#000;
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
    padding:0 15px;

}
.depth-list li .select-label:before{
    content:"";
    display:inline-block;
    width:20px;
    height:20px;
    background:url(../../assets/images/svg/icon-dropbox-depth.svg) no-repeat center;
    vertical-align: middle;
}
</style>
