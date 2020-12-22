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
                <h3 class="form-title mt0">ORDER</h3>
                <hr class="hr-black mt10" />
                <ul class="form-list">
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title">주문자</span>
                        </div>
                        <div class="form-column">
                            <span class="form-val">
                                {{ userNickname }}
                                <span class="val-dec">
                                    {{ userIdVal }}
                                </span>
                            </span>
                        </div>
                    </li>
                    <li class="form-row">
                        <div class="form-column">
                            <span class="label-title">총 예상 금액</span>
                        </div>
                        <div class="form-column">
                            <span class="form-val">
                                <em>{{
                                    totalPrice | formattedNumber('', '')
                                }}</em>
                                원
                            </span>
                        </div>
                    </li>
                </ul>
                <hr class="hr-gray" />
                <p class="form-desc">
                    * VAT 및 운송비,기타,운용비는 제외된 금액입니다. (실제
                    세금계산서의 금액은 다를 수 있습니다.)
                </p>
                <el-scrollbar
                    class="sheet-list-scroll"
                    wrap-class="sheet-list-wrap"
                    :native="false"
                >
                    <ul class="sheet-list">
                        <li
                            class="sheet-item"
                            v-for="(item, index) in basketList"
                            :key="index"
                        >
                            <span class="thumbnail">
                                <img
                                    :src="item.product.imageFilePhysicalName"
                                    :alt="item.product.imageFileName"
                                />
                            </span>
                            <span class="info-box">
                                <strong class="title">
                                    {{ item.product.goodsName }}
                                </strong>
                                <p class="txt">
                                    {{ item.product.goodsDescription }}
                                </p>
                                <span class="desc-txt-box">
                                    <p class="desc">
                                        {{ item.product.goodsName }}
                                    </p>
                                </span>
                            </span>
                            <span class="quantity-txt">
                                <em>{{ item.orderQuantity }}</em>
                                개
                            </span>
                            <span class="add-file">
                                <div class="upload-file-box">
                                    <!--active-->
                                    <ul
                                        style="height:96px;"
                                        class="upload-file-list"
                                        :class="{
                                            'is-file':
                                                item.uploadFileList.length > 0,
                                        }"
                                    >
                                        <template
                                            v-if="
                                                item.orderProductFileList
                                                    .fileList
                                            "
                                        >
                                            <li
                                                v-for="orderFile in item
                                                    .orderProductFileList
                                                    .fileList"
                                                :key="orderFile.fileOrder"
                                            >
                                                <label>
                                                    <span class="checkbox">
                                                        <input
                                                            type="checkbox"
                                                            :value="
                                                                orderFile.fileOrder
                                                            "
                                                            v-model="
                                                                item.checkedFile
                                                            "
                                                        />
                                                        <i></i>
                                                    </span>
                                                    <span class="txt">
                                                        {{ orderFile.fileName }}
                                                    </span>
                                                </label>
                                            </li>
                                        </template>
                                    </ul>
                                    <div class="btn-box">
                                        <div class="fine-file">
                                            <span class="btn-form-gray"
                                                ><span>찾기</span></span
                                            >
                                            <input
                                                type="file"
                                                ref="fileInput"
                                                accept="image/*"
                                                multiple
                                                @change="
                                                    uploadIptChange(
                                                        $event,
                                                        index
                                                    )
                                                "
                                            />
                                        </div>
                                        <button
                                            type="button"
                                            class="btn-form"
                                            @click="removeFile(index)"
                                        >
                                            <span>삭제</span>
                                        </button>
                                    </div>
                                </div>
                                <span class="textarea">
                                    <textarea
                                        v-model="
                                            item.orderProductFileList
                                                .productDescription
                                        "
                                    ></textarea>
                                </span>
                            </span>
                        </li>
                    </ul>
                </el-scrollbar>
                <h3 class="sub-title">COMMENT</h3>
                <div class="mt10">
                    <span class="textarea">
                        <textarea
                            style="height: 80px;"
                            placeholder="에이전시에서 오더 확인 후 연락 가능한 매장 연락처, 업무용 이메일, 배송 받으실 주소를 기재하시길 바랍니다."
                            v-model="orderComment"
                        />
                    </span>
                </div>
                <p class="form-desc-red">
                    * 업무상 필요한 정보 외의 개인 연락처 정보를 기재하지 않도록
                    주의하시기 바랍니다.
                </p>
                <div class="btn-area">
                    <button
                        type="button"
                        class="btn-s-black"
                        @click="uploadFiles"
                    >
                        <span>주문서 발송</span>
                    </button>
                </div>
            </div>
        </el-scrollbar>
    </el-dialog>
</template>

<script>
import { getUserIdFromCookie, getUserNickFromCookie } from '@/utils/cookies';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';

