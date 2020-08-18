<template>
    <ul :class="[`tree-depth${depth}`]">
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
            <div>
                <input
                    type="checkbox"
                    :value="item.authSeq"
                    v-model="detailAuthYn"
                    @click="detailAuthYnUpdate(item.authSeq)"
                />
                <input
                    type="checkbox"
                    :value="item.authSeq"
                    v-model="emailReceptionYn"
                    @click="$emit('emailReceptionYnUpdate', item.authSeq)"
                />
            </div>
            <transition @enter="itemOpen" @leave="itemClose" :css="false">
                <GroupTreeListTable
                    v-show="
                        depth === 0 ||
                        groupTreeOpen.some((el) => el === item.authSeq)
                    "
                    v-if="item.subAuths"
                    :groupTreeData="item.subAuths"
                    :groupTreeActive="groupTreeActive"
                    :groupTreeOpen="groupTreeOpen"
                    :groupTreeAddItem="groupTreeAddItem"
                    :checks="checks"
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
    mounted() {},
    computed: {
        detailAuthYn() {
            const arr = this.checks.filter((el) => el.detailAuthYn === 'Y');
            return arr.map((el) => el.authSeq);
        },
        emailReceptionYn() {
            const arr = this.checks.filter((el) => el.emailReceptionYn === 'Y');
            return arr.map((el) => el.authSeq);
        },
    },
    components: {},
    props: [
        'groupTreeData',
        'groupTreeActive',
        'groupTreeOpen',
        'groupTreeAddItem',
        'depth',
        'checks',
    ],
    methods: {
        detailAuthYnUpdate(seq) {
            bus.$emit('detailAuthYnUpdate', seq);
        },
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
