<template>
    <draggable
        tag="ul"
        v-bind="dragOptions"
        v-if="groupTreeData"
        :list="groupTreeData"
        :class="[`tree-depth${depth}`]"
        @choose="onChoose"
        @start="onStart"
        @end="onEnd"
        @add="onAdd"
        @move="onMove"
        @update="onUpdate"
        @sort="onSort"
        @remove="onRemove"
        @change="onChange"
        @unchoose="onUnchoose"
    >
        <li v-for="item in groupTreeData" :key="item.seq">
            <div :class="treeItemClass(item.seq)">
                <a href="#" @click.prevent="selectActive(item)">
                    <i class="tree-icon"></i>
                    <span class="tree-name">{{ item.name }}</span>
                </a>
                <button
                    v-if="depth !== 0 && item.child && item.child.length"
                    type="button"
                    class="tree-toggle"
                    @click="toggle(item.seq)"
                >
                    toggle
                </button>
            </div>
            <transition @enter="itemOpen" @leave="itemClose" :css="false">
                <GroupTreeList
                    v-show="
                        depth === 0 || groupTreeOpen.some(el => el === item.seq)
                    "
                    v-if="item.child"
                    :groupTreeData="item.child"
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
                        groupTreeAddItem === item.seq &&
                            groupTreeOpen.some(el => el === item.seq)
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
    mounted() {
        console.log(this.groupTreeOpen);
    },
    computed: {
        dragOptions() {
            return {
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
        treeItemClass(seq) {
            return {
                'tree-item': true,
                active: this.groupTreeActive.seq === seq,
                open: this.groupTreeOpen.some(el => el === seq),
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
                onComplete: function() {
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

        onChoose(e) {
            console.log('onChoose', e);
        },
        onStart(e) {
            console.log('onStart', e);
        },
        onEnd(e) {
            console.log('onEnd', e);
        },
        onAdd(e) {
            console.log('onAdd', e);
        },
        onMove(e) {
            console.log('onMove', e);
        },
        onUpdate(e) {
            console.log('onUpdate', e);
        },
        onSort(e) {
            console.log('onSort', e);
        },
        onRemove(e) {
            console.log('onRemove', e);
        },
        onChange(e) {
            console.log('onChange', e);
        },
        onUnchoose(e) {
            console.log('onUnchoose', e);
        },
    },
};
</script>
<style scoped>
.ghost {
    opacity: 1;
}
</style>
