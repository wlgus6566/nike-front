<template>
    <div>
        <h3 class="form-title">파일 설정</h3>
        <hr class="hr-black" />
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click="add">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
        <FileItem />
        <component v-for="item in buttons" :is="item"></component>
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click="add">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-s-white"><span>취소</span></button>
            <button type="button" class="btn-s-black"><span>저장</span></button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'FileSettings',
    data() {
        return {
            buttons: [],
        };
    },
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
    },
    methods: {
        add() {
            this.buttons.push('FileItem');
        },
    },
};
</script>
<style scoped></style>
