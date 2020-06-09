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
                <component :is="aside" />
            </transition>
        </section>
        <footer>
            footer
        </footer>
    </div>
</template>
<script>
import appHeader from '@/components/AppHeader';
import * as appAside from '@/components/AppAside/index.js';

export default {
    name: 'default-layout',
    data() {
        return {
            //aside : 'default-aside'
        };
    },


    computed: {
        aside() {
            if (this.$route.meta.aside) {
                return `Aside${this.$route.meta.aside || 'Default'}`;
            }
        },
    },
    components: {
        appHeader,
        ...appAside,
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
</style>
