<template>
    <div class="filter-select">
        <button type="button" @click="modalOpen">
            {{ selectLabel }}
        </button>
        <ListmModal
            :cascaderList="cascaderList"
            :showList="showList"
            @closeModal="closeModal"
            @changeInput="changeInput"
            @dimmedClose="dimmedClose"
        />
    </div>
</template>
<script>
import ListmModal from '@/components/cascader-select/list-modal';
import bus from '@/utils/bus';
export default {
    name: 'cascader-select',
    data() {
        return {
            showList: false,
            selectLabel: this.cascaderList.options[0].label,
            hideLabel: '',
        };
    },
    props: ['cascaderList'],
    components: {
        ListmModal,
    },
    // watch:{
    //   'cascaderList.value'(val){
    //     this.selectLabel = val
    //   }
    // },
    mounted() {
        bus.$on('changeInput', val => {
            if (val.disabled) return;
            this.closeModal(val);
        });
        bus.$on('closeFn', () => {
          if(this.showList){
            this.dimmedClose();
          }
        });
    },
    destroyed() {
      if(this.showList){
        this.dimmedClose();
        document.querySelector(".modal-list.active").remove()
      }
    },
    methods: {
        changeInput() {
            // console.log(1);
            // console.log(val);
            // this.selectLabel = val;
        },
        dimmedClose() {
            this.showList = false;
            document.querySelector('.modal-list-open').style.overflow = '';
            document.querySelector('#wrap').style.margin = '';
            window.scrollTo(0, this.topScollVal);
            document.querySelector('body').classList.remove('modal-list-open');
        },
        closeModal(val) {
            this.hideLabel = val.label;
            this.cascaderList.value = val.value;
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
