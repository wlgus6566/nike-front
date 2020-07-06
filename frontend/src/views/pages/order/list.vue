<template>
    <div>
        <router-link to="/order/subsidiary/open-package/5">123</router-link>
        {{ $route.meta.title }}
    </div>
</template>
<script>
export default {
    name: 'list',
};
</script>
<style scoped></style>
