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

                <span class="folder-date">
                    <span
                        class="date"
                        v-if="
                            contentsDetail.campaignPeriodSectionCode === 'EVERY'
                        "
                        >365</span
                    >
                    <span class="date" v-else>
                        <em>{{ contentsDetail.campaignBeginDt }}</em>
                        -
                        <em>{{ contentsDetail.campaignEndDt }}</em>
                    </span>
                    <span class="email">
                        {{ contentsDetail.userId }}
                    </span>
                </span>
                <p class="folder-memo">{{ contentsDetail.memo }}</p>
            </div>

            <div class="noti-box">
                <ul class="noti-item-list">
                    <li class="noti-item">
                        <strong>
                            KEEP IT TIGHT! 본 자료는 NIKE.INC.와 NIKE KOREA
                            LLC.의 자산입니다. 보안 규정을 준수하시기 바랍니다.
                        </strong>
                    </li>
                    <li class="noti-item">
                        자료의 조회와 다운로드 이력이 추적 / 관리됩니다. 자료의
                        불법적인 유출은 관련 법에 의거 처벌될 수 있습니다.
                    </li>
                </ul>
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
                    <a href="#" @click.prevent="fileDetailModal(item)">
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
                            <strong class="title">
                                {{ item.title || item.fileName }}
                            </strong>
                        </span>
                    </a>
                </li>
            </ul>
            <div class="no-data" v-else>
                <p class="desc">파일없음</p>
            </div>
        </template>
        <fileDetailPopup
            :visible.sync="visible.modalEx"
            :filePopupFile="filePopupFile"
            @closeModal="closeModal"
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
import fileDetailPopup from '@/views/pages/common/file-Detail-Popup';
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
            size: 9999,
            totalPage: 0,
            fileListLast: false,
            loadingData: false,
            visible: {
                modalEx: false,
            },
            filePopupFile: '',
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
    methods: {
        closeModal() {
            this.visible.modalEx = false;
            this.filePopupFile = '';
        },
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
        async getFileList() {
            this.loading = true;
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id,
                    {
                        orderType: 'ORDER',
                        page: this.page,
                        size: this.size,
                        sectionCode: this.selectTabValue,
                    }
                );
                this.fileList = response.content;
                this.totalPage = response.totalPages;
                this.fileListLast = response.last;
                this.loading = false;
            } catch (error) {
                console.log(error);
            }
        },
        fileDetailModal(item) {
            this.filePopupFile = item;
            this.visible.modalEx = true;
        },
        onClickTab(value) {
            this.selectTabValue = value;
            this.initPageData();
        },
    },
};
</script>
<style scoped>
.no-data {
    padding: 30px 0;
}
</style>
