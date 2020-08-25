<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <h3 class="form-title mt20">상품등록</h3>
        <hr class="hr-black" />
        <form action="" @submit.prevent="addProduct">
            <ul class="form-list-thumb">
                <li class="form-row thumb-row">
                    <span
                        class="thumb-file"
                        :class="{
                            'file-upload':
                                detailData.imageBase64 ||
                                detailData.imageFilePhysicalName,
                        }"
                    >
                        <input type="file" @change="imageChange($event)" />
                        <span class="thumb">
                            <img
                                :src="detailData.imageBase64"
                                :alt="detailData.imageFileName"
                                v-if="detailData.imageBase64"
                            />
                            <img
                                :src="detailData.imageFilePhysicalName"
                                :alt="detailData.imageFileName"
                                v-if="
                                    detailData.imageFilePhysicalName &&
                                    !detailData.imageBase64
                                "
                            />
                        </span>
                        <span class="txt">
                            이미지 등록
                        </span>
                    </span>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">상태</span>
                    </div>
                    <div class="form-column">
                        <label
                            class="check-label"
                            v-for="(radio, index) in exposure.checkItem"
                            :key="index"
                        >
                            <span class="radio">
                                <input
                                    type="radio"
                                    v-model="exposure.value"
                                    :name="exposure.name"
                                    :value="radio.value"
                                />
                                <i></i>
                                <span class="txt">
                                    {{ radio.title }}
                                    {{ exposure.value }}
                                </span>
                            </span>
                        </label>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">구분</label>
                    </div>
                    <div class="form-column">
                        <div class="column-box">
                            <div class="column">
                                <span class="select">
                                    <el-select
                                        v-model="category2Code.value"
                                        placeholder="Select"
                                    >
                                        <el-option
                                            v-for="item in category2Code.listSortOptions"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value"
                                        >
                                        </el-option>
                                    </el-select>
                                </span>
                            </div>
                            <div class="column" ref="test" tabindex="0">
                                <span class="select">
                                    <el-select
                                        v-model="category3Code.value"
                                        placeholder="Select"
                                        @focus="selectFocus"
                                    >
                                        <el-option
                                            v-for="item in category3Code.listSortOptions"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value"
                                        >
                                        </el-option>
                                    </el-select>
                                </span>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">에이전시</label>
                    </div>
                    <div class="form-column">
                        <span class="select" style="width: 100%;">
                            <el-select
                                v-model="agencySeq.value"
                                placeholder="Select"
                            >
                                <el-option
                                    v-for="item in agencySeq.listSortOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                </el-option>
                            </el-select>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">상품 명</label>
                    </div>
                    <div class="form-column">
                        <span class="textarea">
                            <textarea v-model="detailData.goodsName" />
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">추가설명</label>
                    </div>
                    <div class="form-column">
                        <input
                            type="text"
                            ref="goodsDescription"
                            v-model="detailData.goodsDescription"
                        />
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">단가 입력</label>
                    </div>
                    <div class="form-column">
                        <span class="input-txt">
                            <input
                                type="text"
                                v-model="detailData.unitPrice"
                                @input="unitPriceVal"
                            />
                            <span class="txt">원</span>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required"
                            >최소 주문 수량</label
                        >
                    </div>
                    <div class="form-column">
                        <span class="input-txt">
                            <input
                                type="text"
                                v-model="detailData.minimumOrderQuantity"
                                @input="quantityVal"
                            />
                            <span class="txt">개</span>
                        </span>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />
            <div class="btn-area">
                <router-link to="/order/management" class="btn-s-white">
                    <span>취소</span>
                </router-link>
                <button type="submit" class="btn-s-black">
                    <span>저장</span>
                </button>
            </div>
        </form>
    </div>
</template>
<script>
import { getProductDetail, postProduct, putProduct } from '@/api/product';

import { getAgencyContact } from '@/api/agency';
import store from '@/store';
import { getCode } from '@/api/code';
import { getCategoryList } from '@/utils/code';
import { fileUpLoad } from '@/api/file';

