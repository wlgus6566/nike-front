<template>
    <el-dialog
        title=""
        class="modal-wrap"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div class="order-detail-view">
                    <div class="thumbnail">
                        <img src="http://placehold.it/400x200" alt="임시이미지" />
                    </div>
                    <div class="info-box">
                        <div class="title-box">
                            <strong class="title">
                                일이삼사오육칠팔구십일이삼사오육칠팔구십일
                                일이삼사오육칠팔구십일이삼사오육칠팔구십일
                            </strong>
                            <p class="txt">
                                일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠
                            </p>
                            <div class="desc-txt-box">
                                <span class="desc-txt">1,000원</span>
                                <span class="desc-txt">최소주문수량 1,000개</span>
                            </div>
                        </div>
                        <div class="btn-box">
                            <button type="button" class="btn-s">
                                <span>위시리스트 담기</span>
                            </button>
                            <button type="button" class="btn-s-black">
                                <span>CART 담기</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
export default {
    props: {
        visible: Boolean,
        receipt: Object,
    },

    methods: {
        removeBodyClass(className) {
            const body = document.body;
            body.classList.remove(className);
        },
        print() {
            this.removeBodyClass('print-detail');
            window.print();
        },
    },
};
</script>
<style>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
.modal-wrap .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
</style>
