<template>
    <div class="filter-select">
        <button type="button" @click="openModal">
            {{ selectLabel }}
        </button>
        <ListModal
            :selectList="selectList"
            :showList="showList"
            :selectLabel="selectLabel"
            @closeModal="closeModal"
        />
    </div>
</template>
<script>
import ListModal from '@/components/filter-select/list-modal';
export default {
    name: 'FilterSelect',
    data() {
        return {
            showList: false,
            selectLabel: '',
        };
    },
    props: ['selectList'],
    components: {
        ListModal,
    },
    watch: {
        'selectList.value'() {
            const _index = this.selectList.listSortOptions.findIndex(el => {
                return el.value === this.selectList.value;
            });
            this.selectLabel = this.selectList.listSortOptions[_index].label;
        },
    },
    mounted() {
        this.findLabel();
    },
    methods: {
        findLabel() {
            const _index = this.selectList.listSortOptions.findIndex(el => {
                return el.value === this.selectList.value;
            });
            this.selectLabel = this.selectList.listSortOptions[_index].label;
        },
        closeModal() {
            this.showList = false;
            document.querySelector('body').classList.remove('modal-list-open');
        },
        openModal() {
            this.showList = !this.showList;
            if (this.showList) {
                console.log(document.scrollingElement.scrollTop);
                document.querySelector('body').classList.add('modal-list-open');
            }
        },
    },
};
</script>
<style scoped>
.filter-select {
    display: inline-flex;
    padding: 15px 0;
}
.filter-select button {
    color: #000;
    font-size: 14px;
    line-height: 20px;
    font-weight: bold;
    padding-right: 25px;
    background: url(../../assets/images/svg/icon-filter-arrow-down.svg)
        no-repeat center right;
}
</style>
