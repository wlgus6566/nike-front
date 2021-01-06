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

                    <li class="form-row" v-if="userList.length > 0">
                        <div class="form-column">
                            <span class="label-title">수신자</span>
                        </div>
                        <div class="form-column">
                            <div class="check-select" ref="checkSelect">
                                <button
                                    type="button"
                                    class="txt txt-click"
                                    @click="accordion"
                                >
                                    <strong>선택</strong>
                                </button>
                                <el-scrollbar
                                    class="view-list-wrap"
                                    view-class="view-list-scroll"
                                    :native="false"
                                    v-if="
                                        this.orderList.recipientList.length > 0
                                    "
                                >
                                    <ul class="view-list">
                                        <li
                                            v-for="checkUser in this.orderList
                                                .recipientList"
                                            :key="checkUser.userSeq"
                                        >
                                            <span>
                                                {{ checkUser.nickname }}
                                            </span>
                                            <span>
                                                {{ checkUser.userId }}
                                            </span>
                                            <button
                                                type="button"
                                                ref="viewBtn"
                                                @click="userDelEvent(checkUser)"
                                            >
                                                삭제
                                            </button>
                                        </li>
                                    </ul>
                                </el-scrollbar>
                                <div class="bottom-fixed">
                                    <transition
                                        @enter="itemOpen"
                                        @leave="itemClose"
                                        :css="false"
                                    >
                                        <el-scrollbar
                                            class="check-list-wrap"
                                            view-class="check-list-scroll"
                                            :native="false"
                                            v-if="userListOpen"
                                        >
                                            <ul
                                                class="check-list"
                                                ref="checkList"
                                            >
                                                <li
                                                    v-for="user in userList"
                                                    :key="user.userSeq"
                                                >
                                                    <label class="user-item">
                                                        <span class="checkbox">
                                                            <input
                                                                type="checkbox"
                                                                :value="
                                                                    user.userSeq
                                                                "
                                                                v-model="
                                                                    cheched
                                                                "
                                                                @change="
                                                                    userCheckEvent(
                                                                        user
                                                                    )
                                                                "
                                                            />
                                                            <i></i>
                                                        </span>
                                                        <span class="nickname">
                                                            {{ user.nickname }}
                                                        </span>
                                                        <span class="mail">
                                                            {{ user.userId }}
                                                        </span>
                                                    </label>
                                                </li>
                                            </ul>
                                        </el-scrollbar>
                                    </transition>
                                </div>
                            </div>
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
                            v-for="(item,
                            index) in orderList.orderProductFileList"
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
                                <div class="order-upload-file-box">
                                    <!--active-->
                                    <ul class="order-upload-file-list">
                                        <template
                                            v-if="item.fileList.length > 0"
                                        >
                                            <li
                                                v-for="orderFile in item.fileList"
                                                :key="orderFile.fileOrder"
                                            >
                                                <span class="txt">
                                                    {{ orderFile.fileName }}
                                                    <button
                                                        type="button"
                                                        class="del"
                                                        @click="
                                                            removeFile(
                                                                index,
                                                                orderFile.fileOrder
                                                            )
                                                        "
                                                    >
                                                        <span>삭제</span>
                                                    </button>
                                                </span>
                                            </li>
                                        </template>
                                        <li v-else>
                                            <span class="txt"
                                                >첨부 파일은 최대 3장까지
                                                가능합니다.</span
                                            >
                                        </li>
                                    </ul>
                                    <div class="btn-box">
                                        <span class="btn-form-gray"
                                            ><span>찾기</span></span
                                        >
                                        <input
                                            type="file"
                                            ref="fileInput"
                                            accept="image/*"
                                            multiple
                                            @change="
                                                uploadIptChange($event, index)
                                            "
                                        />
                                    </div>
                                </div>
                                <span class="textarea">
                                    <textarea
                                        v-model="item.productDescription"
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
                            v-model="orderList.orderDescription"
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
                        @click="orderSave"
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
import { recipientList } from '@/api/product';
import { fileUpLoad } from '@/api/file';
import bus from '@/utils/bus';
import { getCustomerList } from '@/api/customer';
import { Cubic, gsap } from 'gsap/all';

