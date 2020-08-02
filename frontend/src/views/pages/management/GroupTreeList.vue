<template>
    <ul :class="[`tree-depth${depth}`]">
        <li v-for="item in groupTreeData" :key="item.seq">
            <div :class="treeItemClass(item.seq)">
                <a href="#" @click.prevent="selectActive(item)">
                    <i class="tree-icon"></i>
                    <span class="tree-name">{{ item.name }}</span>
                </a>
                <button
                    v-if="depth !== 0 && item.child"
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
                    <div class="tree-item">
                        <i class="tree-icon"></i>
                        <input type="text" />
                    </div>
                </div>
            </transition>
        </li>
    </ul>
</template>
<script>
import bus from '@/utils/bus';
import { Cubic, gsap } from 'gsap/all';
export default {
    name: 'GroupTreeList',
    data() {
        return {};
    },
    mounted() {
        console.log(this.groupTreeOpen);
    },
    computed: {},
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
    },
};
</script>
<style scoped></style>