export default {
    name: 'registration',
    components: {},
    data() {
        return {
            exposure: {
                checkItem: [
                    { value: 'Y', title: '노출' },
                    { value: 'N', title: '미노출' },
                ],
                name: 'exposure',
                value: 'Y',
            },
            detailData: {
                goodsName: null,
                goodsDescription: null,
                unitPrice: null,
                minimumOrderQuantity: null,
                imageBase64: null,
                imageFileName: null,
                exposureYn: null,
            },
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
    created() {
        this.getAgency();
        this.detailProduct();
        getCategoryList('CATEGORY', this.category2Code.listSortOptions);
    },
    activated() {
        this.detailProduct();
    },
    computed: {
        basketList() {
            if (!this.$store.state.basketListData) {
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
        },
    },

    methods: {
        selectFocus() {
            console.log(this.category2Code.value);
            if (this.category2Code.value === '') {
                alert('대구분을 선택해 주세요 ');
                this.$refs.test.focus();
            }
        },
        //이미지 페이지에 삽입
        imageChange(e) {
            //this.uploadFiles(e.target.files[0], device);
            const imaName = e.target.files[0].name;
            const reader = new FileReader();
            reader.onloadend = () => {
                console.log(reader.result);
                this.detailData.imageBase64 = reader.result;
                this.detailData.imageFileName = imaName;
            };
            if (e.target.files[0]) {
                reader.readAsDataURL(e.target.files[0]);
            } else {
                this.detailData.imageBase64 = '';
                this.detailData.imageFileName = '';
            }
        },

        //이미지 폼데이터로 변환
        // async uploadFiles(file, device) {
        //     console.log(device);
        //     const formData = new FormData();
        //     formData.append('uploadFile', file, file.name);
        //     try {
        //         const {
        //             data: { data: response },
        //         } = await fileUpLoad(formData);
        //         if (device === 'pc') {
        //             this.pcFormFile = response;
        //             console.log(this.pcFormFile);
        //         } else {
        //             this.moFormFile = response;
        //             console.log(this.moFormFile);
        //         }
        //     } catch (e) {
        //         console.log(e);
        //     }
        // },
        // 단가 입력 val
        unitPriceVal() {
            const numbers = /^[0-9]+$/;
            if (this.detailData.unitPrice.match(numbers) === null) {
                alert('숫자만 입력 가능합니다');
                this.detailData.unitPrice = '1';
            } else if (this.detailData.unitPrice == 0) {
                this.detailData.unitPrice = '1';
            }
        },
        //수량 입력 val
        quantityVal() {
            const numbers = /^[0-9]+$/;
            if (this.detailData.minimumOrderQuantity.match(numbers) === null) {
                alert('숫자만 입력 가능합니다');
                this.detailData.minimumOrderQuantity = '1';
            } else if (this.detailData.minimumOrderQuantity == 0) {
                this.detailData.minimumOrderQuantity = '1';
            }
        },
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
                    this.agencySeq.listSortOptions.push(agencyList);
                });
            } catch (error) {
                console.log(error);
            }
        },
        //이미지 받아오기
        cropImage(imageBase64, imgName) {
            this.detailData.imageBase64 = imageBase64;
            this.detailData.imageFileName = imgName;
        },
        validateSelect(e, prop) {
            this.$refs.form.validateField(prop, (error) => {
                if (!error) {
                    alert('submit!');
                } else {
                    console.log('error on selector');
                    return false;
                }
            });
        },
        // 상품 상세 불러오기
        async detailProduct() {
            if (this.$route.params.id) {
                try {
                    const { data: response } = await getProductDetail(
                        this.$route.params.id,
                        {
                            goodsSeq: this.$route.params.id,
                        }
                    );
                    this.detailData = response.data;
                    console.log(this.detailData);
                    if (this.detailData.exposureYn === 'N') {
                        this.exposure.value = 'N';
                    }
                    this.category2Code.value = this.detailData.category2Code;
                    this.category3Code.value = this.detailData.category3Code;
                    this.agencySeq.value = this.detailData.agencySeq;
                } catch (error) {
                    console.log(error);
                }
            }
        },
        //상품 등록
        async addProduct() {
            const data = {
                agencySeq: this.agencySeq.value,
                category2Code: this.category2Code.value,
                category3Code: this.category3Code.value,
                exposureYn: this.exposure.value,
                goodsDescription: this.detailData.goodsDescription,
                goodsName: this.detailData.goodsName,
                imageBase64: this.detailData.imageBase64,
                imageFileName: this.detailData.imageFileName,
                minimumOrderQuantity: this.detailData.minimumOrderQuantity,
                unitPrice: this.detailData.unitPrice,
            };
            if (Object.values(data).some((el) => el === '' || el === null)) {
                alert('필수 입력 값이 누락 되었습니다.');
                return;
            }
            if (this.$route.params.id) {
                let addAlert = confirm('수정하시겠습니까');
                if (addAlert) {
                    try {
                        const { data: response } = await putProduct(
                            this.$route.params.id,
                            data
                        );
                        // await getExistMsg(response);
                        console.log(response);
                        await this.$router.push('/order/management');
                        await store.dispatch('basketList');
                        this.detailData.imageBase64 = null;
                    } catch (error) {
                        console.log(error);
                    }
                }
            } else {
                let addAlert = confirm('저장하시겠습니까');
                if (addAlert) {
                    try {
                        const { data: response } = await postProduct(data);
                        // await getExistMsg(response);
                        this.productDataReset();
                        await this.$router.push('/order/management');
                    } catch (error) {
                        console.log(error);
                    }
                }
            }
        },
        // category data
        async getCategoryList(codeName, array) {
            try {
                const {
                    data: { data: response },
                } = await getCode(codeName);

                response.forEach((el) => {
                    array.push({
                        value: el.code,
                        label: el.codeName,
                    });
                });
            } catch {}
        },
        //데이터 초기화
        productDataReset() {
            this.agencySeq.value = '';
            this.category2Code.value = '';
            this.category3Code.value = '';
            this.exposure.value = 'Y';
            this.detailData.goodsDescription = '';
            this.detailData.goodsName = '';
            this.detailData.imageBase64 = '';
            this.detailData.imageFileName = '';
            this.detailData.minimumOrderQuantity = '';
            this.detailData.unitPrice = '';
            this.imageFilePhysicalName = '';
        },
    },
};
</script>
<style scoped>
::v-deep .column {
    outline: transparent !important;
}
</style>
