<template>
    <div id="app">
        <transition name="component-fade" mode="out-in">
            <component :is="layout" />
        </transition>
    </div>
</template>
<script>
import * as layouts from './views/layouts';

export default {
    name: 'App',
    data() {
        return {
            navActive: true,
        };
    },
    watch: {
        layout() {
            //console.log(this.layout);
        },
    },
    computed: {
        layout() {
            return `Layout${this.$route.meta.layout || 'Default'}`;
        },
    },
    components: {
        ...layouts,
    },
};
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');

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
