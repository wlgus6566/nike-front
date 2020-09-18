<template>
    <ul class="menu-list" v-if="menuData">
        <li
            v-for="(menu, index) in menuData"
            :key="menu.menuSeq"
            ref="menuItem"
            :class="{ active: activeIndex === index }"
        >
            <button
                type="button"
                @click="menuFn($event, menu.menuSeq)"
                v-html="menuTitle(menu.menuName)"
            />
            <transition @enter="itemOpen" @leave="itemClose">
                <ul class="depth" v-if="activeIndex === index">
                    <li
                        v-for="depth in depthMenu(menu.menus)"
                        :key="depth.menuSeq"
                        @click="$emit('menuClose')"
                    >
                        <router-link
                            :to="depth.menuPathUrl"
                            v-html="depthMenuTitle(depth.menuName)"
                        >
                        </router-link>
                    </li>
                </ul>
            </transition>
        </li>
    </ul>
</template>
<script>
import { Cubic, gsap } from 'gsap/all';

export default {
    name: 'menu-list',
    data() {
        return {
            activeIndex: null,
        };
    },
    props: ['menuUse', 'menuData'],
    mounted() {
        this.linkOpen();
        this.routerFn();
    },
    computed: {
        pathUrl() {
            return this.$route.path;
        },
    },
    watch: {},
    methods: {
        depthMenu(menus) {
            const depth = menus.filter(el => {
                return el.listYn === 'Y';
            });
            return depth;
        },
        menuTitle(title) {
            const info =
                '<span>INFORMATION</span><span class="ko">에이전시 정보</span>';
            if (title === info) {
                return '<span>INFO.</span>';
            } else {
                return title;
            }
        },
        depthMenuTitle(title) {
            if (title === 'REPORT <span>업로드</span>') {
                return '<span>업로드</span>';
            } else if (title === 'REPORT <span>관리</span>') {
                return '<span>관리</span>';
            } else if (title === 'AGENCY CONTACT') {
                return 'AGENCY';
            } else {
                return title;
            }
        },
        routerFn() {
            const menuName = this.menuData.map(el => el.menuPathUrl);
            const path = this.pathUrl.split('/')[1];
            const _index = menuName.findIndex(el => {
                return el === `/${path}`;
            });
            this.activeIndex = _index;
            return {};
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
                paddingTop: '10px',
                paddingBottom: '10px',
            });
            gsap.from(el, 0.3, {
                height: 0,
                paddingTop: 0,
                paddingBottom: 0,
                ease: Cubic.easeInOut,
                onComplete: function() {
                    el.style.height = 'auto';
                    el.style.paddingTop = '10px';
                    el.style.paddingBottom = '10px';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                paddingTop: 0,
                paddingBottom: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
        menuFn(e, seq) {
            const seqList = this.menuData.map(el => el.menuSeq);
            const _index = seqList.indexOf(seq);
            if (this.activeIndex !== _index) {
                this.activeIndex = _index;
            } else {
                this.activeIndex = null;
            }
        },
        linkOpen() {
            //console.log(this.$route.path);
        },
    },
};
</script>
<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
