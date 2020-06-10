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
    transition: all 0.3s ease-in-out 0.3ms;
}
.aside-ani-enter,
.aside-ani-leave-to {
    opacity: 0;
    transform: translateY(10px);
}

.component-fade-enter-active,
.component-fade-leave-active {
    transition: opacity 0.3s ease;
}
.component-fade-enter,
.component-fade-leave-to
	/* .component-fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
