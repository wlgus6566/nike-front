<template>
    <div class="user">
        <div class="user-info">
            <span class="store-name">홍대 SKNRS</span>
            <div class="side">
                <button type="button" class="btn-alram active">
                    <span>알람</span>
                </button>
                <div class="alram-box">
                    <strong class="title">NEW</strong>
                    <div class="alram-item">
                        <p class="txt">
                            에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                        </p>
                    </div>
                    <button type="button" class="btn-close">
                        <span>닫기</span>
                    </button>
                </div>
                <div class="alram-box">
                    <strong class="title">NEW</strong>
                    <ul class="alram-list">
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                        <li class="alram-item active">
                            <p class="txt">
                                에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.
                            </p>
                            <span class="date">Now</span>
                        </li>
                    </ul>
                    <button type="button" class="btn-close">
                        <span>닫기</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="space-info">
            <p class="store">WINWIN OFFICE</p>
            <a href="mailto:nike@win-win.co.kr" class="mail">nike@win-win.co.kr</a>
        </div>
    </div>
</template>
<script>
export default {
    name: 'UserInfo.vue',
};
</script>
<style scoped>
.user-info {
    position: relative;
    height: 20px;
}
.user-info:after {
    content: '';
    clear: both;
    display: block;
    overflow: hidden;
}
.user-info .label {
    float: left;
    display: inline-flex;
    height: 16px;
    padding: 0 5px;
    border-radius: 2px;
    align-content: center;
    justify-content: center;
    background: #000;
    font-weight: bold;
    font-size: 10px;
    color: #fff;
    letter-spacing: 0;
}
.user-info .store-name {
    display: inline-flex;
    line-height: 16px;
    font-size: 14px;
    font-weight: bold;
    color: #000;
}
.user-info .side {
    position: absolute;
    top: -2px;
    right: 0;
    display: flex;
    width: 100%;
    justify-content: end;
}
.user-info .side [class^='btn-'] {
    display: block;
    width: 22px;
    height: 22px;
    background: no-repeat center;
}
.user-info .side [class^='btn-'] span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.user-info .side [class^='btn-']:first-child {
    margin-left: auto;
}
.user-info .side [class^='btn-'] + [class^='btn-'] {
    margin-right: -2px;
}
.user-info .side .btn-alram {
    background-image: url('../../assets/images/svg/icon-alarm-off.svg');
}
.user-info .side .btn-alram.active {
    background-image: url('../../assets/images/svg/icon-alarm-on.svg');
}
.alram-box {
    z-index: 2;
    position: absolute;
    top: 34px;
    left: 0;
    display: none;
    box-sizing: border-box;
    width: 100%;
    padding: 12px 0 0;
    border-radius: 2px;
    box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.1);
    background: #fff;
}
.alram-box .title {
    display: block;
    padding: 0 20px;
    font-size: 11px;
    line-height: 17px;
    font-weight: bold;
    color: #000;
}
.alram-item:first-child {
    margin-top: 10px;
    padding-top: 0;
}
.alram-item {
    padding: 12px 20px 12px 20px;
}
.alram-item .date {
    display: block;
    margin-top: 4px;
    font-size: 10px;
    line-height: 15px;
    color: #888;
}
.alram-item .txt {
    font-size: 11px;
    line-height: 17px;
    color: #333;
}
.alram-item.active {
    position: relative;
}
.alram-item.active:before {
    position: absolute;
    top: 17px;
    left: 20px;
    content: '';
    display: block;
    width: 4px;
    height: 4px;
    border-radius: 100%;
    background: #f36910;
}
.alram-item + .alram-item {
    border-top: 1px solid #eee;
}
.alram-list {
    max-height: 311px;
    margin-top: 10px;
    overflow: auto;
}
.alram-list .alram-item:first-child:before{
    top:5px;
}
.alram-list .alram-item {
    margin-top: 0;
    padding-left: 29px;
}
.alram-box .btn-close {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 30px;
    height: 30px;
    border-radius: 0;
    background-image: url('../../assets/images/svg/icon-close-small.svg') !important;
}
.alram-box.active {
    display: block;
}

.space-info {
    margin-top: 13px;
    font-weight: 300;
    font-size: 12px;
    color: #000;
}
.space-info .store {
    display: flex;
    height: 14px;
    align-content: center;
}
.space-info .mail {
    display: flex;
    height: 14px;
    margin-top: 5px;
    align-content: center;
    opacity: 0.6;
}
</style>
