<template>
    <div class="filter-select">
        <button type="button" @click="modalOpen">
            {{ selectLabel }}
        </button>
        <ListModal
            :selectList="selectList"
            :showList="showList"
            :selectLabel="selectLabel"
            @closeModal="closeModal"
            @changeLabelFn="changeLabelFn"
            @dimmedClose="dimmedClose"
        />
    </div>
</template>
<script>
import ListModal from '@/components/filter-select/list-modal';
import bus from "@/utils/bus";
export default {
    name: 'FilterSelect',
    data() {
        return {
            showList: false,
            selectLabel: '',
            hideLabel: '',
            topScollVal: '',
        };
    },
    props: ['selectList'],
    components: {
        ListModal,
    },
    computed:{
      pathUrl() {
        return this.$route.path;
      },
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
        bus.$on('closeFn', () => {
          if(this.showList){
            this.dimmedClose();
          }
        });
    },
    deactivated() {
    },
    destroyed() {
      if(this.showList){
        this.dimmedClose();
        document.querySelector(".modal-list.active").remove()
      }
    },
    methods: {
        changeLabelFn(value, label) {
            this.closeModal(label);
        },
        findLabel() {
            const _index = this.selectList.listSortOptions.findIndex(el => {
                return el.value === this.selectList.value;
            });
            this.selectLabel = this.selectList.listSortOptions[_index].label;
        },
        dimmedClose() {
          console.log(22222)
            this.showList = false;
            document.querySelector('.modal-list-open').style.overflow = '';
            document.querySelector('#wrap').style.margin = '';
            window.scrollTo(0, this.topScollVal);
            document.querySelector('body').classList.remove('modal-list-open');
        },
        closeModal(label) {
            this.hideLabel = label;
            this.showList = false;
            this.selectLabel = this.hideLabel;
            document.querySelector('.modal-list-open').style.overflow = '';
            document.querySelector('#wrap').style.margin = '';
            window.scrollTo(0, this.topScollVal);
            document.querySelector('body').classList.remove('modal-list-open');
        },
        modalOpen() {
            this.topScollVal = document.scrollingElement.scrollTop;
            this.showList = !this.showList;
            if (this.showList) {
                document.querySelector('body').classList.add('modal-list-open');
                document.querySelector('.modal-list-open').style.overflow =
                    'hidden';
                document.querySelector('#wrap').style.marginTop =
                    '-' + this.topScollVal + 'px';
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
