<template>
    <div>
        <FilterSelect :selectList="selectList"></FilterSelect>
        <CascaderSelect :cascaderList="authority"></CascaderSelect>

        <button type="button" @click="openModal">팝업</button> <br />
        <button type="button" @click="openModal2">팝업 full</button>
        <div class="modal-wrap">
            <ModalEx :visible.sync="visible.modalEx" />
            <ModalFull :visible.sync="visible.modalFull" />
        </div>
    </div>
</template>
<script>
import ModalEx from '@/components/modal-ex';
import ModalFull from '@/components/modal-ex/full.vue';
import FilterSelect from '@/components/filter-select';
import CascaderSelect from '@/components/cascader-select';
import { getAuthCacheList } from '@/api/auth';

export default {
    data() {
        return {
            selectList: {
                value: '최신순',
                options: [
                    {
                        label: '최신순',
                        value: '최신순',
                    },
                    {
                        label: '이름순',
                        value: '이름순',
                    },
                ],
            },
            authority: {
                value: ['all'],
	              name: 'authority',
                options: [
                    {
                        value: 'all',
                        label: '전체 그룹',
                    },
                ],
            },
            visible: {
                modalEx: false,
                modalFull: false,
            },
            userDataList: '',
        };
    },
    components: {
        ModalEx,
        ModalFull,
        FilterSelect,
        CascaderSelect,
    },
    created() {
        this.authCacheList();
    },
    mounted() {},
    methods: {
        openModal() {
            this.visible.modalEx = true;
        },
        openModal2() {
            this.visible.modalFull = true;
        },
        //권한 조회
        async authCacheList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthCacheList();

                this.recursionFn(response, this.authority.options, 1);
            } catch (error) {
                console.log(error);
            }
        },
        recursionFn(data, item, minIndx) {
            let _minIndx = minIndx;
            if (minIndx === undefined) {
                _minIndx = 0;
            }
            data.forEach((el, index) => {
                item.push({
                    value: el.authSeq,
                    label: el.authName,
                });
                if (el.subAuths) {
                    item[index + _minIndx].children = [];
                    this.recursionFn(
                        el.subAuths,
                        item[index + _minIndx].children
                    );
                }
            });
        },
    },
};
</script>
<style scoped></style>
