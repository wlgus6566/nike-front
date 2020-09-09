<template>
    <div class="search-input">
        <el-input
            type="text"
            @keyup.enter.native="submit"
            v-model="input"
            placeholder="검색어를 입력해주세요."
        />
        <button type="button" @click="submit">
            <span>검색</span>
        </button>
    </div>
</template>
<script>
export default {
    name: 'searchInput',
    data() {
        return {
            input: null,
        };
    },
    methods: {
        submit() {
            if (this.input !== null) {
                this.$emit('searchSubmit', this.input);
                if (this.input === '') {
                    this.input = null;
                }
            }
        },
    },
    activated() {
        this.input = null;
    },
};
</script>
<style scoped></style>
