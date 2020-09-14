<template>
    <div>
        <div class="all-box">
            <label class="check-label">
                <span class="checkbox">
                    <input
                        type="checkbox"
                        v-model="checkAll"
                        :disabled="
                            !contentsFileListTotal ||
                            (contentsFileList && !contentsFileList.length) ||
                            !folderAuthCheck('DOWNLOAD')
                        "
                        @click="$emit('allCheckFn')"
                    />
                    <i></i>
                </span>
                <strong class="txt" :class="{ 'fc-black': checkAll }">
                    전체선택 (
                    <em
                        :class="{
                            'fc-black': checkContentsFileList.length !== 0,
                        }"
                        >{{ checkContentsFileList.length }}</em
                    >
                    /
                    <em>{{
                        !folderAuthCheck('DOWNLOAD') ? 0 : contentsFileListTotal
                    }}</em>
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
                                    :disabled="
                                        item.fileKindCode !== 'FILE' ||
                                        !folderAuthCheck('DOWNLOAD')
                                    "
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
                                <span
                                    :class="[`extension-vr`]"
                                    v-if="item.fileKindCode === 'VR'"
                                ></span>
                                <span
                                    :class="[`extension-url`]"
                                    v-else-if="item.fileKindCode === 'VIDEO'"
                                ></span>
                                <img
                                    :src="item.thumbnailFilePhysicalName"
                                    :alt="item.thumbnailFileName"
                                    v-else-if="item.thumbnailFilePhysicalName"
                                />
                                <span
                                    :class="[
                                        `extension-${item.fileExtension.toLowerCase()}`,
                                    ]"
                                    v-else
                                ></span>
                            </span>
                            <span class="info-box">
                                <em class="title">{{
                                    item.title || item.fileName
                                }}</em>
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
                                    :disabled="!folderAuthCheck('DOWNLOAD')"
                                >
                                    <span>ADD</span>
                                </button>
                            </template>
                            <button
                                type="button"
                                :class="buttonClass(item.contentsFileSeq)"
                                :disabled="
                                    item.fileKindCode === 'VR' ||
                                    (item.fileKindCode === 'FILE' &&
                                        !item.detailThumbnailFilePhysicalName) ||
                                    (item.fileKindCode === 'VIDEO' && !item.url)
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
                            <div
                                class="inner"
                                v-if="item.fileKindCode === 'FILE'"
                            >
                                <div
                                    class="thumbnail"
                                    v-if="
                                        item.fileContentType.indexOf(
                                            'video'
                                        ) === 0
                                    "
                                >
                                    <div class="video-item">
                                        <video controls>
                                            <source
                                                :src="
                                                    item.detailThumbnailFilePhysicalName
                                                "
                                                type="video/mp4"
                                            />
                                        </video>
                                    </div>
                                </div>

                                <div class="thumbnail" v-else>
                                    <span class="watermark">
                                        <img
                                            :src="
                                                item.detailThumbnailFilePhysicalName
                                            "
                                            alt=""
                                        />
                                    </span>
                                </div>

                                <div class="down-info">
                                    <span class="key">다운로드 횟수</span>
                                    <span class="val">
                                        <strong>{{
                                            item.downloadCount
                                        }}</strong>
                                        회
                                    </span>
                                </div>
                            </div>
                            <div class="inner" v-else>
                                <div
                                    class="thumbnail"
                                    v-if="videoCheck(item.url).type === 'vimeo'"
                                >
                                    <vimeo-player
                                        class="video-item"
                                        :video-id="videoCheck(item.url).id"
                                        :player-height="height"
                                        :player-width="width"
                                    ></vimeo-player>
                                </div>
                                <div
                                    class="thumbnail"
                                    v-else-if="
                                        videoCheck(item.url).type === 'youtube'
                                    "
                                >
                                    <div class="video-item">
                                        <youtube
                                            :video-id="videoCheck(item.url).id"
                                            :player-vars="{
                                                autoplay: 1,
                                            }"
                                        ></youtube>
                                    </div>
                                </div>
                                <div
                                    class="thumbnail"
                                    v-else-if="
                                        videoCheck(item.url).type ===
                                        'brightcove'
                                    "
                                >
                                    <div class="video-item">
                                        <iframe
                                            :src="videoCheck(item.url).id"
                                            allowfullscreen
                                            webkitallowfullscreen
                                            mozallowfullscreen
                                        ></iframe>
                                    </div>
                                </div>
                                <div
                                    class="thumbnail"
                                    v-else-if="
                                        videoCheck(item.url).type === 'mp4'
                                    "
                                >
                                    <div class="video-item">
                                        <video controls>
                                            <source
                                                :src="videoCheck(item.url).id"
                                                type="video/mp4"
                                            />
                                        </video>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </transition>
                </li>
            </draggable>
            <NoData v-else class="file-type">
                <i class="icon-file"></i>
                <p class="desc">등록된 데이터가 없습니다.</p>
            </NoData>
        </template>
        <Loading class="list-loading" :width="172" :height="172" v-else />
    </div>
