<template>
    <ul class="sorting-list">
        <li
            :class="activeClass(item)"
            v-for="item in sectionCode.listSortOptions"
        >
            <button type="button" @click="$emit('sectionCodeChange', item)">
                <span>{{ item }}</span>
            </button>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'SortingList',
    props: ['sectionCode'],
    methods: {
        activeClass(item) {
            return item === this.sectionCode.value ? 'active' : '';
        },
    },
};
</script>
<style scoped></style>
