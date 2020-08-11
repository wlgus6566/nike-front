<template>
    <div>
        <h3 class="form-title mt20">등록/수정</h3>
        <hr class="hr-black" />
        <ul class="form-list">
            <EditInner />
        </ul>
        <hr class="hr-gray" />
        <div class="btn-area">
            <button type="button" class="btn-s-white" @click="$router.go(-1)">
                <span>취소</span>
            </button>
            <button type="button" class="btn-s-black"><span>저장</span></button>
        </div>
    </div>
</template>
<script>
import { getCustomerList } from '@/api/customer';
export default {
    name: 'Edit',
    data() {
        return {};
    },
    components: {
        EditInner: () => import('@/components/news/edit-inner.vue'),
    },
    methods: {},
};
</script>
<style scoped></style>
