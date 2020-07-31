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
                    <Thumbnail
                        @cropImage="cropImage"
                        :imageFilePhysicalName="
                            detailData.imageFilePhysicalName
                        "
                    ></Thumbnail>
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
                                    required
                                />
                                <span></span>
                            </span>
                            <span>
                                {{ radio.title }}
                                {{ exposure.value }}
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
                            <div class="column">
                                <span class="select">
                                    <el-select
                                        v-model="category3Code.value"
                                        placeholder="Select"
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
                                v-model="agency.value"
                                placeholder="Select"
                            >
                                <el-option
                                    v-for="item in agency.listSortOptions"
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
                            <textarea v-model="detailData.goodsName" required />
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
                            v-model="detailData.goodsDescription"
                            required
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
                                required
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
                                required
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
    import {getProductDetail, postProduct} from '@/api/product';
    import {getExistMsg} from '@/utils/common';

    import Thumbnail from '@/components/thumbnail/index';

    export default {
    name: 'registration',
    components: {
        Thumbnail,
    },
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
                goodsName: '',
                goodsDescription: '',
                unitPrice: '',
                minimumOrderQuantity: '',
                imageBase64: '',
                imageFileName: 'text.jpg',
                exposureYn: '',
            },
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
                        label: '에이전시테스트',
                    },
                    {
                        value: '2',
                        label: '에이전시테스트2',
                    },
                ],
                value: '',
            },
        };
    },
    created() {
        this.detailProduct();
    },
    watch: {
        'category2Code.value'() {
            this.select3CodeFn();
        },
    },
    methods: {
        cropImage(imageBase64) {
            this.detailData.imageBase64 = imageBase64;
        },
        select3CodeFn() {
            this.category2Code.listSortOptions.forEach((item) => {
                if (item.value === this.category2Code.value) {
                    this.category3Code = item.category3Code;
                }
            });
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
                    if (this.detailData.exposureYn === 'N') {
                        this.exposure.value = 'N';
                    }
                    this.category2Code.value = this.detailData.category2Code;
                    this.select3CodeFn();
                    this.category3Code.value = this.detailData.category3Code;

                    //await getExistMsg(response);
                } catch (error) {
                    console.log(error);
                }
            }
        },
        async addProduct() {
            try {
                const { data: response } = await postProduct({
                    agencySeq: 1,
                    category2Code: this.category2Code.value,
                    category3Code: this.category3Code.value,
                    exposureYn: this.exposure.value,
                    goodsDescription: this.detailData.goodsDescription,
                    goodsName: this.detailData.goodsName,
                    imageBase64: this.detailData.imageBase64,
                    imageFileName: this.detailData.imageFileName,
                    minimumOrderQuantity: this.detailData.minimumOrderQuantity,
                    unitPrice: this.detailData.unitPrice,
                });
                await getExistMsg(response);
                console.log(response);
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
