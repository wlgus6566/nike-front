<template>
    <div>
        <ul class="sorting-tab">
            <li
                class="active"
                v-for="codeList in categoryCodeList.listSortOptions"
            >
                <a href="#" v-on:click="tabChange(codeList.value)">{{
                    codeList.label
                }}</a>
            </li>
        </ul>
        <ul class="faq-list" v-for="item in faqData">
            <li :class="{ active: item.noticeArticleSeq === activeSeq }">
                <a
                    href="#"
                    class="sbj"
                    @click="setActiveSeq(item.noticeArticleSeq)"
                >
                    <span class="category"
                        >[<em>{{ item.noticeArticleCategoryValue }}</em
                        >]</span
                    >
                    <span class="title">{{ item.title }}</span>
                    <i class="arrow"></i>
                </a>
                <div class="cont isActive">
                    {{ item.contents }}
                </div>
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

export default {
    name: 'faq-list',
    data() {
        return {
            faqList: {},
            faqData: [],
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
        setActiveSeq: function (seq) {
            if (this.activeSeq === seq) {
                this.activeSeq = null;
            } else {
                this.activeSeq = seq;
            }
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getNoticeList();
        },
    },
};
</script>
<style scoped></style>
