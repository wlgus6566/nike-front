<template>
    <div>
        <button type="button" @click="showModal = !showModal">modal open</button>
        <!--v-if="showModal" -->
        <modalComp @close="showModal = false">
            <!-- <template slot="header">
                modal header
            </template>-->
            <template slot="content">
                modal content
            </template>
            <!--<template slot="footer">
                modal footer
            </template>-->
        </modalComp>

        <div>
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
            main<br />
        </div>
    </div>
</template>
<script>
import modalComp from '@/components/modal-comp/index';
export default {
    name: 'MainPage',
    components: { modalComp },
    data() {
        return {
            showModal: false,
        };
    },
    methods: {},
};
</script>
<style scoped></style>
