<template>
    <div id="wrap">
        <appHeader />
        <section id="container">
            <div class="contents">
                <transition name="component-fade" mode="out-in">
                    <router-view></router-view>
                </transition>
            </div>
            <transition mode="out-in" name="aside-ani">
                <component :is="AppAside" />
            </transition>
        </section>
        <footer>
            footer
        </footer>
    </div>
</template>
<script>
import appHeader from '@/components/app-header';

export default {
    name: 'LayoutDefault',
    data() {
        return {};
    },
    computed: {
        AppAside() {
            return `Aside${this.$route.meta.aside || 'Default'}`;
        },
    },
    components: {
        appHeader,
        AsideDefault: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideFile: () => import('@/components/app-aside/AsideDefault.vue'),
        AsideOrder: () => import('@/components/app-aside/AsideOrder.vue'),
    },
};
</script>
<style scoped>
.aside-ani-enter-active {
    transition: all 0.3s ease-in-out;
}
.aside-ani-leave-active {
    transition: all 0.3s ease-in-out;
}
.aside-ani-enter,
.aside-ani-leave-to {
    opacity: 0;
    transform: translateX(100%);
}

.layout-change-enter-active,
.layout-change-leave-active {
    transition: opacity 1s ease;
}
.layout-change-enter,
.layout-change-leave-to {
    opacity: 0;
}
</style>
