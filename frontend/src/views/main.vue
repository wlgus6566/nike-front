<template>
    <div>
        <el-input-number v-model="num" @change="handleChange" :min="1" :max="10"></el-input-number>
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
        main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />main<br />
    </div>
</template>
<script>
export default {
    data() {
        return {
            num: 1,
        };
    },
    methods: {
        handleChange(value) {
            console.log(value);
        },
    },
};
</script>
<style scoped></style>
