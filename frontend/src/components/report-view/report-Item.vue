<template>
    <div>
        <div class="all-box">
            <label class="check-label">
                <span class="checkbox">
                    <input
                        type="checkbox"
                        v-model="checkAll"
                        :disabled="
                            (reportFileData && !reportFileData.length) ||
                            !folderAuthCheck('DOWNLOAD')
                        "
                        @click="$emit('allCheckFn')"
                    />
                    <i></i>
                </span>
                <strong class="txt" :class="{ 'fc-black': checkAll }">
                    전체선택 (
                    <em>{{ checkContentsFileList.length }}</em> /
                    <em>{{
                        !folderAuthCheck('DOWNLOAD') ? 0 : reportFileListTotal
                    }}</em>
                    )
                </strong>
            </label>
            <button
                type="button"
                class="txt-btn"
                @click="$emit('addReportBasket', checkContentsFileList)"
            >
                <span>선택 담기</span>
            </button>
        </div>
        <template v-if="reportFileData">
            <draggable
                tag="ul"
                v-bind="dragOptions"
                v-if="reportFileData.length"
                :list="reportFileData"
                class="file-item-list"
                @start="onStart"
                @end="onEnd"
            >
                <li
                    :class="fileItemClass(item.reportFileSeq)"
                    v-for="item in reportFileData"
                    :key="item.reportFileSeq"
                >
                    <div class="list">
                        <label>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    :value="item.reportFileSeq"
                                    v-model="checkContentsFileList"
                                    :disabled="!folderAuthCheck('DOWNLOAD')"
                                    @click="
                                        $emit(
                                            'checkContentsFile',
                                            item.reportFileSeq
                                        )
                                    "
                                />
                                <i></i>
                            </span>
                            <span class="thumbnail">
                                <img
                                    :src="item.thumbnailFilePhysicalName"
                                    :alt="item.thumbnailFileName"
                                    v-if="item.thumbnailFilePhysicalName"
                                />
                                <!--  //todo 나중에 작업해야함  extension asset 동일 -->
                                <span
                                    :class="[
                                        `extension-${item.fileExtension.toLowerCase()}`,
                                    ]"
                                    v-else
                                ></span>
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
                                    v-if="test(item.reportFileSeq)"
                                >
                                    <i class="icon-check"></i><span>ADDED</span>
                                </button>
                                <button
                                    type="button"
                                    class="btn-s-sm-black"
                                    @click="
                                        $emit('addReportBasket', [
                                            item.reportFileSeq,
                                        ])
                                    "
                                    v-else
                                    :disabled="!folderAuthCheck('DOWNLOAD')"
                                >
                                    <span>ADD</span>
                                </button>
                            </template>
                            <button
                                type="button"
                                :class="buttonClass(item.reportFileSeq)"
                                :disabled="
                                    item.fileKindCode === 'VR' || item.url
                                "
                                @click="accordion(item.reportFileSeq)"
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
                            v-if="openFile === item.reportFileSeq"
                        >
                            <div class="inner">
                                <div class="thumbnail">
                                    <img
                                        :src="
                                            item.detailThumbnailFilePhysicalName
                                        "
                                        :alt="item.detailThumbnailFileName"
                                    />
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
            <NoData v-else class="file-type">
                <i class="icon-file"></i>
                <p class="desc">등록된 파일이 없습니다.</p>
            </NoData>
        </template>
        <Loading v-else />
    </div>
</template>
<script>
import draggable from 'vuedraggable';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import { Cubic, gsap } from 'gsap/all';
import { authCheck } from '@/utils/authCheck';
export default {
    name: 'reportItem',
    mixins: [authCheck],
    data() {
        return { openFile: null, enabled: true, dragging: false };
    },
    components: {
        Loading,
        NoData,
        draggable,
    },
    props: [
        'reportFileData',
        'checkAll',
        'orderType',
        'fileExtension',
        'checkContentsFileList',
        'reportFileListTotal',
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
                return this.$store.state.reportBasketList.map(
                    (el) => el.reportFileSeq
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
                this.$emit('addReportBasket', this.checkContentsFileList);
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
