<template>
    <div>
        <BtnArea @delete="deleteFolder" @edit="editFolder">
            <button type="button" class="btn-o-gray">
                <i class="icon-mail"></i>
                <span>알림메일전송</span>
            </button>
        </BtnArea>
        <folder :folderDetail="folderDetail" v-if="folderDetail"></folder>
        <SortingList />
        <fileItem :contentsFileList="contentsFileList"></fileItem>
    </div>
</template>
<script>
import BtnArea from '@/components/asset-view/btn-area.vue';
import folder from '@/components/asset-view/folder.vue';
import SortingList from '@/components/asset-view/sorting-list.vue';
import fileItem from '@/components/asset-view/file-Item.vue';
import { getContentsView, getContentsViewFile } from '@/api/contents';

export default {
    name: 'folder-view',
    data() {
        return {
            folderDetail: null,
            contentsFileList: null,
            listSortTab: {
                listSortOptions: ['ALL', 'ASSET', 'GUIDE'],
                value: 'ALL',
            },
        };
    },
    mounted() {},
    components: {
        BtnArea,
        folder,
        SortingList,
        fileItem,
    },
    computed: {},
    created() {
        this.getFolderDetail();
        this.getFolderDetailFile();
    },
    methods: {
        async deleteFolder() {},
        async editFolder() {},
        async getFolderDetail() {
            try {
                const {
                    data: { data: response },
                } = await getContentsView(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id
                );
                console.log(response);
                this.folderDetail = response;
            } catch (error) {
                console.log(error);
            }
        },
        async getFolderDetailFile() {
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id,
                    {
                        page: 0,
                        size: 10,
                        sectionCode: 'ALL',
                        orderType: 'ORDER',
                        fileExtension: '',
                    }
                );
                console.log(response);
                this.contentsFileList = response;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped></style>
