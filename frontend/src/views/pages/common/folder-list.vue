<template>
    <div>
        <h2 class="page-title">{{ this.$route.meta.title }}</h2>
        <div class="upload-summury">
            <strong class="title">파일올리기</strong>
            <p class="desc">
                자료는 NIKE.INC.와 NIKE KOREA LLC.의 자산입니다.<br />
                보안 규정을 준수하시기 바랍니다.
            </p>
            <button type="button" @click="infiniteScroll" class="btn-s-black">
                <span class="bebas">UPLOAD</span>
            </button>
        </div>
        <div class="sorting-area">
            <ListSorting v-bind:listTypes="listTypes" />
            <FilterSelect :listSortSelect="listSortSelect" />
            <SearchInput v-on:searchSubmit="searchSubmit" />
        </div>
        <template v-if="folderListData">
            <FolderList
                v-if="folderListData.length"
                v-bind:listTypes="listTypes"
                v-bind:folderListData="folderListData"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''" />
                <NoDataSearch v-else />
            </template>
        </template>
        <Loading v-if="loadingData" />
    </div>
</template>
<script>
import FilterSelect from '@/components/filter-select';
import ListSorting from '@/components/list-sorting/index';
import SearchInput from '@/components/search-input';
import FolderList from '@/components/folder-list';
import Loading from '@/components/folder-list/loading';
import NoData from '@/components/folder-list/nodata';
import NoDataSearch from '@/components/folder-list/nodata-search';

import { getContents } from '@/api/contents.js';

export default {
    name: 'folder-list',
    watch: {
        '$route.meta.menuCode'() {
            this.fetchData();
        },
        'listSortSelect.value'() {
            this.fetchData();
        },
    },
    mounted() {
        this.fetchData();
    },
    data() {
        return {
            lastPage: false,
            page: 0,
            searchKeyword: '',
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'LATEST',
                        label: '최신순',
                    },
                    {
                        value: 'START_DATE',
                        label: '시작일순',
                    },
                ],
                value: 'LATEST',
            },
            listTypes: [
                {
                    title: '컬럼타입',
                    active: true,
                },
                {
                    title: '로우타입',
                    active: false,
                },
            ],
            folderListLoadingList: [
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
                {
                    campaignBeginDt: '',
                    campaignEndDt: '',
                    campaignPeriodSectionCode: '',
                    contentsSeq: '',
                    folderContents: '',
                    folderName: '',
                    imageFileName: '',
                    imageFilePhysicalName: '',
                    imageFileSize: '',
                    menuCode: '',
                    readCount: 0,
                    topMenuCode: '',
                },
            ],
            folderListData: null,
            loadingData: false,
        };
    },
    components: {
        ListSorting,
        FilterSelect,
        FolderList,
        NoData,
        NoDataSearch,
        SearchInput,
        Loading,
    },
    methods: {
        searchSubmit(val) {
            this.searchKeyword = val;
            this.fetchData();
        },
        infiniteScroll() {
            if (this.folderListData.length !== 0) {
                this.fetchData(true);
            }
        },
        async fetchData(infinite) {
            this.loadingData = true;
            if (!infinite) {
                this.folderListData = null;
            }
            this.page = infinite ? this.page + 1 : 0;
            try {
                const {
                    data: { data: response },
                } = await getContents(this.$route.meta.topMenuCode, this.$route.meta.menuCode, {
                    page: this.page,
                    size: 4,
                    keyword: this.searchKeyword,
                    orderType: this.listSortSelect.value,
                });

                if (response.totalPages >= this.page) {
                    if (infinite) {
                        this.folderListData = this.folderListData.concat(response.content);
                    } else {
                        this.folderListData = response.content;
                    }
                }
                this.loadingData = false;
                return;
            } catch (error) {
                console.log(error);
            }
        },
    },
    created() {},
};
</script>
<style scoped></style>
