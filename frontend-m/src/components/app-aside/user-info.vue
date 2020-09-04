<template>
    <div class="user-info">
        <span class="store-name">{{ userNickname }}</span>
        <p class="store">{{ userAuthName }}</p>
        <a :href="`mailto:${userIdVal}`" class="mail">{{ userIdVal }}</a>
    </div>
</template>
<script>
import {
    getAuthNameFromCookie,
    getUserIdFromCookie,
    getUserNickFromCookie,
} from '@/utils/cookies';

export default {
    name: 'user-info',
    computed: {
        userNickname() {
            return this.$store.state.nick || getUserNickFromCookie();
        },
        userIdVal() {
            return this.$store.state.user || getUserIdFromCookie();
        },
        userAuthName() {
            return this.$store.state.authName || getAuthNameFromCookie();
        },
    },
};
</script>
<style scoped></style>
