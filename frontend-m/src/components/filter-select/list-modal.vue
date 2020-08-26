<template>
    <section class="modal-list" :class="{ active: showList }">
        <div class="dimmed" @click="$emit('dimmedClose')"></div>
        <div class="modal-list-contents">
            <ul class="select-options-list">
                <li
                    :class="{
                        selected: selectList.value === val.value,
                    }"
                    v-for="(val, index) in selectList.listSortOptions"
                    :key="index"
                    @click="selectedFn(val.value, val.label)"
                >
                    <span>{{ val.label }}</span>
                </li>
            </ul>
        </div>
    </section>
</template>
<script>
export default {
    name: 'ListModal',
    data() {
        return {
            selectedIndex: null,
        };
    },
    props: ['selectList', 'showList', 'selectLabel'],
    mounted() {
        this.modal();
    },
    methods: {
        selectedFn(value, label) {
            this.selectList.value = value;
            this.$emit('changeLabelFn', value, label);
            // const seqList = this.options.map(el => el.label);
            // const _index = seqList.indexOf(label);
            // if (this.selectedIndex !== _index) {
            //     this.selectedIndex = _index;
            // } else {
            //     this.selectedIndex = null;
            // }
        },
        modal() {
            const app = document.querySelector('#app');
            const modal = document.querySelector('.modal-list');
            app.appendChild(modal);
        },
    },
};
</script>
<style scoped>
.modal-list {
    z-index: 2;
    position: fixed;
    bottom: -100%;
    right: 0;
    width: 100vw;
    height: 100vh;
}
.active .dimmed {
    transition: all 600ms ease;
}
.modal-list.active {
    bottom: 0;
}
.active .dimmed {
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.6);
}
.modal-list-contents {
    position: absolute;
    bottom: -100%;
    left: 0;
    width: 100%;
    padding: 20px 0;
    box-sizing: border-box;
    background: #fff;
    transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
}
.modal-list.active .modal-list-contents {
    bottom: 60px;
}
.select-options-list {
    max-height: 360px;
    overflow: auto;
}
.select-options-list li {
    padding: 0 20px;
    line-height: 45px;
    font-size: 14px;
    color: #000;
}
.select-options-list li.selected {
    color: #fa5400;
    font-weight: bold;
}
.select-options-list li.selected span:after {
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
