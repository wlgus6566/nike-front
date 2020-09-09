<template>
    <div>
        <h2 class="page-title" v-if="this.$route.meta.title === 'OTHERS'">
            {{ title }}
        </h2>
        <h2 class="page-title" v-else>
            <span class="ko">{{ title }}</span>
        </h2>

        <div class="sorting-area">
            <SearchInput @searchSubmit="searchSubmit" />
        </div>
        <template v-if="userProductListData">
            <ProductList
                v-if="userProductListData.length"
                :userProductListData="userProductListData"
                @showDetailView="showDetailView"
            />
            <template v-else>
                <NoData v-if="searchKeyword === ''">
                    <i class="icon-folder-none"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
                <NoData v-else>
                    <i class="icon-search"></i>
                    <p class="desc">검색 결과가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
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
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import detailView from '@/views/pages/product/detail-view';

import { getUserProductList } from '@/api/product.js';
import { getWishCheck, postWishList } from '@/api/wish-list';

export default {
    name: 'product-list',
    data() {
        return {
            title: this.$route.meta.title,
            userProductListData: null,
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
            totalPage: null,
            page: 0,
            itemLength: 20,
            searchKeyword: '',
            wishListData: null,
            visible: {
                detailView: false,
            },
        };
    },
    created() {
        this.initGetUserProduct();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        this.serchReset();
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    components: {
        SearchInput,
        ProductList,
        Loading,
        NoData,
        detailView,
    },
    mounted() {
        //this.initGetUserProduct();
    },
    methods: {
        //검색후 페이지 이동 리셋
        serchReset() {
            this.searchKeyword = '';
            this.searchSubmit(this.searchKeyword);
        },
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.userProductListData.length >= this.itemLength &&
                this.userProductListData.length !== 0
            ) {
                console.log('infiniteScroll');
                this.getUserProduct(true);
            }
        },
        // 상품 검색 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.initGetUserProduct();
        },
        initGetUserProduct() {
            this.totalPage = null;
            this.page = 0;
            this.userProductListData = null;
            this.getUserProduct();
        },

        // 상품 리스트 api
        async getUserProduct(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await getUserProductList(this.$route.meta.category2Code, {
                    page: this.page,
                    size: this.itemLength,
                    category3Code: this.$route.meta.category3Code,
                    keyword: this.searchKeyword,
                });
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.userProductListData = this.userProductListData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.userProductListData = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },

        endPage() {
            alert('마지막 페이지');
        },

        // 상세 팝업
        showDetailView(goodsSeq) {
            this.visible.detailView = true;
            const findIndex = this.userProductListData.findIndex(
                (el) => el.goodsSeq === goodsSeq
            );
            this.productDetailData = this.userProductListData[findIndex];
        },

        // 위시리스트에 상품 추가
        async addWishList(goodsSeq) {
            console.log(goodsSeq.goodsSeq);
            try {
                const {
                    data: { data: response },
                } = await getWishCheck({
                    goodsSeq: goodsSeq.goodsSeq,
                });
                console.log(response);
                if (response.existMsg) {
                    alert(response.msg);
                }
                if (response.goodsSeq === null) {
                    try {
                        await postWishList({
                            goodsSeq: goodsSeq.goodsSeq,
                        });
                        alert(
                            '위시리스트에 추가 되었습니다.\n위시리스트는 마이페이지에서 확인가능합니다.'
                        );
                    } catch (error) {
                        console.error(error);
                    }
                } else {
                    alert('이미 담긴상품 입니다.');
                }
            } catch (error) {
                console.error(error);
                alert(error.data.msg);
                /*if (error.response.data.code === 'NO_AUTH') {
                    //router.go(-1);
                }*/
            }
        },
    },
};
</script>
<style scoped></style>
