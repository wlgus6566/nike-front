<template>
    <div class="group-tree sticky-container" sticky-container>
        <div
            class="group-tree-fixed sticky-content"
            sticky-offset="{top:10, bottom:10}"
            sticky-side="both"
            sticky-z-index="20"
            v-sticky
        >
            <el-scrollbar wrap-class="group-tree-scroll" :native="false">
                <div class="group-tree-inner">
                    <GroupTreeList
                        :authName="authName"
                        :groupTreeData="groupTreeData"
                        :groupTreeActive="groupTreeActive"
                        :groupTreeOpen="groupTreeOpen"
                        :groupTreeAddItem="groupTreeAddItem"
                        :depth="0"
                    />
                </div>
            </el-scrollbar>
            <div class="btn-group">
                <button
                    type="button"
                    class="del"
                    :disabled="deleteDisabled"
                    @click="groupTreeDel"
                >
                    삭제
                </button>
                <button
                    type="button"
                    class="add"
                    :disabled="addDisabled"
                    @click="groupTreeAdd"
                >
                    추가
                </button>
            </div>
        </div>
    </div>
</template>
<script>
import Sticky from 'vue-sticky-directive';
import GroupTreeList from '@/components/group-tree/tree-list';
import bus from '@/utils/bus';
export default {
    name: 'GroupTree',
    components: { GroupTreeList },
    props: [
        'authName',
        'groupTreeData',
        'groupTreeActive',
        'groupTreeOpen',
        'groupTreeAddItem',
    ],
    directives: {
        Sticky,
    },
    computed: {
        deleteDisabled() {
            return !(
                this.groupTreeActive.authSeq !== 'root' &&
                this.authName &&
                !this.groupTreeActive.subAuths
            );
        },
        addDisabled() {
            return (
                this.groupTreeActive.authDepth === 3 ||
                !this.groupTreeActive.authName
            );
        },
    },
    methods: {
        groupTreeDel() {
            bus.$emit('groupTreeDel');
        },
        groupTreeAdd() {
            bus.$emit('groupTreeAdd');
        },
    },
};
</script>

<style scoped>
.group-tree {
    flex: 0 0 220px;
    width: 220px;
}
.group-tree-fixed {
    display: flex;
    flex-direction: column;
    width: 220px;
    min-height: 640px;
    box-sizing: border-box;
    border: 1px solid #ddd;
}
::v-deep .group-tree-fixed > .el-scrollbar {
    flex-grow: 1;
}
.group-tree-fixed.top-sticky {
    /*border: 1px solid red;*/
}
.group-tree-inner {
    padding: 40px 25px;
    font-size: 12px;
    font-weight: 700;
}

.group-tree .btn-group {
    display: flex;
    border-top: 1px solid #ddd;
}
.group-tree .btn-group button {
    font-size: 12px;
    height: 36px;
    color: #333;
    border-radius: 0;
    background: #f7f7f7;
    flex: 1 1 50%;
}
.group-tree .btn-group button:before {
    content: '';
    vertical-align: -2px;
    height: 14px;
    width: 14px;
    display: inline-block;
}
.group-tree .btn-group button.del:before {
    background: url('../../assets/images/svg/icon-common-deleted.svg') 50% /
        contain;
}
.group-tree .btn-group button.add:before {
    background: url('../../assets/images/svg/icon-management-plus.svg') 50% /
        contain;
}
.group-tree .btn-group button[disabled] {
    color: #ccc;
}
.group-tree .btn-group button[disabled]:before {
    opacity: 0.3;
}
.group-tree .btn-group button + button {
    border-left: 1px solid #ddd;
}

/* list */
::v-deep .depth-wrap {
    overflow: hidden;
}
::v-deep .tree-item {
    display: flex;
    align-items: center;
}
::v-deep .tree-item.active > a .tree-name {
    color: #fa5400 !important;
    font-weight: 700 !important;
    text-decoration: underline;
}
::v-deep .tree-item.open > .tree-toggle {
    transform: rotate(-180deg);
}
::v-deep .tree-item > a {
    display: flex;
    align-items: center;
}
::v-deep .tree-item .tree-icon {
    flex: 0 0 auto;
    height: 20px;
    width: 30px;
    margin-right: 5px;
}
::v-deep .tree-item .tree-name {
    flex: 1 1 auto;
    line-height: 18px;
    color: #333;
    word-break: break-all;
}
::v-deep .tree-item input {
    height: 24px;
    width: 100%;
    border: 1px solid #fa5400;
    background: #f7f7f7;
}
::v-deep .tree-item .tree-toggle {
    flex: 0 0 20px;
    height: 20px;
    width: 20px;
    margin-left: 5px;
    text-indent: -9999px;
    background: url('../../assets/images/svg/icon-group-tree-depth-toggle.svg')
        50% / contain;
    transform-origin: 50% 50%;
    transition: transform 300ms ease-in-out 0s;
}
::v-deep .tree-item .tree-toggle[disabled] {
    opacity: 0.2;
}

::v-deep .tree-depth0 {
}
::v-deep .tree-depth0 .tree-item .tree-icon {
    width: 20px;
    background: url('../../assets/images/svg/icon-group-tree-depth-all.svg') 50% /
        contain;
}
::v-deep .tree-depth0 .tree-item.active .tree-icon {
    background-image: url('../../assets/images/svg/icon-group-tree-depth-all-active.svg');
}
::v-deep .tree-depth0 .tree-item .tree-name {
    flex: 1 1 auto;
    line-height: 18px;
    color: #333;
}
::v-deep ul.tree-depth1 {
    margin-top: 10px;
}
::v-deep .tree-depth1 {
    overflow: hidden;
}
::v-deep .tree-depth1 .tree-item {
    padding: 8px 0;
}
::v-deep .tree-depth1 .tree-item .tree-icon {
    width: 30px;
    background: url('../../assets/images/svg/icon-group-tree-depth1.svg') 50% /
        contain;
}
::v-deep .tree-depth1 .tree-item.active .tree-icon {
    background-image: url('../../assets/images/svg/icon-group-tree-depth1-active.svg');
}
::v-deep .tree-depth2 {
    overflow: hidden;
    margin-left: 10px;
}
::v-deep .tree-depth2 .tree-item {
    padding: 6px 0;
}
::v-deep .tree-depth2 .tree-item .tree-icon {
    background: url('../../assets/images/svg/icon-group-tree-depth2.svg') 50% /
        contain;
}
::v-deep .tree-depth2 .tree-item.active .tree-icon {
    background-image: url('../../assets/images/svg/icon-group-tree-depth2-active.svg');
}
::v-deep .tree-depth2 .tree-item .tree-name {
    font-weight: 300;
}
::v-deep .tree-depth3 {
    overflow: hidden;
    margin-left: 35px;
}
::v-deep .tree-depth3 .tree-item {
    padding: 5px 0;
}
::v-deep .tree-depth3 .tree-item .tree-icon {
    width: 3px;
    height: 3px;
    background: #333;
}
::v-deep .tree-depth3 .tree-item.active .tree-icon {
    background: #fa5400;
}
::v-deep .tree-depth3 .tree-item .tree-name {
    font-weight: 300;
}
</style>
