<template>
    <ul :class="[`tree-depth${depth}`]">
        <li v-for="item in groupTreeData" :key="item.authSeq" class="tree-item">
            <div class="inner">
                <button
                    type="button"
                    :class="treeItemClass(item.authSeq)"
                    @click="toggle(item.authSeq)"
                >
                    <i class="tree-icon"></i>
                    <span class="tree-name">{{ item.authName }}</span>
                    <i
                        class="tree-arr"
                        v-if="
                            depth !== 0 && item.subAuths && item.subAuths.length
                        "
                    ></i>
                </button>
                <label v-if="depth !== 0">
                    <span class="checkbox">
                        <input
                            type="checkbox"
                            :checked="item.detailAuthYn === 'Y'"
                            @click="detailAuthUpdate(item.authSeq)"
                        />
                        <i></i>
                    </span>
                </label>
                <label v-if="depth !== 0">
                    <span class="checkbox">
                        <input
                            type="checkbox"
                            :checked="item.emailReceptionYn === 'Y'"
                            @click="emailReceptionUpdate(item.authSeq)"
                        />
                        <i></i>
                    </span>
                </label>
            </div>
            <transition @enter="itemOpen" @leave="itemClose" :css="false">
                <GroupTreeListTable
                    v-show="
                        depth === 0 ||
                        groupTreeOpen.some((el) => el === item.authSeq)
                    "
                    v-if="item.subAuths"
                    :groupTreeData="item.subAuths"
                    :groupTreeOpen="groupTreeOpen"
                    :groupTreeAddItem="groupTreeAddItem"
                    :detailAuthCheck="detailAuthCheck"
                    :emailReceptionCheck="emailReceptionCheck"
                    :depth="depth + 1"
                />
            </transition>
        </li>
    </ul>
</template>
<script>
import bus from '@/utils/bus';
import { Cubic, gsap } from 'gsap/all';
export default {
    name: 'GroupTreeListTable',
    data() {
        return {};
    },
    computed: {
        /*detailAuthYn() {
            const arr = this.groupTreeData.filter(
                (el) => el.detailAuthYn === 'Y'
            );
            return arr.map((el) => el.authSeq);
        },
        emailReceptionYn() {
            const arr = this.groupTreeData.filter(
                (el) => el.emailReceptionYn === 'Y'
            );
            return arr.map((el) => el.authSeq);
        },*/
    },
    components: {},
    props: [
        'groupTreeData',
        'groupTreeOpen',
        'groupTreeAddItem',
        'detailAuthCheck',
        'emailReceptionCheck',
        'depth',
    ],
    methods: {
        detailAuthUpdate(seq) {
            bus.$emit('detailAuthUpdate', seq);
        },
        emailReceptionUpdate(seq) {
            bus.$emit('emailReceptionUpdate', seq);
        },
        treeItemClass(authSeq) {
            return {
                'tree-txt': true,
                open: this.groupTreeOpen.some((el) => el === authSeq),
            };
        },
        toggle(seq) {
            bus.$emit('groupTreeToggle', seq);
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
            });
            gsap.from(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: function () {
                    el.style.height = 'auto';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
    },
};
</script>
<style scoped>
.tree-item {
}
.tree-depth0 .tree-icon {
    width: 20px;
    background: url('../../assets/images/svg/icon-group-tree-depth-all.svg') 50% /
        contain;
}
.tree-depth2 {
    background: #f7f7f7;
}
.tree-depth2 > .tree-item > .inner {
    padding-left: 30px;
}
::v-deep .tree-depth2 > .tree-item > .inner > .tree-txt {
    width: 230px;
}

.tree-depth3 > .tree-item > .inner {
    padding-left: 60px;
}
::v-deep .tree-depth3 > .tree-item > .inner > .tree-txt {
    width: 200px;
}
.tree-depth0 ul {
    overflow: hidden;
}
.tree-depth1 .tree-icon {
    width: 30px;
    background: url('../../assets/images/svg/icon-group-tree-depth1.svg') 50% /
        contain;
}
.tree-depth2 .tree-icon {
    width: 30px;
    background: url('../../assets/images/svg/icon-group-tree-depth2.svg') 50% /
        contain;
}
.tree-depth3 .tree-icon {
    width: 3px;
    height: 3px;
    background: #333;
    display: inline-block;
}
.tree-icon {
    width: 30px;
    height: 20px;
    margin-right: 5px;
    display: inline-block;
    vertical-align: middle;
    flex: 0 0 auto;
}
.tree-name {
    display: inline-block;
    vertical-align: middle;
}
.tree-arr {
    display: inline-block;
    height: 20px;
    width: 20px;
    vertical-align: middle;
    background: red;
    flex: 0 0 auto;
    background: url('../../assets/images/svg/icon-group-tree-depth-toggle.svg')
        50% / contain;
}
::v-deep .tree-txt.open > .tree-arr {
    transform: rotate(-180deg);
}
.tree-item > .inner {
    display: flex;
    padding-right: 40px;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #eee;
}
.tree-item > .inner .tree-txt {
    display: flex;
    line-height: 20px;
    padding: 10px;
    box-sizing: border-box;
    width: 260px;
    align-items: center;
}
.tree-item > .inner label {
    width: 100px;
    text-align: center;
}
</style>
