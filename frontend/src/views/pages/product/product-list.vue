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
                @addProductBasket="addProductBasket"
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
    import {postBasketSave} from '@/api/basket.js';

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
            addProductBasketArray: [],
            loadingData: false,
            page: 0,
            itemLength: 5,
            searchKeyword: '',
            visible: {
                detailView: false,
            },
        };
    },
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
            this.productDetailData = this.productListData[this.goodeSeqIndex(goodsSeq)];
        },

        // 카트 장바구니 누르면 배열에 상품이 담김
        // addProductBasket(goodsSeq) {
        //     this.addProductBasketArray.push(this.productListData[this.goodeSeqIndex(goodsSeq)]);
        //     console.log(this.addProductBasketArray);
        // },

        //상품 시퀀스가 속한 배열의 index 값을 리턴
        goodeSeqIndex(goodsSeq) {
            const goodsSeqIndex = this.productListData
                .map((data) => data.goodsSeq)
                .indexOf(goodsSeq);

            return goodsSeqIndex;
        },

        // 상품 장바구니에 담기
        async addProductBasket() {
            try {
                const {
                    data: { data: response },
                } = await postBasketSave({});
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
