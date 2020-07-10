<template>
    <div class="sorting-area">
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

        <SelectBox :listSortSelect="listSortSelect" />
        <SelectBox :listSortSelect="listSortSelect2" />

        <div class="search-input">
            <input type="text" placeholder="검색어를 입력해주세요." />
            <button type="button"><span>검색</span></button>
        </div>
    </div>
</template>
<script>
import SelectBox from '@/components/select-box';
export default {
    name: 'ListSorting',
    components: { SelectBox },
    data() {
        return {
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'new',
                        label:
                            '최신 순 최 신 순 최 신 순최신순최신순최신순최신순최신순최신순최신순',
                    },
                    {
                        value: 'startDate',
                        label: '시작일순',
                    },
                ],
                value: 'new',
            },
            listSortSelect2: {
                listSortOptions: [
                    {
                        value: 'new',
                        label: '최신순최신순최신순최신순최신순최신순최신순최신순최신순최신순',
                    },
                    {
                        value: 'startDate',
                        label: '시작일순',
                    },
                ],
                value: 'startDate',
            },
        };
    },
    props: ['listTypes'],
    methods: {
        update(index) {
            if (this.listTypes[index].active == false) {
                this.listTypes.forEach((element) => (element.active = false));
                this.listTypes[index].active = true;
            }
            // 자식 컴포넌트에서 부모 컴포넌트로 보내는 것이 $emit()
            //this.$emit("child",this.user)
        },
    },
};
</script>
<style scoped></style>
