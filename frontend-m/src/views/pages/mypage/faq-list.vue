<template>
    <div>
        <ul class="sorting-tab">
            <li
                v-for="codeList in categoryCodeList.listSortOptions"
                key="index"
                :class="{ active: categoryCodeList.value === codeList.value }"
            >
                <a href="#" v-on:click="tabChange(codeList.value)">{{
                    codeList.label
                }}</a>
            </li>
        </ul>
        <ul class="faq-list">
            <li
                v-for="(item, index) in faqData"
                :key="index"
                :class="{ active: isActive === item.noticeArticleSeq }"
            >
                <a
                    href="#"
                    class="sbj"
                    @click.prevent="faqToggle(item.noticeArticleSeq)"
                >
                    <span class="category"
                        >[<em>{{ item.noticeArticleCategoryValue }}</em
                        >]</span
                    >
                    <span class="title">{{ item.title }}</span>
                    tttt{{ item.noticeArticleSeq }}
                    <i class="arrow"></i>
                </a>

                <transition @enter="itemOpen" @leave="itemClose">
                    <div class="cont" v-if="isActive === item.noticeArticleSeq">
                        {{ item.contents }}
                    </div>
                </transition>
            </li>
        </ul>
        <Pagination
            v-if="faqData.length"
            :itemLength="itemLength"
            :pageCount="pageCount"
            :totalItem="totalElements"
            @handleCurrentChange="handleCurrentChange"
        />
    </div>
</template>
<script>
import { getCustomerList, getCustomerDetail } from '@/api/customer/';
import { getCode } from '@/api/code/';
import { Cubic, gsap } from 'gsap/all';

export default {
    name: 'faq-list',
    data() {
        return {
            faqList: {},
            faqData: [],
            isActive: null,
            page: 0,
            pageCount: 11,
            itemLength: 10,
            keyword: '',
            totalElements: 0,
            activeSeq: null,
            categoryCodeList: {
                listSortOptions: [{ value: '', label: 'ALL' }],
                value: '',
            },
            noticeArticleCategoryCode: null,
        };
    },
    components: {
        Pagination: () => import('@/components/pagination/'),
    },
    mounted() {
        this.getCategoryCode();
        this.getFaqList();
        console.log(this.categoryCodeList.listSortOptions);
    },
    watch: {
        'categoryCodeList.value'() {
            this.getFaqList();
        },
    },
    methods: {
        async getFaqList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList('QNA', {
                    page: this.page,
                    size: this.itemLength,
                    noticeArticleCategoryCode: this.categoryCodeList.value,
                });
                console.log(response);
                this.faqList = response;
                this.faqData = response.content;
                this.totalElements = response.totalElements;
            } catch (error) {
                console.log(error);
            }
        },
        async getCategoryCode() {
            try {
                const {
                    data: { data: response },
                } = await getCode('NOTICE_CATEGORY_CODE');

                response.forEach((el) => {
                    this.categoryCodeList.listSortOptions.push({
                        value: el.code,
                        label: el.codeName,
                    });
                });
            } catch (error) {
                console.log(error);
            }
        },
        tabChange: function (code) {
            this.categoryCodeList.value = code;
        },
        // setActiveSeq: function (seq) {
        //     if (this.activeSeq === seq) {
        //         this.activeSeq = null;
        //     } else {
        //         this.activeSeq = seq;
        //     }
        // },
        handleCurrentChange(val) {
            this.page = val;
            this.getNoticeList();
        },
        //리스트 아코디언
        faqToggle(seq) {
            console.log('aa');
            if (seq === this.isActive) {
                this.isActive = null;
            } else {
                this.isActive = seq;
            }
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
                paddingTop: 30,
                paddingBottom: 30,
            });
            gsap.from(el, 0.3, {
                height: 0,
                paddingTop: 0,
                paddingBottom: 0,
                ease: Cubic.easeInOut,
                onComplete: function () {
                    el.style.height = 'auto';
                    el.style.paddingTop = '30px';
                    el.style.paddingBottom = '30px';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                paddingTop: 0,
                paddingBottom: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
    },
};
</script>
<style scoped></style>
