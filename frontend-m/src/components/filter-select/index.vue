<template>
    <div class="filter-select">
        <select v-model="selectList.value" ref="select">
            <option
                v-for="(option, index) in selectList.options"
                :key="index"
                :value="option.value"
            >
                {{ option.label }}
            </option>
        </select>
    </div>
</template>
<script>
export default {
    name: 'filter-select',
    props: ['selectList'],
    mounted() {
        this.selectView();
    },
    methods: {
        selectView() {
            this.$refs.select.style.display = 'none';
            const parent = this.$refs.select.parentNode;
            console.log(parent);
            parent.createElement('<button>asdasd</button>');
        },
    },
};
</script>
<style scoped>
select {
    color: #000;
    font-size: 14px;
}
</style>
