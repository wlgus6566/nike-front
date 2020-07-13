<template>
    <div class="filter-list">
        <button
            type="button"
            class="type-list"
            v-bind:class="{ active: listType.active === true }"
            v-for="(listType, index) in listTypes"
            :key="index"
            v-on:click="update(index)"
        >
            <span>{{ listType.title }}</span>
        </button>
    </div>
</template>
<script>
export default {
    name: 'ListSorting',
    props: ['listTypes'],
    methods: {
        update(index) {
            if (this.listTypes[index].active == false) {
                this.listTypes.forEach((element) => (element.active = false));
                this.listTypes[index].active = true;
            }
        },
    },
};
</script>
<style scoped></style>
