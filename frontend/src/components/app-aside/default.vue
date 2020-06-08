<template>
    <aside class="sticky-container" sticky-container>
        <div
            class="inner"
            on-stick="onStick"
            sticky-offset="{top: 10, bottom: 30}"
            sticky-side="both"
            sticky-z-index="20"
            v-sticky
        >
            <div class="aside-wrap">
                <user></user>
                <asideMenu></asideMenu>
                <file></file>
                <div class="tab-box">
                    <ul class="tab-list">
                        <li class="active">
                            <a href="#"><span>HISTORY</span></a>
                        </li>
                        <li>
                            <a href="#"><span>ORDER</span></a>
                        </li>
                    </ul>
                    <div class="tab-contents">
                        <ul>
                            <li></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </aside>
</template>
<script>
import Sticky from 'vue-sticky-directive'
import user from '../../components/aside/user.vue'
import asideMenu from '../../components/aside/asideMenu.vue'
import file from '../../components/aside/file.vue'

export default {
    name: 'default-aside',
    directives: {
        Sticky,
    },
    components: {
        user,
        asideMenu,
        file,
    },
    mounted() {},
    methods: {},
}
</script>
<style scoped>
.tab-box {
    margin-top: 63px;
}
.tab-list {
    display: flex;
    margin-left: -15px;
}
.tab-list a {
    display: block;
    padding: 0 15px;
    line-height: 40px;
    font-size: 14px;
    color: #ccc;
    font-weight: bold;
}
.tab-list .active a {
    color: #000;
}
.tab-list .active span {
    text-decoration: underline;
}
</style>
