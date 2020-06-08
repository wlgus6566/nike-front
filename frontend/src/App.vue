<template>
    <div id="app">
        <transition mode="out-in" name="layout-change">
            <component :is="layout" />
        </transition>
    </div>
</template>
<script>
import * as layouts from './views/layouts'

export default {
    name: 'App',
    data() {
        return {
            navActive: true,
            //layout: 'clean-layout'
        }
    },
    computed: {
        layout() {
            console.log(this.$route.meta)
            return `${this.$route.meta.layout || 'default'}-layout`
        },
    },
    components: {
        ...layouts,
    },
    mounted() {
        console.log(this.layout)
    },
    methods: {},
}
</script>
<style>
@import url('assets/css/fonts.css');
@import url('assets/css/layout.css');
@import url('assets/css/reset.css');

.layout-change-enter-active,
.layout-change-leave-active {
    transition: opacity 0.3s ease;
}
.layout-change-enter,
	.layout-change-leave-to
		/* .layout-change-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
