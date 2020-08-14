<template>
    <div>
        <div class="sorting-area">
            <button
                type="button"
                :class="[viewType ? 'type-list' : 'type-list-thum']"
                @click="viewTypeToggle()"
            >
                <span>컬럼타입</span>
            </button>
            <!-- todo select 스크립트 작업 필요  -->
            <div class="filter-select">
                <select>
                    <option value="최신순">최신순</option>
                </select>
            </div>
            <div class="filter-select">
                <select>
                    <option value="전체그룹">전체그룹</option>
                </select>
            </div>
            <div class="search-input" :class="{ active: searchIsActive }">
                <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input
                        type="text"
                        placeholder="검색어를 입력해주세요."
                        @keyup.enter="search()"
                        v-model="keyword"
                    />
                    <button type="button" class="search" @click="searchView()">
                        <span>검색</span>
                    </button>
                </div>
                <button type="button" class="btn-txt" @click="searchView()">
                    <span>취소</span>
                </button>
            </div>
        </div>
        <ul class="folder-list-row">
            <li
                class="folder-list-item"
                v-for="item in reportList"
                :key="item.reportSeq"
            >
                <a :href="`/report/detail-view/${item.reportSeq}`">
                    <div class="thumbnail">
                        <img :src="item.imageFilePhysicalName" alt="" />
                    </div>
                    <div class="info-box">
                        <strong class="title" v-text="item.nickname"
                            >NIKE GANGNAM</strong
                        >
                        <p class="txt" v-text="item.reportName">
                            FA2ss0 RN NIKE DIRECT 시공 보고서 자료
                        </p>
                        <p class="date">
                            {{
                                $moment(item.registrationDt).format(
                                    'YYYY.MM.DD'
                                )
                            }}
                        </p>
                    </div>
                    <div class="view-area">
                        <span class="view" v-text="item.readCount">10,000</span>
                    </div>
                </a>
            </li>
        </ul>
    </div>
</template>
<script>
import { getReportList } from '@/api/report';

export default {
    name: 'management',
    data() {
        return {
            loading: false,
            page: 0,
            size: 20,
            keyword: '',
            sectionCode: '',
            groupSeq: '',
            reportList: [],
            searchIsActive: false,
            viewType: true,
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        // 기본 데이터 조회
        async fetchData() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getReportList({
                    page: this.page,
                    size: this.size,
                    keyword: this.keyword,
                    sectionCode: this.sectionCode,
                    groupSeq: this.groupSeq,
                });
                this.reportList = response.content;
            } catch (error) {
                console.log(error);
            }
        },
        searchView() {
            if (this.searchIsActive) {
                this.searchIsActive = false;
            } else {
                this.searchIsActive = true;
            }
        },
        viewTypeToggle() {
            if (this.viewType) {
                this.viewType = false;
            } else {
                this.viewType = true;
            }
        },
        search() {
            this.fetchData();
        },
    },
};
</script>
<style scoped></style>
