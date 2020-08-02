<template>
    <div>
        <h2 class="page-title">권한 그룹관리</h2>

        <div class="group-management">
            <div class="group-tree sticky-container" sticky-container>
                <div
                    class="group-tree-fixed sticky-content"
                    sticky-offset="{top:0, bottom:0}"
                    sticky-side="both"
                    sticky-z-index="20"
                    v-sticky
                >
                    <el-scrollbar view-class="view-box" :native="false">
                        <div class="group-tree-inner">
                            <h3>전체 권한 그룹</h3>
                            <ul>
                                <li>
                                    <span>NSP KEY ACCOUNT</span>
                                    <button>toggle</button>
                                    <ul>
                                        <li>
                                            <span>대연</span>
                                            <button>toggle</button>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span>NSP KEY ACCOUNT</span>
                                    <button>toggle</button>
                                </li>
                            </ul>
                        </div>
                    </el-scrollbar>
                    <div>
                        <button>삭제</button>
                        <button>추가</button>
                    </div>
                </div>
            </div>
            <div class="group-detail">detail</div>
        </div>
    </div>
</template>
<script>
import Sticky from 'vue-sticky-directive';
export default {
    name: 'group',
    directives: {
        Sticky,
    },
};
</script>
<style scoped>
.group-management {
    display: flex;
}
.group-tree {
    flex: 0 0 220px;
    width: 220px;
    background: red;
}
.group-tree-fixed {
    background: blue;
    width: 220px;
    box-sizing: border-box;
    border: 1px solid #ddd;
}
.group-detail {
    height: 3000px;
    flex: 1 1 auto;
    margin-left: 30px;
    background: red;
}
</style>
