<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <SortingList
            :sectionCode="sectionCode"
            @sectionCodeChange="sectionCodeChange"
        />
        <template v-if="historyFolderData">
            <FolderList
                v-if="historyFolderData.length"
                :listTypes="listTypes"
                :folderListData="historyFolderData"
            />
            <template v-else>
                <NoData>
                    <i class="icon-file"></i>
                    <p class="desc">최근 본 폴더가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading :loadingStyle="loadingStyle" v-if="loadingData" />
    </div>
</template>

<script>
    import {historyFolderViewList} from '@/api/mypage';
    import SortingList from '@/components/asset-view/sorting-list.vue';
    import FolderList from '@/components/folder-list';
    import Loading from '@/components/loading';
    import NoData from '@/components/no-data';

    export default {
    data() {
        return {
            listTypes: [
                {
                    title: '컬럼타입',
                    active: false,
                },
                {
                    title: '로우타입',
                    active: true,
                },
            ],
            historyFolderData: [],
            page: 0,
            itemLength: 20,
            sectionCode: {
                listSortOptions: [
                    {
                        value: 'ALL',
                        title: 'ALL',
                    },
                    {
                        value: 'ASSET',
                        title: 'ASSET',
                    },
                    {
                        value: 'TOOLKIT',
                        title: 'TOOLKIT',
                    },
                    {
                        value: 'FOUNDATION',
                        title: 'FOUNDATION',
                    },
                    {
                        value: 'REPORT_MANAGE',
                        title: 'REPORT',
                    },
                ],
                value: 'ALL',
            },

            typeCd: '',
            totalPage: '',
            loadingData: false,
            loadingStyle: {
                width: this.width ? `${this.width}px` : '100%',
                height: this.height ? `${this.height}px` : '100%',
                overflow: 'hidden',
                margin: '0 auto',
            },
        };
    },
    watch: {
        'sectionCode.value'() {
            this.historyViewDataList();
        },
    },
    mounted() {},
    components: {
        SortingList,
        FolderList,
        Loading,
        NoData,
    },
    created() {
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        sectionCodeChange(value) {
            this.sectionCode.value = value;
        },
        //클릭시 최근 본 폴더 리스트 다시 불러오기
        handleScroll() {
            console.log(1);
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                console.log(2);
                this.infiniteScroll();
            }
        },
        initFetchData() {
            console.log('initFetchData');
            this.totalPage = null;
            this.page = 0;
            this.historyFolderData = null;
            this.historyViewDataList();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.historyFolderData.length >= this.itemLength &&
                this.historyFolderData.length !== 0
            ) {
                console.log('infiniteScroll');
                this.historyViewDataList(true);
            }
        },
        endPage() {
            alert('마지막 페이지');
        },
        //최근 본 폴더 리스트
        async historyViewDataList(infinite) {
            this.loadingData = true;
            console.log(this.typeCd);
            try {
                const {
                    data: { data: response },
                } = await historyFolderViewList({
                    page: this.page,
                    size: this.itemLength,
                    typeCd: this.sectionCode.value,
                });
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.historyFolderData = this.historyFolderData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.historyFolderData = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>

<style scoped></style>