</template>
<script>
import draggable from 'vuedraggable';
import FilterSelect from '@/components/filter-select';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';
import { Cubic, gsap } from 'gsap/all';
import { authCheck } from '@/utils/authCheck';
export default {
    mixins: [authCheck],
    name: 'fileItem',
    data() {
        return {
            openFile: null,
            enabled: true,
            dragging: false,

            height: 'auto',
            width: '600',
            options: {},
            playerReady: false,

            /*tt: {
                contentsFileSeq: 725,
                contentsSeq: 311,
                fileSectionCode: 'GUIDE',
                fileKindCode: 'FILE',
                fileOrder: 2,
                detailThumbnailFileName: '12_mcdonalds_200729_detail.mp4',
                detailThumbnailFileSize: '465703',
                detailThumbnailFilePhysicalName:
                    'https://devupload.nikespace.co.kr/contents/2020090151200043VOw5khNz_thumbnail.mp4?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZGV2dXBsb2FkLm5pa2VzcGFjZS5jby5rci9jb250ZW50cy8yMDIwMDkwMTUxMjAwMDQzVk93NWtoTnpfdGh1bWJuYWlsLm1wNCIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5OTIwODkyOX0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTkyMDcwNjl9fX1dfQ__&Signature=FZtYFkemnuU82TKao8Tz~2Z3zrHIUPlKA9ncADMaZmsHhsppYx5oKLxlS-HDCgr97ddfHpsbjEXwg8koXq4T9BrTrQJZbiK25V0JY~8~X082GHIMb4fLeXf2fNkFHHqrTkv5eYDWpLsLDykuhwNmpvNqnnsGAnapPw3POya02ZwT3POs9PRfxfa9Bv8VtpelV3~sYh~FvkbX2VoYb-6qIKgF7C9YDMHd8yAUWSds8j05OL2w5ptw36GfNlNEdX4GYG7V9d6MEBTxjIYvJZ0w46SiBSQLu~sl3re0BmFT355M26S0FkuTS~q03nqI~G5UHWtUivmSrF1B3tXYAXzKeA__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                fileContentType: 'video/mp4',
                fileExtension: 'MP4',
                fileName: '12_mcdonalds_200729.mp4',
                fileSize: 20627280,
                filePhysicalName:
                    'https://devupload.nikespace.co.kr/contents/2020090151200043VOw5khNz.mp4?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZGV2dXBsb2FkLm5pa2VzcGFjZS5jby5rci9jb250ZW50cy8yMDIwMDkwMTUxMjAwMDQzVk93NWtoTnoubXA0IiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNTk5MjA4OTI5fSwiSXBBZGRyZXNzIjp7IkFXUzpTb3VyY2VJcCI6IjAuMC4wLjAvMCJ9LCJEYXRlR3JlYXRlclRoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5OTIwNzA2OX19fV19&Signature=N29srj2U4fOqfQ1w-~Q41IZs9saw6-BuQ9f26K3OTu-J3Qt4kUisw8skh9Zcs~b-IYxwGAykeONgpcq0dMB6odrBoTY2vNfptxHp9oqFtiLCoPxZeR3bKlQX63cdCHS24B4ZJ1Fl3mQqUD3nVAB4HHnWRvCQQamMw3gzYy8E4S5jcJgvWjN3v07J6xNheBHkcLvtpRDDxAmuX44~S2OTlY5dUxI~VdJogAPXJK1e0~8~OBuhA5s~RTe1UlgwfUUtCJyrd2whsr9dIg4r99bAym36hCfQwJFVZAv3Riww635OrAop6KV2VuoOgl601yOaP17-T6k04u754Euz4IaVBA__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                downloadCount: 0,
            },*/
        };
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
        videoCheck(url) {
            url.match(
                /(http:|https:|)\/\/(player.|www.)?(vimeo\.com|youtu(be\.com|\.be|be\.googleapis\.com))\/(video\/|embed\/|watch\?v=|v\/)?([A-Za-z0-9._%-]*)(\&\S+)?/
            );
            if (RegExp.$3.indexOf('youtu') > -1) {
                return {
                    type: 'youtube',
                    id: RegExp.$6,
                };
            } else if (RegExp.$3.indexOf('vimeo') > -1) {
                return {
                    type: 'vimeo',
                    id: RegExp.$6,
                };
            } else if (url.indexOf('brightcove') > -1) {
                return {
                    type: 'brightcove',
                    id: url,
                };
            } else if (url.indexOf('.mp4') > -1) {
                return {
                    type: 'mp4',
                    id: url,
                };
            }
        },
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
            //console.log(123);
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
