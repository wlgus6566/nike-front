<template>
    <ul class="faq-tab">
        <li
            v-for="(item, index) in categoryCodeList.listSortOptions"
            :key="index"
            :class="{ active: categoryCodeList.value === item.value }"
        >
            <a href="#" @click.prevent="$emit('tabFnc', item.value)">
                <span v-html="item.label"></span>
            </a>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'faq-tab',
    data() {
        return {};
    },
    props: ['categoryCodeList', 'isTabActive'],
    methods: {},
};
</script>
