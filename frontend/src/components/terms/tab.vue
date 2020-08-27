<template>
    <ul class="box-tab">
        <li :class="{ active: tabName === '개인정보 처리방침' }">
            <router-link to="/terms/privacy">개인정보 처리방침</router-link>
        </li>
        <li :class="{ active: tabName === '이용약관' }">
            <router-link to="/terms/service">이용약관</router-link>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'termsTab',
    props: ['tabName'],
    mounted() {
        console.log(this.tabName);
    },
    data() {
        return {
            termsHistory: '2020.01.01',
        };
    },
};
</script>
