<template>
    <el-dialog
        title=""
        class="modal-wrap order-sheet-modal"
        :visible="visible"
        :append-to-body="true"
        @close="$emit('update:visible', false)"
    >
        <el-scrollbar view-class="view-box" :native="false">
            <div class="el-dialog__inner">
                <div>
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
                        <template v-if="orderDetailData.orderRecipientList">
                            <li
                                class="form-row"
                                v-if="
                                    orderDetailData.orderRecipientList.length >
                                        0
                                "
                            >
                                <div class="form-column">
                                    <span class="label-title">수신자</span>
                                </div>
                                <div class="form-column">
                                    <div
                                        :class="{
                                            'check-select': true,
                                            active: userListOpen,
                                        }"
                                        ref="checkSelect"
                                    >
                                        <button
                                            type="button"
                                            class="txt txt-click user-item"
                                            @click="accordion"
                                            v-if="
                                                orderDetailData
                                                    .orderRecipientList.length >
                                                    1
                                            "
                                        >
                                            <span class="nickname">
                                                {{
                                                    orderDetailData
                                                        .orderRecipientList[0]
                                                        .nickname
                                                }}
                                            </span>
                                            <span class="mail">
                                                {{
                                                    orderDetailData
                                                        .orderRecipientList[0]
                                                        .userId
                                                }} </span
                                            >,
                                            <span class="ect">
                                                외{{
                                                    orderDetailData
                                                        .orderRecipientList
                                                        .length - 1
                                                }}명
                                            </span>
                                        </button>
                                        <span class="txt user-item" v-else>
                                            <span class="nickname">
                                                {{
                                                    orderDetailData
                                                        .orderRecipientList[0]
                                                        .nickname
                                                }}
                                            </span>
                                            <span class="mail">
                                                {{
                                                    orderDetailData
                                                        .orderRecipientList[0]
                                                        .userId
                                                }}
                                            </span>
                                        </span>
                                        <div class="bottom-fixed">
                                            <transition
                                                @enter="itemOpen"
                                                @leave="itemClose"
                                                :css="false"
                                            >
                                                <div
                                                    class="check-list-wrap"
                                                    v-if="userListOpen"
                                                >
                                                    <el-scrollbar
                                                        view-class="check-list-scroll"
                                                        :native="false"
                                                        v-if="userListOpen"
                                                    >
                                                        <ul
                                                            class="check-list"
                                                            ref="checkList"
                                                        >
                                                            <li
                                                                v-for="user in orderDetailData.orderRecipientList"
                                                                :key="
                                                                    user.userSeq
                                                                "
                                                            >
                                                                <div
                                                                    class="user-item"
                                                                >
                                                                    <span
                                                                        class="nickname"
                                                                    >
                                                                        {{
                                                                            user.nickname
                                                                        }}
                                                                    </span>
                                                                    <span
                                                                        class="mail"
                                                                    >
                                                                        {{
                                                                            user.userId
                                                                        }}
                                                                    </span>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </el-scrollbar>
                                                </div>
                                            </transition>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </template>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">총 예상 금액</span>
                            </div>
                            <div class="form-column">
                                <span class="form-val">
                                    <em>
                                        {{
                                            orderDetailData.totalAmount
                                                | formattedNumber('', '')
                                        }}
                                    </em>
                                    원
                                </span>
                            </div>
                        </li>
                        <li class="form-row">
                            <div class="form-column">
                                <span class="label-title">주문일시</span>
                            </div>
                            <div class="form-column">
                                <span class="form-val">
                                    {{ orderDetailData.registrationDt }}
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
                                v-for="item in orderDetailData.orderProductMapping"
                                :key="item.goodsSeq"
                            >
                                <span class="thumbnail">
                                    <img
                                        :src="
                                            item.product.imageFilePhysicalName
                                        "
                                        :alt="item.product.imageFileName"
                                    />
                                </span>
                                <div class="info-box-wrap">
                                    <span class="info-box">
                                        <strong class="title">
                                            {{ item.product.goodsName }}
                                        </strong>
                                        <p class="txt">
                                            {{ item.product.goodsDescription }}
                                        </p>
                                        <span class="desc-txt-box">
                                            <p class="desc">
                                                {{
                                                    item.product.agency
                                                        .agencyName
                                                }}
                                            </p>
                                        </span>
                                    </span>
                                    <span class="quantity-txt">
                                        <!-- 주문수량  -->
                                        <span
                                            ><em>{{ item.orderQuantity }}</em>
                                            개</span
                                        >
                                    </span>
                                </div>
                                <ul class="add-order-list">
                                    <li
                                        v-if="
                                            item.orderProductFileList.length > 0
                                        "
                                    >
                                        <span class="key">첨부파일</span>
                                        <span class="val">
                                            <ul class="file-list">
                                                <li
                                                    v-for="(fileItem,
                                                    fileIndex) in item.orderProductFileList"
                                                    :key="fileIndex"
                                                >
                                                    {{ fileItem.fileName }}
                                                </li>
                                            </ul>
                                        </span>
                                    </li>
                                    <li v-if="item.productDescription">
                                        <span class="key">요청사항</span>
                                        <span class="val">
                                            {{ item.productDescription }}
                                        </span>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </el-scrollbar>
                    <div v-if="orderDetailData.orderDescription">
                        <h3 class="sub-title">COMMENT</h3>
                        <div class="mt10">
                            <span class="textarea">
                                <textarea
                                    style="height: 60px;"
                                    readonly
                                    :value="orderDetailData.orderDescription"
                                />
                            </span>
                        </div>
                    </div>
                    <p class="form-desc-red">
                        * 업무상 필요한 정보 외의 개인 연락처 정보를 기재하지
                        않도록 주의하시기 바랍니다.
                    </p>
                </div>
            </div>
        </el-scrollbar>

        <!--        <div slot="footer" class="dialog-footer">-->
        <!--            <el-button @click="$emit('update:visible', false)" type="info" round>-->
        <!--                확인-->
        <!--            </el-button>-->
        <!--        </div>-->
    </el-dialog>
</template>

<script>
import {
    getRoleFromCookie,
    getUserIdFromCookie,
    getUserNickFromCookie,
} from '@/utils/cookies';
import { Cubic, gsap } from 'gsap/all';

export default {
    data() {
        return {
            userListOpen: false,
        };
    },
    props: ['visible', 'orderDetailData'],
    created() {
        //console.log(this.orderDetailData);
    },
    computed: {
        userNickname() {
            return this.$store.state.nick || getUserNickFromCookie();
        },
        userIdVal() {
            return getUserIdFromCookie();
        },
    },
    mounted() {},
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
    },
};
</script>
<style scoped>
.modal-wrap {
    display: flex;
    justify-content: center;
    align-items: center;
}
::v-deep .el-dialog {
    margin: 0 !important;
}
.modal-wrap .el-scrollbar__wrap {
    max-height: 80vh;
}
::v-deep .el-dialog,
::v-deep .el-dialog__body {
    box-sizing: border-box;
}
textarea[readonly] {
    background: #fff;
    color: #000;
}
.check-select .check-list-wrap {
    left: -20px;
    width: calc(100% + 30px);
}
.check-select .check-list {
    padding-left: 20px;
}
</style>
