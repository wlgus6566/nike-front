<template>
    <div class="filter-select">
        <button type="button" @click="modalSelect">
            {{ selectList.value }}
        </button>
        <ListmModal
            :selectList="selectList"
            :showList="showList"
            @closeModal="closeModal"
        />
    </div>
</template>
<script>
import ListmModal from '@/components/filter-select/list-modal';
export default {
    name: 'filter-select',
    data() {
        return {
            showList: false,
        };
    },
    props: ['selectList'],
    components: {
        ListmModal,
    },
    mounted() {},
    methods: {
        closeModal() {
            this.showList = false;
            document.querySelector('body').classList.remove('modal-list-open');
        },
        modalSelect() {
            this.showList = !this.showList;
            if (this.showList) {
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
