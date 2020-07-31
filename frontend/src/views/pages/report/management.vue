<template>
    <div>
        <h2 class="page-title">
            {{ this.$route.meta.title }}
        </h2>
        <div class="sorting-area">
            <ListSorting :listTypes="listTypes" />
            <FilterSelect :listSortSelect="listSortSelect" />
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="reportListData">
            <ReportList
                v-if="reportListData.length"
                :listTypes="listTypes"
                :reportListData="reportListData"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading v-if="loadingData" />
    </div>
</template>
<script>
	import ReportList from '@/components/report-list';
	import FilterSelect from '@/components/filter-select';
	import ListSorting from '@/components/list-sorting';
	import SearchInput from '@/components/search-input';
	import NoData from '@/components/no-data';
	import Loading from '@/components/loading';
	import {getReportList} from '@/api/report';

	export default {
    name: 'management',
    data() {
        return {
            reportListData: null,
            page: 0,
            itemLength: 20,
            groupSeq: '',
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
            listSortSelect: {
                listSortOptions: [
                    {
                        value: 'ALL',
                        label: 'ALL',
                    },
                    {
                        value: 'SP',
                        label: 'SP',
                    },
                    {
                        value: 'SU',
                        label: 'SU',
                    },
                    {
                        value: 'FA',
                        label: 'FA',
                    },
                    {
                        value: 'HO',
                        label: 'HO',
                    },
                ],
                value: 'ALL',
            },
            loadingData: false,
            searchKeyword: '',
        };
    },
    components: {
        ReportList,
        FilterSelect,
        ListSorting,
        SearchInput,
        NoData,
        Loading,
    },
    mounted() {
        this.getReport();
    },
    watch: {
        'listSortSelect.value'() {
            this.getReport();
        },
    },
    methods: {
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getReport();
        },
        async getReport() {
            try {
                const {
                    data: { data: response },
                } = await getReportList({
                    page: this.page,
                    size: this.itemLength,
                    keyword: this.searchKeyword,
                    sectionCode: this.listSortSelect.value,
                    groupSeq: this.groupSeq,
                });
                console.log(response);
                this.reportListData = response.content;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
