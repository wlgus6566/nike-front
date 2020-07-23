<template>
    <div>
        <div class="all-box">
            <!-- todo 전체선택 스크립트 작업 필요  -->
            <label class="check-label">
                <span class="checkbox">
                    <input type="checkbox" v-model="checkAll.state" v-on:change="checkAllChange" />
                    <span></span>
                </span>
                <strong class="txt">전체선택</strong>
            </label>
            <p class="desc">
                <span v-if="checkAll.state" class="fc-black">
                    전체 파일이 선택됨
                </span>
                <span v-else :class="{ 'fc-black': checkTT }">
                    <em>
                        {{ checkTxt.length }}
                    </em>
                    개의 파일이 선택됨
                </span>
            </p>
            <div class="right">
                <filterSelect :listSortSelect="listSortSelect" />
                <filterSelect :listSortSelect="listSortSelect2" />
            </div>
        </div>
        <ul class="file-item-list">
            <li class="file-item" v-for="(item, index) in items" :key="index">
                <div class="list">
                    <el-checkbox
                        v-model="item.state"
                        v-on:change="checkedCitiesChange()"
                        :disabled="!!item.url"
                    >
                        <span class="thumbnail">
                            <img :src="item.imageFileName" alt="" />
                        </span>
                        <span class="info-box">
                            <strong class="title">{{ item.title }}</strong>
                        </span>
                    </el-checkbox>
                    <div class="btn-box">
                        <a :href="item.url" target="_blank" class="btn-s-sm-white" v-if="item.url">
                            <i class="icon-link"></i><span>LINK</span>
                        </a>
                        <div v-else>
                            <button type="button" class="btn-s-sm-white" v-if="item.state">
                                <i class="icon-check"></i><span>ADDED</span>
                            </button>
                            <button type="button" class="btn-s-sm-black" v-else>
                                <span>ADD</span>
                            </button>
                        </div>

                        <button
                            type="button"
                            class="btn-more"
                            :class="{ active: item.acd }"
                            :disabled="item.url"
                            v-on:click="$emit('accordion', index)"
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
        </ul>
    </div>
</template>
<script>
import filterSelect from '@/components/filter-select';

export default {
    name: 'fileItem',
    data() {
        return {
            check: this.items.state,
            listSortSelect: {
                listSortOptions: [
                    {
                        value: '기본 정렬',
                        label: '기본 정렬',
                    },
                    {
                        value: '파일명 순',
                        label: '파일명 순',
                    },
                ],
                value: '기본 정렬',
            },
            listSortSelect2: {
                listSortOptions: [
                    {
                        value: '전체확장자',
                        label: '전체확장자',
                    },
                    {
                        value: 'ai',
                        label: 'ai',
                    },
                    {
                        value: 'jpg',
                        label: 'jpg',
                    },
                    {
                        value: 'jpeg',
                        label: 'jpeg',
                    },
                    {
                        value: 'gif',
                        label: 'gif',
                    },
                    {
                        value: 'tif',
                        label: 'tif',
                    },
                    {
                        value: 'psd',
                        label: 'psd',
                    },
                ],
                value: '전체확장자',
            },
        };
    },
    components: {
        filterSelect,
    },
    props: {
        items: Array,
        checkAll: Object,
        checkTxt: Object,
    },
    created() {},
    computed: {
        checkTT() {
            if (this.checkTxt.length !== 0) {
                this.checkTxt.state = true;
            } else {
                this.checkTxt.state = false;
            }
            return this.checkTxt.state;
        },
    },
    methods: {
        checkAllChange(value) {
            this.$emit('checkAllChange', value);
        },
        checkedCitiesChange(value) {
            this.$emit('checkedCitiesChange', value);
        },
        accordion(index) {
            alert(123);
            //this.$emit('accordion');
            this.items[index].acd = !this.items[index].acd;
        },
    },
};
</script>
<style scoped></style>
