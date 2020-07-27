<template>
    <div>
        <div class="sorting-area">
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="productListData">
            <ProductList
                v-if="productListData.length"
                :productListData="productListData"
                @showdetailView="showdetailView"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''" />
                <NoDataSearch v-else />
            </template>
        </template>
        <Loading v-if="loadingData" />
        <detailView :visible.sync="visible.detailView" :productDetailData="productDetailData" />
    </div>
</template>
<script>
    import SearchInput from '@/components/search-input';
    import ProductList from '@/components/product-list';
    import Loading from '@/components/product-list/loading';
    import NoData from '@/components/product-list/nodata';
    import NoDataSearch from '@/components/product-list/nodata-search';
    import detailView from '@/views/pages/product/detail-view';

    import {getProductList} from '@/api/product.js';

    export default {
    name: 'product-list',
    data() {
        return {
            productListData: null,
            productDetailData: {
                goodsName: '',
                goodsDescription: '',
                imageFilePhysicalName: '',
                imageFileName: '',
                unitPrice: '',
                minimumOrderQuantity: '',
                exposureYn: '',
            },
            loadingData: false,
            page: 0,
            itemLength: 5,
            searchKeyword: '',
            visible: {
                detailView: false,
            },
        };
    },
    created() {},
    components: {
        SearchInput,
        ProductList,
        Loading,
        NoData,
        NoDataSearch,
        detailView,
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        // 상품 리스트 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.fetchData();
        },
        // 상품 리스트 api
        async fetchData() {
            try {
                const {
                    data: { data: response },
                } = await getProductList({
                    page: this.page,
                    size: this.itemLength,
                    category2Code: this.$route.meta.category2Code,
                    category3Code: this.$route.meta.category3Code,
                    keyword: this.searchKeyword,
                });
                this.productListData = response.content;
            } catch (error) {
                console.log(error);
            }
        },

        // detial modal open
        showdetailView(goodsSeq) {
            this.visible.detailView = true;
            const findIndex = this.productListData.findIndex((el) => el.goodsSeq === goodsSeq);
            this.productDetailData = this.productListData[findIndex];
        },

        /*// 상품 장바구니에 담기
        async addProductBasket(goodsSeq) {
            try {
                const {
                    data: { data: response },
                } = await postBasketSave({
                    goodsSeq: goodsSeq,
                    orderQuantity: 1,
                });
                await this.$store.dispatch('basketList');
            } catch (error) {
                console.log(error);
            }
        },*/
    },
};
</script>
<style scoped></style>
