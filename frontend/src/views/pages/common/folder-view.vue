<template>
    <div>
        {{ viewData }}
        <BtnArea></BtnArea>
        <folder :items="items"></folder>
        <SortingList></SortingList>
        <fileItem
            v-on:checkAllChange="checkAllChange"
            v-on:accordion="accordion"
            v-on:checkedCitiesChange="checkedCitiesChange"
            :items="items.contentsFileList"
            :checkAll="checkAll"
            :checkTxt="checkTxt"
        ></fileItem>
    </div>
</template>
<script>
    import BtnArea from '@/components/asset-view/btn-area.vue';
    import folder from '@/components/asset-view/folder.vue';
    import SortingList from '@/components/asset-view/sorting-list.vue';
    import fileItem from '@/components/asset-view/file-Item.vue';
    /*
		import { createNewPost, fetchPosts } from '@/api/contents';
		import { contents } from '@/api';
		import axios from 'axios';
		const ApiDefault = { url: '/api/contents' };
		ApiDefault.instance = axios.create({ baseURL: `${process.env.VUE_APP_API_URL}` });
		 */

export default {
    name: 'folder-view',
    data() {
        return {
            checkTxt: {
                state: false,
                length: 0,
            },
            viewData: 11,
            checkAll: {
                state: false,
            },
            items: {
                folderName: 'SP20 NSW NIKE DIRECT AM90',
                folderContents: 'SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료',
                campaignBeginDt: '2020.07.01 11:58:19',
                campaignEndDt: '2020.07.01 11:58:19',
                memo: '',
                contentsFileList: [
                    {
                        fileKindCode: '',
                        title: 'SP20 NSW NIKE DIRECT AM90',
                        imageFileName: require('@/assets/images/img-asset-none@2x.png'),
                        url: null,
                        downloadCount: 0,
                    },
                    {
                        fileKindCode: '',
                        title: 'SP20 NSW NIKE DIRECT AM902',
                        imageFileName: require('@/assets/images/img-asset-none@2x.png'),
                        url: 'https://kr.vuejs.org/v2/guide/conditional.html',
                        downloadCount: 0,
                    },
                ],
            },
        };
    },
    created() {
        this.items.contentsFileList.forEach((el) => {
            el.acd = false;
            el.state = false;
        });
    },
    mounted() {
        //this.fetchData();
    },
    components: {
        BtnArea,
        folder,
        SortingList,
        fileItem,
    },
    computed: {
        checkLength() {
            return (this.checkTxt.length = this.items.contentsFileList.filter((el) => {
                if (el.state) {
                    return el;
                }
            }).length);
        },
    },
    methods: {
        /*
        async fetchData() {
            try {
                const {
                    data: { contents: viewData },
                } = await ApiDefault.instance.get(`api/contents/ASSET/SP/4`);

                ``;
                this.viewData = viewData;
                return;
            } catch (error) {
                console.log(error);
            }
        },*/
        checkAllChange(value) {
            this.items.contentsFileList.forEach((el) => {
                if (el.url == null) {
                    el.state = value;
                }
            });
        },
        checkedCitiesChange() {
            const length = this.items.contentsFileList.length;
            this.checkAll.state = length === this.checkLength;
        },
        accordion(idx) {
            console.log(idx);
            console.log(this.items.contentsFileList[idx].acd);
            this.items.contentsFileList[idx].acd = !this.items.contentsFileList[idx].acd;
        },
    },
};
</script>
<style scoped></style>
