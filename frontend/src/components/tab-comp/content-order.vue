<template>
    <div class="aside-order">
        <ul class="cart-item-list">
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
            <li class="cart-item">
                <div class="thumbnail">
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                </div>
                <div class="info-box">
                    <p class="title">듀브레</p>
                    <div class="quantity">
                        <button type="button" class="quantity-down">
                            <span>감소</span>
                        </button>
                        <span class="num">100</span>
                        <button type="button" class="quantity-up">
                            <span>증가</span>
                        </button>
                    </div>
                </div>
                <button type="button" class="del"><span>삭제</span></button>
            </li>
        </ul>
        <button type="button" class="btn-order">
            <span class="txt">ORDER</span>
        </button>
    </div>
</template>
<script>
export default {
    name: 'OderItem',
};
</script>
<style scoped>
.aside-order {
    margin-top: 15px;
}
.cart-item-list {
    box-sizing: border-box;
    height: 360px;
    padding: 8px 18px;
    background: #eee;
    overflow: auto;
}
.cart-item {
    position: relative;
    box-sizing: border-box;
    display: flex;
    padding: 10px 0;
    align-items: center;
}
.cart-item + .cart-item {
    border-top: 1px solid #e5e5e5;
}
.cart-item .thumbnail {
    width: 50px;
    height: 50px;
    margin-right: 10px;
}
.cart-item .thumbnail img {
    vertical-align: top;
}
.cart-item .del {
    position: absolute;
    top: 4px;
    right: -4px;
    display: block;
    width: 30px;
    height: 30px;
    background: url(../../assets/images/svg/icon-close-small.svg) no-repeat center;
}
.cart-item .del span {
    display: block;
    overflow: hidden;
    text-indent: -99999px;
}
.info-box .title {
    display: block;
    line-height: 18px;
    font-size: 12px;
    letter-spacing: -0.05px;
    color: #000;
}
.quantity {
    display: flex;
}
.quantity .num {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    font-size: 12px;
    font-weight: bold;
}
.quantity [class*='quantity-'] {
    display: block;
    width: 20px;
    height: 20px;
    background: url('../../assets/images/svg/icon-spinner-minus.svg') no-repeat center;
}
.quantity .quantity-up {
    background-image: url('../../assets/images/svg/icon-spinner-plus.svg');
}
.quantity [class^='quantity-'] span {
    display: block;
    overflow: hidden;
    text-indent: -9999px;
}
.btn-order {
    position: relative;
    display: flex;
    width: 100%;
    height: 40px;
    border-radius: 0;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    color: #fff;
    background: #000;
}
.btn-order .txt {
    position: relative;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
</style>