<template>
    <ul class="menu-list" v-if="menuData">
        <li
            v-for="(menu, index) in menuData"
            :key="menu.menuSeq"
            ref="menuItem"
            :class="{ active: activeIndex === index }"
        >
            <button type="button" @click="menuFn($event, menu.menuSeq)">
                <span>{{ menu.menuName }}</span>
            </button>
            <transition @enter="itemOpen" @leave="itemClose">
                <ul class="depth" v-if="activeIndex === index">
                    <li
                        v-for="depth in menu.menus"
                        :key="depth.menuSeq"
                        @click="$emit('menuClose')"
                    >
                        <router-link :to="depth.menuPathUrl">
                            {{ depth.menuName }}
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
    },
    methods: {
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
            //const menu = this.$refs.menuItem[_index];
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
.menu-list {
    margin-top: 40px;
    max-height: calc(100% - 40px - 95px);
}
.menu-list li {
    overflow: hidden;
    transition: height ease-in-out 0.3s;
}
.menu-list li button,
.menu-list li a {
    position: relative;
    display: block;
    width: 100%;
    border-bottom: 1px solid #000;
    line-height: 50px;
    text-align: left;
    border-radius: 0;
    font-size: 18px;
    color: #000;
    font-family: 'Bebas Neue', 'Noto Sans KR', sans-serif;
    letter-spacing: 0.05em;
}
.menu-list li button:before,
.menu-list li a:before {
    content: '';
    position: absolute;
    top: 50%;
    right: -10px;
    display: block;
    width: 36px;
    height: 36px;
    background: url(../../assets/images/svg/icon-plus.svg) no-repeat center;
    transform: translateY(-50%);
}
.menu-list li.active {
    transition: height ease-in-out 0.3s;
}
.menu-list li.active > button:before,
.menu-list li.active > a:before {
    background-image: url(../../assets/images/svg/icon-minus.svg);
}
.menu-list li button span,
.menu-list li a span {
    font-size: 16px;
    letter-spacing: -0.5px;
}
.depth {
    padding: 10px 0;
}
.depth li a {
    line-height: 40px;
    font-size: 16px;
    color: #888;
    border: none;
}
.depth li a:before {
    right: 0;
    width: 20px;
    height: 20px;
    background: url(../../assets/images/svg/icon-more-gray.svg) no-repeat center;
}
</style>
