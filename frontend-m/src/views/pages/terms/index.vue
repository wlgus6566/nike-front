<template>
    <el-dialog
        class="modal full"
        :visible="visible"
        fullscreen
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <div class="modal-contents terms">
            <ul class="nav-tab">
                <li :class="{ active: tabContent === 'privacy' }">
                    <a
                        href="#"
                        @click.prevent="$emit('termsUpdate', 'privacy')"
                    >
                        <span>개인정보 처리방침</span>
                    </a>
                </li>
                <li :class="{ active: tabContent === 'service' }">
                    <a
                        href="#"
                        @click.prevent="$emit('termsUpdate', 'service')"
                    >
                        <span>이용약관</span>
                    </a>
                </li>
            </ul>
            <component :is="tabContent" :key="tabContent"></component>
        </div>
    </el-dialog>
</template>
<script>
export default {
    name: 'terms',
    components: {
        privacy: () => import('@/components/terms/privacy'),
        service: () => import('@/components/terms/service'),
    },
    props: ['visible', 'tabContent'],
    data() {
        return {
            termsHistory: '2020.09.21',
        };
    },
    methods: {},
};
</script>
<style scoped></style>
