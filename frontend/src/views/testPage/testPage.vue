<template>
    <div>
        <input type="radio" value="ContentMypage" id="a" v-model="view" />
        <label for="a">A</label>
        <input type="radio" value="ContentOrder" id="b" v-model="view" />
        <label for="b">B</label>

        <transition name="component-fade" mode="out-in">
            <component v-bind:is="view"></component>
        </transition>
        <span>선택: {{ view }}</span>
    </div>
</template>
<script>
export default {
    name: 'testP',
    data() {
        return {
            view: 'ContentMypage',
        };
    },
    components: {
        ContentMypage: () => import('@/components/tab-components/ContentMypage.vue'),
        ContentOrder: () => import('@/components/tab-components/ContentOrder.vue'),
    },
};
</script>
<style scoped>
.component-fade-enter-active,
.component-fade-leave-active {
    transition: opacity 0.3s ease;
}
.component-fade-enter,
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
