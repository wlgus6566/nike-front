<template>
    <div>
        <div class="folder-wrap">
            <h2 class="folder-title">
                {{ contentsDetail.folderName }}
            </h2>
            <div class="inner">
                <p class="folder-desc">
                    {{ contentsDetail.folderContents }}
                </p>
                <span
                    class="folder-date"
                    v-if="contentsDetail.campaignPeriodSectionCode === 'EVERY'"
                    >365</span
                >
                <span class="folder-date" v-else>
                    <em>{{ contentsDetail.campaignBeginDt }}</em>
                    -
                    <em>{{ contentsDetail.campaignEndDt }}</em>
                </span>
                <p class="folder-memo">{{ contentsDetail.memo }}</p>
            </div>
        </div>

        <ul class="sorting-list">
            <li
                v-for="item in fileTabList"
                :key="item.value"
                :class="{ active: item.value === selectTabValue }"
            >
                <button type="button" @click="onClickTab(item.value)">
                    <span>{{ item.title }}</span>
                </button>
            </li>
        </ul>
        <template v-if="fileList">
            <ul class="file-item-list" v-if="fileList.length">
                <li
                    class="file-item"
                    v-for="item in fileList"
                    :key="item.contentsFileSeq"
                >
                    <a
                        href="#"
                        @click="
                            fileDetailModal(
                                item.contentsFileSeq,
                                item.filePhysicalName,
                                item.fileName
                            )
                        "
                    >
                        <span class="thumbnail">
<!--                            <img :src="item.thumbnailFilePhysicalName" alt="" />-->
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
                            <strong class="title">{{ item.fileName }}</strong>
                        </span>
                    </a>
                </li>
            </ul>
            <template v-else>
                <NoData>
                    <i class="icon-file"></i>
                    <p class="desc">파일 없음</p>
                </NoData>
            </template>
        </template>
        <fileDetailPopup
            :visible.sync="visible.modalEx"
            :filePopupFile="filePopupFile"
            :filePopupName="filePopupName"
        />
        <Loading
            class="list-loading"
            :width="172"
            :height="172"
            v-if="loadingData"
        />
    </div>
</template>
<script>
import { getContentsView, getContentsViewFile } from '@/api/contents';
import fileDetailPopup from '@/views/pages/report/file-Detail-Popup';
import NoData from '@/components/no-data';

export default {
    name: 'folder-view',
    components: {
        Loading: () => import('@/components/loading/'),
        fileDetailPopup: fileDetailPopup,
        NoData,
    },
    data() {
        return {
            loading: false,
            contentsDetail: {},
            fileList: [],
            page: 0,
            size: 3,
            totalPage: 0,
            fileListLast: false,
            loadingData: false,
            visible: {
                modalEx: false,
            },
            filePopupFile: '',
            filePopupName: '',
            selectTabValue: 'ALL',
            fileTabList: [
                {
                    value: 'ALL',
                    title: 'ALL',
                },
                {
                    value: 'GUIDE',
                    title: 'GUIDE',
                },
                {
                    value: 'VIDEO',
                    title: 'VIDEO',
                },
                {
                    value: 'VR',
                    title: 'VR',
                },
            ],
        };
    },
    created() {
        this.initPageData();
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
        initPageData() {
            this.fileList = null;
            this.page = 0;
            this.initFetchData();
        },
        initFetchData() {
            this.getDetailData();
            this.getFileList();
        },
        async getDetailData() {
            this.loading = true;
            try {
                const { data: response } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id
                );
                this.contentsDetail = response.data;
            } catch (error) {
                console.log(error);
            }
        },
        async getFileList(paging) {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id,
                    {
                        page: this.page,
                        size: this.size,
                        sectionCode: this.selectTabValue,
                    }
                );
                if (paging) {
                    this.fileList = this.fileList.concat(response.content);
                } else {
                    this.fileList = response.content;
                }
                this.totalPage = response.totalPages;
                this.fileListLast = response.last;
                this.loading = false;
            } catch (error) {
                console.log(error);
            }
        },
        fileDetailModal(reportFileSeq, filePhysicalName, fileName) {
            this.filePopupFile = filePhysicalName;
            this.filePopupName = fileName;
            this.visible.modalEx = true;
        },
        onClickTab(value) {
            this.selectTabValue = value;
            this.initPageData();
        },
        handleScroll() {
            if (this.loadingData) return;
            const windowE = document.documentElement;
            console.log(
                'dddd',
                windowE.clientHeight + windowE.scrollTop,
                windowE.scrollHeight
            );
            if (
                windowE.clientHeight + windowE.scrollTop >=
                windowE.scrollHeight
            ) {
                this.infiniteScroll();
            }
        },
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.fileList.length >= this.size &&
                this.fileList.length !== 0 &&
                !this.pageLast
            ) {
                this.page++;
                this.getFileList(true);
            }
        },
    },
};
</script>
<style scoped></style>
