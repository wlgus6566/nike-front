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
            <transition @enter="itemOpen" @leave="itemClose">
                <div
                    class="cont"
                    v-if="isActive === item.noticeArticleSeq"
                    v-html="item.contents"
                >
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
            </transition>
        </li>
    </ul>
</template>

<script>
import { Cubic, gsap } from 'gsap/all';

export default {
    name: 'faq-list',
    data() {
        return {};
    },
    props: ['faqData', 'isActive'],
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
