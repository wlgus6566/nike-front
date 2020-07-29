<template>
    <div>
        {{ checkItem }}
        <h2 class="page-title"><span class="ko">상품관리</span></h2>
        <div class="sorting-area">
            <FilterSelect :listSortSelect="category2Code" />
            <FilterSelect :listSortSelect="category3Code" />
            <FilterSelect :listSortSelect="agency" />
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="productListData">
            <ProductManagement
                v-if="productListData.length"
                :productListData="productListData"
                :checkItem="checkItem"
                :checkAll="checkAll"
                :loading="loading"
                @allCheckFn="allCheckFn"
                @checked="checked"
                @checkDel="checkDel"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''" />
                <NoDataSearch v-else />
            </template>
            <el-pagination
                v-if="productListData.length"
                @current-change="handleCurrentChange"
                :current-page.sync="page"
                :pager-count="11"
                layout="prev, pager, next"
                :total="totalItem"
            >
            </el-pagination>
        </template>
    </div>
</template>
<script>
    import SearchInput from '@/components/search-input';
    import FilterSelect from '@/components/filter-select';
    import ProductManagement from '@/components/product-management';
    import NoData from '@/components/product-management/nodata';
    import NoDataSearch from '@/components/product-management/nodata-search';
    import {delProduct, getProductList} from '@/api/product';

    export default {
    name: 'management',
    data() {
        return {
            totalItem: 0,
            productList: null,
            productListData: null,
            page: 0,
            itemLength: 20,
            searchKeyword: '',
            checkAll: false,
            checkItem: [],
            deleteLoading: [],
            loading: false,
            category2Code: {
                listSortOptions: [
                    {
                        value: '',
                        label: '대구분',
                        category3Code: {
                            listSortOptions: [
                                {
                                    value: '',
                                    label: '소구분',
                                },
                            ],
                            value: '',
                        },
                    },
                    {
                        value: 'SUBSIDIARY',
                        label: '부자재',
                        category3Code: {
                            listSortOptions: [
                                {
                                    value: '',
                                    label: '소구분',
                                },
                                {
                                    value: 'SUBSIDIARY21',
                                    label: '운영 비품',
                                },
                                {
                                    value: 'SUBSIDIARY22',
                                    label: '스태프 비품',
                                },
                                {
                                    value: 'SUBSIDIARY23',
                                    label: '운영 사이니지',
                                },
                                {
                                    value: 'SUBSIDIARY24',
                                    label: '세일 사이니지',
                                },
                                {
                                    value: 'SUBSIDIARY25',
                                    label: '오픈 패키지',
                                },
                                {
                                    value: 'SUBSIDIARY26',
                                    label: '나이키 골프',
                                },
                            ],
                            value: '',
                        },
                    },
                    {
                        value: 'NIKE_BY_YOU',
                        label: 'NIKE BY YOU',
                        category3Code: {
                            listSortOptions: [
                                {
                                    value: '',
                                    label: '소구분',
                                },
                                {
                                    value: 'NIKE_BY_YOU27',
                                    label: '신발 커스텀(단품)',
                                },
                                {
                                    value: 'NIKE_BY_YOU28',
                                    label: '신발 커스텀(패키지)',
                                },
                                {
                                    value: 'NIKE_BY_YOU29',
                                    label: '의류 커스텀(단품)',
                                },
                                {
                                    value: 'NIKE_BY_YOU30',
                                    label: '의류 커스텀(패키지)',
                                },
                                {
                                    value: 'NIKE_BY_YOU31',
                                    label: 'OTHERS',
                                },
                            ],
                            value: '',
                        },
                    },
                    {
                        value: 'CUSTOM23',
                        label: 'CUSTOM23(JORDAN ONLY)',
                        category3Code: {
                            listSortOptions: [
                                {
                                    value: '',
                                    label: '소구분',
                                },
                                {
                                    value: 'CUSTOM2332',
                                    label: '신발 커스텀(단품)',
                                },
                                {
                                    value: 'CUSTOM2333',
                                    label: '신발 커스텀(패키지)',
                                },
                                {
                                    value: 'CUSTOM2334',
                                    label: '의류 커스텀(단품)',
                                },
                                {
                                    value: 'CUSTOM2335',
                                    label: '의류 커스텀(패키지)',
                                },
                                {
                                    value: 'CUSTOM2336',
                                    label: 'OTHERS',
                                },
                            ],
                            value: '',
                        },
                    },
                    {
                        value: 'MNQ',
                        label: 'MNQ',
                        category3Code: {
                            listSortOptions: [
                                {
                                    value: '',
                                    label: '소구분',
                                },
                                {
                                    value: 'MNQ37',
                                    label: '남성',
                                },
                                {
                                    value: 'MNQ38',
                                    label: '여성',
                                },
                                {
                                    value: 'MNQ39',
                                    label: '유아동',
                                },
                                {
                                    value: 'MNQ40',
                                    label: '수리/보수',
                                },
                            ],
                            value: '',
                        },
                    },
                ],
                value: '',
            },
            category3Code: {
                listSortOptions: [
                    {
                        value: '',
                        label: '소구분',
                    },
                ],
                value: '',
            },
            agency: {
                listSortOptions: [
                    {
                        value: '',
                        label: '에이전시',
                    },
                ],
                value: '',
            },
        };
    },
    components: {
        SearchInput,
        FilterSelect,
        ProductManagement,
        NoData,
        NoDataSearch,
    },
    created() {
        //console.log(this.productList.content);
    },
    computed: {},
    watch: {
        'category2Code.value'() {
            this.category2Code.listSortOptions.forEach((item) => {
                if (item.value === this.category2Code.value) {
                    this.category3Code = item.category3Code;
                }
            });
            this.getProduct();
        },
        'category3Code.value'() {
            this.getProduct();
        },
    },
    mounted() {
        this.getProduct();
    },
    methods: {
        handleCurrentChange(val) {
            console.log(val);
            this.getProduct();
        },
        // checkbox
        checked(seq, del) {
            //배열의 seq의 인덱스 값 반환
            const indexOfChecked = this.checkItem.findIndex((el) => el === seq);
            //del 아니거나 인데스 값이 -1 이면 checkItem배열에 seq 추가
            if (!del && indexOfChecked === -1) {
                this.checkItem.push(seq);
                //인덱스 값이 -1이 아니거니 del이면 false 반환
            } else {
                this.checkItem = this.checkItem.filter((el) => {
                    return el !== seq;
                });
            }
            this.checkAll =
                this.checkItem.length === this.productListData.length;
        },
        // 전체 checkbox
        allCheckFn() {
            this.checkAll = !this.checkAll;
            if (this.checkAll) {
                this.productListData.forEach((el) => {
                    const indexOfChecked = this.checkItem.findIndex(
                        (elChecked) => elChecked === el.goodsSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkItem.push(el.goodsSeq);
                    }
                });
            } else {
                this.checkItem = [];
            }
        },
        isLoading(seq) {
            const indexFind = this.deleteLoading.findIndex((el) => {
                return el === seq;
            });
            return indexFind !== -1;
        },
        async checkDel() {
            this.loading = true;
            if (this.checkItem.length > 0) {
                if (!confirm('선택한 상품을 삭제하시겠습니까? ')) {
                    return false;
                }
                this.deleteLoading = this.checkItem;
                try {
                    await delProduct({
                        goodsSeqList: this.checkItem.toString(),
                    });
                    await this.getProduct();
                    this.loading = false;
                    this.checkItem.forEach((seq) => {
                        this.checked(seq, true);
                    });
                    this.deleteLoading = [];
                } catch (error) {
                    console.log(error);
                    if (error.data.existMsg) {
                        alert(error.data.msg);
                    }
                }
            } else {
                alert('하나 이상의 상품을 선택해 주세요.');
            }
        },
        // 상품 검색 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getProduct();
        },

        // 상품 리스트 api
        async getProduct() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getProductList({
                    page: this.page,
                    size: this.itemLength,
                    category2Code: this.category2Code.value,
                    category3Code: this.category3Code.value,
                    keyword: this.searchKeyword,
                    agencySeq: this.agency.value,
                });
                this.productList = response;
                this.productListData = response.content;
                this.loading = false;
                this.totalItem = this.productList.totalElements;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
