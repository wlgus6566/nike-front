<template>
    <div>
        <div class="sorting-area">
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="productListData">
            <ProductList
                v-if="productListData.length"
                :productListData="productListData"
                @showDetailView="showDetailView"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''" />
                <NoDataSearch v-else />
            </template>
        </template>
        <Loading v-if="loadingData" />
        <detailView
            :visible.sync="visible.detailView"
            :productDetailData="productDetailData"
            @addWishList="addWishList"
        />
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
    import {getWishList, postWishList} from '@/api/wish-list';

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
            itemLength: 20,
            wishListData: null,
            searchKeyword: '',
            visible: {
                detailView: false,
            },
        };
    },
    created() {
        this.getProduct();
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
        this.getProduct();
        this.getWishiList();
    },
    activated() {},
    methods: {
        // 상품 검색 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getProduct();
        },

        // 상품 리스트 api
        async getProduct() {
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

        // 상세 팝업
        showDetailView(goodsSeq) {
            this.visible.detailView = true;
            const findIndex = this.productListData.findIndex(
                (el) => el.goodsSeq === goodsSeq
            );
            this.productDetailData = this.productListData[findIndex];
        },

        // 위시리스트 목록 가져오기
        async getWishiList() {
            try {
                const {
                    data: { data: response },
                } = await getWishList({
                    page: this.page,
                    size: this.itemLength,
                });
                this.wishListData = response.content;
            } catch (error) {
                console.log(error);
            }
        },

        // 위시리스트에 상품 추가
        async addWishList(goodsSeq) {
            try {
                const findIndex = this.wishListData.findIndex(
                    (el) => el.goodsSeq === goodsSeq.goodsSeq
                );
                if (findIndex == -1) {
                    await postWishList({
                        goodsSeq: goodsSeq.goodsSeq,
                    });
                    await this.getWishiList();
                    alert(
                        '위시리스트에 추가 되었습니다.\n 위시리스트는 마이페이지에서 확인가능합니다.'
                    );
                } else {
                    alert('이미 담긴 상품입니다.');
                }
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