export default {
    data() {
        return {
            userListOpen: false,
            cheched: [],
            orderList: {
                orderProductFileList: [],
                totalAmount: '',
                orderDescription: null,
                recipientList: [],
            },
            fileList: [],
            userList: [],
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
    components: {},
    props: ['visible', 'basketList', 'totalPrice'],
    created() {
        this.basketArray();
        this.userListInit();
    },
    watch: {
        basketList() {
            this.basketArray();
        },
    },
    methods: {
        htmlClick(e) {
            const target = e.target;
            if (
                !target.closest('.check-list') &&
                !target.closest('.txt') &&
                !target.closest('.view-list')
            ) {
                this.userListOpen = false;
            }
            /* if (
                target.closest('.check-select') !== this.$refs.checkSelect &&
                !target.closest('.txt')
            ) {
                this.userListOpen = false;
            }*/
        },
        compMount() {
            document
                .querySelector('html')
                .addEventListener('click', this.htmlClick);
        },
        itemOpen(el, done) {
            gsap.set(el, {
                height: 'auto',
            });

            gsap.from(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: function() {
                    el.style.height = 'auto';
                    done();
                },
            });
        },
        itemClose(el, done) {
            gsap.to(el, 0.3, {
                height: 0,
                ease: Cubic.easeInOut,
                onComplete: done,
            });
        },
        accordion() {
            this.userListOpen = !this.userListOpen;
            this.compMount();
        },
        userDelEvent(user) {
            const checkIndex = this.cheched.findIndex(check => {
                return check === user.userSeq;
            });
            this.cheched.splice(checkIndex, 1);
            const userIndex = this.orderList.recipientList.findIndex(el => {
                return el.userSeq === user.userSeq;
            });
            this.orderList.recipientList.splice(userIndex, 1);
        },
        userCheckEvent(user) {
            const index = this.cheched.findIndex(check => {
                return check === user.userSeq;
            });
            if (index !== -1) {
                this.orderList.recipientList.push(user);
            } else {
                const userIndex = this.orderList.recipientList.findIndex(el => {
                    return el.userSeq === user.userSeq;
                });
                this.orderList.recipientList.splice(userIndex, 1);
            }
        },
        async userListInit() {
            try {
                const {
                    data: { data: response },
                } = await recipientList();
                if (response.depthCheckYn === 'Y') {
                    this.userList = response.userList;
                }
                /*       this.userList = [
                    {
                        nickname: '테스트계정',
                        userId:
                            ' test@nike.co.krtest@nike.co.krtest@nike.co.krtest@nike.co.krtest@nike.co.krtest@nike.co.krtest@nike.co.kr',
                        userSeq: 0,
                    },
                    {
                        nickname: '테스트계정1',
                        userId: 'test@nike.co.kr1',
                        userSeq: 1,
                    },
                    {
                        nickname: '테스트계정2',
                        userId: 'test@nike.co.kr2',
                        userSeq: 2,
                    },
                    {
                        nickname: '테스트계정3',
                        userId: 'test@nike.co.kr3',
                        userSeq: 3,
                    },
                    {
                        nickname: '테스트계정4',
                        userId: 'test@nike.co.kr4',
                        userSeq: 4,
                    },
                    {
                        nickname: '테스트계정5',
                        userId: 'test@nike.co.kr5',
                        userSeq: 5,
                    },
                    {
                        nickname: '테스트계정6',
                        userId: 'test@nike.co.kr6',
                        userSeq: 6,
                    },
                    {
                        nickname: '테스트계정7',
                        userId: 'test@nike.co.kr7',
                        userSeq: 7,
                    },
                    {
                        nickname: '테스트계정8',
                        userId: 'test@nike.co.kr8',
                        userSeq: 8,
                    },
                    {
                        nickname: '테스트계정9',
                        userId: 'test@nike.co.kr9',
                        userSeq: 9,
                    },
                    {
                        nickname: '테스트계정10',
                        userId: 'test@nike.co.kr10',
                        userSeq: 10,
                    },
                    {
                        nickname: '테스트계정11',
                        userId: 'test@nike.co.kr11',
                        userSeq: 11,
                    },
                    {
                        nickname: '테스트계정12',
                        userId: 'test@nike.co.kr12',
                        userSeq: 12,
                    },
                    {
                        nickname: '테스트계정13',
                        userId: 'test@nike.co.kr13',
                        userSeq: 13,
                    },
                    {
                        nickname: '테스트계정14',
                        userId: 'test@nike.co.kr14',
                        userSeq: 14,
                    },
                ];*/
            } catch (error) {
                console.error(error);
            }
        },
        orderSave() {
            console.log(this.orderList);
            //this.$emit('orderSave', this.orderComment);
        },

        // basketList add file , comment
        basketArray() {
            this.orderList.totalAmount = this.totalPrice;
            this.basketList.forEach((el, index) => {
                this.orderList.orderProductFileList.push({
                    fileList: [],
                    goodsSeq: el.goodsSeq,
                    orderQuantity: el.orderQuantity,
                    productDescription: null,
                    uploadFileList: [],
                    product: el.product,
                });
            });
        },
        //file 업로드
        uploadIptChange(e, index) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter(item => {
                return this.orderList.orderProductFileList[
                    index
                ].fileList.every(el => {
                    return (
                        item.name !== el.fileName && item.size !== el.fileSize
                    );
                });
            });
            if (
                mergeArray.length +
                    this.orderList.orderProductFileList[index].uploadFileList
                        .length >
                3
            ) {
                alert('3개 이상 등록 할 수 없습니다.');
                if (
                    this.orderList.orderProductFileList[index].uploadFileList
                        .length === 3
                )
                    return;
                let maxNum = 3;
                if (
                    this.orderList.orderProductFileList[index].uploadFileList
                        .length > 0
                ) {
                    maxNum =
                        3 -
                        this.orderList.orderProductFileList[index]
                            .uploadFileList.length;
                }
                mergeArray.splice(maxNum, 9999);
            }

            mergeArray.forEach(el => {
                this.orderList.orderProductFileList[index].fileList.push({
                    fileOrder: this.orderList.orderProductFileList[index]
                        .fileList.length,
                    fileName: el.name,
                    fileSize: el.size,
                    fileContentType: el.type,
                    progress: 0,
                });
            });
            this.orderList.orderProductFileList[
                index
            ].uploadFileList = this.orderList.orderProductFileList[
                index
            ].uploadFileList.concat(mergeArray); //
            this.fileList[index] = this.orderList.orderProductFileList[
                index
            ].fileList;
        },

        async uploadFiles() {
            bus.$emit('pageLoading', true);
            await Promise.all(
                this.orderList.orderProductFileList.map(async basket => {
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
                                        basket.fileList.forEach(item => {
                                            if (
                                                item.fileName === el.name &&
                                                item.fileContentType ===
                                                    el.type &&
                                                item.fileSize === el.size
                                            ) {
                                                item.progress = percentCompleted;
                                            }
                                        });
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
                                basket.fileList.forEach((item, idx, array) => {
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
                                });
                            } catch (error) {
                                console.log(error);
                            }
                        })
                    );
                })
            );
            await this.$emit('orderSave', this.orderList);
        },
        removeFile(index, fileOrder) {
            this.orderList.orderProductFileList[
                index
            ].fileList = this.orderList.orderProductFileList[
                index
            ].fileList.filter(b => b.fileOrder !== fileOrder);
            this.fileOrderSet(index);
        },
        fileOrderSet(index) {
            this.orderList.orderProductFileList[
                index
            ].uploadFileList = this.orderList.orderProductFileList[
                index
            ].uploadFileList.filter(a => {
                return this.orderList.orderProductFileList[index].fileList.some(
                    b => {
                        return (
                            a.name === b.fileName &&
                            a.type === b.fileContentType &&
                            a.size === b.fileSize
                        );
                    }
                );
            });
            this.orderList.orderProductFileList[index].fileList.forEach(
                (el, index) => {
                    el.fileOrder = index;
                }
            );
        },
    },
};
</script>
<style scoped>
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

.drag-item {
    opacity: 1 !important;
    border: none !important;
    background: transparent !important;
}
.drag-item .list {
    padding: 0;
}
.drag-item .checkbox {
    display: none;
}
.drag-item .thumbnail {
    border: 1px solid #ddd;
}
.drag-item .info-box {
    display: none;
}
.drag-item .btn-box {
    display: none;
}
.drag-item ul {
    display: none !important;
}
</style>
