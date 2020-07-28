<template>
    <div>
        <h2 class="page-title"><span class="ko">상품관리</span></h2>
        <div class="sorting-area">
            <div class="filter-select">
                <el-select
                    v-model="category2Val"
                    placeholder="Select"
                    style="width: 100px;"
                >
                    <el-option
                        v-for="item in category2List"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </div>
            <div class="filter-select">
                <el-select v-model="category3Val" placeholder="Select">
                    <el-option
                        v-for="item in category3List"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </div>
            <div class="filter-select">
                <el-select v-model="agencyVal" placeholder="Select">
                    <el-option
                        v-for="item in agency"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </div>
            <SearchInput @searchSubmit="searchSubmit" />
        </div>

        <div class="tbl-list" v-if="productListData.length">
            <table>
                <colgroup>
                    <col style="width: 60px;" />
                    <col style="width: 160px;" />
                    <col style="width: 219px;" />
                    <col style="width: 62px;" />
                    <col style="width: 89px;" />
                    <col style="width: 91px;" />
                    <col style="width: 88px;" />
                    <col style="width: 51px;" />
                </colgroup>
                <thead>
                    <tr>
                        <th>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                        </th>
                        <th>구분</th>
                        <th>상품명</th>
                        <th>최소<br />주문수량</th>
                        <th>AGENCY</th>
                        <th>최종수정자</th>
                        <th>최종수정일</th>
                        <th>노출</th>
                    </tr>
                </thead>
                <tbody>
                    <tr
                        v-for="(userProduct, index) in productListData"
                        :key="index"
                    >
                        <td>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                        </td>
                        <td>
                            {{ userProduct.category2Name }} >
                            {{ userProduct.category3Name }}
                        </td>
                        <td>
                            <a href="#" class="under-link">
                                <span>
                                    {{ userProduct.goodsName }}
                                </span>
                            </a>
                        </td>
                        <td>
                            {{ userProduct.minimumOrderQuantity }}
                        </td>
                        <td>{{ userProduct.agencyName }}</td>
                        <td>데이터 없음</td>
                        <td>{{ userProduct.updateDt }}</td>
                        <td>{{ userProduct.exposureYn }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div v-else>
            <NoData v-if="searchKeyword === ''" />
            <NoDataSearch v-else />
        </div>
        <div class="btn-tbl-box">
            <button type="button" class="btn-form">
                <span data-v-1756ba49="">삭제</span>
            </button>
            <div class="right">
                <button type="button" class="btn-form-gray">
                    <span data-v-1756ba49="">등록</span>
                </button>
            </div>
        </div>
        <!--        <el-pagination-->
        <!--            layout="prev, pager, next"-->
        <!--            :total="1000"-->
        <!--            :pager-count="10"-->
        <!--        />-->
    </div>
</template>
<script>
    import SearchInput from '@/components/search-input';
    import NoData from '@/components/product-list/nodata';
    import NoDataSearch from '@/components/product-list/nodata-search';
    import {getProductList} from '@/api/product';

    export default {
    name: 'management',
    data() {
        return {
            productListData: null,
            page: 0,
            itemLength: 100,
            searchKeyword: '',
            category2Code: '',
            category3Code: '',
            category2List: [
                {
                    value: '부자재',
                    label: '부자재',
                },
                {
                    value: 'NIKE BY YOU',
                    label: 'NIKE BY YOU',
                },
                {
                    value: 'CUSTOM23(JORDAN ONLY)',
                    label: 'CUSTOM23(JORDAN ONLY)',
                },
                {
                    value: 'MNQ',
                    label: 'MNQ',
                },
            ],
            category2Val: '대분류',
            category3List: [
                {
                    value: '최신순',
                    label: '최신순',
                },
                {
                    value: '시작일 순',
                    label: '시작일 순',
                },
            ],
            category3Val: '소구분',
            agency: [
                {
                    value: '최신순',
                    label: '최신순',
                },
                {
                    value: '시작일 순',
                    label: '시작일 순',
                },
            ],
            agencyVal: '전체 AGENCY',
        };
    },
    components: {
        SearchInput,
        NoData,
        NoDataSearch,
    },
    mounted() {
        this.getProduct();
    },
    methods: {
        // 상품 검색 api
        searchSubmit(val) {
            this.searchKeyword = val;
            this.getProduct();
        },

        // 상품 리스트 api
        async getProduct() {
            console.log(this.$route);
            try {
                const {
                    data: { data: response },
                } = await getProductList({
                    page: this.page,
                    size: this.itemLength,
                    category2Code: this.category2Code,
                    category3Code: this.category3Code,
                    keyword: this.searchKeyword,
                });

                this.productListData = response.content;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
