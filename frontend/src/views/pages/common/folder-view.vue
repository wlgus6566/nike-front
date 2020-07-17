<template>
    <div>
        <BtnArea></BtnArea>
        <folder></folder>
        <SortingList></SortingList>
        <allCheck></allCheck>
        <fileItem></fileItem>
    </div>
</template>
<script>
import BtnArea from '@/components/asset-view/btn-area.vue';
import folder from '@/components/asset-view/folder.vue';
import SortingList from '@/components/asset-view/sorting-list.vue';
import allCheck from '@/components/asset-view/all-check.vue';
import fileItem from '@/components/asset-view/file-Item.vue';
export default {
    name: 'folder-view',
    data() {
        return {};
    },
    components: {
        BtnArea,
        folder,
        SortingList,
        allCheck,
        fileItem,
    },
};
</script>
<style scoped></style>
