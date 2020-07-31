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
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>
            <p class="desc">
                <span class="fc-black" v-if="checkAll">
                    전체 파일이 선택됨
                </span>
                <span v-else>
                    <em>{{ checkContentsFileList.length }}</em>
                    개의 파일이 선택됨
                </span>
            </p>
            <div class="right">
                <FilterSelect :listSortSelect="orderType" />
                <FilterSelect :listSortSelect="fileExtension" />
            </div>
        </div>
        <template v-if="contentsFileList">
            <draggable
                tag="ul"
                :sort="false"
                v-if="contentsFileList.length"
                :list="contentsFileList"
                :disabled="!enabled"
                class="file-item-list"
                ghost-class="ghost"
                drag-class="test"
                chosen-class="chosen"
                @choose="onChoose"
                @start="onStart"
                @end="onEnd"
                @add="onAdd"
                @move="onMove"
                @update="onUpdate"
                @sort="onSort"
                @remove="onRemove"
                @change="onChange"
                @unchoose="onUnchoose"
                :forceFallback="true"
            >
                <li
                    class="file-item"
                    v-for="(item, index) in contentsFileList"
                    :key="item.contentsFileSeq"
                >
                    <div class="list">
                        <label>
                            <span class="checkbox">
                                <input
                                    type="checkbox"
                                    :value="item.contentsFileSeq"
                                    v-model="checkContentsFileList"
                                    @click="
                                        $emit(
                                            'checkContentsFile',
                                            item.contentsFileSeq
                                        )
                                    "
                                />
                                <span></span>
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
                                    v-if="item.state"
                                >
                                    <i class="icon-check"></i><span>ADDED</span>
                                </button>
                                <button
                                    type="button"
                                    class="btn-s-sm-black"
                                    v-else
                                >
                                    <span>ADD</span>
                                </button>
                            </template>
                            <button
                                type="button"
                                class="btn-more"
                                :class="{ active: item.acd }"
                                :disabled="item.fileKindCode === 'VR'"
                                @click="accordion"
                            >
                                <span>더보기</span>
                            </button>
                        </div>
                    </div>
                    <div class="detail" :class="{ active: item.acd }">
                        <div class="inner">
                            <div class="thumbnail">
                                <img :src="item.imageFileName" alt="" />
                            </div>
                            <div class="down-info">
                                <span class="key">다운로드 횟수</span>
                                <span class="val">
                                    <strong>{{ item.downloadCount }}</strong>
                                </span>
                            </div>
                        </div>
                    </div>
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

export default {
    name: 'fileItem',
    data() {
        return { enabled: true, dragging: false };
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
    ],
    created() {},
    computed: {},
    methods: {
        onChoose(e) {
            console.log('onChoose', e);
        },
        onStart(e) {
            console.log('onStart', e);
            const thumbnail = document.querySelector('.test .thumbnail');
            const left = e.originalEvent.pageX - e.item.offsetLeft - 60;
            const top = e.originalEvent.pageY - e.item.offsetTop - 60;
            thumbnail.style.transform = `translate(${left}px,${top}px)`;
        },
        onEnd(e) {
            console.log('onEnd', e);
        },
        onAdd(e) {
            console.log('onAdd', e);
        },
        onMove(e) {
            console.log('cart onMove', e);
        },
        onUpdate(e) {
            console.log('onUpdate', e);
        },
        onSort(e) {
            console.log('onSort', e);
        },
        onRemove(e) {
            console.log('onRemove', e);
        },
        onChange(e) {
            console.log('onChange', e);
        },
        onUnchoose(e) {
            console.log('onUnchoose', e);
        },
        accordion() {},
    },
};
</script>
<style>
.no-data {
    height: 140px;
    margin-top: 0;
    border-left: 0;
    border-right: 0;
}
.test {
    opacity: 1 !important;
    border: none !important;
    background: none !important;
}
.test .list {
    padding: 0;
}
.test .checkbox {
    display: none;
}
.test .thumbnail {
}
.test .info-box {
    display: none;
}
.test .btn-box {
    display: none;
}
.test .detail {
    display: none !important;
}
</style>
