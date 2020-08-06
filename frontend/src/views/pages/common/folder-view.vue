<template>
    <div>
        <BtnArea @delete="deleteFolder" @edit="editFolder">
            <button type="button" class="btn-o-gray">
                <i class="icon-mail"></i>
                <span>알림메일전송</span>
            </button>
        </BtnArea>
        <folder :folderDetail="folderDetail" v-if="folderDetail"></folder>
        <div class="sorting-list-wrap">
            <SortingList
                :sectionCode="sectionCode"
                @sectionCodeChange="sectionCodeChange"
            />
            <div class="btn-box">
                <button
                    type="button"
                    class="btn-s-lightgray-sm"
                    @click="addContBasket(checkContentsFileList)"
                >
                    <span>선택 담기</span>
                </button>
            </div>
        </div>
        <fileItem
            :contentsFileList="contentsFileList"
            :checkAll="checkAll"
            :orderType="orderType"
            :fileExtension="fileExtension"
            :checkContentsFileList="checkContentsFileList"
            @allCheckFn="allCheckFn"
            @checkContentsFile="checkContentsFile"
            @addContBasket="addContBasket"
        ></fileItem>
    </div>
</template>
<script>
import BtnArea from '@/components/asset-view/btn-area.vue';
import folder from '@/components/asset-view/folder.vue';
import SortingList from '@/components/asset-view/sorting-list.vue';
import fileItem from '@/components/asset-view/file-Item.vue';
import {
    addContentsBasket,
    deleteContents,
    getContentsView,
    getContentsViewFile,
} from '@/api/contents';
import FilterSelect from '@/components/filter-select';

export default {
    name: 'folder-view',
    data() {
        return {
            totalPage: null,
            loadingData: false,

            page: 0,
            itemLength: 10,
            checkAll: false,
            folderDetail: null,
            contentsFileList: null,
            checkContentsFileList: [],
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
                        value: 'GUIDE',
                        title: 'GUIDE',
                    },
                ],
                value: 'ALL',
            },
            orderType: {
                listSortOptions: [
                    {
                        value: 'ORDER',
                        label: '기본 정렬',
                    },
                    {
                        value: 'FILE_NAME',
                        label: '파일명 순',
                    },
                ],
                value: 'ORDER',
            },
            fileExtension: {
                listSortOptions: [
                    {
                        value: '',
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
                    {
                        value: 'bmp',
                        label: 'bmp',
                    },
                    {
                        value: 'png',
                        label: 'png',
                    },
                    {
                        value: 'pdf',
                        label: 'pdf',
                    },
                    {
                        value: 'ppt',
                        label: 'ppt',
                    },
                    {
                        value: 'pptx',
                        label: 'pptx',
                    },
                    {
                        value: 'doc',
                        label: 'doc',
                    },
                    {
                        value: 'docx',
                        label: 'docx',
                    },
                    {
                        value: 'xls',
                        label: 'xls',
                    },
                    {
                        value: 'xlsx',
                        label: 'xlsx',
                    },
                    {
                        value: 'key',
                        label: 'key',
                    },
                    {
                        value: 'mp4',
                        label: 'mp4',
                    },
                    {
                        value: 'avi',
                        label: 'avi',
                    },
                    {
                        value: 'mov',
                        label: 'mov',
                    },
                    {
                        value: 'TTF',
                        label: 'TTF',
                    },
                    {
                        value: 'OTF',
                        label: 'OTF',
                    },
                    {
                        value: 'INDD',
                        label: 'INDD',
                    },
                ],
                value: '',
            },
        };
    },
    mounted() {},
    components: {
        BtnArea,
        folder,
        SortingList,
        fileItem,
        FilterSelect,
    },
    computed: {
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
    watch: {
        'sectionCode.value'() {
            this.initFetchData();
        },
        'orderType.value'() {
            this.initFetchData();
        },
        'fileExtension.value'() {
            this.initFetchData();
        },
    },
    methods: {
        async deleteFolder() {
            if (
                !confirm(
                    '삭제 시 등록한 내용이 전부 삭제 됩니다. 삭제하시겠습니까?'
                )
            )
                return;
            if (!confirm('정말 삭제하시겠습니까?')) return;
            try {
                const response = await deleteContents(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id
                );
                this.$store.commit('SET_RELOAD', true);
                if (response.data.success) {
                    this.$router.go(-1);
                }
                console.log(response);
            } catch (error) {
                console.log(error);
            }
        },
        editFolder() {},
        initFetchData() {
            this.totalPage = null;
            this.page = 0;
            this.contentsFileList = null;
            this.getFolderDetailFile();
        },
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
        infiniteScroll() {
            if (
                !this.loadingData &&
                this.totalPage > this.page - 1 &&
                this.contentsFileList.length >= this.itemLength &&
                this.contentsFileList.length !== 0
            ) {
                this.getFolderDetailFile(true);
            }
        },
        endPage() {
            alert('마지막');
        },

        sectionCodeChange(value) {
            this.sectionCode.value = value;
        },
        allCheckFn() {
            this.checkAll = !this.checkAll;
            if (this.checkAll) {
                this.contentsFileList.forEach((el) => {
                    const indexOfChecked = this.checkContentsFileList.findIndex(
                        (elChecked) => elChecked === el.contentsFileSeq
                    );
                    if (indexOfChecked === -1) {
                        this.checkContentsFileList.push(el.contentsFileSeq);
                    }
                });
            } else {
                this.checkContentsFileList = [];
            }
        },
        checkContentsFile(seq) {
            const indexOfChecked = this.checkContentsFileList.findIndex(
                (el) => el === seq
            );
            if (indexOfChecked === -1) {
                this.checkContentsFileList.push(seq);
            } else {
                this.checkContentsFileList = this.checkContentsFileList.filter(
                    (el) => {
                        return el !== seq;
                    }
                );
            }
            this.checkAll =
                this.checkContentsFileList.length ===
                this.contentsFileList.length;
        },
        async getFolderDetail() {
            try {
                const {
                    data: { data: response },
                } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id
                );
                this.folderDetail = response;
            } catch (error) {
                console.log(error);
            }
        },
        async getFolderDetailFile(infinite) {
            this.loadingData = true;
            this.checkAll = false;
            this.checkContentsFileList = [];
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id,
                    {
                        page: this.page,
                        size: this.itemLength,
                        sectionCode: this.sectionCode.value,
                        orderType: this.orderType.value,
                        fileExtension: this.fileExtension.value,
                    }
                );
                this.totalPage = response.totalPages - 1;
                if (infinite) {
                    this.contentsFileList = this.contentsFileList.concat(
                        response.content
                    );
                } else {
                    this.contentsFileList = response.content;
                }
                this.page++;
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
        async addContBasket(seq) {
            try {
                await addContentsBasket(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    seq.filter((el) => {
                        return !this.storeContBasketList.includes(el);
                    })
                );
                await this.$store.dispatch('getContBasket');
            } catch (e) {
                console.log(e);
            }
        },
    },
    created() {
        this.getFolderDetail();
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
};
</script>
<style scoped>
.sorting-list-wrap {
    position: relative;
}
.sorting-list-wrap .btn-box {
    position: absolute;
    top: 0;
    right: 0;
}
</style>
