<template>
    <ul class="faq-list">
        <li
            v-for="(item, index) in faqData"
            :key="index"
            :class="{ active: isActive === item.noticeArticleSeq }"
        >
            <a
                href="#"
                class="sbj"
                @click.prevent="$emit('faqToggle', item.noticeArticleSeq)"
            >
                <span class="label">
                    [<em>{{ item.noticeArticleCategoryValue }}</em
                    >]
                </span>

                <span class="title">{{ item.title }}</span>
                <i class="arrow"></i>
            </a>
            <div class="cont">
                {{ item.contents }}
                <div class="btn-area">
                    <button
                        type="button"
                        class="btn-s-lightgray-sm"
                        @click="$emit('delete', item.noticeArticleSeq)"
                    >
                        <span>삭제</span>
                    </button>
                    <button
                        type="button"
                        class="btn-s-lightgray-sm"
                        @click="$emit('edit', item.noticeArticleSeq)"
                    >
                        <span>수정</span>
                    </button>
                </div>
            </div>
        </li>
    </ul>
</template>

<script>
export default {
    name: 'faq-list',
    data() {
        return {};
    },
    props: ['faqData', 'isActive'],
    methods: {},
};
</script>
<style scoped></style>
