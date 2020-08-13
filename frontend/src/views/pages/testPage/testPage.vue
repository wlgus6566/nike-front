<template>
    <div>
        <button type="button" @click="openPop">123</button> {{ cloneTxt }}

        <testModal />
    </div>
</template>
<script>
import testModal from '@/components/modal-comp/testmodal';
import deepmerge from 'deepmerge';
export default {
    data() {
        return {
            x: [
                {
                    txt: '가',
                    tit: '가',
                },
                {
                    txt: '나',
                    tit: '가',
                },
            ],
            y: [
                {
                    txt: '가',
                    tit: '가',
                },
                {
                    txt: '나',
                    tit: '가',
                },
                {
                    txt: '다',
                    tit: '가',
                },
            ],
            test: {
                options: [
                    { value: null, label: '전체 권한그룹' },
                    {
                        value: 1,
                        label: 'ADMINISTRATOR',
                        children: [
                            {
                                value: 2,
                                label: 'string11111111111',
                                children: [
                                    { value: 7, label: 'string' },
                                    { value: 8, label: 'string' },
                                    { value: 10, label: 'string' },
                                    { value: 11, label: 'string' },
                                    { value: 12, label: 'string' },
                                    { value: 13, label: 'string' },
                                    { value: 14, label: 'string' },
                                    { value: 15, label: 'string' },
                                ],
                            },
                            {
                                value: 3,
                                label: 'USER1',
                                children: [{ value: 6, label: 'string' }],
                            },
                            { value: 4, label: 'USER2' },
                            { value: 5, label: 'USER3' },
                        ],
                    },
                ],
                value: [1, 2, 7],
            },
            cloneTxt: '',
        };
    },
    mounted() {
        this.test.value.forEach((el) => {
            this.testArr(this.test.options, el);
        });

        let concatArray = this.x.concat(this.y);

        console.log(concatArray);

        let testArray = this.x.filter((item) => {
            return this.y.every((el) => {
                console.log(item.tit !== el.tit || item.txt !== el.txt);
                return item.tit !== el.tit || item.txt !== el.txt;
            });
        });

        console.log(this.y.concat(testArray));
    },
    components: { testModal },
    methods: {
        openPop() {
            this.$modal.show('testModal');
        },

        testArr(arr, sep) {
            arr.forEach((el) => {
                if (el.value === sep) {
                    console.log(el.label);
                    this.cloneTxt = this.cloneTxt + el.label;
                }
                if (el.children) {
                    this.testArr(el.children, sep);
                }
            });
        },
    },
};
</script>
<style scoped></style>
