<template>
    <div>
        <h2 class="page-title">
            <span class="ko">내가 업로드한 폴더</span>
        </h2>
        <SortingList
            :sectionCode="sectionCode"
            @sectionCodeChange="sectionCodeChange"
        />
        <template v-if="uploadFolderData">
            <MyFolderList
                v-if="uploadFolderData.length"
                :listTypes="listTypes"
                :folderListData="uploadFolderData"
            />
            <template v-else>
                <NoData>
                    <i class="icon-folder-none"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </NoData>
            </template>
        </template>
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
    </div>
</template>

<script>
import { uploadFolderViewList } from '@/api/mypage';
import SortingList from '@/components/asset-view/sorting-list.vue';
import MyFolderList from '@/components/my-folder-list';
import Loading from '@/components/loading';
import NoData from '@/components/no-data';

export default {
    data() {
        return {
            uploadFolderDataList: [],
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
            uploadFolderData: [],
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
            totalPage: null,
            loadingData: false,
        };
    },
    watch: {
        'sectionCode.value'() {
            this.page = 0;
            this.initFetchData();
        },
    },
    mounted() {},
    components: {
        SortingList,
        MyFolderList,
        Loading,
        NoData,
    },
    created() {
        this.initFetchData();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        this.initFetchData();
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
        //클릭시 업로드 한 폴더 리스트 다시 불러오기
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.uploadFolderData = null;
            this.uploadViewDataList();
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.uploadFolderData.length >= this.itemLength &&
                this.uploadFolderData.length !== 0
            ) {
                this.uploadViewDataList(true);
            }
        },
        endPage() {
            alert('마지막 페이지 입니다.');
        },
        //업로드 한 폴더 리스트
        async uploadViewDataList(infinite) {
            this.loadingData = true;
            try {
                const {
                    data: { data: response },
                } = await uploadFolderViewList({
                    page: this.page,
                    size: this.itemLength,
                    typeCd: this.sectionCode.value,
                });
                this.totalPage = response.totalPages;
                if (infinite) {
                    if (this.totalPage > this.page - 1) {
                        this.uploadFolderData = this.uploadFolderData.concat(
                            response.content
                        );
                    } else if (this.totalPage === this.page - 1) {
                        this.endPage();
                    }
                } else {
                    this.uploadFolderDataList = response;
                    this.uploadFolderData = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>

<style scoped>
.list-loading {
    position: relative;
    padding-top: 70%;
}
::v-deep .list-loading .lottie {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
