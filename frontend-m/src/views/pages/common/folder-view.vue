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
                    <li class="noti-item">
                        이용 약관은 페이지 하단에서 확인 할 수 있습니다.
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
                    <span>
                        {{ item.title }}
                        <em v-if="item.count !== '00'">({{ item.count }})</em>
                    </span>
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
                        :href="item.filePhysicalName"
                        :download="item.fileName"
                        v-if="
                            item.fileExtension === 'PPT' ||
                            item.fileExtension === 'PPTX' ||
                            item.fileExtension === 'PDF'
                        "
                    >
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
                            <span class="date">
                                {{ item.registrationDt }}
                            </span>
                        </span>
                    </a>
                    <a href="#" @click.prevent="fileDetailModal(item)" v-else>
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
                            <span class="date">
                                {{ item.registrationDt }}
                            </span>
                        </span>
                    </a>
                </li>
            </ul>
            <NoData v-else class="file-type">
                <i class="icon-file"></i>
                <p class="desc">등록된 데이터가 없습니다.</p>
            </NoData>
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
import {
    getContentsView,
    getContentsViewFile,
    getContentsFileCount,
} from '@/api/contents';
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
            fileTabList: [],
        };
    },
    created() {
        this.initPageData();
        this.getFolderItemCount();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        //this.initPageData();
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        async getFolderItemCount() {
            try {
                const {
                    data: { data: response },
                } = await getContentsFileCount(
                    this.$route.meta.topMenuCode,
                    this.$route.params.pathMatch.toUpperCase(),
                    this.$route.params.id
                );
                response.forEach((el) => {
                    const count =
                        String(el.count).length > 1 ? el.count : '0' + el.count;
                    this.fileTabList.push({
                        value: el.sectionCode,
                        title: el.sectionCode,
                        count: count,
                    });
                });
            } catch (error) {
                console.error(error);
            }
        },
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
            console.log(item);
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
<style scoped></style>
