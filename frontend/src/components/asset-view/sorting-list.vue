<template>
    <ul class="sorting-list">
        <li
            :class="activeClass(item.value)"
            v-for="item in sectionCode.listSortOptions"
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
    methods: {
        activeClass(value) {
            return value === this.sectionCode.value ? 'active' : '';
        },
    },
};
</script>
<style scoped></style>
