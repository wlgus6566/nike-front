<template>
    <ul class="sorting-list">
        <li
            :class="activeClass(item.value)"
            v-for="item in sectionCode.listSortOptions"
            :key="item.value"
        >
            <button
                type="button"
                @click="$emit('sectionCodeChange', item.value)"
            >
                <span>{{ item.title }}</span>
            </button>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'SortingList',
    props: ['sectionCode'],
    mounted() {
        console.log(this.sectionCode);
    },
    methods: {
        activeClass(value) {
            return value === this.sectionCode.value ? 'active' : '';
        },
    },
};
</script>
<style scoped>
.sorting-list {
    display: flex;
    margin-top: 55px;
    border-bottom: 1px solid #eee;
}
.sorting-list li {
    position: relative;
}
.sorting-list li + li:before {
    position: absolute;
    top: 50%;
    left: 0;
    content: '';
    display: block;
    width: 1px;
    height: 10px;
    margin-top: -5px;
    background: #ddd;
}
.sorting-list li button {
    display: block;
    font-size: 18px;
    color: #ccc;
    line-height: 21px;
    font-family: 'Bebas Neue', sans-serif;
}
.sorting-list li button span {
    position: relative;
    display: inline-block;
    padding: 11px 10px 10px;
    letter-spacing: 0.5px;
}
.sorting-list li.active button {
    color: #000;
}
.sorting-list li.active button span:before {
    content: '';
    position: absolute;
    bottom: -1px;
    left: 0;
    display: block;
    width: 100%;
    height: 2px;
    background: #000;
}
</style>