export default {
    data() {
        return {
            orderComment: '',
            orderList: [],
        };
    },
    computed: {
        userNickname() {
            return this.$store.state.nick || getUserNickFromCookie();
        },
        userIdVal() {
            return getUserIdFromCookie();
        },
    },
    props: ['visible', 'basketList', 'totalPrice'],
    created() {
        this.basketArray();
    },
    watch: {
        basketList() {
            this.basketArray();
        },
    },
    methods: {
        /* orderSave(){
     this.uploadFiles();
   this.basketList.forEach((el, index) =>{
       this.uploadFiles(index);
     })
     this.$emit('orderSave' , this.orderComment, this.orderList);
    },*/

        // basketList add file , comment
        basketArray() {
            this.basketList.forEach(el => {
                el.orderProductFileList = {};
                el.orderProductFileList.fileList = [];
                el.orderProductFileList.goodsSeq = el.goodsSeq;
                el.orderProductFileList.orderQuantity = el.orderQuantity;
                el.orderProductFileList.productDescription = null;
                el.uploadFileList = [];
                el.checkedFile = [];
            });
        },
        //file 업로드
        uploadIptChange(e, index) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter(item => {
                return this.basketList[
                    index
                ].orderProductFileList.fileList.every(el => {
                    return (
                        item.name !== el.fileName && item.size !== el.fileSize
                    );
                });
            });
            if (
                mergeArray.length +
                    this.basketList[index].uploadFileList.length >
                3
            ) {
                alert('3개 이상 등록 할 수 없습니다.');
                if (this.basketList[index].uploadFileList.length === 3) return;
                let maxNum = 3;
                if (this.basketList[index].uploadFileList.length > 0) {
                    maxNum = 3 - this.basketList[index].uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach(el => {
                this.basketList[index].orderProductFileList.fileList.push({
                    fileOrder: this.basketList[index].orderProductFileList
                        .fileList.length,
                    fileName: el.name,
                    fileSize: el.size,
                    fileContentType: el.type,
                    progress: 0,
                });
            });
            this.basketList[index].uploadFileList = this.basketList[
                index
            ].uploadFileList.concat(mergeArray);
        },
        async uploadFiles() {
            bus.$emit('pageLoading', true);
            await Promise.all(
                this.basketList.map(async basket => {
                    await Promise.all(
                        basket.uploadFileList.map(async el => {
                            try {
                                const formData = new FormData();
                                formData.append('uploadFile', el);
                                const config = {
                                    onUploadProgress: progressEvent => {
                                        const percentCompleted = Math.round(
                                            (progressEvent.loaded * 100) /
                                                progressEvent.total
                                        );
                                        basket.orderProductFileList.fileList.forEach(
                                            item => {
                                                if (
                                                    item.fileName === el.name &&
                                                    item.fileContentType ===
                                                        el.type &&
                                                    item.fileSize === el.size
                                                ) {
                                                    item.progress = percentCompleted;
                                                }
                                            }
                                        );
                                    },
                                };
                                formData.append('menuCode', 'order');
                                const response = await fileUpLoad(
                                    formData,
                                    config
                                );
                                //console.log(response);
                                if (response.existMsg) {
                                    alert(response.msg);
                                }
                                basket.orderProductFileList.fileList.forEach(
                                    (item, idx, array) => {
                                        if (
                                            item.fileName === el.name &&
                                            item.fileContentType === el.type &&
                                            item.fileSize === el.size
                                        ) {
                                            array[idx] = {
                                                progress: 100,
                                                fileOrder: idx,
                                                ...response.data.data,
                                            };
                                        }
                                    }
                                );
                            } catch (error) {
                                console.log(error);
                            }
                        })
                    );
                    this.orderList.push(basket.orderProductFileList);
                })
            );
            await console.log(this.orderList);
            await this.$emit('orderSave', this.orderComment, this.orderList);
        },
        removeFile(index) {
            this.basketList[index].checkedFile.forEach(a => {
                this.basketList[
                    index
                ].orderProductFileList.fileList = this.basketList[
                    index
                ].orderProductFileList.fileList.filter(b => b.fileOrder !== a);
            });
            this.basketList[index].checkedFile = [];
            this.fileOrderSet(index);
        },
        fileOrderSet(index) {
            this.basketList[index].uploadFileList = this.basketList[
                index
            ].uploadFileList.filter(a => {
                return this.basketList[
                    index
                ].orderProductFileList.fileList.some(b => {
                    return (
                        a.name === b.fileName &&
                        a.type === b.fileContentType &&
                        a.size === b.fileSize
                    );
                });
            });
            this.basketList[index].orderProductFileList.fileList.forEach(
                (el, index) => {
                    el.fileOrder = index;
                }
            );
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
.form-desc {
    margin-top: 5px;
    font-size: 10px;
}
.sub-title {
    line-height: 16px;
}
</style>
