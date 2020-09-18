<template>
    <footer>
        <ul class="info-list">
            <li>
                <a href="#" @click.prevent="termsOpen('service')">
                    <span>이용약관</span>
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="termsOpen('privacy')">
                    <span>개인정보처리방침</span>
                </a>
            </li>
        </ul>
        <p class="copy">© 2020 Nike Korea LLC. All Rights Reserved.</p>
        <terms
            :visible.sync="visible.termsModal"
            :tabContent="tabContent"
            @termsUpdate="termsUpdate"
        />
    </footer>
</template>
<script>
export default {
    name: 'app-footer',
    components: {
        terms: () => import('@/views/pages/terms/index'),
    },
    data() {
        return {
            tabContent: 'privacy',
            visible: {
                termsModal: false,
            },
        };
    },
    methods: {
        termsUpdate(value) {
            this.tabContent = value;
        },
        termsOpen(value) {
            this.termsUpdate(value);
            this.visible.termsModal = true;
        },
    },
};
</script>
<style scoped></style>
