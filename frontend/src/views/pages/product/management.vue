<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
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
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-file"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
            <Pagination
                v-if="productListData.length"
                :itemLength="itemLength"
                :pageCount="pageCount"
                :totalItem="totalItem"
                @handleCurrentChange="handleCurrentChange"
            />
        </template>
    </div>
</template>
<script>
    import SearchInput from '@/components/search-input';
    import FilterSelect from '@/components/filter-select';
    import ProductManagement from '@/components/product-management';
    import Pagination from '@/components/pagination';
    import NoData from '@/components/no-data';
    import {delProduct, getProductList} from '@/api/product';
    import {getAgencyContact} from '@/api/agency';

    export default {
    name: 'management',
    data() {
        return {
            productList: null,
            productListData: null,
            pageCount: 11,
            totalItem: 0,
            page: 0,
            itemLength: 2,
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
        Pagination,
        NoData,
    },
    created() {},
    computed: {
        basketList() {
            if (!!this.$store.state.basketListData) {
                return this.$store.state.basketListData;
            } else {
                return [];
            }
        },
    },
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
        'agency.value'() {
            return 'dfdsf';
        },
    },
    mounted() {
        this.getProduct();
        this.getAgency();
    },
    activated() {
        this.getProduct();
    },
    methods: {
        //에이전시 리스트
        async getAgency() {
            try {
                const {
                    data: { data: response },
                } = await getAgencyContact({});
                const agencyData = response;
                agencyData.forEach((item, index) => {
                    const agencyList = {
                        value: item.agencySeq,
                        label: item.agencyName,
                    };
                    this.agency.listSortOptions.push(agencyList);
                });
            } catch (error) {
                console.log(error);
            }
        },

        // Pagination fn
        handleCurrentChange(val) {
            this.page = val;
            this.getProduct();
        },
        // checkbox
        checked(seq, del) {
            const indexOfChecked = this.checkItem.findIndex((el) => el === seq);
            if (!del && indexOfChecked === -1) {
                this.checkItem.push(seq);
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
                    //todo 장바구니 다건 삭제 추가
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
