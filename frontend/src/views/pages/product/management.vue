<template>
    <div>
        <h2 class="page-title">
            <span class="ko">상품관리</span>
        </h2>
        <div class="sorting-area" ref="focusOut" tabindex="0">
            <FilterSelect :listSortSelect="category2Code" />
            <FilterSelect
                :listSortSelect="category3Code"
                @selectFocus="selectFocus"
            />
            <FilterSelect :listSortSelect="agencySeq" />
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
import { delProduct, getProductList } from '@/api/product';
import { getAgencyContact } from '@/api/agency';
import { getCategoryList } from '@/utils/code';

export default {
    name: 'management',
    data() {
        return {
            productList: null,
            productListData: null,
            pageCount: 11,
            totalItem: 0,
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
            agencySeq: {
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
        'category2Code.value'(val) {
            if (val === '') {
                this.category3Code = {
                    listSortOptions: [
                        {
                            value: '',
                            label: '소구분',
                        },
                    ],
                    value: '',
                };
            } else {
                getCategoryList(val, this.category3Code.listSortOptions);
            }

            this.getProduct();
        },
        'category3Code.value'() {
            this.getProduct();
        },
        'agencySeq.value'() {
            this.getProduct();
        },
    },
    mounted() {
        this.getAgency();
        getCategoryList('CATEGORY', this.category2Code.listSortOptions);
    },
    activated() {
        this.getProduct();
    },
    methods: {
        selectFocus() {
            console.log(this.category2Code.value);
            if (this.category2Code.value === '') {
                alert('대구분을 선택해 주세요 ');
                this.$refs.focusOut.focus();
            }
        },
        //에이전시 리스트
        async getAgency() {
            try {
                const {
                    data: { data: response },
                } = await getAgencyContact();
                const agencyData = response;
                console.log(response);
                agencyData.forEach((item) => {
                    const agencyList = {
                        value: item.agencySeq,
                        label: item.agencyName,
                    };
                    this.agencySeq.listSortOptions.push(agencyList);
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
            if (this.checkItem.length > 0) {
                this.loading = true;
                if (!confirm('선택한 상품을 삭제하시겠습니까? ')) {
                    return false;
                }
                this.deleteLoading = this.checkItem;
                try {
                    await delProduct({
                        goodsSeqList: this.checkItem.toString(),
                    });
                    await this.getProduct();
                    await this.$store.dispatch('basketList');
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
            this.checkAll = false;
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
                    agencySeq: this.agencySeq.value,
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
<style scoped>
::v-deep .sorting-area {
    outline: transparent !important;
}
</style>
