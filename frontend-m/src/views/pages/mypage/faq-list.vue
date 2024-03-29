<template>
    <div>
        <ul class="sorting-tab line2">
            <li
                :class="{ active: categoryCodeList.value === codeList.value }"
                v-for="(codeList, index) in categoryCodeList.listSortOptions"
                :key="index"
            >
                <a href="#" v-on:click="tabChange(codeList.value)">{{
                    codeList.label
                }}</a>
            </li>
        </ul>

        <template v-if="faqData">
            <ul class="faq-list" v-if="faqData.length > 0">
                <li
                    v-for="(item, index) in faqData"
                    :class="{ active: item.noticeArticleSeq === activeSeq }"
                    :key="index"
                >
                    <a
                        href="#"
                        class="sbj"
                        @click.prevent="setActiveSeq(item.noticeArticleSeq)"
                    >
                        <span class="category">
                            [<em>{{ item.noticeArticleCategoryValue }}</em
                            >]
                        </span>
                        <span class="title">{{ item.title }}</span>
                        <i class="arrow"></i>
                    </a>
                    <transition @enter="itemOpen" @leave="itemClose">
                        <div
                            class="cont"
                            v-if="activeSeq === item.noticeArticleSeq"
                            v-html="item.contents"
                        ></div>
                    </transition>
                </li>
            </ul>
            <NoData v-else>
                <i class="icon-upload"></i>
                <p class="desc">등록된 데이터가 없습니다.</p>
            </NoData>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
        <template v-if="faqData">
            <Pagination
                v-if="faqData.length"
                :itemLength="itemLength"
                :pageCount="pageCount"
                :totalItem="totalElements"
                @handleCurrentChange="handleCurrentChange"
            />
        </template>
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
            page: 0,
            pageCount: 5,
            itemLength: 10,
            keyword: '',
            totalElements: 0,
            activeSeq: null,
            categoryCodeList: {
                listSortOptions: [{ value: '', label: 'ALL' }],
                value: '',
            },
            noticeArticleCategoryCode: null,
            loadingData: false,
        };
    },
    components: {
        Pagination: () => import('@/components/pagination/'),
        Loading: () => import('@/components/loading/'),
        NoData: () => import('@/components/no-data'),
    },
    mounted() {
        this.getCategoryCode();
        this.getFaqList();
    },
    watch: {
        'categoryCodeList.value'() {
            this.totalElements = 0;
            this.page = 0;
            this.faqData = null;
            this.getFaqList();
        },
    },
    methods: {
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
                onComplete: function() {
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
        async getFaqList() {
            try {
                const {
                    data: { data: response },
                } = await getCustomerList('QNA', {
                    page: this.page,
                    size: this.itemLength,
                    noticeArticleCategoryCode: this.categoryCodeList.value,
                });
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

                response.forEach(el => {
                    this.categoryCodeList.listSortOptions.push({
                        value: el.code,
                        label: el.codeName,
                    });
                });
            } catch (error) {
                console.log(error);
            }
        },
        tabChange: function(code) {
            this.categoryCodeList.value = code;
        },
        setActiveSeq: function(seq) {
            if (this.activeSeq === seq) {
                this.activeSeq = null;
            } else {
                this.activeSeq = seq;
            }
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getFaqList();
        },
    },
};
</script>
<style scoped>
.list-loading {
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
</style>
