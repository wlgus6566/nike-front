<template>
    <draggable
        tag="ul"
        v-bind="dragOptions"
        v-if="groupTreeData"
        :list="groupTreeData"
        :class="[`tree-depth${depth}`]"
    >
        <li v-for="item in groupTreeData" :key="item.authSeq">
            <div :class="treeItemClass(item.authSeq)">
                <a href="#" @click.prevent="selectActive(item)">
                    <i class="tree-icon"></i>
                    <span class="tree-name">{{ item.authName }}</span>
                </a>
                <button
                    v-if="depth !== 0 && item.subAuths && item.subAuths.length"
                    type="button"
                    class="tree-toggle"
                    @click="toggle(item.authSeq)"
                >
                    toggle
                </button>
            </div>
            <transition @enter="itemOpen" @leave="itemClose" :css="false">
                <GroupTreeList
                    v-show="
                        depth === 0 ||
                        groupTreeOpen.some((el) => el === item.authSeq)
                    "
                    v-if="item.subAuths"
                    :groupTreeData="item.subAuths"
                    :groupTreeActive="groupTreeActive"
                    :groupTreeOpen="groupTreeOpen"
                    :groupTreeAddItem="groupTreeAddItem"
                    :depth="depth + 1"
                />
            </transition>
            <transition @enter="itemOpen" @leave="itemClose" :css="false">
                <div
                    :class="[`tree-depth${depth + 1}`]"
                    v-if="
                        groupTreeAddItem === item.authSeq &&
                        groupTreeOpen.some((el) => el === item.authSeq)
                    "
                >
                    <div class="tree-item active">
                        <i class="tree-icon"></i>
                        <input type="text" />
                    </div>
                </div>
            </transition>
        </li>
    </draggable>
</template>
<script>
import bus from '@/utils/bus';
import { Cubic, gsap } from 'gsap/all';
import draggable from 'vuedraggable';
export default {
    name: 'GroupTreeList',
    data() {
        return {};
    },
    mounted() {},
    computed: {
        dragOptions() {
            return {
                touchStartThreshold: 5,
                animation: 100,
                disabled: false,
                ghostClass: 'ghost',
                forceFallback: true,
            };
        },
    },
    components: {
        draggable,
    },
    props: [
        'groupTreeData',
        'groupTreeActive',
        'groupTreeOpen',
        'groupTreeAddItem',
        'depth',
    ],
    methods: {
        treeItemClass(authSeq) {
            return {
                'tree-item': true,
                active: this.groupTreeActive.authSeq === authSeq,
                open: this.groupTreeOpen.some((el) => el === authSeq),
            };
        },
        selectActive(item) {
            bus.$emit('selectActive', item);
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
.ghost {
    opacity: 1;
}
</style>
