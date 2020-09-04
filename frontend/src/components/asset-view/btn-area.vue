<template>
    <div class="btn-detail">
        <a @click="$emit('goToList')" class="btn-list"
            ><span>목록으로 가기</span></a
        >
        <div class="btn-box">
            <button
                type="button"
                class="btn-o-white"
                @click="$emit('delete')"
                v-if="authCheckVal('DELETE')"
            >
                <span>삭제</span>
            </button>
            <button
                type="button"
                class="btn-o-white"
                @click="$emit('edit')"
                v-if="authCheckVal('CREATE')"
            >
                <span>수정</span>
            </button>
            <slot></slot>
        </div>
    </div>
</template>
<script>
import { authCheck } from '@/utils/authCheck';
export default {
    mixins: [authCheck],
    name: 'BtnArea',
};
</script>
<style scoped></style>
