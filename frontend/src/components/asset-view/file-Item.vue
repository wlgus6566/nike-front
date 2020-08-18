<template>
    <div>
        <div class="all-box">
            <label class="check-label">
                <span class="checkbox">
                    <input
                        type="checkbox"
                        v-model="checkAll"
                        :disabled="contentsFileList && !contentsFileList.length"
                        @click="$emit('allCheckFn')"
                    />
                    <i></i>
                </span>
                <strong class="txt" :class="{ 'fc-black': checkAll }">
                    전체선택 (
                    <em>{{ checkContentsFileList.length }}</em> /
                    <em>{{ contentsFileListTotal }}</em>
                    )
                </strong>
            </label>
            <button
                type="button"
                class="txt-btn"
                @click="$emit('addContBasket', checkContentsFileList)"
            >
                <span>선택 담기</span>
            </button>
            <div class="right">
                <FilterSelect :listSortSelect="orderType" />
                <FilterSelect :listSortSelect="fileExtension" />
            </div>
        </div>
        <template v-if="contentsFileList">
            <draggable
                tag="ul"
                v-bind="dragOptions"
                v-if="contentsFileList.length"
                :list="contentsFileList"
                class="file-item-list"
                @start="onStart"
                @end="onEnd"
            >
                <li
                    :class="fileItemClass(item.contentsFileSeq)"
                    v-for="item in contentsFileList"
                    :key="item.contentsFileSeq"
                >
                    <div class="list">
                        <label>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    :value="item.contentsFileSeq"
                                    v-model="checkContentsFileList"
                                    :disabled="item.url"
                                    @click="
                                        $emit(
                                            'checkContentsFile',
                                            item.contentsFileSeq
                                        )
                                    "
                                />
                                <i></i>
                            </span>
                            <span class="thumbnail">
                                <img
                                    :src="item.thumbnailFilePhysicalName"
                                    alt=""
                                />
                            </span>
                            <span class="info-box">
                                <strong class="title">{{
                                    item.title || item.thumbnailFileName
                                }}</strong>
                            </span>
                        </label>
                        <div class="btn-box">
                            <a
                                :href="item.url"
                                target="_blank"
                                class="btn-s-sm-white"
                                v-if="item.url"
                            >
                                <i class="icon-link"></i><span>LINK</span>
                            </a>
                            <template v-else>
                                <button
                                    type="button"
                                    class="btn-s-sm-white"
                                    disabled="disabled"
                                    v-if="test(item.contentsFileSeq)"
                                >
                                    <i class="icon-check"></i><span>ADDED</span>
                                </button>
                                <button
                                    type="button"
                                    class="btn-s-sm-black"
                                    @click="
                                        $emit('addContBasket', [
                                            item.contentsFileSeq,
                                        ])
                                    "
                                    v-else
                                >
                                    <span>ADD</span>
                                </button>
                            </template>
                            <button
                                type="button"
                                :class="buttonClass(item.contentsFileSeq)"
                                :disabled="
                                    item.fileKindCode === 'VR' || item.url
                                "
                                @click="accordion(item.contentsFileSeq)"
                            >
                                <span>더보기</span>
                            </button>
                        </div>
                    </div>
                    <transition
                        @enter="itemOpen"
                        @leave="itemClose"
                        :css="false"
                    >
                        <div
                            class="detail"
                            v-if="openFile === item.contentsFileSeq"
                        >
                            <div class="inner">
                                <div class="thumbnail">
                                    <img :src="item.imageFileName" alt="" />
                                </div>
                                <div class="down-info">
                                    <span class="key">다운로드 횟수</span>
                                    <span class="val">
                                        <strong>{{
                                            item.downloadCount
                                        }}</strong>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </transition>
                </li>
            </draggable>
            <NoData class="no-data" v-else>
                <div>파일 없음</div>
            </NoData>
        </template>
        <Loading v-else />
    </div>
</template>
<script>
import draggable from 'vuedraggable';
import FilterSelect from '@/components/filter-select';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import { Cubic, gsap } from 'gsap/all';
export default {
    name: 'fileItem',
    data() {
        return { openFile: null, enabled: true, dragging: false };
    },
    components: {
        Loading,
        NoData,
        FilterSelect,
        draggable,
    },
    props: [
        'contentsFileList',
        'checkAll',
        'orderType',
        'fileExtension',
        'checkContentsFileList',
        'contentsFileListTotal',
    ],
    created() {},
    computed: {
        dragOptions() {
            return {
                touchStartThreshold: 5,
                animation: 100,
                disabled: false,
                ghostClass: 'ghost-item',
                dragClass: 'drag-item',
                chosenClass: 'chosen',
                forceFallback: true,
                filter: '.ignore-elements',
                sort: false,
            };
        },
        storeContBasketList: {
            get() {
                return this.$store.state.contBasketList.map(
                    (el) => el.contentsFileSeq
                );
            },
            set(value) {
                this.$store.commit('SET_CONT_BASKET', value);
            },
        },
    },
    methods: {
        test(seq) {
            return this.storeContBasketList.some((el) => el === seq);
        },

        buttonClass(seq) {
            return {
                'btn-more': true,
                active: this.openFile === seq,
            };
        },
        fileItemClass(seq) {
            const ignore = this.checkContentsFileList.every((el) => el !== seq);
            const added = this.storeContBasketList.some((el) => el === seq);
            return {
                'file-item': true,
                'ignore-elements': ignore || added,
            };
        },
        onStart(e) {
            const thumbnail = document.querySelector('.drag-item .thumbnail');
            const absoluteLeft =
                window.pageXOffset + thumbnail.getBoundingClientRect().left;
            const absoluteTop =
                window.pageYOffset + thumbnail.getBoundingClientRect().top;
            const left = e.originalEvent.pageX - absoluteLeft - 50;
            const top = e.originalEvent.pageY - absoluteTop - 50;
            thumbnail.style.transform = `translate(${left}px,${top}px)`;
            this.$store.commit('SET_BASKET_ITEM_DRAG', true);
        },
        onEnd(e) {
            if (this.$store.getters['basketAppendCheck']) {
                this.$emit('addContBasket', this.checkContentsFileList);
            }
            this.$store.commit('SET_BASKET_ITEM_DRAG', false);
        },
        accordion(seq) {
            this.openFile = this.openFile === seq ? null : seq;
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
            });
            gsap.from(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: function () {
                    el.style.height = 'auto';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
    },
};
</script>
<style scoped>
.no-data {
    height: 140px;
    margin-top: 0;
    border-left: 0;
    border-right: 0;
}
.drag-item {
    opacity: 1 !important;
    border: none !important;
    background: transparent !important;
}
.drag-item .list {
    padding: 0;
}
.drag-item .checkbox {
    display: none;
}
.drag-item .thumbnail {
    border: 1px solid #ddd;
}
.drag-item .info-box {
    display: none;
}
.drag-item .btn-box {
    display: none;
}
.drag-item .detail {
    display: none !important;
}
</style>
